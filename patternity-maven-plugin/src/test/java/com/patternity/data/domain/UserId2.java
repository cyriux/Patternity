package com.patternity.data.domain;

import com.patternity.data.annotation.ValueObject;

/**
 *
 */
@ValueObject
public class UserId2 {

    public void invalidReference() {
        User2 user2 = new User2();
        if (user2.getUserId1() == null)
            throw new IllegalStateException();
    }
}
