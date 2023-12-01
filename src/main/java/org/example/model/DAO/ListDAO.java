package org.example.model.DAO;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Song;
import org.example.model.domain.User;
import org.example.model.domain.list;

import javax.persistence.*;

public class ListDAO extends list implements iDAO<list, Integer> {
    private final static String FINDALL ="SELECT id, description, name_list, name_user FROM list";

    private final static   String ListSub ="SELECT l.name_list FROM list l JOIN subscription s ON l.id = s.id_list WHERE s.name_user  = ?";


    private final static String FINBYID ="SELECT s.* FROM song s JOIN song_list sl ON s.id = sl.id_song JOIN list l ON sl.id_list = l.id WHERE l.id = ?";

    private final static String INSERT ="INSERT INTO list (id, name_list, description, name_user) (:id,:name_list,:description,:name_user)";

    private final static String INSERTSonginList ="INSERT INTO song_list (id_list,id_song)(:is_list, :id_song)";
    private final static String UPDATE ="UPDATE id = ?, , description = ?, , name_list= ?, name_user  = ? WHERE id=?";
    private final static String DELETE ="DELETE FROM list WHERE id= :idList";
    private final static String DELETESongofList ="DELETE FROM song_list  WHERE id_song=? and id_list=?";

    /**
     * funcion para mostrar las listas de la base de datos
     * @return
     * @throws SQLException
     */

    private EntityManager manager;
    private static EntityManagerFactory emf;
    public ListDAO() {
        // Código de inicialización
        emf = Persistence.createEntityManagerFactory("aplicacion");
        manager = emf.createEntityManager();
    }
    public List<String> findAllNameLists() throws SQLException {
        manager = Connection.getConnect().createEntityManager();
        List<String> nameLists = new ArrayList<>();
        Query query = manager.createQuery("SELECT u FROM list u", list.class);
        query.executeUpdate();
        return query.getResultList();
    }


    @Override
    public List<list> findAll() throws SQLException {
        return null;
    }

    @Override
    public list findById(Integer id) throws SQLException {
        return null;
    }

    /**
     * funcion para guardar la list en la base de datos
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public list save(list entity) throws SQLException {
        if (entity != null) {
            Query query = manager.createNativeQuery(INSERT);
            query.setParameter("id", entity.getId());
            query.setParameter("name_list", entity.getName_list());
            query.setParameter("description", entity.getDescription());
            query.setParameter("name_user", entity.getName_user());
            query.executeUpdate();

        }
        return entity;
    }

    @Override
    public void delete(list entity) throws SQLException {

    }

    /**
     * funcion para borrar las listas
     * @param id
     * @throws SQLException
     */
    public void delete(int id) throws SQLException {
        manager = Connection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        String Hola = "hola";
        Query query = manager.createNativeQuery("DELETE FROM list WHERE id = VALUE (:idList)",List.class);
        query.setParameter("idList", id);
        query.executeUpdate();
    }

    public List<String> findAllNameListsByUser(String loggedInUserName) throws SQLException {

        String querys = "SELECT name_list FROM list WHERE name_user = VALUE (:loggedUser)";
        Query query = manager.createNativeQuery(querys);
        query.setParameter("loggedUser", loggedInUserName);
        List<String> nameLists = query.getResultList();
        query.executeUpdate();
        return nameLists;
    }


    public int findIdByName(String listName) throws SQLException {
        int listId = -1; // Valor predeterminado si no se encuentra la lista

        String idbyname = "SELECT id FROM list WHERE name_list = VALUE (:Name)";
        Query query = manager.createNativeQuery(idbyname);
        query.setParameter("Name", listName);
        query.executeUpdate();
        int id = query.getFirstResult();

        return listId;
    }

    /**
     * funcion para modificar las listas
     * @param entity
     * @return
     * @throws SQLException
     */
    public list update(list entity) throws SQLException {
        String INSERT ="INSERT INTO list (id, name_list, description, name_user)(:ID,:name_list,:description,:name_user)";
        Query query = manager.createNativeQuery(INSERT);
        query.setParameter("ID", entity.getId());
        query.setParameter("name_list", entity.getName_list());
        query.setParameter("description", entity.getDescription());
        query.setParameter("name_user", entity.getName_user());
        query.executeUpdate();
        return entity;
    }


    public List<String> findSubscribedLists(String userName) throws SQLException {
        List<String> subscribedLists = new ArrayList<>();
        String FindySublists = "SELECT l.name_list FROM list l JOIN subscription s ON l.id = s.id_list WHERE s.name_user  = value(:userName)";
        Query query = manager.createNativeQuery(FindySublists);
        query.setParameter("userName", userName);
        query.executeUpdate();
        subscribedLists = query.getResultList();
        return subscribedLists;
    }

    /**
     * fUNCION PARA GUARDAR LA CANCION EN LAS LISTA QUE SELECCIONAMOS
     * @param idList
     * @param songId
     * @throws SQLException
     */
    public void insertSongInList(int idList, int songId) throws SQLException {
        // La conexión a la base de datos debería estar establecida antes de llamar a este método
        Query query = manager.createNativeQuery(INSERTSonginList);
            query.setParameter("id_list", idList);
            query.setParameter("id_song", songId);
            query.executeUpdate();
        }



    public List<Song> findSongsByListId(int listId) throws SQLException {
        List<Song> songs = new ArrayList<>();

                    Song song = new Song();
                 /*   song.setDuration(rs.getString("duration"));
                    // También necesitarás establecer el álbum al que pertenece esta canción aquí
                    Album album = new Album();
                    album.setName(rs.getString("name_disk"));
                    song.setAlbum(album);
                    songs.add(song);*/
                    return songs;
    }


    /**
     * Funcion para borrar la cancion de la lista
     * @param songId
     * @param listId
     * @throws SQLException
     */
    public void deleteSongOfList(int songId, int listId) throws SQLException {
        /*try (PreparedStatement pst = conn.prepareStatement(DELETESongofList)) {
            pst.setInt(1, songId);
            pst.setInt(2, listId);

            // Ejecutar la consulta de eliminación
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("La canción se eliminó correctamente de la lista.");
            } else {
                System.out.println("No se encontró la relación entre la canción y la lista.");
                // Puedes manejar este caso según tus necesidades
            }
        }*/
    }



}
