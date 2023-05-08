package com.empik.githubservice.error;

import java.util.UUID;

public record ErrorResponse(UUID uuid) {

    public ErrorResponse() {
        this(UUID.randomUUID());
    }
}
