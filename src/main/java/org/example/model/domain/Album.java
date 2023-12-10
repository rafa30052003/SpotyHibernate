package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "album")
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "publication_date")
    private Date publicTime;

    @Column(name = "n_reproduction")
    private int nReproduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name_artist", referencedColumnName = "name")
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs;

    public Album() {
    }

    public Album(String name, byte[] photo, Date publicTime, int nReproduction, Artist artist, List<Song> songs) {
        this.name = name;
        this.photo = photo;
        this.publicTime = publicTime;
        this.nReproduction = nReproduction;
        this.artist = artist;
        this.songs = songs;
    }

   /* public Album(String photo, Date public_time, int nrepro, Artist artist) {
        this.photo = photo;
        this.public_time = public_time;
        this.nrepro = nrepro;
        this.artist = artist;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public int getnReproduction() {
        return nReproduction;
    }

    public void setnReproduction(int nReproduction) {
        this.nReproduction = nReproduction;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return nReproduction == album.nReproduction && Objects.equals(name, album.name) && Arrays.equals(photo, album.photo) && Objects.equals(publicTime, album.publicTime) && Objects.equals(artist, album.artist) && Objects.equals(songs, album.songs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, publicTime, nReproduction, artist, songs);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", publicTime=" + publicTime +
                ", nReproduction=" + nReproduction +
                ", artist=" + artist +
                ", songs=" + songs +
                '}';
    }
}
