package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;

import org.example.model.domain.Song;

import javax.persistence.EntityManager;
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
        List<Song> result = new ArrayList<>();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Song s = new Song();
                    s.setId(res.getInt("id"));
                    s.setName_song(res.getString("name_song"));
                    s.setGender(res.getString("gender"));
                    s.setNrepro(res.getInt("N_reproduction"));  // Corregido el nombre de la columna
                    s.setDuration(res.getString("duration"));
                    s.setArchive_song(res.getString("archive_song"));
                    String nameDisk = res.getString("name_disk");
                    AlbumDAO adao = new AlbumDAO(this.conn);

                    Album a = adao.findById(nameDisk); // Asumiendo que existe un método "findByName" en tu clase AlbumDAO.

                  

                    s.setAlbum(a);

                    result.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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








    public void updateReproductionCount(Song song) throws SQLException {
        if (song != null) {
            String updateQuery = "UPDATE song SET N_reproduction = ? WHERE id = ?";
            try (PreparedStatement pst = this.conn.prepareStatement(updateQuery)) {
                pst.setInt(1, song.getNrepro());
                pst.setInt(2, song.getId());
                pst.executeUpdate();
            }
        }
    }
}
