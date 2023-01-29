package entity;

import jakarta.persistence.*;
import util.Messages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name" + Messages.NOT_NULL_MESSAGE)
    @Size(min = 5, max = 268, message = "Name" + Messages.VALID_SIZE_MESSAGE)
    private String name;

    @NotNull(message = "Weight" + Messages.NOT_NULL_MESSAGE)
    @Min(1)
    private double weight;

    @ManyToOne()
    @JoinColumn(name = "transportation_id", nullable = true)
    private Transportation transportation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
