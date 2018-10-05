package com.naim.recruitingapp.repository;

import com.naim.recruitingapp.model.Application;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "applications", path = "applications")
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
    Application findByCandidateEmail(String candidateEmail);
}
