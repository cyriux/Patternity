package com.patternity.data.service;

import com.patternity.data.annotation.Repository;

/**
 *
 */
@Repository
public class RepositoryBase<T> {

    public void add(T entity) {
    }

    public Repository load(String uuid) {
        return null;
    }
}
