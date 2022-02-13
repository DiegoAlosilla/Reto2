package com.bootcamp.blogs.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(unique = true , nullable = false)
    private int id;
    private Date date;
    private String name; //author quien comento
    private String comentario;
    //private String estado;  (profesor dijo que lo quitemos)

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(){}

    public Comment(int id, Date date, String name, String comentario, Post post) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.comentario = comentario;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", comentario='" + comentario + '\'' +
                ", post=" + post +
                '}';
    }
}
