package com.patternity.data.domain;

import com.patternity.data.service.StoryRepository;

import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 */
public class Epic5 {

    public List<Story> getStories() throws Exception {
        // anonymous case
        return new Callable<List<Story>>() {
            @Override
            public List<Story> call() throws Exception {
                return StoryRepository.get().loadStories();
            }
        }.call();
    }
}
