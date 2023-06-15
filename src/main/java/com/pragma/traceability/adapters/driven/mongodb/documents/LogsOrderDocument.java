package com.pragma.traceability.adapters.driven.mongodb.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "traceability")
public class LogsOrderDocument {
    @Id
    private ObjectId id;
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private LocalDateTime date;
    private OrderStatusDocument stateBefore;
    private OrderStatusDocument stateNew;
    private Long idEmployee;
    private String emailEmployee;
}
