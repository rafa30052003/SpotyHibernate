package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Artist;
import org.example.model.domain.Song;


import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlbumDAO implements iDAO <Album,String> {

    private static EntityManager manager;




    @Override
    public List<Album> findAll() {
        List<Album> albums = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<Album> query = manager.createQuery("SELECT a FROM Album a", Album.class);
            albums = query.getResultList();
        } catch (NoResultException e) {
            // Handle NoResultException if needed
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other specific exceptions if needed
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }

        return albums;
    }

    @Override
    public Album findById(String id) {
        Album album = null;

        try {
            manager = Connection.getConnect().createEntityManager();
            album = manager.find(Album.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }

        return album;
    }


    @Override
    public void delete(Album entity) {
        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            // Ensure the entity is in the context of persistence before removing
            entity = manager.merge(entity);

            manager.remove(entity);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
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
            manager = Connection.getConnect().createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            try {
                transaction.begin();

                if (!manager.contains(entity)) {
                    // INSERT
                    manager.persist(entity);
                } else {
                    // UPDATE
                    entity = manager.merge(entity);
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                if (manager != null && manager.isOpen()) {
                    manager.close();
                }
            }
        } catch (Exception e) {
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
        try {
            manager = Connection.getConnect().createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            try {
                transaction.begin();
                Album albumToUpdate = manager.find(Album.class, album.getName());

                if (albumToUpdate != null) {
                    albumToUpdate.setNrepro(album.getNrepro());
                    manager.merge(albumToUpdate);
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                if (manager != null && manager.isOpen()) {
                    manager.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Song> findSongsByAlbumName(String albumName) {
        List<Song> songs = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();
            TypedQuery<Song> query = manager.createQuery("SELECT s FROM Song s WHERE s.album.name = :albumName", Song.class);
            query.setParameter("albumName", albumName);
            songs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }

        return songs;
    }



    public List<String> findNames() {
        List<String> names = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();
            TypedQuery<String> query = manager.createQuery("SELECT a.name FROM Album a", String.class);
            names = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }

        return names;
    }

    //QUEDA POR CAMBIAR
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
