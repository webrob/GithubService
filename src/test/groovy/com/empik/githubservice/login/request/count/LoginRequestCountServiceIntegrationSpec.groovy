package com.empik.githubservice.login.request.count

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.empik.githubservice.GithubServiceApplication

import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = GithubServiceApplication)
class LoginRequestCountServiceIntegrationSpec extends Specification {

    public static final String LOGIN1 = 'login1'

    public static final String LOGIN2 = 'login2'

    LoginRequestCount loginRequestCount1

    LoginRequestCount loginRequestCount2

    @Autowired
    LoginRequestCountRepository loginRequestCountRepository

    @Subject
    @Autowired
    LoginRequestCountService loginRequestCountService

    void setup() {
        loginRequestCount1 = saveLoginRequestCount(LOGIN1)
        loginRequestCount2 = saveLoginRequestCount(LOGIN2)
    }

    void cleanup() {
        loginRequestCountRepository.deleteAll()
    }

    void 'should create request count when login request count does not exist'() {
        given:
            String notExistingLogin = 'not existing login'
        expect:
            loginRequestCountRepository.findAll().login == [loginRequestCount1.login, loginRequestCount2.login]
        when:
            loginRequestCountService.updateRequestCount(notExistingLogin)
        then:
            with(loginRequestCountRepository.findAll()) {
                it.login == [loginRequestCount1.login, loginRequestCount2.login, notExistingLogin]
                with(it.last()) {
                    it.login == notExistingLogin
                    requestCount == 1
                    entityVersion == 0
                }
            }
    }

    void 'should update request count when login request count exists'() {
        given:
            String login = 'login'
            LoginRequestCount loginRequestCount3 = saveLoginRequestCount(login)
        expect:
            loginRequestCountRepository.findAll().login == [loginRequestCount1.login, loginRequestCount2.login, loginRequestCount3.login]
        when:
            loginRequestCountService.updateRequestCount(login)
        then:
            with(loginRequestCountRepository.findAll()) {
                it.login == [loginRequestCount1.login, loginRequestCount2.login, loginRequestCount3.login]
                with(it.last()) {
                    it.login == login
                    requestCount == 2
                    entityVersion == 1
                }
            }
    }

    private LoginRequestCount saveLoginRequestCount(String login) {
        LoginRequestCount loginRequestCount = new LoginRequestCount(login)
        return loginRequestCountRepository.save(loginRequestCount)
    }
}
