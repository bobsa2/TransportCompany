package repository;

import entity.*;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ClientRepository {
    static final String[] client_names = {
            "Misho",
            "Tisho",
            "Kitodar",
            "Candice",
            "Jeniffer",
            "Magnus",
            "Hikaru"
    };

    public static Set<Client> clients = LongStream
            .range(0, 6)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(6);

                Client client = new Client();

                client.setAge(3 * 10 + index);
                client.setName(client_names[index]);
                client.setTelephone("088-266-333-" + index);
                client.setMail(client.getName().toLowerCase() + index + "@gmail.com");

                client.setTransportation((Transportation) TransportationRepository.transportations.toArray()[1]);

                client.setHasPaid(i % 2 == 0 ? true : false);

                return client;

            }).collect(Collectors.toSet());

}
