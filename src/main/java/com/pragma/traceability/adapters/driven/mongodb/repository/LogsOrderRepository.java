package com.pragma.traceability.adapters.driven.mongodb.repository;

import com.pragma.traceability.adapters.driven.mongodb.documents.LogsOrderDocument;
import com.pragma.traceability.adapters.driven.mongodb.documents.OrderStatusDocument;
import com.pragma.traceability.domain.models.TimeOrdersEmployee;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsOrderRepository extends MongoRepository<LogsOrderDocument, Long> {
    List<LogsOrderDocument> findByIdClient(Long idClient);
    @Query("{ 'idOrder': ?0, $or: [{ 'stateBefore': ?1 }, { 'stateNew': ?1 }] }")
    LogsOrderDocument findByIdOrderAndState(Long idOrder, OrderStatusDocument orderStatus);
    @Aggregation(pipeline = {
            "{$match: {idEmployee: ?0}}",
            "{$lookup: {from: 'traceability', let: {orderId: '$idOrder', empId: '$idEmployee'}, pipeline: [{$match: {$expr: {$and: [{$eq: ['$idOrder', '$$orderId']}, {$eq: ['$idEmployee', '$$empId']}]}}}, {$project: {idOrder: 1, stateBefore: 1, stateNew: 1, date: 1}}, {$match: {$or: [{stateBefore: {$eq: 'PENDING'}}, {stateNew: {$eq: 'DELIVERED'}}]}}], as: 'matchedDocuments'}}",
            "{$unwind: '$matchedDocuments'}",
            "{$group: {_id: '$idOrder', dateStatePending: {$min: {$cond: [{$eq: ['$matchedDocuments.stateBefore', 'PENDING']}, '$matchedDocuments.date', null]}}, dateStateDelivered: {$min: {$cond: [{$eq: ['$matchedDocuments.stateNew', 'DELIVERED']}, '$matchedDocuments.date', null]}}}}",
            "{$match: {$and: [{dateStatePending: {$ne: null}}, {dateStateDelivered: {$ne: null}}]}}",
            "{$project: {_id: 0, idOrder: '$_id', dateStatePending: 1, dateStateDelivered: 1}}"
    })
    List<TimeOrdersEmployee> findByIdEmployee(@Param("idEmployee") Long idEmployee);



}
