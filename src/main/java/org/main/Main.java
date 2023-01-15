package org.main;

import repository.*;
import util.EntitySeeder;

import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);
        EntitySeeder.seedRecords(EmployeeRepository.employees);
        EntitySeeder.seedRecords(QualificationRepository.qualifications);
        EntitySeeder.seedRecords(TransportationRepository.transportations);
        EntitySeeder.seedRecords(ClientRepository.clients);
        EntitySeeder.seedRecords(VehicleRepository.vehicles);
    }
}