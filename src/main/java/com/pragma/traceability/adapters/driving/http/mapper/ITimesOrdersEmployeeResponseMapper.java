package com.pragma.traceability.adapters.driving.http.mapper;

import com.pragma.traceability.adapters.driving.http.dto.response.TimeOrdersEmployeeResponseDto;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITimesOrdersEmployeeResponseMapper {
    TimeOrdersEmployee toTimeOrdersEmployee(TimeOrdersEmployeeResponseDto timeOrdersEmployeeResponseDto);
}
