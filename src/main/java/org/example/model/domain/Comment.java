package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private  int id;
    @Column(name = "name_user")
    private  String name_user;
    @Column(name = "id_list")
    private int id_list;
    @Column(name = "comment")
    private  String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private List list;

    public Comment(int id, String name_user, int id_list, String comment) {
        this.id = id;
        this.name_user = name_user;
        this.id_list = id_list;
        this.comment = comment;
    }

    public Comment(String commentText) {
        this.comment = commentText;
    }

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name_user='" + name_user + '\'' +
                ", id_list=" + id_list +
                ", comment='" + comment + '\'' +
                '}';
    }
}
