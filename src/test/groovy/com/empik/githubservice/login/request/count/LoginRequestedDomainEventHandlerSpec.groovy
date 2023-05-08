package com.empik.githubservice.login.request.count

import spock.lang.Specification
import spock.lang.Subject

class LoginRequestedDomainEventHandlerSpec extends Specification {

    LoginRequestCountService loginRequestCountService = Mock()

    @Subject
    LoginRequestedDomainEventHandler loginRequestedDomainEventHandler = new LoginRequestedDomainEventHandler(loginRequestCountService)

    void 'should update request count for login while handling LoginRequested event'() {
        given:
            LoginRequested loginRequested = new LoginRequested('login')
        when:
            loginRequestedDomainEventHandler.handle(loginRequested)
        then:
            1 * loginRequestCountService.updateRequestCount(loginRequested.login())
    }
}
