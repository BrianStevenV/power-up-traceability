package com.pragma.traceability.adapters.driving.http.mapper;

import com.pragma.traceability.adapters.driving.http.dto.OrderStatusRequestDto;
import com.pragma.traceability.adapters.driving.http.dto.request.LogsOrderRequestDto;
import com.pragma.traceability.adapters.driving.http.dto.response.LogsOrderResponseDto;
import com.pragma.traceability.domain.models.LogsOrder;
import com.pragma.traceability.domain.models.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILogsRequestMapper {
    LogsOrder toLogsOrder(LogsOrderRequestDto logsOrderRequestDto);
    LogsOrderResponseDto toLogsOrderResponseDto(LogsOrder logsOrder);

    OrderStatus toOrderStatus(OrderStatusRequestDto orderStatusRequestDto);

    OrderStatusRequestDto toOrderStatusRequestDto(OrderStatus orderStatus);
}
