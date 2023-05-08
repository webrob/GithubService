package com.empik.githubservice.login.request.count;

import com.empik.githubservice.domain.event.DomainEvent;

public record LoginRequested(String login) implements DomainEvent {

}
