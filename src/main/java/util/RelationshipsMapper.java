package util;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;

public class RelationshipsMapper {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void mapEmployeeQualifications() {

        entityManager.getTransaction().begin();
        ArrayList<Employee> employees = (ArrayList<Employee>) entityManager
                .createNativeQuery("SELECT * FROM Employee", Employee.class)
                .getResultList();

        ArrayList<Qualification> qualifications = (ArrayList<Qualification>) entityManager
                .createNativeQuery("SELECT * FROM Qualification", Qualification.class)
                .getResultList();


        if (employees.size() > 0 && qualifications.size() > 0) {
            employees.forEach(employee -> {
                employee.getQualifications().add(qualifications.get(0));
            });
            qualifications.forEach(qualification -> {
                qualification.getEmployees().add(employees.get(0));
            });
        }
        entityManager.getTransaction().commit();

    }

    public static void mapClientTransportCompanies() {
        entityManager.getTransaction().begin();
        ArrayList<Client> clients = (ArrayList<Client>)entityManager
                .createNativeQuery("SELECT * FROM Client", Client.class)
                .getResultList();

        ArrayList<TransportCompany> transportCompanies = (ArrayList<TransportCompany>)entityManager
                .createNativeQuery("SELECT * FROM transport_company", TransportCompany.class )
                .getResultList();

        if (clients.size() > 0 && transportCompanies.size() > 0 ){
            clients.forEach(client -> {
                client.getTransportCompanies().add(transportCompanies.get(0));
            });

            transportCompanies.forEach(transportCompany -> {
                transportCompany.getClients().add(clients.get(0));
            });
        }
        entityManager.getTransaction().commit();
    }

}
