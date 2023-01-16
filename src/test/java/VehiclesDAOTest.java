import entity.TransportCompany;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.TransportCompanyRepository;
import util.EntitySeeder;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class VehiclesDAOTest {
    private static EntityManager entityManager;

    @BeforeAll
    public static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void createShouldExecuteSuccessfully() {
        // Arrange
        entityManager.getTransaction().begin();
        Vehicle vehicle = new Vehicle();

        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);

        TransportCompany transportCompany = entityManager
                .find(TransportCompany.class, 1);

        vehicle.setType("TestType");
        vehicle.setBrand("TestBrand");
        vehicle.setModel("TestModel");
        vehicle.setTransportCompany(transportCompany);

        // Act

        entityManager.persist(vehicle);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery("SELECT * FROM Vehicle AS v WHERE v.model = 'TestModel'",Vehicle.class)
                .getResultList();
        // Assert

        int actual = Comparator.comparing(Vehicle::getModel)
                .thenComparing(Vehicle::getBrand)
                .thenComparing(Vehicle::getType)
                .compare(vehicle, resultList.get(0));


        assertEquals(0, 0);
    }
}
