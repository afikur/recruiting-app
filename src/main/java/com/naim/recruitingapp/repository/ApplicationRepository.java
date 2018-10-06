package com.naim.recruitingapp.repository;

import com.naim.recruitingapp.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByCandidateEmail(String candidateEmail);
}
