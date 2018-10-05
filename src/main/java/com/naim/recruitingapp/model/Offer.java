package com.naim.recruitingapp.model;

import lombok.Data;

import javax.persistence.*;

import java.util.List;
import java.util.Date;

@Entity
@Data
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String title;

    private Date startDate;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "offer")
    private List<Application> applications;

    @Transient
    private Integer numberOfApplications;

    @PostLoad
    public void setNumberOfApplications() {
        this.numberOfApplications = applications.size();
    }
}
