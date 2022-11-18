package ru.karpov.NewsPublic.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Subscribe {
    private Integer idUser;
    private Integer idUserSubscribe;
    private String id;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(final Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUserSubscribe() {
        return idUserSubscribe;
    }

    public void setIdUserSubscribe(final Integer idUserSubscribe) {
        this.idUserSubscribe = idUserSubscribe;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
