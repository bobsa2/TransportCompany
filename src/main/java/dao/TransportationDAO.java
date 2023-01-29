package dao;

import entity.Transportation;
import entity.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TransportationDAO implements Dao<Transportation> {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void create(Transportation transportation) {
        entityManager.getTransaction().begin();
        entityManager.persist(transportation);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(long id, Transportation transportation) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            Transportation retrievedTransportation = entityManager.find(Transportation.class, id);

            retrievedTransportation.setTransportCompany(transportation.getTransportCompany());
            retrievedTransportation.setIncome(transportation.getIncome());
            retrievedTransportation.setDateArrival(transportation.getDateArrival());
            retrievedTransportation.setDateDeparture(transportation.getDateDeparture());
            retrievedTransportation.setStartingPoint(transportation.getStartingPoint());
            retrievedTransportation.setEndingPoint(transportation.getEndingPoint());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        if (isValid(id)) {
            entityManager.getTransaction().begin();

            Transportation retrievedTransportation = entityManager.find(Transportation.class, id);

            entityManager.remove(retrievedTransportation);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM transportation WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, Transportation.class).getResultList().size();

        return resultCount > 0;
    }

    public ArrayList<Transportation> getTransportations() {
        String idQuery = "SELECT * FROM transportation";

        return (ArrayList<Transportation>) entityManager.createNativeQuery(idQuery, Transportation.class).getResultList();
    }

    public void sortTransportationsByDestination(){

        ArrayList<Transportation> transportations = getTransportations();

        Collections.sort(transportations);
        transportations.forEach(System.out::println);
    }

    public void filterTransportationsByDestination(String destination) {

        ArrayList<Transportation> transportations = getTransportations();
        transportations.stream().filter(transportation -> transportation.getEndingPoint().equals(destination)).forEach(System.out::println);

    }

    public File createFile() {
        File file = new File("C:\\TransportCompany\\TransportationFile.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return file;
    }

    public void saveTransportationsToFile(File file) {

        ArrayList<Transportation> transportations = getTransportations();

        try {
            FileWriter myWriter = new FileWriter("TransportationFile.txt");
            myWriter.write(String.valueOf(transportations));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void extractTransportationsData(File file) {

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
