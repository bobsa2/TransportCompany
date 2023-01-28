package repository;

import entity.Employee;
import entity.Qualification;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class QualificationRepository {
    static final String[] qualification_names = {
            "Large Packages",
            "Fragile",
            "Valuable",
            "Plane"
    };

    public static Set<Qualification> qualifications = LongStream
            .range(0, 3)
            .mapToObj((i) -> {
                Random random = new Random();
                int index = random.nextInt(3);

                Qualification qualification = new Qualification();
                Employee employee = (Employee) EmployeeRepository.employees.toArray()[index];

                qualification.setName(qualification_names[index]);

                return qualification;

            }).collect(Collectors.toSet());

}
