package org.example.model.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
@Entity
@Table (name = "ARTIST")
public class Artist implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @Column(name = "NAME")
    private String name;
    @Column(name = "NATIONALITY")
    private Nationality nationality;
    @Column(name = "PHOTO")
    private String photo;

    public Artist() {
    }

    public Artist(String name, Nationality nationality, String photo) {
        this.name = name;
        this.nationality = nationality;
        this.photo = photo;
    }

    public Artist(Nationality nationality, String photo) {
        this.nationality = nationality;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nacionality) {
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
