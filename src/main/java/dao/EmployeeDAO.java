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
            entityManager.getTransaction().begin();

            Employee retrievedEmployee = entityManager.find(Employee.class, id);

            entityManager.remove(retrievedEmployee);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM client WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, Client.class).getResultList().size();

        return resultCount > 0;
    }
}
