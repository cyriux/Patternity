package com.patternity.data.domain;

import com.patternity.data.service.StoryRepository;

import java.util.List;

/**
 *
 */
public class Epic1 {

    public List<Story> getStories(StoryRepository repository) {
        return repository.loadStories();
    }
}
