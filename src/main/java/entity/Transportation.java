package entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transportation")

public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "starting_point", nullable = false)
    private String startingPoint;

    @Column(name = "ending_point", nullable = false)
    private String endingPoint;

    @Column(name = "date_departure", nullable = false)
    private LocalDate dateDeparture;

    @Column(name = "date_arrival", nullable = false)
    private LocalDate dateArrival;

    @ManyToOne
    @JoinColumn(name = "transport_company_id", nullable = false)
    private TransportCompany transportCompany;
}
