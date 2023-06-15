package com.pragma.traceability.adapters.driven.mongodb.mapper;

import com.pragma.traceability.adapters.driven.mongodb.documents.OrderStatusDocument;
import com.pragma.traceability.domain.models.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderStatusEntityMapper {
    OrderStatusDocument toOrderStatusDocument(OrderStatus orderStatus);
}
