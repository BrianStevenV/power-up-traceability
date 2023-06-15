package com.pragma.traceability.configuration;

import com.pragma.traceability.adapters.driven.mongodb.adapter.LogsOrderMongodbAdapter;
import com.pragma.traceability.adapters.driven.mongodb.mapper.ILogsOrderEntityMapper;
import com.pragma.traceability.adapters.driven.mongodb.repository.LogsOrderRepository;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import com.pragma.traceability.domain.api.ILogsServicePort;
import com.pragma.traceability.domain.spi.ILogsPersistencePort;
import com.pragma.traceability.domain.usecase.LogsOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final LogsOrderRepository logsOrderRepository;
    private final ILogsOrderEntityMapper logsOrderEntityMapper;

    // LogsOrderUseCase

    private final IAuthenticationUserInfoServicePort authenticationUserInfoServicePort;
    @Bean
    public ILogsServicePort logsServicePort(){
        return new LogsOrderUseCase(logsPersistencePort(), authenticationUserInfoServicePort);
    }

    @Bean
    public ILogsPersistencePort logsPersistencePort(){
        return new LogsOrderMongodbAdapter(logsOrderRepository, logsOrderEntityMapper);
    }
}
