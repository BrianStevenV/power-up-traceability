package com.pragma.traceability.domain.models;

import java.time.LocalDateTime;

public class TimeOrdersEmployee {
    private Long idOrder;
    private LocalDateTime dateStatePending;
    private LocalDateTime dateStateDelivered;

    public TimeOrdersEmployee(Long idOrder, LocalDateTime dateStatePending, LocalDateTime dateStateDelivered) {
        this.idOrder = idOrder;
        this.dateStatePending = dateStatePending;
        this.dateStateDelivered = dateStateDelivered;
    }

    public TimeOrdersEmployee() {
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDateTime getDateStatePending() {
        return dateStatePending;
    }

    public void setDateStatePending(LocalDateTime dateStatePending) {
        this.dateStatePending = dateStatePending;
    }

    public LocalDateTime getDateStateDelivered() {
        return dateStateDelivered;
    }

    public void setDateStateDelivered(LocalDateTime dateStateDelivered) {
        this.dateStateDelivered = dateStateDelivered;
    }
}
