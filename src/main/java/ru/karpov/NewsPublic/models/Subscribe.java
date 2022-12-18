package ru.karpov.NewsPublic.models;

import javax.persistence.*;

@Entity
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String idUser;
    private String idUserSubscribe;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(final String idUser) {
        this.idUser = idUser;
    }

    public String getIdUserSubscribe() {
        return idUserSubscribe;
    }

    public void setIdUserSubscribe(final String idUserSubscribe) {
        this.idUserSubscribe = idUserSubscribe;
    }
}
