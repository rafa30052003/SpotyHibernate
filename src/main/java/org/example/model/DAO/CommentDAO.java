package org.example.model.DAO;

import org.example.conexion.Connection;
import org.example.interfaceDAO.iDAO;
import org.example.model.domain.Comment;
import org.example.model.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements iDAO<Comment, Object> {
    private static EntityManager manager;

    // Constructor
    public CommentDAO() {
        // Utiliza la conexión existente en lugar de crear una nueva EntityManagerFactory
        manager = Connection.getConnect().createEntityManager();
    }


    // Método para mostrar los comentarios de las listas
    public List<Comment> findCommentsByListId(int listId) {
        List<Comment> comments = new ArrayList<>();

        // Utilizar EntityManager para consultar entidades
        try {
            // Utiliza el nombre correcto de la clase de entidad en tu consulta
            String jpql = "SELECT c FROM Comment c WHERE c.id= :listId";
            List<Comment> resultList = manager.createQuery(jpql, Comment.class)
                    .setParameter("listId", listId)
                    .getResultList();

            comments.addAll(resultList);
        } catch (Exception e) {
            e.printStackTrace(); // Maneja las excepciones adecuadamente en tu aplicación
        }

        return comments;
    }

    /**
     * Método para añadir un comentario
     *
     * @return Si el comentario ha sido añadido
     */
    /**
     * Método para añadir un comentario
     *
     * @param c Comment a añadir
     * @return Si el comentario ha sido añadido
     */

    public boolean create(Comment c) {
        EntityTransaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = manager.getTransaction();
            transaction.begin();

            // Persistir el comentario en la base de datos
            manager.persist(c);

            // Commit de la transacción
            transaction.commit();
            return true;
        } catch (Exception e) {
            // Manejar la excepción y hacer rollback si es necesario
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Maneja las excepciones adecuadamente en tu aplicación
            return false;
        }
    }

    /**
     * Método para borrar un comentario
     *
     * @return Si el comentario ha sido borrado
     */
    /**
     * Método para borrar un comentario
     *
     * @param c Comment a borrar
     * @return Si el comentario ha sido borrado
     */

    public void delete(Comment c) {
        EntityTransaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = manager.getTransaction();
            transaction.begin();

            // Buscar el comentario en la base de datos
            Comment commentToDelete = manager.find(Comment.class, c.getId());

            if (commentToDelete != null) {
                // Si el comentario existe, eliminarlo
                manager.remove(commentToDelete);
            }

            // Commit de la transacción
            transaction.commit();
        } catch (Exception e) {
            // Manejar la excepción y hacer rollback si es necesario
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Maneja las excepciones adecuadamente en tu aplicación
        }
    }


    @Override
    public List<Comment> findAll() {
        // Implementa la lógica de búsqueda de todos los comentarios
        return null;
    }

    @Override
    public Comment findById(Object id) {
        try {
            int commentId = (int) id;

            // Utilizar EntityManager para buscar el comentario por ID
            Comment comment = manager.find(Comment.class, commentId);

            return comment;
        } catch (Exception e) {
            e.printStackTrace(); // Maneja las excepciones adecuadamente en tu aplicación
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public Comment save(Comment entity) {
        // Implementa la lógica de guardado según tus requisitos
        return null;
    }

    @Override
    public boolean insert(User user) {
        return false;
    }


    public void close() {
        Connection.close();
    }


}
