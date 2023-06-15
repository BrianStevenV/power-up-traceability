package com.pragma.traceability.LogsOrderServiceTest;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;
import com.pragma.traceability.domain.usecase.LogsOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

}
