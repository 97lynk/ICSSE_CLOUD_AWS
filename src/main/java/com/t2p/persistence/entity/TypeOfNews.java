package com.t2p.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "types")
public class TypeOfNews implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int type_id;

    private String type_name;

    @JsonIgnore
    @OneToMany(mappedBy = "typeOfNews", targetEntity = News.class)
    private List<News> news;

    public TypeOfNews() {
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
