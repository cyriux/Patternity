package com.patternity.data.domain;

import com.patternity.data.infrastructure.Provider;
import com.patternity.data.service.StoryRepository;

import java.util.List;

/**
 *
 */
public class Epic4 {

    private Provider<StoryRepository> provider;

    public List<Story> getStories() {
        return provider.get().loadStories();
    }
}
