package com.empik.githubservice.external.client.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubClientUserRepresentation(@JsonProperty("id") long id,
                                             @JsonProperty("login") String login,
                                             @JsonProperty("name") String name,
                                             @JsonProperty("type") String type,
                                             @JsonProperty("avatar_url") String avatarUrl,
                                             @JsonProperty("created_at") String createdAt,
                                             @JsonProperty("followers") int followersCount,
                                             @JsonProperty("public_repos") int publicReposCount) {

}
