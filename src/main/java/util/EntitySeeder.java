package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import entity.Client;

import java.util.Set;

public class EntitySeeder {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static <T> void seedRecords(Set<T> records) {
        entityManager.getTransaction().begin();

        var entityName = records.toArray()[0].getClass().getName().replace("entity.", "");
        var countQuery = String.format("SELECT COUNT(E)  FROM %s E", entityName);
        long count = (long) entityManager.createQuery(countQuery, records.toArray()[0].getClass()).getSingleResult();

        if (count > 0) {
            entityManager.getTransaction().commit();
            return;
        }
        records.stream().forEach((record) -> {

            entityManager.persist(record);
        });

        entityManager.getTransaction().commit();
    }
}
