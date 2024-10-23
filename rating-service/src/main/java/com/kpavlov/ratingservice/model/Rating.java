package com.kpavlov.ratingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long driverId;

    @Column(nullable = false)
    private long passengerId;

    @Column(nullable = false)
    private long rideId;

    @Column(nullable = false)
    private int driverRate;

    @Column(nullable = false)
    private int passengerRate;

    @Column
    private String driverComment;

    @Column
    private String passengerComment;
}