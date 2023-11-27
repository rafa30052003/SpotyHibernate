package org.example.model.domain;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "STUDENT_TBL")

public class list {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "NAMELIST")
    private String name_list;
    @Column(name = "NAMEUSER")
    private String name_user;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "song_list",
    joinColumns = {
            @JoinColumn(name = "id_list", referencedColumnName = "id")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "id_song",referencedColumnName = "id")
    }
            )
    private Set<Song> songs;

    public list(int id, String description, String name_list, String name_user) {
        this.id = id;
        this.description = description;
        this.name_list = name_list;
        this.name_user = name_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name_list='" + name_list + '\'' +
                ", name_user='" + name_user + '\'' +
                '}';
    }

    public list() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        list list = (list) o;
        return id == list.id && Objects.equals(description, list.description) && Objects.equals(name_list, list.name_list) && Objects.equals(name_user, list.name_user) && Objects.equals(songs, list.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, name_list, name_user, songs);
    }
}