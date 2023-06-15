package com.pragma.traceability.adapters.driven.mongodb.repository;

import com.pragma.traceability.adapters.driven.mongodb.documents.LogsOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsOrderRepository extends MongoRepository<LogsOrderDocument, Long> {
    List<LogsOrderDocument> findByIdClient(Long idClient);
}
