package com.bootcamp.blogs.entities;

import javax.persistence.*;

@Entity
@Table(name="blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(unique = true , nullable = false)
    private  int id;
    private String name;
    private String description;
    private String url;
    private String status;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author  author;

    public Blog() {
    }

    public Blog(int id, String name, String description, String url, String status, Author author) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = status;
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", author=" + author +
                '}';
    }
}
