package com.patternity.data.domain;

import com.patternity.data.annotation.Entity;
import com.patternity.data.annotation.ValueObject;

/**
 *
 */
@Entity
public class User1 {

    @ValueObject
    public static class Id {
        private final String uuid;

        public Id(String uuid) {
            this.uuid = uuid;
        }
    }

    public Id getUserId() {
        return null;
    }
}
