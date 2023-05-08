package com.empik.githubservice.user;

public record UserRepresentation(String id,
                                 String login,
                                 String name,
                                 String type,
                                 String avatarUrl,
                                 String createdAt,
                                 String calculations) {

}
