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

        testVehicle.setType("TestType");
        testVehicle.setBrand("TestBrand");
        testVehicle.setModel("TestModel");
    }
    @BeforeEach()
    public void beforeEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE vehicle").executeUpdate();
        entityManager.getTransaction().commit();
    }
    @AfterEach
    public void afterEach(){
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE vehicle").executeUpdate();
        entityManager.getTransaction().commit();
    }
    @Test
    @Order(1)
    public void createShouldExecuteSuccessfully() {
        // Arrange
        entityManager.getTransaction().begin();
        String selectVehicleQuery = "SELECT * FROM Vehicle Where Model = 'TestModel'";

        // Act
        vehicleDAO.create(testVehicle);

        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        // Assert
        int actual = Comparator.comparing(Vehicle::getModel)
                .thenComparing(Vehicle::getBrand)
                .thenComparing(Vehicle::getType)
                .compare(testVehicle, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, 0);
    }

    @Test
    @Order(3)
    public void updateShouldExecuteSuccessfully() {
        // Arrange
        entityManager.getTransaction().begin();
        String selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'UpdatedModel'";

        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);
        EntitySeeder.seedRecords(VehicleRepository.vehicles);

        testVehicle.setType("UpdatedType");
        testVehicle.setBrand("UpdatedBrand");
        testVehicle.setModel("UpdatedModel");

        //Act
        vehicleDAO.update(1, testVehicle);

        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));

        int actual = Comparator.comparing(Vehicle::getModel)
                .thenComparing(Vehicle::getBrand)
                .thenComparing(Vehicle::getType)
                .compare(testVehicle, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);

    }

    @Test
    @Order(2)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();

        vehicleDAO.create(testVehicle);

        //Act
        vehicleDAO.delete(1);

        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM vehicle")
                .getResultList()
                .size();

        //Assert
        entityManager.getTransaction().commit();
        assertEquals(0, actualCount);
    }
}
