package com.naim.recruitingapp.controller;

import com.naim.recruitingapp.exception.OfferNotFoundException;
import com.naim.recruitingapp.model.Offer;
import com.naim.recruitingapp.repository.OfferRepository;
import com.naim.recruitingapp.resource.OfferResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/offers", produces = "application/hal+json")
public class OfferController {
    private OfferRepository offerRepository;

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @GetMapping
    public ResponseEntity<Resources<OfferResource>> all() {
        final List<OfferResource> offers = offerRepository.findAll().stream().map(OfferResource::new).collect(Collectors.toList());
        final Resources<OfferResource> resources = new Resources<>(offers);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResource> get(@PathVariable final Long id) throws OfferNotFoundException {
        return offerRepository.findById(id).map(offer -> ResponseEntity.ok(new OfferResource(offer))).orElseThrow(() -> new OfferNotFoundException(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<?> postOffers(@RequestBody Offer offer) {
        offerRepository.save(offer);
        Resource<Offer> resources = new Resource<>(offer);

        return ResponseEntity.ok(resources);
    }
}
