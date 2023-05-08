package com.empik.githubservice.external.client.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    value = "github-client",
    url = "${github-client.host}:${github-client.port:}"
)
public interface GitHubClient {

    @GetMapping(
        value = "/users/{login}",
        headers = "X-GitHub-Api-Version:2022-11-28"
    )
    GitHubClientUserRepresentation getUser(@PathVariable String login);

}
