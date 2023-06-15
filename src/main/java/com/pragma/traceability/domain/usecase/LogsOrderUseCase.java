package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.traceability.domain.api.ILogsServicePort;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;

import java.time.LocalDateTime;
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
}
