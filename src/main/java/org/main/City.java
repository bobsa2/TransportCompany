package org.main;

import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(name = "CountryCode", nullable = false)
    private String CountryCode;
    @Column(name = "District", nullable = false)
    private String District;
    @Column(name = "Population", nullable = false)
    private int Population;

    @Override
    public String toString() {
        return "City{" +
                "ID=" + ID +
                ", CountryCode='" + CountryCode + '\'' +
                ", District='" + District + '\'' +
                ", Population=" + Population +
                '}';
    }
}