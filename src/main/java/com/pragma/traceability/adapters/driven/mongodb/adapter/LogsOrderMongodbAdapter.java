package com.pragma.traceability.adapters.driven.mongodb.adapter;

import com.pragma.traceability.adapters.driven.mongodb.documents.LogsOrderDocument;
import com.pragma.traceability.adapters.driven.mongodb.mapper.ILogsOrderEntityMapper;
import com.pragma.traceability.adapters.driven.mongodb.mapper.IOrderStatusEntityMapper;
import com.pragma.traceability.adapters.driven.mongodb.repository.LogsOrderRepository;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.models.OrderStatus;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LogsOrderMongodbAdapter implements ILogsPersistencePort {
    private final LogsOrderRepository logsOrderRepository;
    private final ILogsOrderEntityMapper logsOrderEntityMapper;
    private final IOrderStatusEntityMapper orderStatusEntityMapper;
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

    @Override
    public LogsOrder getLogOrderByState(Long idOrder, OrderStatus state) {
        return logsOrderEntityMapper.toLogsOrder(logsOrderRepository.findByIdOrderAndState(idOrder, orderStatusEntityMapper.toOrderStatusDocument(state)));
    }

    @Override
    public List<TimeOrdersEmployee> getTimeEmployeeRanked(Long idEmployee) {
        return logsOrderRepository.findByIdEmployee(idEmployee);

    }
}
