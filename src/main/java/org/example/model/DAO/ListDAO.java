package org.example.model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Album;
import org.example.model.domain.Song;
import org.example.model.domain.Playlist;

import javax.persistence.*;

public class ListDAO extends Playlist implements iDAO<Playlist, Integer> {
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

    public List<String> findAllNameLists() {
        EntityManager manager = null;
        List<String> nameLists = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            Query query = manager.createQuery("SELECT u.name_list FROM Playlist u", String.class);
            nameLists = query.getResultList();

            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }

        return nameLists;
    }


    @Override
    public List<Playlist> findAll() throws SQLException {
        return null;
    }

    @Override
    public Playlist findById(Integer id) throws SQLException {
        return null;
    }

    /**
     * funcion para guardar la list en la base de datos
     * @param entity
     * @return
     * @throws
     */

    @Override
    public Playlist save(Playlist entity) {
        Playlist result = null;

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

    @Override
    public void delete(Playlist entity) throws SQLException {

    }


    /**
     * funcion para borrar las listas
     * @param id
     * @throws SQLException
     */
    public void delete(int id) {
        EntityManager manager = null;

        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            Playlist playlistToDelete = manager.find(Playlist.class, id);
            if (playlistToDelete != null) {
                manager.remove(playlistToDelete);
                manager.getTransaction().commit();
                System.out.println("Playlist eliminada correctamente");
            } else {
                System.out.println("No se encontró la Playlist con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }
    }


    public List<String> findAllNameListsByUser(String loggedInUserName) {
        EntityManager manager = null;
        List<String> nameLists = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            String querys = "SELECT name_list FROM list WHERE name_user = :loggedUser";
            Query query = manager.createNativeQuery(querys);
            query.setParameter("loggedUser", loggedInUserName);

            nameLists = query.getResultList();
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }

        return nameLists;
    }


    public int findIdByName(String listName) {
        EntityManager manager = null;
        int listId = -1; // Valor predeterminado si no se encuentra la lista

        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            String idByNameQuery = "SELECT id FROM list WHERE name_list = :Name";
            Query query = manager.createNativeQuery(idByNameQuery);
            query.setParameter("Name", listName);

            List<?> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                // Si hay resultados, obtén el primer elemento de la lista
                listId = (int) resultList.get(0);
            }

            manager.getTransaction().commit();
        } catch (NoResultException e) {
            // Manejar el caso en el que no se encuentre ningún resultado
            e.printStackTrace();
        } catch (Exception e) {
            // Manejar otras excepciones
            e.printStackTrace();

            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }

        return listId;
    }

    /**
     * funcion para modificar las listas
     * @param entity
     * @return
     * @throws SQLException
     */
    public Playlist update(Playlist entity) throws SQLException {
        String INSERT ="INSERT INTO list (id, name_list, description, name_user)(:ID,:name_list,:description,:name_user)";
        Query query = manager.createNativeQuery(INSERT);
        query.setParameter("ID", entity.getId());
        query.setParameter("name_list", entity.getName_list());
        query.setParameter("description", entity.getDescription());
        query.setParameter("name_user", entity.getName_user());
        query.executeUpdate();
        return entity;
    }


    public List<String> findSubscribedLists(String userName) {
        EntityManager manager = null;
        List<String> subscribedLists = new ArrayList<>();

        try {
            manager = Connection.getConnect().createEntityManager();
            manager.getTransaction().begin();

            String FindySublists = "SELECT l.name_list FROM list l JOIN subscription s ON l.id = s.id_list WHERE s.name_user = :userName";
            Query query = manager.createNativeQuery(FindySublists);
            query.setParameter("userName", userName);

            subscribedLists = query.getResultList();
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (manager != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            if (manager != null) {
                manager.close();
            }
        }

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
                    return songs;
    }


    /**
     * Funcion para borrar la cancion de la lista
     * @param songId
     * @param listId
     * @throws SQLException
     */
    public void deleteSongOfList(int songId, int listId) throws SQLException {

    }



}
