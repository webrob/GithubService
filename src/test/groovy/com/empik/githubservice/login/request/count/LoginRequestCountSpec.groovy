package com.empik.githubservice.login.request.count

import spock.lang.Specification
import spock.lang.Subject

class LoginRequestCountSpec extends Specification {

    @Subject
    LoginRequestCount loginRequestCount = new LoginRequestCount('login')

    void 'should initialize LoginRequestCount with 1 count'() {
        expect:
            loginRequestCount.requestCount == 1
    }

    void 'should increment count by 1'() {
        expect:
            loginRequestCount.requestCount == 1
        when:
            loginRequestCount.incrementCount()
        then:
            loginRequestCount.requestCount == 2
    }
}
