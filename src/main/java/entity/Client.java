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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Set<TransportCompany> getTransportCompanies() {
        return transportCompanies;
    }

    public void setTransportCompanies(Set<TransportCompany> transportCompanies) {
        this.transportCompanies = transportCompanies;
    }

    @ManyToMany
    Set<TransportCompany> transportCompanies;
}
