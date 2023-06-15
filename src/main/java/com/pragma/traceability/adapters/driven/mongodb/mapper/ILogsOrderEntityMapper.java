package com.pragma.traceability.adapters.driven.mongodb.mapper;

import com.pragma.traceability.adapters.driven.mongodb.documents.LogsOrderDocument;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILogsOrderEntityMapper {
    LogsOrderDocument toLogsOrderEntity(LogsOrder logsOrder);
    LogsOrderResponseDto toLogsOrderResponseDto(LogsOrderDocument logsOrderDocument);

    List<LogsOrderResponseDto> toLogsOrderResponseDtos(List<LogsOrderDocument> logsOrderDocuments);

    LogsOrder toLogsOrder(LogsOrderDocument logsOrderDocument);
}
