package com.naim.recruitingapp.model;

import com.naim.recruitingapp.utils.ApplicationStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String candidateEmail;

    private String resume;

    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
