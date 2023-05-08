package com.empik.githubservice.login.request.count;

import org.springframework.stereotype.Component;

import com.empik.githubservice.domain.event.DomainEventHandler;

@Component
class LoginRequestedDomainEventHandler implements DomainEventHandler<LoginRequested> {

    private final LoginRequestCountService loginRequestCountService;

    LoginRequestedDomainEventHandler(LoginRequestCountService loginRequestCountService) {
        this.loginRequestCountService = loginRequestCountService;
    }

    @Override
    public void handle(LoginRequested loginRequested) {
        loginRequestCountService.updateRequestCount(loginRequested.login());
    }

}
