package ru.karpov.NewsPublic.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String text;
    private Date date;
    private String authorName;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    private Categories category;

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

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        switch(category)
        {
            case "Sport":
                this.category = Categories.Sport;
                break;
            case "Culture":
                this.category = Categories.Culture;
                break;
            case "Economic":
                this.category = Categories.Economic;
                break;
            case "Science":
                this.category = Categories.Science;
                break;
            case "Politics":
                this.category = Categories.Politics;
                break;
        }

    }
}