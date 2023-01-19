package entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "employee")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @ManyToOne
    @JoinColumn(name = "transport_company_id", nullable = false)
    private TransportCompany transportCompany;

    @OneToMany(mappedBy = "employee")
    private Set<Qualification> qualification;

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

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    public Set<Qualification> getQualification() {
        return qualification;
    }

    public void setQualification(Set<Qualification> qualification) {
        this.qualification = qualification;
    }
}
