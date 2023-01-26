import dao.TransportCompanyDAO;
import dao.VehicleDAO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.TransportCompanyRepository;
import repository.TransportationRepository;
import util.Comparators;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransportCompanyDAOTest {
    static EntityManager entityManager;
    static TransportCompanyDAO transportCompanyDAO = new TransportCompanyDAO();
    static TransportCompany testTransportCompany = new TransportCompany();

    @BeforeAll
    public static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();

        VehiclesDAOTest.testVehicle.setType("TestType");
        testTransportCompany.setName("TestName");
        testTransportCompany.setAddress("TestAddress");
        testTransportCompany.setTotalIncome(BigDecimal.ONE);
    }

    @BeforeEach()
    public void beforeEach() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @AfterAll()
    public static void afterEach() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    @Order(1)
    public void createShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        String selectedTransportCompanyQuery = "SELECT * FROM transport_company AS tp WHERE tp.name = 'TestName'";

        //Act
        transportCompanyDAO.create(testTransportCompany);

        ArrayList<TransportCompany> resultList = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectedTransportCompanyQuery, TransportCompany.class)
                .getResultList();

        // Assert
        int actual = Comparators.transportCompanyComparator
                .compare(testTransportCompany, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }

    @Test
    @Order(2)
    public void updateShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        String selectedTransportCompanyQuery = "SELECT * FROM transport_company AS tp WHERE tp.name = 'UpdatedName'";
        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);

        testTransportCompany.setName("UpdatedName");
        testTransportCompany.setTotalIncome(BigDecimal.TEN);
        testTransportCompany.setAddress("UpdatedAddress");
        testTransportCompany.setClients(new HashSet<Client>());
        testTransportCompany.setEmployees(new HashSet<Employee>());
        testTransportCompany.setVehicles(new HashSet<Vehicle>());
        testTransportCompany.setTransportations(new HashSet<Transportation>());

        //Act
        transportCompanyDAO.update(1, testTransportCompany);
        ArrayList<TransportCompany> resultList = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectedTransportCompanyQuery, TransportCompany.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));
        //Assert
        int actual = Comparators.transportCompanyComparator
                .compare(testTransportCompany, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }

    @Test
    @Order(3)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        transportCompanyDAO.create(testTransportCompany);

        //Act
        transportCompanyDAO.delete(1);
        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM transport_company")
                .getResultList()
                .size();

        //Assert
        entityManager.getTransaction().commit();
        assertEquals(0, actualCount);

    }
}
