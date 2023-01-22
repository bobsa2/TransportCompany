import dao.ClientDAO;
import dao.EmployeeDAO;
import entity.*;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.ClientRepository;
import repository.TransportCompanyRepository;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

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
        entityManager.createNativeQuery("TRUNCATE TABLE Client").executeUpdate();
        entityManager.getTransaction().commit();


    }

    @BeforeAll
    public static void afterEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
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
        int actual = comparing(Client::getName)
                .thenComparing(Client::getAge)
                .thenComparing(Client::getHasPaid)
                .thenComparing(Client::getMail)
                .thenComparing(Client::getTelephone)
                .compare(testClient, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }

    @Test
    @Order(3)
    public void updateShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        String selectClientQuery = "SELECT * FROM Client AS v WHERE v.Name = 'UpdatedName'";

        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);
        EntitySeeder.seedRecords(ClientRepository.clients);

        testClient.setName("UpdatedName");
        testClient.setTelephone("UpdatedNumber");
        testClient.setMail("UpdateMail");
        testClient.setAge(19);

        //Act
        clientDAO.update(1, testClient);

        ArrayList<Client> resultList = (ArrayList<Client>) entityManager
                .createNativeQuery("SELECT * FROM Client", Client.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));

        //Assert
        int actual = Comparator.comparing(Client::getName)
                .thenComparing(Client::getAge)
                .thenComparing(Client::getAge)
                .thenComparing(Client::getHasPaid)
                .thenComparing(Client::getMail)
                .compare(testClient, resultList.get(0));

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }
    @Test
    @Order(1)
    public void deleteShouldExecuteSuccessfully(){
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
}
