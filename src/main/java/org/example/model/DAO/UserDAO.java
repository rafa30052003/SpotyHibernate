package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.User;
import org.example.model.domain.list;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDAO implements iDAO<User, String> {
    private final static String FIND_USER_BY_NAME = "SELECT u FROM User u WHERE u.name = :name";
    private final static String FIND_USER_BY_NAME_AND_PASSWORD = "SELECT u FROM User u WHERE u.name = :name AND u.password = :password";

    private final static String FINDBYNAME_ADMIN = "SELECT a.name FROM Admin a WHERE a.name = :name";
    private final static String INSERT_SUBSCRIPTION = "INSERT INTO Subscription(name_user, id_list) VALUES (:nameUser, :idList)";
    //private final static String deletesub = "DELETE FROM Subscription s WHERE s.nameUser = :nameUser AND s.idList = :idList";

    private static EntityManager manager;

    // Constructor
    public UserDAO() {
        // Utiliza la conexión existente en lugar de crear una nueva EntityManagerFactory
        manager = Connection.getConnect().createEntityManager();
    }

    /**
     * estas funcion muestre muestra todos los usuarios de la base de datos
     * @return la lista de usuarios
     */
    @Override
    public List<User> findAll() {
        Query query = manager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    /**
     * funcion para buscar todos los usuarios por el nombre
     * @param name es el nombre de el usuairio
     * @return el suaurio que salga en la busqueda
     */

    @Override
    public User findById(String name) {
        return findUserByName(name);
    }

    /**
     * funcion para insertar el usuario en la base de datos
     * @param user
     * @return true si el usuario se a insertado en labase de datos y un false si no se ha creado
     */


    @Override
    public boolean insert(User user) {
        try {
            manager.getTransaction().begin();
            manager.persist(user);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * funcion para eliminar el usuario de la base de datos
     * @param user
     * @return true si el usuario se elimina en la base de datos y un false si el usuario no se ha podido eliminar
     */

    @Override
    public void delete(User user) {
        try {
            manager.getTransaction().begin();
            User mergedUser = manager.merge(user);
            manager.remove(mergedUser);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }


    /**
     * funcion para poder modificar el usuario
     *
     * @param user
     * @return true si el usuario se mosifica en la base de datos y un fasle si el usuario no se apodido modificar en la base de datos
     */
    @Override
    public boolean update(User user) {
        try {
            manager.getTransaction().begin();
            manager.merge(user);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }


    /**
     *
     * @param entity
     * @return
     */
    @Override
    public User save(User entity) {
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public User findUserByName(String name) {
        Query query = manager.createQuery(FIND_USER_BY_NAME, User.class);
        query.setParameter("name", name);
        return (User) query.getSingleResult();
    }
    /**
     * funcion para hacer la subcrippcion de el usuario
     * @param idList

     */
    public void addSubscription(String nameUser, int idList) {
        try {
            manager.getTransaction().begin();

            Query query = manager.createNativeQuery(INSERT_SUBSCRIPTION);
            query.setParameter("nameUser", nameUser);
            query.setParameter("idList", idList);
            query.executeUpdate();

            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    /**
     * funcion para borrar la subcripcion para la lista
     * @param nameUser
     * @param idList

     */
    public void deleteSubscription(String nameUser, int idList) {
        try {
            manager.getTransaction().begin();

            User user = manager.find(User.class, nameUser);
            if (user != null) {
                List<list> lists = user.getLists();
                if (lists != null) {
                    lists.removeIf(list -> list.getId() == idList);

                }

                manager.getTransaction().commit();
                System.out.println("Suscripción eliminada correctamente");
            } else {
                // Si no se encuentra el usuario, hacer rollback
                if (manager.getTransaction().isActive()) {
                    manager.getTransaction().rollback();
                }
                System.out.println("No se encontró el usuario para eliminar la suscripción");
            }
        } catch (Exception e) {
            // Manejar la excepción y hacer rollback si es necesario
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            System.out.println("Error al eliminar la suscripción");
        }
    }
    public User findUserByNameAndPassword(String name, String password) {
        Query query = manager.createQuery(FIND_USER_BY_NAME_AND_PASSWORD, User.class);
        query.setParameter("name", name);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }



    /**
     * funcion para validar si el usuario es admin o no
     * @param name
     * @return true si el usuario es admin si no lo es devuelve un false

     */

    public boolean isAdmin(String name) {
        Query query = manager.createQuery(FINDBYNAME_ADMIN, User.class);
        query.setParameter("name", name);
        return !query.getResultList().isEmpty();
    }
    public void close() {
        Connection.close();
    }
}
