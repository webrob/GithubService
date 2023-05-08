package com.empik.githubservice.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empik.githubservice.domain.event.DomainEventPublisher;
import com.empik.githubservice.external.client.github.GitHubClient;
import com.empik.githubservice.external.client.github.GitHubClientUserRepresentation;
import com.empik.githubservice.login.request.count.LoginRequested;

@Service
@Transactional
class UserApplicationService {

    private final DomainEventPublisher domainEventPublisher;

    private final GitHubClient githubClient;

    private final UserRepresentationMapper userRepresentationMapper;

    UserApplicationService(DomainEventPublisher domainEventPublisher,
                           GitHubClient githubClient,
                           UserRepresentationMapper userRepresentationMapper) {
        this.domainEventPublisher = domainEventPublisher;
        this.githubClient = githubClient;
        this.userRepresentationMapper = userRepresentationMapper;
    }

    public UserRepresentation getUser(String login) {
        publishEvent(login);
        GitHubClientUserRepresentation githubClientUserRepresentation = githubClient.getUser(login);
        return userRepresentationMapper.from(githubClientUserRepresentation);
    }

    @SuppressWarnings("unchecked")
    private void publishEvent(String login) {
        LoginRequested loginRequested = new LoginRequested(login);
        domainEventPublisher.publish(loginRequested);
    }

}
