package com.pragma.traceability.domain.api;

import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.adapters.driving.http.dto.response.TimeOrdersEmployeeResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILogsServicePort {
    void createLogs(LogsOrder logsOrder);

    List<LogsOrderResponseDto> getLogsOrderByClient();

    Long getTimeOrder(Long idOrder);

    List<TimeOrdersEmployeeResponseDto> getTimeEmployeeRanked(Long idEmployee);
}
