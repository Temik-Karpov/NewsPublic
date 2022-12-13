package ru.karpov.NewsPublic.models;

import javax.persistence.*;

@Entity
public class userInfo {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String description;
    private float averageMark;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(final float averageMark) {
        this.averageMark = averageMark;
    }
}