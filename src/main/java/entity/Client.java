package entity;

import jakarta.persistence.*;

import util.Messages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "client")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull(message = "Name" + Messages.NOT_NULL_MESSAGE)
    @Size(min = 3, max = 245, message = "Name" + Messages.VALID_SIZE_MESSAGE)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Age" + Messages.NOT_NULL_MESSAGE)
    @Min(value = 18, message = "Client should be an adult!")
    @Column(name = "age", nullable = false)
    private int age;

    @NotNull(message = "Mail" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "e-mail")
    private String mail;

    @NotNull(message = "Telephone" + Messages.NOT_NULL_MESSAGE)
    @Size(min = 5, max = 20, message = "Telephone"+ Messages.VALID_SIZE_MESSAGE)
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull(message = "Has paid" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "has_paid", nullable = false)
    private boolean hasPaid;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;
    @ManyToMany
    Set<TransportCompany> transportCompanies;

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

    public boolean getHasPaid() {
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

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }


}
