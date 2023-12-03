package org.example.conexion;

import org.example.Assets.Loggers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
    public static EntityManagerFactory emf;
    private static Connection _newInstance;

    private Connection() {
        emf = Persistence.createEntityManagerFactory("aplicacion");
        if(emf==null) {
            Loggers.LogsSevere("No se puede establecer la conexion");
        }
    }
    public static EntityManagerFactory getConnect() {
        if(_newInstance==null) {
            _newInstance=new Connection();
        }
        return emf;
    }

    public static void close() {
        if(emf != null) {
            emf.close();
        }
    }

}
