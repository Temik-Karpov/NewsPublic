package ru.karpov.NewsPublic.models;

import javax.persistence.*;

@Entity
public class userInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private Integer age;
    private String description;
    private float averageMark;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
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