package ru.karpov.NewsPublic.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "News")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="text")
    private String text;
    @Column(name="date")
    private Date date;
    @Column(name = "category")
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

    public void setCategory(final Categories category) {
        this.category = category;
    }
}
