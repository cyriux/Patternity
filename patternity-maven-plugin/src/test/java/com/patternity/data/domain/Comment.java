package com.patternity.data.domain;

import com.patternity.data.annotation.ValueObject;

/**
 *
 */
@ValueObject
public class Comment {
    private String text;
    private UserId1 userId1;
}
