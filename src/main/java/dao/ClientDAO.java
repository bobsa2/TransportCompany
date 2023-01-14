package dao;

import entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.Dao;

public class ClientDAO implements Dao<Client> {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public ClientDAO() { }

    @Override
    public void create(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(int id, Client client) {
        entityManager.getTransaction().begin();

        Client retrievedClient = entityManager.find(entity.Client.class, id);

        retrievedClient.setName(client.getName());
        retrievedClient.setMail(client.getMail());
        retrievedClient.setAge(client.getAge());
        retrievedClient.setTelephone(client.getTelephone());

        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id, Client client) {
        entityManager.getTransaction().begin();

        Client retrievedClient = entityManager.find(Client.class, id);

        entityManager.remove(retrievedClient);
        entityManager.getTransaction().commit();
    }
}
