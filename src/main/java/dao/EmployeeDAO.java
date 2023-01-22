package dao;

import entity.Client;
import entity.Employee;
import entity.Qualification;
import entity.TransportCompany;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EmployeeDAO implements Dao<Employee> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void create(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(long id, Employee employee) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            Employee retrievedEmployee = entityManager.find(Employee.class, id);
            retrievedEmployee.setName(employee.getName());
            retrievedEmployee.setIncome(employee.getIncome());
            retrievedEmployee.setTransportCompany(employee.getTransportCompany());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        if (isValid(id)) {

            Employee retrievedEmployee = entityManager.find(Employee.class, id);

            String qualificationsQuery = "SELECT * FROM Qualification WHERE employee_id = " + id;
            ArrayList<Qualification> qualifications = (ArrayList<Qualification>) entityManager
                    .createNativeQuery(qualificationsQuery).getResultList();
            if (qualifications.size() > 0) {
                qualifications.forEach(qualification -> {
                    qualification.setEmployee(null);
                });
            }
            entityManager.getTransaction().begin();
            entityManager.remove(retrievedEmployee);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM employee WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, Employee.class).getResultList().size();

        return resultCount > 0;
    }

    public ArrayList<Employee> getEmployees() {
        String idQuery = "SELECT * FROM employee";

        return (ArrayList<Employee>) entityManager.createNativeQuery(idQuery, Employee.class).getResultList();
    }

    public void sortEmployeesByQualification(){

        ArrayList<Employee> employees = getEmployees();

        employees.sort(Employee.employeeComparatorQualification);
        employees.forEach(System.out::println);
    }

    public void sortEmployeesByIncome(){

        ArrayList<Employee> employees = getEmployees();

        employees.sort(Employee.employeeComparatorIncome);
        employees.forEach(System.out::println);
    }

    public void filterEmployeesByQualification(Qualification qualification) {

        ArrayList<Employee> employees = getEmployees();
        employees.stream().filter(employee -> employee.getQualification().contains(qualification)).forEach(System.out::println);

    }

    public void filterEmployeesByIncome(BigDecimal value) {

        ArrayList<Employee> employees = getEmployees();
        employees.stream().filter(employee -> employee.getIncome().compareTo(value) > 0).forEach(System.out::println);

    }
}
