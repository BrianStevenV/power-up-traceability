package com.pragma.traceability.adapters.driving.http.handler;

import com.pragma.traceability.adapters.driving.http.dto.request.LogsOrderRequestDto;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;

import java.util.List;

public interface ILogsHandler {
    void createLogs(LogsOrderRequestDto logsOrderRequestDto);

    List<LogsOrderResponseDto> getLogsOrderByClient();
}
