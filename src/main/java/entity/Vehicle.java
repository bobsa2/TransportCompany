package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import util.Messages;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehicle")

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Type" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "type")
    private String type;

    @NotNull(message = "Type" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "brand")
    private String brand;


    @NotNull(message = "Type" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "transport_company_id", nullable = true)
    private TransportCompany transportCompany;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }
}
