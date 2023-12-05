package org.example.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
@Entity
@Table (name = "artist")
public class Artist implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "photo")
    private String photo;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums;
    public Artist() {
    }

    public Artist(String name, String nationality, String photo) {
        this.name = name;
        this.nationality = nationality;
        this.photo = photo;
    }

    public Artist(String nationality, String photo) {
        this.nationality = nationality;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nacionality) {
        this.nationality = nacionality;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
