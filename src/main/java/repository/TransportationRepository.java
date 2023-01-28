package repository;

import entity.Employee;
import entity.TransportCompany;
import entity.Transportation;
import entity.TransportationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TransportationRepository {
    static final String[] cargo_types = {
            "Heavy",
            "Lightweight",
            "Fireproof",
    };
    static final String[] ending_points = {
            "Canada",
            "Lithuania",
            "Germany"
    };

    public static Set<Transportation> transportations = LongStream
            .range(0, 3)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(3);

                Transportation transportation = new Transportation();

                transportation.setDateDeparture(LocalDate.now());
                transportation.setDateArrival(LocalDate.now().plusDays(index));
                transportation.setHasFinished(i % 2 == 0 ? true : false);
                transportation.setType(i % 2 == 0 ? TransportationType.Cargo : TransportationType.People);
                transportation.setEndingPoint(ending_points[index]);
                transportation.setStartingPoint("Bulgaria");

                TransportCompany transportCompany = (TransportCompany) TransportCompanyRepository.transportCompanies.toArray()[index];
                transportation.setTransportCompany(transportCompany);

                return transportation;

            }).collect(Collectors.toSet());
}
