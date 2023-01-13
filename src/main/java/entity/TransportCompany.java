package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "transport_company")

public class TransportCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

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

}
