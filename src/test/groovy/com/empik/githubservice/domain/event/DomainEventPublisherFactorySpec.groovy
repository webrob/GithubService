package com.empik.githubservice.domain.event

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class DomainEventPublisherFactorySpec extends Specification {

    @Shared
    TestDomainEventHandler1 handler1 = new TestDomainEventHandler1()

    @Shared
    TestDomainEventHandler2 handler2 = new TestDomainEventHandler2()

    @Subject
    DomainEventPublisherFactory domainEventPublisherFactory = new DomainEventPublisherFactory()

    void 'should correctly create DomainEventPublisher'() {
        when:
            DomainEventPublisher<DomainEvent> domainEventPublisher = domainEventPublisherFactory.domainEventPublisher(domainEventHandlers)
        then:
            with(domainEventPublisher) {
                executorService != null
                it.domainEventHandlersMap == domainEventHandlersMap
            }
        where:
            domainEventHandlers  || domainEventHandlersMap
            []                   || [:]
            [handler1, handler2] || [(TestDomainEvent1): handler1, (TestDomainEvent2): handler2]
    }

    static class TestDomainEvent1 implements DomainEvent {

    }

    static class TestDomainEvent2 implements DomainEvent {

    }

    static class TestDomainEventHandler1 implements DomainEventHandler<TestDomainEvent1> {

        @Override
        void handle(TestDomainEvent1 event) {

        }
    }

    static class TestDomainEventHandler2 implements DomainEventHandler<TestDomainEvent2> {

        @Override
        void handle(TestDomainEvent2 event) {

        }
    }
}
