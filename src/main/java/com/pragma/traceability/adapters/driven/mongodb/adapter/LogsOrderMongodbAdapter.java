package com.pragma.traceability.adapters.driven.mongodb.adapter;

import com.pragma.traceability.adapters.driven.mongodb.documents.LogsOrderDocument;
import com.pragma.traceability.adapters.driven.mongodb.mapper.ILogsOrderEntityMapper;
import com.pragma.traceability.adapters.driven.mongodb.repository.LogsOrderRepository;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LogsOrderMongodbAdapter implements ILogsPersistencePort {
    private final LogsOrderRepository logsOrderRepository;
    private final ILogsOrderEntityMapper logsOrderEntityMapper;
    @Override
    public void createLogs(LogsOrder logsOrder) {
        logsOrderRepository.save(logsOrderEntityMapper.toLogsOrderEntity(logsOrder));

    }


    @Override
    public List<LogsOrderResponseDto> getLogsOrderByClient(Long idClient) {
        List<LogsOrderDocument> documents = logsOrderRepository.findByIdClient(idClient);
        List<LogsOrderResponseDto> dtos = logsOrderEntityMapper.toLogsOrderResponseDtos(documents);
        return dtos;
    }
}
