package dao;

import entity.Client;
import entity.Employee;
import entity.TransportCompany;
import entity.Transportation;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

import java.util.ArrayList;

public class TransportCompanyDAO implements Dao<TransportCompany> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public TransportCompanyDAO() {
    }

    @Override
    public void create(TransportCompany transportCompany) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportCompany);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(long id, TransportCompany transportCompany) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            TransportCompany retrievedTransportCompany = entityManager.find(entity.TransportCompany.class, id);
            retrievedTransportCompany.setName(transportCompany.getName());
            retrievedTransportCompany.setAddress(transportCompany.getAddress());
            retrievedTransportCompany.setTotalIncome(transportCompany.getTotalIncome());
            retrievedTransportCompany.setVehicles(transportCompany.getVehicles());
            retrievedTransportCompany.setEmployees(transportCompany.getEmployees());
            retrievedTransportCompany.setTransportations(transportCompany.getTransportations());
            retrievedTransportCompany.setClients(transportCompany.getClients());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        if (isValid(id)) {

            TransportCompany retrievedTransportCompany = entityManager.find(TransportCompany.class, id);

            String query = "SELECT * FROM %s WHERE transport_company_id = %s";

            ArrayList<Employee> employees = (ArrayList<Employee>) entityManager
                    .createNativeQuery(String.format(query, "Employee", id), Employee.class)
                    .getResultList();

            if (employees.size() > 0) {
                entityManager.getTransaction().begin();
                employees.forEach(employee -> {
                    employee.setTransportCompany(null);
                });
                entityManager.getTransaction().commit();
            }
            ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) entityManager
                    .createNativeQuery(String.format(query, "Vehicle", id), Vehicle.class)
                    .getResultList();

            if (vehicles.size() > 0) {
                entityManager.getTransaction().begin();
                vehicles.forEach(vehicle -> {
                    vehicle.setTransportCompany(null);
                });
                entityManager.getTransaction().commit();
            }
            ArrayList<Transportation> transportations = (ArrayList<Transportation>) entityManager
                    .createNativeQuery(String.format(query, "Transportation", id), Transportation.class)
                    .getResultList();

            if (transportations.size() > 0) {
                entityManager.getTransaction().begin();
                transportations.forEach(transportation -> {
                    transportation.setTransportCompany(null);
                });
                entityManager.getTransaction().commit();
            }
            entityManager.getTransaction().begin();
            entityManager.remove(retrievedTransportCompany);
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
