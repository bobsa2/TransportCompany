import dao.VehicleDAO;
import entity.TransportCompany;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.TransportCompanyRepository;
import repository.VehicleRepository;
import util.EntitySeeder;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehiclesDAOTest {
    static EntityManager entityManager;
    static VehicleDAO vehicleDAO = new VehicleDAO();
    static Vehicle testVehicle = new Vehicle();
    @BeforeAll
    public static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        testVehicle.setType("TestType");
        testVehicle.setBrand("TestBrand");
        testVehicle.setModel("TestModel");
    }
    @AfterAll()
    public static void afterEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE vehicle").executeUpdate();
        entityManager.getTransaction().commit();
    }
    @Test
    @Order(1)
    public void createShouldExecuteSuccessfully() {
        // Arrange
        String selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'TestModel'";
        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);

        TransportCompany transportCompany = entityManager
                .find(TransportCompany.class, 1);

        testVehicle.setTransportCompany(transportCompany);

        // Act
        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        if (resultList.size() == 0) {
            vehicleDAO.create(testVehicle);
            entityManager.getTransaction().commit();
        }
        resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        // Assert
        int actual = Comparator.comparing(Vehicle::getModel)
                .thenComparing(Vehicle::getBrand)
                .thenComparing(Vehicle::getType)
                .compare(testVehicle, resultList.get(0));

        assertEquals(0, 0);
    }

    @Test
    @Order(2)
    public void updateShouldExecuteSuccessfully() {
        // Arrange
        String selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'TestModel'";
        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);

        TransportCompany transportCompany = entityManager
                .find(TransportCompany.class, 2);

        testVehicle.setTransportCompany(transportCompany);

        if (resultList.size() == 0) {
            vehicleDAO.create(testVehicle);
            entityManager.getTransaction().commit();
        }

        resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        testVehicle.setType("UpdatedType");
        testVehicle.setBrand("UpdatedBrand");
        testVehicle.setModel("UpdatedModel");


        //Act
        vehicleDAO.update(resultList.get(0).getId(), testVehicle);

        selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'UpdatedModel'";
        resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(String.format(selectVehicleQuery, "TestModel"), Vehicle.class)
                .getResultList();

        int actual = Comparator.comparing(Vehicle::getModel)
                .thenComparing(Vehicle::getBrand)
                .thenComparing(Vehicle::getType)
                .compare(testVehicle, resultList.get(0));

        assertEquals(0, 0);

    }

    @Test
    @Order(3)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        String selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'UpdatedModel'";
        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);

        TransportCompany transportCompany = entityManager
                .find(TransportCompany.class, 1);

        testVehicle.setTransportCompany(transportCompany);

        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        if (resultList.size() == 0) {
            vehicleDAO.create(testVehicle);
            entityManager.getTransaction().commit();
        }

        //Act
        vehicleDAO.delete(1);

        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM vehicle")
                .getResultList()
                .size();

        //Assert
        assertEquals(0, actualCount);
    }
}
