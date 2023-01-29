package entity;

import jakarta.persistence.*;
import util.Messages;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "employee")

public class Employee implements Comparable<Employee> {

    public Employee() {
        this.qualifications = new HashSet<Qualification>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name" + Messages.NOT_NULL_MESSAGE)
    @Size(min = 3, max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Income" + Messages.NOT_NULL_MESSAGE)
    @Min(value = 0, message = "Income should not be negative!")
    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @ManyToOne
    @JoinColumn(name = "transport_company_id", nullable = true)
    private TransportCompany transportCompany;

    @ManyToMany
    private Set<Transportation> transportations;

    @ManyToMany
    private Set<Qualification> qualifications;

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

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setQualification(Set<Qualification> qualification) {
        this.qualifications = qualification;
    }

    public Set<Transportation> getTransportations() {
        return transportations;
    }

    public void setTransportations(Set<Transportation> transportations) {
        this.transportations = transportations;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    @Override
    public int compareTo(Employee employee) {
        return this.income.compareTo(employee.income);
    }

    public static Comparator<Employee> employeeComparatorQualification = new Comparator<Employee>() {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            return Integer.compare(employee1.getQualifications().size(), employee2.getQualifications().size());
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
