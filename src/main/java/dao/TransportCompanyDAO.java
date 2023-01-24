package dao;

import entity.Client;
import entity.Employee;
import entity.TransportCompany;
import entity.Transportation;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransportCompanyDAO implements Dao<TransportCompany> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public TransportCompanyDAO() {
    }

    @Override
    public void create(TransportCompany transportCompany) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportCompany);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(long id, TransportCompany transportCompany) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            TransportCompany retrievedTransportCompany = entityManager.find(entity.TransportCompany.class, id);
            retrievedTransportCompany.setName(transportCompany.getName());
            retrievedTransportCompany.setAddress(transportCompany.getAddress());
            retrievedTransportCompany.setTotalIncome(transportCompany.getTotalIncome());
            retrievedTransportCompany.setVehicles(transportCompany.getVehicles());
            retrievedTransportCompany.setEmployees(transportCompany.getEmployees());
            retrievedTransportCompany.setTransportations(transportCompany.getTransportations());
            retrievedTransportCompany.setClients(transportCompany.getClients());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        if (isValid(id)) {

            TransportCompany retrievedTransportCompany = entityManager.find(TransportCompany.class, id);

            entityManager.getTransaction().begin();
            entityManager.remove(retrievedTransportCompany);
            entityManager.getTransaction().commit();
        }
    }
    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM transport_company WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, TransportCompany.class).getResultList().size();

        return resultCount > 0;
    }

    public ArrayList<TransportCompany> getTransportCompanies() {
        String idQuery = "SELECT * FROM transport_company";

        return (ArrayList<TransportCompany>) entityManager.createNativeQuery(idQuery, TransportCompany.class).getResultList();
    }

    public void sortTransportCompaniesByName(){

        ArrayList<TransportCompany> transportCompanies = getTransportCompanies();

        transportCompanies.sort(TransportCompany.transportCompanyComparatorName);
        transportCompanies.forEach(System.out::print);
    }

    public void sortTransportCompaniesByIncome(){

        ArrayList<TransportCompany> transportCompanies = getTransportCompanies();

        transportCompanies.sort(TransportCompany.transportCompanyComparatorIncome);
        transportCompanies.forEach(System.out::print);
    }

    public void filterTransportCompaniesByName(String sequence) {

        ArrayList<TransportCompany> transportCompanies = getTransportCompanies();
        transportCompanies.stream().filter(transportCompany -> transportCompany.getName().contains(sequence)).forEach(System.out::println);

    }

    public void filterTransportCompaniesByIncome(BigDecimal value) {

        ArrayList<TransportCompany> transportCompanies = getTransportCompanies();
        transportCompanies.stream().filter(transportCompany -> transportCompany.getTotalIncome().compareTo(value) >= 0).forEach(System.out::println);

    }
}
