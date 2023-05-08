package com.empik.githubservice.domain.event;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;

public class DomainEventPublisher<T extends DomainEvent> {

    private static final Logger LOG = getLogger(lookup().lookupClass());

    private final ExecutorService executorService;

    private final Map<Class<T>, DomainEventHandler<T>> domainEventHandlersMap;

    DomainEventPublisher(ExecutorService executorService, Map<Class<T>, DomainEventHandler<T>> domainEventHandlersMap) {
        this.executorService = executorService;
        this.domainEventHandlersMap = domainEventHandlersMap;
    }

    public void publish(T domainEvent) {
        Optional.ofNullable(domainEventHandlersMap.get(domainEvent.getClass()))
            .ifPresent(domainEventHandler -> publishEvent(domainEvent, domainEventHandler));
    }

    private void publishEvent(T domainEvent, DomainEventHandler<T> domainEventHandler) {
        executorService.submit(() -> handleEvent(domainEvent, domainEventHandler));
        LOG.info("domain event {} published", domainEvent);
    }

    private void handleEvent(T domainEvent, DomainEventHandler<T> domainEventHandler) {
        domainEventHandler.handle(domainEvent);
        LOG.info("domain event {} handled", domainEvent);
    }

}
