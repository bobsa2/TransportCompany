package dao;

import entity.Transportation;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

public class TransportationDAO implements Dao<Transportation> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void create(Transportation transportation) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportation);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(long id, Transportation transportation) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            Transportation retrievedTransportation = entityManager.find(Transportation.class, id);

            retrievedTransportation.setTransportCompany(transportation.getTransportCompany());
            retrievedTransportation.setCargo(transportation.getCargo());
            retrievedTransportation.setPrice(transportation.getPrice());
            retrievedTransportation.setDateArrival(transportation.getDateArrival());
            retrievedTransportation.setDateDeparture(transportation.getDateDeparture());
            retrievedTransportation.setStartingPoint(transportation.getStartingPoint());
            retrievedTransportation.setEndingPoint(transportation.getEndingPoint());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            Transportation retrievedTransportation = entityManager.find(Transportation.class, id);

            entityManager.remove(retrievedTransportation);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM transportation WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, Transportation.class).getResultList().size();

        return resultCount > 0;
    }
}
