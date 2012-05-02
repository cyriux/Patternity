package com.patternity.data.domain;

import com.patternity.data.annotation.ValueObject;

/**
 *
 */
@ValueObject
public class UserId1 {
    private final String uuid;

    public UserId1(String uuid) {
        this.uuid = uuid;
    }
}
