package com.naim.recruitingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naim.recruitingapp.utils.ApplicationStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String candidateEmail;

    private String resume;

    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
