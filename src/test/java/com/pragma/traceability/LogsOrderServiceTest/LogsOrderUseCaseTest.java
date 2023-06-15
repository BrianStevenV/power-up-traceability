package com.pragma.traceability.LogsOrderServiceTest;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.adapters.driving.http.dto.response.TimeOrdersEmployeeResponseDto;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.models.OrderStatus;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;
import com.pragma.traceability.domain.usecase.LogsOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class LogsOrderUseCaseTest {
    @Mock
    private ILogsPersistencePort logsPersistencePort;
    private LogsOrderUseCase logsOrderUseCase;
    @Mock
    private IAuthenticationUserInfoServicePort authenticationUserInfoServicePort;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        logsOrderUseCase = new LogsOrderUseCase(logsPersistencePort, authenticationUserInfoServicePort);
    }

    @Test
    @DisplayName("Test: createLogs - Success")
    public void createLogsSuccessfulTest() {
        // Arrange

        LogsOrder logsOrder = new LogsOrder();
        logsOrder.setIdOrder(123L);
        logsOrder.setIdClient(456L);
        logsOrder.setEmailClient("cliente@example.com");

        // Act

        logsOrderUseCase.createLogs(logsOrder);

        // Assert

        verify(logsPersistencePort, times(1)).createLogs(logsOrder);
        verify(logsPersistencePort, times(1)).createLogs(any(LogsOrder.class));
        assertNotNull(logsOrder.getDate());
        assertEquals(LocalDateTime.now().getDayOfYear(), logsOrder.getDate().getDayOfYear());
    }

    @Test
    @DisplayName("Test: getLogsOrderByClient - Success")
    public void getLogsOrderByClientSuccessTest() {
        // Arrange

        Long idClient = 123L;
        when(authenticationUserInfoServicePort.getIdUserFromToken()).thenReturn(idClient);

        LogsOrderResponseDto logsOrder1 = new LogsOrderResponseDto();
        LogsOrderResponseDto logsOrder2 = new LogsOrderResponseDto();
        List<LogsOrderResponseDto> expectedLogs = Arrays.asList(logsOrder1, logsOrder2);
        when(logsPersistencePort.getLogsOrderByClient(idClient)).thenReturn(expectedLogs);

        // Act

        List<LogsOrderResponseDto> result = logsOrderUseCase.getLogsOrderByClient();

        // Assert

        assertEquals(expectedLogs, result);
        verify(authenticationUserInfoServicePort, times(1)).getIdUserFromToken();
        verify(logsPersistencePort, times(1)).getLogsOrderByClient(idClient);
    }

    @Test
    @DisplayName("Test: getTimeOrder - Success")
    public void getTimeOrderSuccessTest() {
        // Arrange
        Long idOrder = 123L;
        LocalDateTime dateOrderPending = LocalDateTime.of(2023, 6, 1, 10, 0, 0);
        LocalDateTime dateOrderDelivered = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        Duration expectedDuration = Duration.ofMinutes(120);

        LogsOrder pendingOrder = Mockito.mock(LogsOrder.class);
        LogsOrder deliveredOrder = Mockito.mock(LogsOrder.class);

        when(logsPersistencePort.getLogOrderByState(idOrder, OrderStatus.PENDING)).thenReturn(pendingOrder);
        when(logsPersistencePort.getLogOrderByState(idOrder, OrderStatus.DELIVERED)).thenReturn(deliveredOrder);
        when(pendingOrder.getDate()).thenReturn(dateOrderPending);
        when(deliveredOrder.getDate()).thenReturn(dateOrderDelivered);

        // Act
        Long result = logsOrderUseCase.getTimeOrder(idOrder);

        // Assert
        assertEquals(expectedDuration.toMinutes(), result);
    }

    @Test
    @DisplayName("Test: getTimeEmployeeRanked - Success")
    public void getTimeEmployeeRankedSuccessTest() {
        // Arrange

        Long idEmployee = 123L;
        List<TimeOrdersEmployee> listToConvert = new ArrayList<>();
        LocalDateTime dateOrderPending1 = LocalDateTime.of(2023, 6, 1, 10, 0, 0);
        LocalDateTime dateOrderDelivered1 = LocalDateTime.of(2023, 6, 1, 12, 0, 0);
        LocalDateTime dateOrderPending2 = LocalDateTime.of(2023, 6, 2, 9, 0, 0);
        LocalDateTime dateOrderDelivered2 = LocalDateTime.of(2023, 6, 2, 11, 0, 0);

        listToConvert.add(new TimeOrdersEmployee(1L, dateOrderPending1, dateOrderDelivered1));
        listToConvert.add(new TimeOrdersEmployee(2L, dateOrderPending2, dateOrderDelivered2));

        List<TimeOrdersEmployeeResponseDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(new TimeOrdersEmployeeResponseDto(2L, Duration.between(dateOrderPending2, dateOrderDelivered2).toMinutes()));
        expectedResponse.add(new TimeOrdersEmployeeResponseDto(1L, Duration.between(dateOrderPending1, dateOrderDelivered1).toMinutes()));

        when(logsPersistencePort.getTimeEmployeeRanked(idEmployee)).thenReturn(listToConvert);

        // Act

        List<TimeOrdersEmployeeResponseDto> result = logsOrderUseCase.getTimeEmployeeRanked(idEmployee);

        // Assert

        assertEquals(expectedResponse.size(), result.size());
        assertTrue(expectedResponse.stream()
                .map(TimeOrdersEmployeeResponseDto::getIdOrder)
                .allMatch(id -> result.stream()
                        .map(TimeOrdersEmployeeResponseDto::getIdOrder)
                        .anyMatch(id::equals)));
        assertTrue(expectedResponse.stream()
                .map(TimeOrdersEmployeeResponseDto::getTimes)
                .allMatch(time -> result.stream()
                        .map(TimeOrdersEmployeeResponseDto::getTimes)
                        .anyMatch(time::equals)));
    }







}
