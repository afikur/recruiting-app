package com.naim.recruitingapp.repository;

import com.naim.recruitingapp.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferJpaRepository extends JpaRepository<Offer, Long> {

}
