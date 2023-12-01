package org.example.model.domain;

import org.example.model.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "name")  // Corregir aquí
    private User user;

    @ManyToOne
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private list myList;

    @Column(name = "comment")
    private String comment;

    public Comment() {
    }


    public Comment(User user, List myList, String comment) {
        this.user = user;
        this.myList = (list) myList;
        this.comment = comment;
    }

    public Comment(String nameUser, int selectedListId, String comment) {
        this.user = new User(nameUser);
        this.myList = new list(selectedListId, null, null, null);
        this.comment = comment;
    }

    public Comment(int id, String nameUser, int selectedListId, String comment) {
        this.id = id;
        this.user = new User(nameUser);
        this.myList = new list(selectedListId, null, null, null);
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }



    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public list getMyList() {
        return myList;
    }

    public void setMyList(list myList) {
        this.myList = myList;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", myList=" + myList +
                ", comment='" + comment + '\'' +
                '}';
    }
}
