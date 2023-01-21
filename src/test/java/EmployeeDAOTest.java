import dao.EmployeeDAO;
import dao.TransportCompanyDAO;
import dao.VehicleDAO;
import entity.*;
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
public class EmployeeDAOTest {
    static EntityManager entityManager;
    static EmployeeDAO employeeDAO = new EmployeeDAO();
    static Employee testEmployee = new Employee();

    @BeforeAll
    public static void init() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
        entityManager = entityManagerFactory.createEntityManager();

        testEmployee.setIncome(BigDecimal.TEN);
        testEmployee.setName("TestName");
    }
    @BeforeEach
    public void beforeEach(){
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();

    }
    @AfterEach
    public void afterEach(){
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
        // Arrange
        String selectEmployeeQuery = "SELECT * FROM Employee AS v WHERE v.Name = 'TestName'";

        TransportCompany testTransportCompany = new TransportCompany();
        testTransportCompany.setName("TestName");
        testTransportCompany.setAddress("TestAddress");
        testTransportCompany.setTotalIncome(BigDecimal.ONE);

        new TransportCompanyDAO().create(testTransportCompany);

        testEmployee.setTransportCompany(testTransportCompany);

        // Act
        employeeDAO.create(testEmployee);
        ArrayList<Employee> resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        // Assert
        int actual = Comparator.comparing(Employee::getName)
                .thenComparing(Employee::getIncome)
                .compare(testEmployee, resultList.get(0));

        assertEquals(0, 0);
    }

    @Test
    @Order(2)
    public void updateShouldExecuteSuccessfully() {
        // Arrange
        String selectEmployeeQuery = "SELECT * FROM Employee AS v WHERE v.Name = 'TestName'";

        ArrayList<Employee> resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        TransportCompany testTransportCompany = new TransportCompany();
        testTransportCompany.setName("TestName");
        testTransportCompany.setAddress("TestAddress");
        testTransportCompany.setTotalIncome(BigDecimal.ONE);

        if (entityManager.find(TransportCompany.class,1) == null){
            entityManager.getTransaction().begin();
            entityManager.persist(testTransportCompany);
            entityManager.getTransaction().commit();
        }

        TransportCompany transportCompany = (TransportCompany) entityManager
                .find(TransportCompany.class, (long)1);

        testEmployee.setTransportCompany(transportCompany);

        entityManager.getTransaction().begin();
        employeeDAO.create(testEmployee);
        entityManager.getTransaction().commit();

        resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        testEmployee.setName("UpdatedName");
        testEmployee.setIncome(BigDecimal.ONE);


        //Act
        employeeDAO.update(resultList.get(0).getId(), testEmployee);

        selectEmployeeQuery = "SELECT * FROM Employee AS v WHERE v.Name = 'UpdatedName'";
        resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        //Actual
        int actual = Comparator.comparing(Employee::getName)
                .thenComparing(Employee::getIncome)
                .compare(testEmployee, resultList.get(0));

        assertEquals(0, 0);

    }
    @Test
    @Order(3)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        String selectEmployeeQuery = "SELECT * FROM TransportCompany AS v WHERE v.Name = 'UpdatedName'";

        TransportCompany testTransportCompany = new TransportCompany();

        testTransportCompany.setName("TestName");
        testTransportCompany.setAddress("TestAddress");
        testTransportCompany.setTotalIncome(BigDecimal.ONE);

        if (entityManager.find(TransportCompany.class,1) == null){
            entityManager.getTransaction().begin();
            entityManager.persist(testTransportCompany);
            entityManager.getTransaction().commit();
        }
        TransportCompany transportCompany = (TransportCompany) entityManager
                .find(TransportCompany.class, (long)1);

        testEmployee.setTransportCompany(transportCompany);

        entityManager.getTransaction().begin();
        employeeDAO.create(testEmployee);
        entityManager.getTransaction().commit();

        //Act
        employeeDAO.delete(1);

        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM vehicle")
                .getResultList()
                .size();

        //Assert
        assertEquals(0, actualCount);
    }
}
