package repository;

import entity.Client;
import entity.TransportCompany;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TransportCompanyRepository {
    static final String[] transport_company_names = {
            "Tesla",
            "Amazon",
            "United-Airs",
            "Facebook",
            "Ryanair",
            "Orbit",
            "Kingston"
    };
    static final String[] transport_company_addresses = {
            "Washington Street 61",
            "Druzhba 312",
            "Paris 31",
            "Milwakee 512",
            "Copenhagen 123",
            "Sofia 3123",
            "Geo Milev 1231"
    };

    static final BigDecimal[] transport_company_incomes = {
            BigDecimal.valueOf(311202.32),
            BigDecimal.valueOf(2123.32),
            BigDecimal.valueOf(45213.67),
            BigDecimal.valueOf(75213.67),
            BigDecimal.valueOf(25213.67),
            BigDecimal.valueOf(15213.67),
    };

    public static Set<TransportCompany> transportCompanies = LongStream
            .range(0, 6)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(6);

                TransportCompany transportCompany = new TransportCompany();

                transportCompany.setAddress(transport_company_addresses[index]);
                transportCompany.setTotalIncome(transport_company_incomes[index]);
                transportCompany.setName(transport_company_names[index]);


                return transportCompany;

            }).collect(Collectors.toSet());

}
