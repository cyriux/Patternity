package com.patternity.data.domain;

import com.patternity.data.annotation.Entity;

/**
 *
 */
@Entity
public class User2 {

    private UserId1 userId1;

    public void setUserId1(UserId1 userId1) {
        this.userId1 = userId1;
    }

    public UserId1 getUserId1() {
        return userId1;
    }
}
