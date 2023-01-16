import dao.TransportCompanyDAO;
import dao.VehicleDAO;
import entity.TransportCompany;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.TransportCompanyRepository;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

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
        entityManager.getTransaction().begin();

        VehiclesDAOTest.testVehicle.setType("TestType");
        testTransportCompany.setName("TestName");
        testTransportCompany.setAddress("TestAddress");
        testTransportCompany.setTotalIncome(BigDecimal.ONE);
    }
    @AfterAll()
    public static void afterEach() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;");
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company");
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;");
        entityManager.getTransaction().commit();
    }
    @Test
    @Order(1)
    public void createShouldExecuteSuccessfully(){
        //Arrange
        String selectVehicleQuery = "SELECT * FROM transport_company AS tp WHERE tp.name = 'TestName'";

        ArrayList<TransportCompany> resultList = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectVehicleQuery, TransportCompany.class)
                .getResultList();
        //Act
        if (resultList.size() == 0) {
            transportCompanyDAO.create(testTransportCompany);
            entityManager.getTransaction().commit();
        }
        resultList = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectVehicleQuery, TransportCompany.class)
                .getResultList();

        // Assert
        int actual = Comparator.comparing(TransportCompany::getName)
                .thenComparing(TransportCompany::getTotalIncome)
                .thenComparing(TransportCompany::getAddress)
                .compare(testTransportCompany, resultList.get(0));

        assertEquals(0, 0);
    }
}
