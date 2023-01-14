package org.main;

import repository.*;
import util.EntitySeeder;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies.stream().distinct().collect(Collectors.toSet()));
        EntitySeeder.seedRecords(EmployeeRepository.employees.stream().distinct().collect(Collectors.toSet()));
        EntitySeeder.seedRecords(QualificationRepository.qualifications.stream().distinct().collect(Collectors.toSet()));
        EntitySeeder.seedRecords(TransportationRepository.transportations.stream().distinct().collect(Collectors.toSet()));
        EntitySeeder.seedRecords(ClientRepository.clients.stream().distinct().collect(Collectors.toSet()));
        EntitySeeder.seedRecords(VehicleRepository.vehicles.stream().distinct().collect(Collectors.toSet()));
    }
}