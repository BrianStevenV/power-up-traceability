package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.adapters.driving.http.dto.response.TimeOrdersEmployeeResponseDto;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.traceability.domain.api.ILogsServicePort;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.models.OrderStatus;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LogsOrderUseCase implements ILogsServicePort {
    private final ILogsPersistencePort logsPersistencePort;

    private final IAuthenticationUserInfoServicePort authenticationUserInfoServicePort;

    public LogsOrderUseCase(ILogsPersistencePort logsPersistencePort, IAuthenticationUserInfoServicePort authenticationUserInfoServicePort){
        this.logsPersistencePort = logsPersistencePort;
        this.authenticationUserInfoServicePort = authenticationUserInfoServicePort;
    }

    @Override
    public void createLogs(LogsOrder logsOrder) {
        logsOrder.setDate(LocalDateTime.now());
        logsPersistencePort.createLogs(logsOrder);
    }

    @Override
    public List<LogsOrderResponseDto> getLogsOrderByClient() {
        Long idClient = authenticationUserInfoServicePort.getIdUserFromToken();
        return logsPersistencePort.getLogsOrderByClient(idClient);
    }

    @Override
    public Long getTimeOrder(Long idOrder) {
        LocalDateTime dateOrderPending = logsPersistencePort.getLogOrderByState(idOrder, OrderStatus.PENDING).getDate();
        LocalDateTime dateOrderDelivered = logsPersistencePort.getLogOrderByState(idOrder,OrderStatus.DELIVERED).getDate();
        Duration durationBetweenDates = Duration.between(dateOrderPending, dateOrderDelivered);
        return durationBetweenDates.toMinutes();
    }

    @Override
    public List<TimeOrdersEmployeeResponseDto> getTimeEmployeeRanked(Long idEmployee) {
        List<TimeOrdersEmployee> listToConvert = logsPersistencePort.getTimeEmployeeRanked(idEmployee);
        List<TimeOrdersEmployeeResponseDto> response = new ArrayList<>();

        listToConvert.forEach(dto -> {
            LocalDateTime dateOrderPending = dto.getDateStatePending();
            LocalDateTime dateOrderDelivered = dto.getDateStateDelivered();

            Duration durationBetweenDates = Duration.between(dateOrderPending, dateOrderDelivered);
            long durationInMinutes = durationBetweenDates.toMinutes();

            TimeOrdersEmployeeResponseDto responseDto = new TimeOrdersEmployeeResponseDto(dto.getIdOrder(), durationInMinutes);
            response.add(responseDto);
        });

        Collections.sort(response, Comparator.comparingLong(TimeOrdersEmployeeResponseDto::getTimes).reversed());
        return response;

    }
}
