package com.pragma.traceability.adapters.driving.http.handler.impl;


import com.pragma.traceability.adapters.driving.http.dto.request.LogsOrderRequestDto;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.adapters.driving.http.handler.ILogsHandler;
import com.pragma.traceability.adapters.driving.http.mapper.ILogsRequestMapper;
import com.pragma.traceability.domain.api.ILogsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsHandlerImp implements ILogsHandler {
    private final ILogsServicePort logsServicePort;

    private final ILogsRequestMapper logsRequestMapper;
    @Override
    public void createLogs(LogsOrderRequestDto logsOrderRequestDto) {
        logsServicePort.createLogs(logsRequestMapper.toLogsOrder(logsOrderRequestDto));
    }

    @Override
    public List<LogsOrderResponseDto> getLogsOrderByClient() {
        return logsServicePort.getLogsOrderByClient();
    }
}
