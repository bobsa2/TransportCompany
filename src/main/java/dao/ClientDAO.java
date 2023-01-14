package dao;

import entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jdk.jshell.spi.ExecutionControl;
import util.Dao;

public class ClientDAO implements Dao<Client> {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public ClientDAO() {
    }

    @Override
    public <Client> void create(Client client) {
    }

    @Override
    public <Client> void update(Client client) {

    }

    @Override
    public <Client> void delete(Client client) {
    }
}
