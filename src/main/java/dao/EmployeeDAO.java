package dao;

import entity.Client;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

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
    public void update(int id, Employee employee) {
        entityManager.getTransaction().begin();

        Employee retrievedEmployee = entityManager.find(entity.Employee.class, id);
        retrievedEmployee.setName(employee.getName());
        retrievedEmployee.setIncome(employee.getIncome());
        //update transport company

        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        entityManager.getTransaction().begin();

        Employee retrievedEmployee = entityManager.find(Employee.class, id);

        entityManager.remove(retrievedEmployee);
        entityManager.getTransaction().commit();
    }
}
