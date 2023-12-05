package org.example.model.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "NAME", referencedColumnName = "NAME")
public class Admin extends User implements Serializable {

    private  static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NAME")
    private  String name;

    public Admin(String name) {
        this.name = name;
    }

    public Admin() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                '}';
    }
}