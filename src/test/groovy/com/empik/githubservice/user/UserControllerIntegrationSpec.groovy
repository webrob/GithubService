package com.empik.githubservice.user

import com.empik.githubservice.login.request.count.LoginRequestCountRepository

import static com.empik.githubservice.constant.ApiVersions.ALWAYS_NEWEST_API_VERSION
import static com.empik.githubservice.constant.ApiVersions.API_V1
import static org.apache.commons.lang3.StringUtils.EMPTY
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.web.context.WebApplicationContext

import com.empik.githubservice.error.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper

import spock.lang.Specification

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles('test')
@AutoConfigureWireMock(port = 0)
class UserControllerIntegrationSpec extends Specification {

    static final String EXISTING_USER = 'octocat'

    static final String NOT_EXISTING_USER = 'not-existing'

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    LoginRequestCountRepository loginRequestCountRepository

    @Autowired
    WebApplicationContext webApplicationContext

    MockMvc mockMvc

    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build()
    }

    void cleanup() {
        loginRequestCountRepository.deleteAll()
    }

    void 'should return OK with body after calling users endpoint with found github user with supported accept header'() {
        given:
            String existingLogin = EXISTING_USER
        when:
            MvcResult mvcResult = callGetUserEndpoint(existingLogin, acceptHeader)
        then:
            with(mvcResult.getResponse()) {
                status == OK.value()
                with(getUserRepresentation(getContentAsString())) {
                    id() == '583231'
                    login() == 'octocat'
                    name() == 'The Octocat'
                    type() == 'User'
                    avatarUrl() == 'https://avatars.githubusercontent.com/u/583231?v=4'
                    createdAt() == '2011-01-25T18:44:36Z'
                    calculations() == '0'
                }
            }
        and:
            assertRequestLoginCount(existingLogin, 1)
        where:
            acceptHeader << [EMPTY, API_V1, ALWAYS_NEWEST_API_VERSION]
    }

    void 'should return Not Acceptable without body after calling users endpoint with found github user with unsupported accept header'() {
        given:
            String login = EXISTING_USER
        when:
            MvcResult mvcResult = callGetUserEndpoint(login, 'unsupported')
        then:
            with(mvcResult.getResponse()) {
                status == NOT_ACCEPTABLE.value()
                getContentAsString() == EMPTY
            }
        and:
            loginRequestCountRepository.findById(login).isEmpty()
    }

    void 'should return Not Found with error response body after calling users endpoint with found github user with unsupported accept header'() {
        given:
            String login = NOT_EXISTING_USER
        when:
            MvcResult mvcResult = callGetUserEndpoint(login)
        then:
            with(mvcResult.getResponse()) {
                status == NOT_FOUND.value()
                with(getErrorResponse(getContentAsString())) {
                    uuid() != null
                }
            }
        and:
            assertRequestLoginCount(login, 1)
    }

    void 'should correctly count request login after three times calling get user endpoint'() {
        given:
            int requestCount = 3
            String login = EXISTING_USER
        when:
            requestCount.times { callGetUserEndpoint(login) }
        then:
            assertRequestLoginCount(login, requestCount)
    }

    private MvcResult callGetUserEndpoint(String user, String acceptHeader = API_V1) {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/${user}")
            .header(ACCEPT, acceptHeader))
            .andReturn()
    }

    private UserRepresentation getUserRepresentation(String content) {
        return objectMapper.readValue(content, UserRepresentation)
    }

    private ErrorResponse getErrorResponse(String content) {
        return objectMapper.readValue(content, ErrorResponse)
    }

    private void assertRequestLoginCount(String login, int requestCount) {
        with(loginRequestCountRepository.findById(login).orElseThrow()) {
            it.login == login
            it.requestCount == requestCount
        }
    }

}
