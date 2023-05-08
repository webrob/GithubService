package com.empik.githubservice.constant;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public final class ApiVersions {

    public static final String ALWAYS_NEWEST_API_VERSION = APPLICATION_JSON_VALUE;

    public static final String API_V1 = "application/vnd.empik.v1+json";

    private ApiVersions() {
    }

}
