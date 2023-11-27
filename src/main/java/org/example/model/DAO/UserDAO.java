package org.example.model.DAO;

import org.example.interfaceDAO.iDAO;
import org.example.model.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UserDAO implements iDAO<User, String> {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    // Constructor
    public UserDAO() {
        // Código de inicialización
        emf = Persistence.createEntityManagerFactory("aplicacion");
        manager = emf.createEntityManager();
    }

    private final static String FIND_USER_BY_NAME = "SELECT u FROM User u WHERE u.name = :name";
    private final static String FINDBYNAME_ADMIN = "SELECT a.name FROM Admin a WHERE a.name = :name";
    private final static String INSERT_SUBSCRIPTION = "INSERT INTO Subscription(name_user, id_list) VALUES (:nameUser, :idList)";
    private final static String deletesub = "DELETE FROM Subscription s WHERE s.nameUser = :nameUser AND s.idList = :idList";


    @Override
    public List<User> findAll() {
        Query query = manager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(String id) {
        return manager.find(User.class, id);
    }

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

    @Override
    public boolean delete(User user) {
        try {
            manager.getTransaction().begin();
            User mergedUser = manager.merge(user);
            manager.remove(mergedUser);
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

    @Override
    public boolean update(User user) {
        try {
            manager.getTransaction().begin();
            manager.merge(user);
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
    public void deleteSubscription(String nameUser, int idList) {
        try {
            manager.getTransaction().begin();

            Query query = manager.createQuery(deletesub);
            query.setParameter("nameUser", nameUser);
            query.setParameter("idList", idList);
            int deletedCount = query.executeUpdate();

            if (deletedCount > 0) {
                // Commit de la transacción si se eliminó al menos un registro
                manager.getTransaction().commit();
                System.out.println("Suscripción eliminada correctamente");
            } else {
                // Si no se eliminó ningún registro, hacer rollback
                if (manager.getTransaction().isActive()) {
                    manager.getTransaction().rollback();
                }
                System.out.println("No se encontró la suscripción para eliminar");
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



    public boolean isAdmin(String name) {
        Query query = manager.createQuery(FINDBYNAME_ADMIN, User.class);
        query.setParameter("name", name);
        return !query.getResultList().isEmpty();
    }
}
