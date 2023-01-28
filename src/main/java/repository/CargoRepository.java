package repository;

import entity.Cargo;
import entity.Transportation;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CargoRepository {
    static final String[] cargo_names = {
            "Computer",
            "Airplane",
            "Broom",
            "Pencils",
            "Keyboard",
            "Glasses",
            "Paper"
    };

    public static Set<Cargo> cargos = LongStream
            .range(0, 6)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(6);

                Cargo cargo = new Cargo();

                cargo.setName(cargo_names[index]);
                cargo.setWeight(123.25 * index);
                cargo.setTransportation((Transportation) TransportationRepository.transportations.toArray()[0]);

                return cargo;
            }).collect(Collectors.toSet());
}
