package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "photo")
    private String photo;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "subscripcion",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "list_id")
    )
    private List<list> lists;

    public User() {
        this.lists = new ArrayList<>();
    }

    public User(String name, String mail, String photo, String password) {
        this.name = name;
        this.mail = mail;
        this.photo = photo;
        this.password = password;
        this.lists = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setLists(List<list> lists) {
        this.lists = lists;
    }
    // Métodos getter y setter para 'lists'

    public List<List> getLists() {
        return lists;
    }

    public void setLists(List<List> lists) {
        this.lists = lists;
    }

    // Resto de los métodos existentes...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", photo='" + photo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
