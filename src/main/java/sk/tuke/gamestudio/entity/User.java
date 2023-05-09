package sk.tuke.gamestudio.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "name" } ) } )
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Date registeredOn;
    public User(String name, String password, Date registeredOn) {
        this.name = name;
        this.password = password;
        this.registeredOn = registeredOn;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", registeredOn=" + registeredOn +
                '}';
    }
}
