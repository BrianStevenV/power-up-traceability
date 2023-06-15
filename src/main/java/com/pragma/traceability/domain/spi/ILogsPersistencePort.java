package com.pragma.traceability.domain.spi;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.models.OrderStatus;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;

import java.util.List;

public interface ILogsPersistencePort {
    void createLogs(LogsOrder logsOrder);

    List<LogsOrderResponseDto> getLogsOrderByClient(Long idClient);

    LogsOrder getLogOrderByState(Long idOrder, OrderStatus state);
    List<TimeOrdersEmployee> getTimeEmployeeRanked(Long idEmployee);
}
