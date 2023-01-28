import dao.ClientDAO;
import dao.EmployeeDAO;
import entity.*;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.ClientRepository;
import util.Comparators;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.LongStream;

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDAOTest {
    static EntityManager entityManager;
    static ClientDAO clientDAO = new ClientDAO();
    static Client testClient = new Client();

    @BeforeAll
    public static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();

        testClient.setMail("test@gmail.com");
        testClient.setName("TestName");
        testClient.setHasPaid(false);
        testClient.setAge(2);
        testClient.setTelephone("(888)-(213)");
    }

    @BeforeEach
    public void beforeEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transportation").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE Client").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void afterEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transportation").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE Client").executeUpdate();
        entityManager.getTransaction().commit();

    }

    @Test
    @Order(2)
    public void createShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        String selectClientQuery = "SELECT * FROM Client AS v WHERE v.Name = 'TestName'";

        //Act
        clientDAO.create(testClient);
        ArrayList<Client> resultList = (ArrayList<Client>) entityManager
                .createNativeQuery(selectClientQuery, Client.class)
                .getResultList();


        //Assert
        int actual = Comparators.clientComparator
                .compare(testClient, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }

    @Test
    @Order(3)
    public void updateShouldExecuteSuccessfully() {
        //Arrange
        entityManager.clear();
        entityManager.getTransaction().begin();
        String selectClientQuery = "SELECT * FROM Client AS v WHERE v.Name = 'UpdatedName'";
        String selectTransportationQuery = "SELECT * FROM Transportation";

        seedTestTransportations();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Client updateClient = new Client();

        ArrayList<Transportation> transportations = (ArrayList<Transportation>) entityManager
                .createNativeQuery(selectTransportationQuery, Transportation.class)
                .getResultList();

        updateClient.setTransportation(transportations.get(0));
        updateClient.setName("TestName");
        updateClient.setTelephone("TestNumber");
        updateClient.setMail("TestMail");
        updateClient.setAge(19);

        entityManager.merge(updateClient);
        entityManager.getTransaction().commit();

        //Act
        updateClient.setName("UpdatedName");
        updateClient.setTelephone("UpdatedNumber");
        updateClient.setMail("UpdatedMail");
        updateClient.setAge(22);

        clientDAO.update(1, updateClient);

        ArrayList<Client> resultList = (ArrayList<Client>) entityManager
                .createNativeQuery(selectClientQuery, Client.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));

        //Assert
        int actual = Comparators.clientComparator
                .compare(updateClient, resultList.get(0));

        assertEquals(0, actual);
    }

    @Test
    @Order(1)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();


        Client client = new Client();
        client.setMail("312");
        client.setName("T123est");
        client.setHasPaid(true);
        client.setAge(35215);
        client.setTelephone("(251251251251213)");

        clientDAO.create(client);

        //Act
        clientDAO.delete(1);
        //Assert
        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM client")
                .getResultList()
                .size();

        entityManager.getTransaction().commit();
        assertEquals(0, actualCount);
    }

    private void seedTestTransportations() {
        LongStream.range(1, 7)
                .mapToObj((i) -> {
                    Transportation transportation = new Transportation();
                    transportation.setType(TransportationType.People);
                    transportation.setHasFinished(false);
                    transportation.setStartingPoint("TestStartingPoint" + i);
                    transportation.setEndingPoint("TestEndingPoint" + i);
                    transportation.setDateArrival(LocalDate.now().plusDays(i));
                    transportation.setDateDeparture(LocalDate.now());
                    return transportation;
                }).forEach(transportCompany -> {
                    entityManager.persist(transportCompany);
                });
    }
}
