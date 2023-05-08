package com.empik.githubservice.user;

import org.springframework.stereotype.Component;

import com.empik.githubservice.external.client.github.GitHubClientUserRepresentation;

@Component
class UserRepresentationMapper {

    private static final String NOT_A_NUMBER = "NaN";

    UserRepresentation from(GitHubClientUserRepresentation githubClientUserRepresentation) {
        return new UserRepresentation(
            String.valueOf(githubClientUserRepresentation.id()),
            githubClientUserRepresentation.login(),
            githubClientUserRepresentation.name(),
            githubClientUserRepresentation.type(),
            githubClientUserRepresentation.avatarUrl(),
            githubClientUserRepresentation.createdAt(),
            calculateCalculations(githubClientUserRepresentation.followersCount(), githubClientUserRepresentation.publicReposCount())
        );
    }

    private String calculateCalculations(int followersCount, int publicReposCount) {
        return followersCount == 0
            ? NOT_A_NUMBER
            : Integer.toString(6 / followersCount * (2 + publicReposCount));
    }

}
