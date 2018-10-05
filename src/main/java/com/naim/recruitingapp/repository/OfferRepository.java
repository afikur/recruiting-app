package com.naim.recruitingapp.repository;

import com.naim.recruitingapp.model.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "offers", path = "offers")
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {

}
