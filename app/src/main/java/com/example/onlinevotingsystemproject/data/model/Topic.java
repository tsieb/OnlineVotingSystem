package com.example.onlinevotingsystemproject.data.model;

import java.util.List;
import java.util.Map;

public class Topic {
    private String id;
    private String title;
    private String description;
    private List<String> options;
    private Map<String, String> votes;

    public Topic() {
        // Default constructor required for calls to DataSnapshot.getValue(Topic.class)
    }

    public Topic(String id, String title, String description, List<String> options, Map<String, String> votes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.options = options;
        this.votes = votes;
    }

    // Getter and setter methods for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Map<String, String> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, String> votes) {
        this.votes = votes;
    }
}
