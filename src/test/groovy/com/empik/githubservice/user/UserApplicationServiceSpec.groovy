package com.empik.githubservice.user

import com.empik.githubservice.domain.event.DomainEventPublisher
import com.empik.githubservice.external.client.github.GitHubClient
import com.empik.githubservice.external.client.github.GitHubClientUserRepresentation
import com.empik.githubservice.login.request.count.LoginRequested

import spock.lang.Specification
import spock.lang.Subject

class UserApplicationServiceSpec extends Specification {

    DomainEventPublisher domainEventPublisher = Mock()

    GitHubClient githubClient = Mock()

    UserRepresentationMapper userRepresentationMapper = Stub()

    @Subject
    UserApplicationService userApplicationService = new UserApplicationService(
        domainEventPublisher,
        githubClient,
        userRepresentationMapper
    )

    void 'should try to get user by login and publish event when user not found'() {
        given:
            String login = 'login'
        when:
            userApplicationService.getUser(login)
        then:
            1 * domainEventPublisher.publish { LoginRequested loginRequested -> loginRequested.login() == login }
        and:
            1 * githubClient.getUser(login) >> { throw new RuntimeException() }
        and:
            thrown RuntimeException
    }

    void 'should get user by login and publish event when user found'() {
        given:
            String login = 'login'
            GitHubClientUserRepresentation gitHubUserRepresentation = new GitHubClientUserRepresentation(
                1,
                'login',
                'name',
                'type',
                'avatarUrl',
                'createdAt',
                0,
                0
            )
            UserRepresentation givenUserRepresentation = new UserRepresentation(
                '1',
                'login',
                'name',
                'type',
                'avatarUrl',
                'createdAt',
                '10'
            )
        and:
            userRepresentationMapper.from(gitHubUserRepresentation) >> givenUserRepresentation
        when:
            UserRepresentation userRepresentation = userApplicationService.getUser(login)
        then:
            userRepresentation == givenUserRepresentation
        and:
            1 * domainEventPublisher.publish { LoginRequested loginRequested -> loginRequested.login() == login }
        and:
            1 * githubClient.getUser(login) >> gitHubUserRepresentation
    }
}
