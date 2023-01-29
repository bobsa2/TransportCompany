package entity;

import jakarta.persistence.*;
import util.Messages;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "qualification")

public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name" + Messages.NOT_NULL_MESSAGE)
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "qualifications")
    private Set<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
