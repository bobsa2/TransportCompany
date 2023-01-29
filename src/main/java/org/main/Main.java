package org.main;

import dao.*;
import entity.*;
import repository.*;
import util.EntitySeeder;
import util.RelationshipsMapper;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Main {
    public static TransportCompanyDAO transportCompanyDAO = new TransportCompanyDAO();
    public static ClientDAO clientDAO = new ClientDAO();
    public static EmployeeDAO employeeDAO = new EmployeeDAO();
    public static TransportationDAO transportationDAO = new TransportationDAO();
    public static VehicleDAO vehicleDAO = new VehicleDAO();
    public static void main(String[] args) {
        
        EntitySeeder.seedRecords(TransportCompanyRepository.transportCompanies);
        EntitySeeder.seedRecords(EmployeeRepository.employees);
        EntitySeeder.seedRecords(QualificationRepository.qualifications);
        EntitySeeder.seedRecords(TransportationRepository.transportations);
        EntitySeeder.seedRecords(ClientRepository.clients);
        EntitySeeder.seedRecords(CargoRepository.cargos);
        EntitySeeder.seedRecords(VehicleRepository.vehicles);

        RelationshipsMapper.mapEmployeeQualifications();
        RelationshipsMapper.mapClientTransportCompanies();

        employeeDAO.delete(3);
        transportCompanyDAO.printIncomeFromEmployees(2);
        TransportCompany transportCompany = new TransportCompany();
        transportCompany.setName("Bulgarian Transport");
        transportCompany.setAddress("Geo Milev street");
        transportCompany.setTotalIncome(BigDecimal.valueOf(60000000));


        Client client = new Client();
        client.setName("Georgi");
        client.setAge(30);
        client.setMail("georgi_ivanov@gmail.com");
        client.setTelephone("0888878654");
        client.setHasPaid(true);

        Qualification qualification = new Qualification();
        qualification.setName("Fragile");

        Employee employee = new Employee();
        employee.setTransportCompany(transportCompany);
        employee.setIncome(BigDecimal.valueOf(3000));
        employee.setName("Dimitar");

        Transportation transportation = new Transportation();
        transportation.setTransportCompany(transportCompany);
        transportation.setStartingPoint("Plovdiv");
        transportation.setEndingPoint("Sofia");
        transportation.setPrice(200000);
        transportation.setDateDeparture(LocalDate.of(2023,1,24));
        transportation.setDateArrival(LocalDate.of(2023, 2, 15));

        Vehicle vehicle = new Vehicle();
        vehicle.setType("Truck");
        vehicle.setBrand("Volvo");
        vehicle.setModel("600");
        vehicle.setTransportCompany(transportCompany);

        //Transport Company operations

        System.out.println("Transport Company operations");
        //transportCompanyDAO.create(transportCompany);
        //transportCompanyDAO.update(1, transportCompany);
        //transportCompanyDAO.delete(2);
        //transportCompanyDAO.sortTransportCompaniesByName();
        //transportCompanyDAO.sortTransportCompaniesByIncome();
        //transportCompanyDAO.filterTransportCompaniesByName("Facebook");
        //transportCompanyDAO.filterTransportCompaniesByIncome(BigDecimal.valueOf(50000));


        //Client operations

        System.out.println("Client operations");
        //clientDAO.create(client);
        //clientDAO.update(1, client);
        //clientDAO.delete(2);


        //Employee operations

        System.out.print("Employee operations");
        //employeeDAO.create(employee);
        //employeeDAO.update(1, employee);
        //employeeDAO.delete(2);
        //employeeDAO.sortEmployeesByIncome();
        //employeeDAO.sortEmployeesByQualification();
        //employeeDAO.filterEmployeesByIncome(BigDecimal.valueOf(3000));
        //employeeDAO.filterEmployeesByQualification(qualification);


        //Transportation operations

        System.out.println("Transportation operations");
        //transportationDAO.create(transportation);
        //transportationDAO.update(1, transportation);
        //transportationDAO.delete(2);
        //transportationDAO.sortTransportationsByDestination();
        //transportationDAO.filterTransportationsByDestination("Germany");
        //File file = transportationDAO.createFile();
        //transportationDAO.saveTransportationsToFile(file);
        //transportationDAO.extractTransportationsData(file);


        //Vehicle operations

        System.out.println("Vehicle operations");
        //vehicleDAO.create(vehicle);
        //vehicleDAO.update(1, vehicle);
        //vehicleDAO.delete(2);

    }
}