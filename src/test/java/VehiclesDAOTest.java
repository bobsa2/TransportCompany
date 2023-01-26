import dao.VehicleDAO;
import entity.Employee;
import entity.TransportCompany;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.TransportCompanyRepository;
import repository.VehicleRepository;
import util.Comparators;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.LongStream;

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

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void afterEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE vehicle").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
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
        int actual = Comparators.vehicleComparator
                .compare(testVehicle, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, 0);
    }

    @Test
    @Order(3)
    public void updateShouldExecuteSuccessfully() {
        // Arrange
        entityManager.clear();
        entityManager.getTransaction().begin();
        String selectVehicleQuery = "SELECT * FROM Vehicle AS v WHERE v.model = 'UpdatedModel'";
        String selectTransportCompanyQuery = "SELECT * FROM transport_company";

        seedTestTransportCompanies();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Vehicle testUpdateVehicle = new Vehicle();

        testUpdateVehicle.setType("UpdateTestType");
        testUpdateVehicle.setBrand("UpdateTestBrand");
        testUpdateVehicle.setModel("UpdateTestModel");

        ArrayList<TransportCompany> transportCompanies = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectTransportCompanyQuery, TransportCompany.class)
                .getResultList();

        testUpdateVehicle.setTransportCompany(transportCompanies.get(0));

        entityManager.merge(testUpdateVehicle);
        entityManager.getTransaction().commit();

        //Act
        testUpdateVehicle.setModel("UpdatedModel");
        testUpdateVehicle.setBrand("UpdatedBrand");
        vehicleDAO.update(1, testUpdateVehicle);

        ArrayList<Vehicle> resultList = (ArrayList<Vehicle>) entityManager
                .createNativeQuery(selectVehicleQuery, Vehicle.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));

        int actual = Comparators.vehicleComparator
                .compare(testUpdateVehicle,resultList.get(0));

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

    private void seedTestTransportCompanies() {
        LongStream.range(1, 7)
                .mapToObj((i) -> {
                    TransportCompany transportCompany = new TransportCompany();
                    transportCompany.setAddress("TestVehicleAddress" + i);
                    transportCompany.setTotalIncome(BigDecimal.valueOf((Long) i));
                    transportCompany.setName("TestVehicleAddress" + i);
                    return transportCompany;
                }).forEach(transportCompany -> {
                    entityManager.persist(transportCompany);
                });
    }
}
