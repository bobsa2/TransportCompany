package entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "client")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "e-mail")
    private String mail;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "has_paid", nullable = false)
    private boolean hasPaid;

    @ManyToMany
    Set<TransportCompany> transportCompanies;
}
