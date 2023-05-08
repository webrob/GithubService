package com.empik.githubservice.domain.event

import java.util.concurrent.ExecutorService

import spock.lang.Specification
import spock.lang.Subject

class DomainEventPublisherSpec extends Specification {

    TestDomainEventHandler1 handler1 = new TestDomainEventHandler1()

    ExecutorService executorService = Mock()

    @Subject
    DomainEventPublisher domainEventPublisher = new DomainEventPublisher(
        executorService,
        [
            (TestDomainEvent1): handler1
        ]
    )

    void 'should handle event when published domain event handler found'() {
        given:
            TestDomainEvent1 event1 = new TestDomainEvent1()
        when:
            domainEventPublisher.publish(event1)
        then:
            1 * executorService.submit { handler1.handle(event1) }
    }

    void 'should not handle event when published domain event handler not found'() {
        given:
            TestDomainEvent2 event2 = new TestDomainEvent2()
        when:
            domainEventPublisher.publish(event2)
        then:
            0 * executorService._
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

}
