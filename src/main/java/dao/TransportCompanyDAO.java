package dao;

import entity.Client;
import entity.Employee;
import entity.TransportCompany;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

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
            entityManager.getTransaction().begin();

            TransportCompany retrievedTransportCompany = entityManager.find(TransportCompany.class, id);

            entityManager.remove(retrievedTransportCompany);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM TransportCompany WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, TransportCompany.class).getResultList().size();

        return resultCount > 0;
    }
}
