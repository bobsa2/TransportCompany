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
    public void update(long id, Client client) {
        entityManager.getTransaction().begin();

        if (isValid(id)) {

            Client retrievedClient = entityManager.find(entity.Client.class, id);

            retrievedClient.setName(client.getName());
            retrievedClient.setMail(client.getMail());
            retrievedClient.setAge(client.getAge());
            retrievedClient.setTelephone(client.getTelephone());
            retrievedClient.setHasPaid(client.getHasPaid());

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        entityManager.getTransaction().begin();

        if (isValid(id)) {

            Client retrievedClient = entityManager.find(Client.class, id);

            entityManager.remove(retrievedClient);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public boolean isValid(long id) {
        String idQuery = String.format("SELECT * FROM client WHERE id = %s", id);

        int resultCount = entityManager.createNativeQuery(idQuery, Client.class).getResultList().size();

        return resultCount > 0;
    }
}
