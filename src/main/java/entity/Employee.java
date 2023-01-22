package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "employee")

public class Employee implements Comparable<Employee>{

    public Employee(){
        this.qualifications = new HashSet<Qualification>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @ManyToOne
    @JoinColumn(name = "transport_company_id", nullable = true)
    private TransportCompany transportCompany;

    @OneToMany(mappedBy = "employee")
    private Set<Qualification> qualifications;

    @PreRemove
    public void removeRelationships(){
        this.qualifications.forEach((qualification -> qualification.setEmployee(null)));
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
        return qualifications;
    }

    public void setQualification(Set<Qualification> qualification) {
        this.qualifications = qualification;
    }

    @Override
    public int compareTo(Employee employee) {
        return this.income.compareTo(employee.income);
    }

    public static Comparator<Employee> employeeComparatorQualification = new Comparator<Employee>() {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            return Integer.compare(employee1.getQualification().size(), employee2.getQualification().size());
        }
    };

    public static Comparator<Employee> employeeComparatorIncome = new Comparator<Employee>() {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            return employee1.getIncome().compareTo(employee2.getIncome());
        }
    };

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", income=" + income +
                ", transportCompany=" + transportCompany +
                ", qualifications=" + qualifications +
                '}';
    }
}
