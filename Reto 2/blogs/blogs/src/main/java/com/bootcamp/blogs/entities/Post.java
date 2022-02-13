package com.bootcamp.blogs.entities;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(unique = true , nullable = false)
    private int id;
    private String title;
    private Date date;
    private String status;
    private String content;

    @ManyToOne()
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public Post(){}

    public Post(int id, String title, Date date, String status, String content, Blog blog) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.content = content;
        this.blog = blog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", blog=" + blog +
                '}';
    }

}
