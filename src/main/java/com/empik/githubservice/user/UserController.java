package com.empik.githubservice.user;

import static com.empik.githubservice.constant.ApiVersions.ALWAYS_NEWEST_API_VERSION;
import static com.empik.githubservice.constant.ApiVersions.API_V1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/users",
    produces = { ALWAYS_NEWEST_API_VERSION, API_V1 }
)
public class UserController {

    private final UserApplicationService userApplicationService;

    UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping(value = "/{login}")
    UserRepresentation getUser(@PathVariable String login) {
        return userApplicationService.getUser(login);
    }
}
