package com.empik.githubservice.login.request.count;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
class LoginRequestCount {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private int requestCount = 1;

    @Version
    @Column(name = "ENTITY_VERSION", nullable = false)
    private Long entityVersion;

    LoginRequestCount() {
    }

    LoginRequestCount(String login) {
        this.login = login;
    }

    void incrementCount() {
        requestCount++;
    }

}
