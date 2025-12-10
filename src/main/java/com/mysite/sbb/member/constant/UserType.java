package com.mysite.sbb.member.constant;

import lombok.Getter;

@Getter
public enum UserType {
    JOB_SEEKER("개인회원"),
    SCOUT("기업회원");

    private final String description;

    UserType(String description) {
        this.description = description;
    }
}
