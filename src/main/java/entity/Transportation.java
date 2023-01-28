package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transportation")

public class Transportation implements Comparable<Transportation> {

    public Transportation(){
        this.clients = new HashSet<Client>();
        this.cargos = new HashSet<Cargo>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "hasFinished")
    private boolean hasFinished;

    @Column(name = "price")
    private double price;

    @Column(name = "type")
    private TransportationType type;

    @OneToMany(mappedBy = "transportation")
    private Set<Cargo> cargos;

    @OneToMany(mappedBy = "transportation")
    private Set<Client> clients;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name = "ending_point")
    private String endingPoint;

    @Column(name = "date_departure")
    private LocalDate dateDeparture;

    @Column(name = "date_arrival")
    private LocalDate dateArrival;

    @ManyToOne
    @JoinColumn(name = "transport_company_id")
    private TransportCompany transportCompany;

    @PreRemove
    public void removeRelationships(){
        this.clients.forEach(client -> client.setTransportation(null));
        this.cargos.forEach(cargo -> cargo.setTransportation(null));
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public LocalDate getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(LocalDate dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public LocalDate getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(LocalDate dateArrival) {
        this.dateArrival = dateArrival;
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getHasFinished() {
        return this.hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public Set<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(Set<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public int compareTo(Transportation transportation) {
        return this.endingPoint.compareTo(transportation.endingPoint);
    }

    @Override
    public String toString() {
        return "Transportation{" +
                ", price=" + price +
                ", startingPoint='" + startingPoint + '\'' +
                ", endingPoint='" + endingPoint + '\'' +
                ", dateDeparture=" + dateDeparture +
                ", dateArrival=" + dateArrival +
                ", transportCompany=" + transportCompany +
                '}';
    }
}
