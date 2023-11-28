package org.example.model.DAO;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Artist;
import org.example.model.domain.Song;


import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlbumDAO implements iDAO <Album,String> {

    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public AlbumDAO() {
        emf = Persistence.createEntityManagerFactory("aplicacion");
        manager = emf.createEntityManager();
    }


    @Override
    public List<Album> findAll() throws SQLException {
        List<Album> albums=new ArrayList<>();
        try{
            Query q=manager.createNativeQuery("SELECT a FROM Album a",Album.class);
            albums=q.getResultList();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return albums;
    }

    @Override
    public Album findById(String id)  {
        Album album=null;
        try{
            album=manager.find(Album.class,id);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Album entity)  {
        try {
            manager.getTransaction().begin();
            manager.remove(entity);
            manager.getTransaction().commit();
        } catch(Exception e ) {
            if(manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            e.printStackTrace();
        }

    }
    public void updateAlbumName(String newName, String name) {
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            Album albumToUpdate = manager.find(Album.class, name);
            if (albumToUpdate != null) {
                albumToUpdate.setName(newName);
                manager.merge(albumToUpdate);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Album save(Album entity) {
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
        } catch(Exception e ) {
        if(manager.getTransaction().isActive())
    manager.getTransaction().rollback();
            e.printStackTrace();
        }
        return entity;
    }

    public List<Album> findByName (String name){
        Album album=null;
        try{
            album=manager.find(Album.class,name);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;

    }
    public void updateReproductionCount(Album album) {
        try{
            EntityTransaction transaction=manager.getTransaction();
            transaction.begin();
            Album albumToUpdate=manager.find(Album.class,album.getName());
            if(albumToUpdate!=null){
                albumToUpdate.setNrepro(album.getNrepro());
                manager.merge(albumToUpdate);
            }
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Song> findSongsByAlbumName(String albumName) {
        try {
            Query q=manager.createNativeQuery("SELECT s FROM Song s WHERE s.album.name = :albumName");
            q.setParameter("albumName", albumName);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


public List<String> findNames(){
        try{
            Query q=manager.createNativeQuery("SELECT a.name FROM Album a ");
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
}

    public void updateAlbum(String newName, Date newPublicationDate, Artist newNameArtist, String name) {
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            Album albumToUpdate = manager.find(Album.class, name);
            if (albumToUpdate != null) {
                albumToUpdate.setName(newName);
                albumToUpdate.setPublic_time(newPublicationDate);
                albumToUpdate.setName(String.valueOf(newNameArtist));
                manager.merge(albumToUpdate);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
