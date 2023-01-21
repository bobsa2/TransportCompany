package repository;

import entity.Employee;
import entity.TransportCompany;
import entity.Vehicle;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class VehicleRepository {

    static final String[] vehicle_types = {
            "Bus",
            "Car",
            "Plane",
            "Truck"
    };
    static final String[] vehicle_brands = {
            "Audi",
            "BMW",
            "Mercedes",
            "Toyota"
    };
    public static Set<Vehicle> vehicles = LongStream
            .range(0, 4)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(4);

                Vehicle vehicle = new Vehicle();
                vehicle.setBrand(vehicle_brands[index]);
                vehicle.setModel("X-" + index);
                vehicle.setType(vehicle_types[index]);

                return vehicle;

            }).collect(Collectors.toSet());
}
