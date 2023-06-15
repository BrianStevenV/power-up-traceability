package com.pragma.traceability.domain.spi;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;

import java.util.List;

public interface ILogsPersistencePort {
    void createLogs(LogsOrder logsOrder);

    List<LogsOrderResponseDto> getLogsOrderByClient(Long idClient);
}
