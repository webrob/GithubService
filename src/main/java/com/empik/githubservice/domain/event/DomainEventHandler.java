package com.empik.githubservice.domain.event;

public interface DomainEventHandler<T extends DomainEvent> {

    void handle(T event);
}
