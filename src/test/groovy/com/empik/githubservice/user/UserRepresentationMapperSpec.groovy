package com.empik.githubservice.user

import static com.empik.githubservice.user.UserRepresentationMapper.NOT_A_NUMBER

import com.empik.githubservice.external.client.github.GitHubClientUserRepresentation

import spock.lang.Specification
import spock.lang.Subject

class UserRepresentationMapperSpec extends Specification {

    @Subject
    UserRepresentationMapper userRepresentationMapper = new UserRepresentationMapper()

    void 'should map from GitHubUserRepresentation'() {
        given:
            GitHubClientUserRepresentation gitHubClientUserRepresentation = new GitHubClientUserRepresentation(
                1,
                'login',
                'name',
                'type',
                'avatarUrl',
                'createdAt',
                followersCount,
                publicReposCount
            )
        when:
            UserRepresentation userRepresentation = userRepresentationMapper.from(gitHubClientUserRepresentation)
        then:
            with(userRepresentation) {
                id() == String.valueOf(gitHubClientUserRepresentation.id())
                login() == gitHubClientUserRepresentation.login()
                name() == gitHubClientUserRepresentation.name()
                type() == gitHubClientUserRepresentation.type()
                avatarUrl() == gitHubClientUserRepresentation.avatarUrl()
                createdAt() == gitHubClientUserRepresentation.createdAt()
                calculations() == expectedCalculations
            }
        where:
            followersCount | publicReposCount || expectedCalculations
            0              | 0                || NOT_A_NUMBER
            0              | 1                || NOT_A_NUMBER
            1              | 0                || '12'
            1              | 1                || '18'
            6              | 0                || '2'
            7              | 1                || '0'
    }

}
