package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;

import org.example.model.domain.Song;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
        Album result = null;

        if (entity != null) {
            manager = Connection.getConnect().createEntityManager();

            try {
                manager.getTransaction().begin();

                if (!manager.contains(entity)) {
                    // INSERT
                    manager.persist(entity);
                } else {
                    // UPDATE
                    entity = manager.merge(entity);
                }


                manager.getTransaction().commit();
                result = entity;

            } catch (Exception e) {
                e.printStackTrace();

                if (manager.getTransaction().isActive()) {
                    manager.getTransaction().rollback();
                }
            } finally {
                manager.close();
            }
        }

        return result;
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
                    albumToUpdate.setnReproduction(album.getnReproduction());
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

    public Album updateAlbum(Album updatedAlbum) {
        Album result = null;

        if (updatedAlbum != null) {
            EntityManager entityManager = null;

            try {
                entityManager = Connection.getConnect().createEntityManager();
                entityManager.getTransaction().begin();

                Album albumToUpdate = entityManager.find(Album.class, updatedAlbum.getName());
                if (albumToUpdate != null) {
                    // UPDATE
                    albumToUpdate.setName(updatedAlbum.getName());
                    albumToUpdate.setPublicTime(updatedAlbum.getPublicTime());
                    albumToUpdate.setArtist(updatedAlbum.getArtist());

                    albumToUpdate = entityManager.merge(albumToUpdate);
                    result = albumToUpdate;
                }

                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();

                if (entityManager != null && entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }

        return result;
    }
    



}
