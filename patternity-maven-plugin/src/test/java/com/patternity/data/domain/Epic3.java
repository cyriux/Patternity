package com.patternity.data.domain;

import com.patternity.data.service.StoryRepository;

import java.util.List;

/**
 *
 */
public class Epic3 {

    private StoryRepository storyRepository;

    public List<Story> getStories() {
        return storyRepository.loadStories();
    }
}
