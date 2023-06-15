package com.pragma.traceability.adapters.driving.http.dto.request;

import com.pragma.traceability.adapters.driving.http.dto.OrderStatusRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogsOrderRequestDto {
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private OrderStatusRequestDto stateBefore;
    private OrderStatusRequestDto stateNew;
    private Long idEmployee;
    private String emailEmployee;

}
