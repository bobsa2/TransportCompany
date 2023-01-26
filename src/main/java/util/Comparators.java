package util;

import entity.*;

import java.util.Comparator;

public class Comparators {
    public static Comparator<TransportCompany> transportCompanyComparator = new Comparator<TransportCompany>() {
        @Override
        public int compare(TransportCompany t1, TransportCompany t2) {
            return Comparator.comparing(TransportCompany::getName)
                    .thenComparing(TransportCompany::getTotalIncome)
                    .thenComparing(TransportCompany::getAddress)
                    .compare(t1, t2);
        }
    };
    public static Comparator<Employee> employeeComparator = new Comparator<Employee>() {
        @Override
        public int compare(Employee e1, Employee e2) {
            return Comparator.comparing(Employee::getName)
                    .thenComparing(Employee::getIncome)
                    .compare(e1, e2);
        }
    };

    public static Comparator<Client> clientComparator = new Comparator<Client>() {
        @Override
        public int compare(Client c1, Client c2) {
            return Comparator.comparing(Client::getName)
                    .thenComparing(Client::getTelephone)
                    .thenComparing(Client::getMail)
                    .thenComparing(Client::getTelephone)
                    .thenComparing(Client::getHasPaid)
                    .compare(c1, c2);
        }
    };

    public static Comparator<Vehicle> vehicleComparator = new Comparator<Vehicle>() {
        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            return Comparator.comparing(Vehicle::getModel)
                    .thenComparing(Vehicle::getBrand)
                    .thenComparing(Vehicle::getType)
                    .compare(v1, v2);
        }
    };

}
