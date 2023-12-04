package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "album")
public class Album  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name")
    private String name;

    @Column(name = "photo", columnDefinition = "longblob")
    private String photo;
    @Column(name = "publication_date")
    private Date public_time;
    @Column(name = "n_reproduction")
    private int nrepro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name_artist")
    private Artist artist;

    public Album() {
    }

    public Album(String name, String photo, Date public_time, int nrepro, Artist artist) {
        this.name = name;
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nrepro;
        this.artist = artist;
    }

    public Album(String photo, Date public_time, int nrepro, Artist artist) {
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nrepro;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getPublic_time() {
        return public_time;
    }

    public void setPublic_time(Date public_time) {
        this.public_time = public_time;
    }

    public int getNrepro() {
        return nrepro;
    }

    public void setNrepro(int nrepro) {
        this.nrepro = nrepro;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return nrepro == album.nrepro && Objects.equals(name, album.name) && Objects.equals(photo, album.photo) && Objects.equals(public_time, album.public_time) && Objects.equals(artist, album.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, photo, public_time, nrepro, artist);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", public_time=" + public_time +
                ", nrepro=" + nrepro +
                ", artist=" + artist +
                '}';
    }
}
