package dao;

import entity.TransportCompany;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

public class VehicleDAO implements Dao<Vehicle> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void create(Vehicle vehicle) {
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(int id, Vehicle vehicle) {
        entityManager.getTransaction().begin();
        Vehicle retrievedVehicle = entityManager.find(entity.Vehicle.class, id);
        retrievedVehicle.setBrand(vehicle.getBrand());
        retrievedVehicle.setType(vehicle.getType());
        retrievedVehicle.setModel(vehicle.getModel());
        retrievedVehicle.setTransportCompany(vehicle.getTransportCompany());
        entityManager.getTransaction().commit();

    }

    @Override
    public void delete(int id) {
        entityManager.getTransaction().begin();

        Vehicle retrievedVehicle = entityManager.find(Vehicle.class, id);

        entityManager.remove(retrievedVehicle);
        entityManager.getTransaction().commit();
    }
}
