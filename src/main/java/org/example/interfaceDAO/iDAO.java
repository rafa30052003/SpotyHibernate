package org.example.interfaceDAO;

import org.example.model.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface iDAO<T,K> {
    List<T> findAll() throws SQLException;
    T findById (K id) throws SQLException;

   // boolean update(User user);

    T save(T entity) throws SQLException ;


    //boolean insert(User user);

    void delete(T entity) throws SQLException ;

}
