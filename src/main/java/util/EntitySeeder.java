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
        records.stream().forEach((record) -> {
            entityManager.persist(record);
        });
        entityManager.getTransaction().commit();
    }
}
