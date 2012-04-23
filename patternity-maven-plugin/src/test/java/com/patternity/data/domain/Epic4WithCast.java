package com.patternity.data.domain;

import com.patternity.data.infrastructure.Provider;
import com.patternity.data.service.StoryRepository;

import java.util.List;

/**
 *
 */
public class Epic4WithCast {

    private Provider<?> provider;

    public List<Story> getStories() {
        return ((StoryRepository) provider.get()).loadStories();
    }
}
