package com.pragma.traceability.adapters.driving.http.dto.response;

import com.pragma.traceability.adapters.driving.http.dto.OrderStatusRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogsOrderResponseDto {
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private OrderStatusRequestDto stateBefore;
    private OrderStatusRequestDto stateNew;
    private Long idEmployee;
    private String emailEmployee;
}
