package com.pragma.traceability.domain.models;

import java.time.LocalDateTime;

public class LogsOrder {
    private Long idOrder;
    private Long idClient;
    private String emailClient;
    private LocalDateTime date;
    private OrderStatus stateBefore;
    private OrderStatus stateNew;
    private Long idEmployee;
    private String emailEmployee;

    public LogsOrder(Long idOrder, Long idClient, String emailClient, LocalDateTime date, OrderStatus stateBefore, OrderStatus stateNew, Long idEmployee, String emailEmployee) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.emailClient = emailClient;
        this.date = date;
        this.stateBefore = stateBefore;
        this.stateNew = stateNew;
        this.idEmployee = idEmployee;
        this.emailEmployee = emailEmployee;
    }

    public LogsOrder() {
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStateBefore() {
        return stateBefore;
    }

    public void setStateBefore(OrderStatus stateBefore) {
        this.stateBefore = stateBefore;
    }

    public OrderStatus getStateNew() {
        return stateNew;
    }

    public void setStateNew(OrderStatus stateNew) {
        this.stateNew = stateNew;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }
}
