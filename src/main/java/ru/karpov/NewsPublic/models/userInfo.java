package ru.karpov.NewsPublic.models;

import javax.persistence.*;

@Entity
public class userInfo {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String description;
    private Integer countOfMarks;
    private Integer summaryOfMarks;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public Integer getCountOfMarks() {
        return countOfMarks;
    }

    public void setCountOfMarks(final Integer countOfMarks) {
        this.countOfMarks = countOfMarks;
    }

    public Integer getSummaryOfMarks() {
        return summaryOfMarks;
    }

    public void setSummaryOfMarks(final Integer summaryOfMarks) {
        this.summaryOfMarks = summaryOfMarks;
    }

    public double getAverageMark()
    {
        return this.getCountOfMarks() == 0 ? 0 :
                this.getSummaryOfMarks() / this.getCountOfMarks();
    }
}
