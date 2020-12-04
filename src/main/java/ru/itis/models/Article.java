package ru.itis.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Builder
@ToString
public class Article {
    private String text;
    private Integer countLikes;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Integer countLikes) {
        this.countLikes = countLikes;
    }
}
