package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;

import org.example.model.domain.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO extends Song implements iDAO<Song, Integer> {


    /**
     * Conexion
     */
    private EntityManager manager;

    @Override
    public List<Song> findAll() throws SQLException {
        List<Song> songs=new ArrayList<>();
        try{
            Query q=manager.createNativeQuery("SELECT a FROM Song a",Song.class);
            songs=q.getResultList();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return songs;

    }

    /**
     * Metodo que usca una cancion por su clave
     * @param id clave de la cancion
     * @return la cancion a buscar
     */
    @Override
    public Song findById(Integer id) {
        Song result = null;

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<Song> query = manager.createQuery(
                    "SELECT s FROM Song s WHERE s.id = :id",
                    Song.class
            );

            query.setParameter("id", id);

            result = query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return result;
    }
    /**
     * Metodo que inserta una cancion , else (la cancion si esta creada lo modifica)
     * @param entity: La cancion a crear o modificar
     * @return la cancion
     */
    @Override
    public Song save(Song entity) {
        Song result = new Song();

        if (entity != null) {
            try {
                manager = Connection.getConnect().createEntityManager();
                manager.getTransaction().begin();

                if (entity.getId() == -1) {
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

    /**
     * Metodo que elimina la cancion
     * @param entity la cancion a eliminar
     */
    @Override
    public void delete(Song entity) {
        if (entity != null) {
            try {
                manager = Connection.getConnect().createEntityManager();
                manager.getTransaction().begin();

                // Si la entidad no está en el contexto de persistencia, adjuntarla antes de eliminar
                if (!manager.contains(entity)) {
                    entity = manager.merge(entity);
                }

                manager.remove(entity);
                manager.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();

                if (manager.getTransaction().isActive()) {
                    manager.getTransaction().rollback();
                }
            } finally {
                manager.close();
            }
        }
    }


    public void updateReproductionCount(Song song) {
        try {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            Song songToUpdate = manager.find(Song.class, song.getId());
            if (songToUpdate != null) {
                songToUpdate.setNrepro(song.getNrepro());
               manager.merge(songToUpdate);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



