package com.empik.githubservice.login.request.count;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class LoginRequestCountService {

    private final LoginRequestCountRepository loginRequestCountRepository;

    LoginRequestCountService(LoginRequestCountRepository loginRequestCountRepository) {
        this.loginRequestCountRepository = loginRequestCountRepository;
    }

    public void updateRequestCount(String login) {
        loginRequestCountRepository.findById(login)
            .ifPresentOrElse(
                LoginRequestCount::incrementCount,
                () -> persistLoginRequestCount(login)
            );
    }

    private void persistLoginRequestCount(String login) {
        LoginRequestCount loginRequestCount = new LoginRequestCount(login);
        loginRequestCountRepository.save(loginRequestCount);
    }
}
