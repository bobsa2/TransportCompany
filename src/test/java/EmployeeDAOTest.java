import dao.EmployeeDAO;
import dao.TransportCompanyDAO;
import dao.VehicleDAO;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import repository.EmployeeRepository;
import repository.TransportCompanyRepository;
import util.EntitySeeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
    public void beforeEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE employee").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE qualification").executeUpdate();
        entityManager.getTransaction().commit();

    }

    @AfterEach
    public void afterEach() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE transport_company").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE employee").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("TRUNCATE TABLE qualification").executeUpdate();
        entityManager.getTransaction().commit();

    }

    @Test
    @Order(2)
    public void createShouldExecuteSuccessfully() {
        // Arrange
        entityManager.getTransaction().begin();
        String selectEmployeeQuery = "SELECT * FROM Employee AS v WHERE v.Name = 'TestName'";

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

        entityManager.getTransaction().commit();
        assertEquals(0, actual);
    }

    @Test
    @Order(3)
    public void updateShouldExecuteSuccessfully() {
        // Arrange
        entityManager.clear();
        entityManager.getTransaction().begin();
        String selectEmployeeQuery = "SELECT * FROM Employee AS v WHERE v.Name = 'UpdatedName'";
        String selectTransportCompanyQuery = "SELECT * FROM transport_company";

        seedTestTransportCompanies();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee updateEmployee = new Employee();

        ArrayList<TransportCompany> transportCompanies = (ArrayList<TransportCompany>) entityManager
                .createNativeQuery(selectTransportCompanyQuery, TransportCompany.class)
                .getResultList();

        updateEmployee.setTransportCompany(transportCompanies.get(0));
        updateEmployee.setName("TestEmployeeName");
        updateEmployee.setIncome(BigDecimal.valueOf(2.52));

        entityManager.merge(updateEmployee);
        entityManager.getTransaction().commit();

        //Act
        updateEmployee.setName("UpdatedName");
        updateEmployee.setIncome(BigDecimal.TEN);

        employeeDAO.update(1, updateEmployee);


        ArrayList<Employee> resultList = (ArrayList<Employee>) entityManager
                .createNativeQuery(selectEmployeeQuery, Employee.class)
                .getResultList();

        entityManager.refresh(resultList.get(0));

        //Assert
        int actual = Comparator.comparing(Employee::getName)
                .thenComparing(Employee::getIncome)
                .compare(updateEmployee, resultList.get(0));

        assertEquals(0, actual);

    }

    @Test
    @Order(1)
    public void deleteShouldExecuteSuccessfully() {
        //Arrange
        entityManager.getTransaction().begin();
        Employee currentEmployee = new Employee();
        currentEmployee.setName("test");
        currentEmployee.setIncome(BigDecimal.TEN);

        employeeDAO.create(currentEmployee);

        //Act
        employeeDAO.delete(1);

        int actualCount = entityManager
                .createNativeQuery("SELECT * FROM Employee")
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
                    transportCompany.setAddress("TestEmployeeAddress" + i);
                    transportCompany.setTotalIncome(BigDecimal.valueOf((Long) i));
                    transportCompany.setName("TestEmployeeCompany" + i);
                    return transportCompany;
                }).forEach(transportCompany -> {
                    entityManager.persist(transportCompany);
                });
    }
}
