package com.empik.githubservice.domain.event;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static org.springframework.aop.support.AopUtils.getTargetClass;
import static org.springframework.core.GenericTypeResolver.resolveTypeArgument;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DomainEventPublisherFactory {

    @Bean
    <T extends DomainEvent> DomainEventPublisher<T> domainEventPublisher(List<DomainEventHandler<T>> domainEventHandlers) {
        Map<Class<T>, DomainEventHandler<T>> domainEventHandlersMap = domainEventHandlers.stream()
            .collect(toUnmodifiableMap(this::resolveDomainEventType, identity()));
        return new DomainEventPublisher<>(newSingleThreadExecutor(), domainEventHandlersMap);
    }

    @SuppressWarnings("unchecked")
    private <T extends DomainEvent> Class<T> resolveDomainEventType(DomainEventHandler<T> handler) {
        return (Class<T>) resolveTypeArgument(getTargetClass(handler), DomainEventHandler.class);
    }
}
