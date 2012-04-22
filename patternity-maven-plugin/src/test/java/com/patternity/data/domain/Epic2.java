package com.patternity.data.domain;

import com.patternity.data.service.StoryRepository;

import java.util.List;

/**
 *
 */
public class Epic2 {

    public List<Story> getStories() {
        return StoryRepository.get().loadStories();
    }
}
