package repository;

import entity.Employee;
import entity.TransportCompany;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class EmployeeRepository {
    static final String[] employee_names = {
            "Fred",
            "Jane",
            "Richard",
            "John",
            "Mellisa",
            "Ivan",
            "Jonathan"
    };
    static final BigDecimal[] employee_salaries = {
            BigDecimal.valueOf(1202.32),
            BigDecimal.valueOf(2123.32),
            BigDecimal.valueOf(5213.67),
            BigDecimal.valueOf(9123.32),
            BigDecimal.valueOf(2132.51),
            BigDecimal.valueOf(6132.51),
            BigDecimal.valueOf(6132.51)
    };

    public static Set<Employee> employees = LongStream
            .range(0, 6)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(6);

                Employee employee = new Employee();
                employee.setIncome(employee_salaries[index]);
                employee.setName(employee_names[index]);

                TransportCompany transportCompany = (TransportCompany) TransportCompanyRepository.transportCompanies.toArray()[index];
                employee.setTransportCompany(transportCompany);

                return employee;

            }).collect(Collectors.toSet());
}
