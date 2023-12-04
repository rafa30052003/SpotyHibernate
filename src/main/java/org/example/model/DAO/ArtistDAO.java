package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Artist;
import org.example.model.domain.Nationality;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO extends Artist implements iDAO<Artist,String> {


   private static EntityManager manager;


    /**
     * Metodo que muestra todos los Artistas de la base de datos
     * @return: todos los artistas
     */
    @Override
    public List<Artist> findAll() {
        List<Artist> result = null;

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<Artist> query = manager.createQuery("SELECT a FROM Artist a", Artist.class);
            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen()) {
                manager.close();
            }
        }

        return result;
    }


    /**
     * Metodo que busca el artista por su id
     * @param id: tipo String , id del Arista
     * @return : el artista en concreto
     * @throws SQLException
     */
    @Override
    public Artist findById(String id) throws SQLException {
        Artist result = null;

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<Artist> query = manager.createQuery("SELECT a FROM Artist a WHERE a.name = :name", Artist.class);
            query.setParameter("name", id);

            result = query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return result;
    }

    /**
     * Metodo que inserta un artista, else (el artista si esta creado lo modifica)
     * @param entity: El artista a crear o modificar
     * @return el artista
     */
    @Override
    public Artist save(Artist entity) {
        Artist result = null;

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

    /**
     * Metodo que elimina el artista
     * @param entity: el artista a eliminar
     */
    @Override
    public void delete(Artist entity) {
        try {
            manager = Connection.getConnect().createEntityManager();

            manager.getTransaction().begin();
            entity = manager.merge(entity); // Si no está en el contexto de persistencia, attach la entidad
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

    /**
     * Metodo que busca los nombres de un artista
     * Este metoso esta hecho en especifico para mostrar los nombres de los artistas en un comboBox
     * @return todos los nombres de los artistas
     */
    public List<String> findNames() {
        List<String> names = null;

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<String> query = manager.createQuery(
                    "SELECT a.name FROM Artist a",
                    String.class
            );

            names = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return names;
    }

    /**
     * Metodo que filtra los artistas por nacionalidad
     * @param nationality un enun que es un atributo del artista
     * @return los artistas depemdiendo de la nacionalidad a buscar
     */
    public List<Artist> findByNationality(Nationality nationality) {
        List<Artist> result = null;

        try {
            manager = Connection.getConnect().createEntityManager();

            TypedQuery<Artist> query = manager.createQuery(
                    "SELECT a FROM Artist a WHERE a.nationality = :nationality",
                    Artist.class
            );

            query.setParameter("nationality", nationality);

            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return result;
    }

    /**
     * metodo que convierte la nacionalidad a string
     * @param nationalityStr
     * @return : la nacionalidad convertida
     */
    private Nationality convertToNationality(String nationalityStr){
        try {
            return Nationality.valueOf(nationalityStr);
        } catch (IllegalArgumentException e) {
            // Handle the case where the string doesn't match any enum value.
            return Nationality.UNKNOWN; // You can define a default value for this case.
        }
    }


}
