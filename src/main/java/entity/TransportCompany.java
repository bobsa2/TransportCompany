package entity;

import jakarta.persistence.*;
import util.Messages;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transport_company")

public class TransportCompany implements Comparable<TransportCompany>{

    public TransportCompany(){
        this.clients = new HashSet<Client>();
        this.vehicles = new HashSet<Vehicle>();
        this.employees = new HashSet<Employee>();
        this.transportations = new HashSet<Transportation>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Address" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull(message = "Total Income" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "total_income")
    private BigDecimal totalIncome;

    @OneToMany(mappedBy = "transportCompany")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "transportCompany")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "transportCompany")
    private Set<Transportation> transportations;

    @ManyToMany(mappedBy = "transportCompanies")
    private Set<Client> clients;
    @PreRemove
    public void removeRelationships(){
        System.out.println("Before removing");
        this.employees.forEach((employee) -> employee.setTransportCompany(null));
        this.vehicles.forEach((vehicle) -> vehicle.setTransportCompany(null));
        this.transportations.forEach((transportation) -> transportation.setTransportCompany(null));
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Transportation> getTransportations() {
        return transportations;
    }

    public void setTransportations(Set<Transportation> transportations) {
        this.transportations = transportations;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public static Comparator<TransportCompany> transportCompanyComparatorIncome = new Comparator<TransportCompany>() {
        @Override
        public int compare(TransportCompany transportCompany1, TransportCompany transportCompany2) {
            return transportCompany1.totalIncome.compareTo(transportCompany2.totalIncome);
        }
    };

    public static Comparator<TransportCompany> transportCompanyComparatorName = new Comparator<TransportCompany>() {
        @Override
        public int compare(TransportCompany transportCompany1, TransportCompany transportCompany2) {
            return transportCompany1.name.compareTo(transportCompany2.name);
        }
    };

    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", totalIncome=" + totalIncome +
                '}';
    }

    @Override
    public int compareTo(TransportCompany transportCompany) {
        return this.name.compareTo(transportCompany.name);
    }
}
