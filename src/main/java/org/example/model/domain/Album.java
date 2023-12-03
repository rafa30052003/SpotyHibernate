package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "album")
public class Album  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name")
    private String name;
    @Column(name = "photo")
    private String photo;
    @Column(name = "publication_date")
    private Date public_time;
    @Column(name = "n_reproduction")
    private int nrepro;
    @ManyToMany
    @JoinTable(
            name = "ALBUM_ARTIST",
            joinColumns = @JoinColumn(name = "NAME_ARTIST"),
            inverseJoinColumns = @JoinColumn(name = "NAME")
    )
    private Set<Artist> artists;

    public Album() {
    }

    public Album(String name, String photo, Date public_time, int nrepro, Set<Artist> artists) {
        this.name = name;
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nrepro;
        this.artists = artists;
    }

    public Album(String photo, Date public_time, int nrepro, Set<Artist> artists) {
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nrepro;
        this.artists = artists;
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

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return nrepro == album.nrepro && Objects.equals(name, album.name) && Objects.equals(photo, album.photo) && Objects.equals(public_time, album.public_time) && Objects.equals(artists, album.artists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, photo, public_time, nrepro, artists);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", public_time=" + public_time +
                ", nrepro=" + nrepro +
                ", artists=" + artists +
                '}';
    }
}
