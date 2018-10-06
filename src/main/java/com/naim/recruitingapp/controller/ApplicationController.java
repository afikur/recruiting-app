package com.naim.recruitingapp.controller;

import com.naim.recruitingapp.exception.OfferNotFoundException;
import com.naim.recruitingapp.model.Application;
import com.naim.recruitingapp.model.Offer;
import com.naim.recruitingapp.repository.ApplicationRepository;
import com.naim.recruitingapp.repository.OfferRepository;
import com.naim.recruitingapp.resource.ApplicationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ApplicationController {
    private ApplicationRepository applicationRepository;
    private OfferRepository offerRepository;

    @Autowired
    public void setApplicationRepository(
            final ApplicationRepository applicationRepository,
            final OfferRepository offerRepository) {
        this.applicationRepository = applicationRepository;
        this.offerRepository = offerRepository;
    }

    @GetMapping(value = "/offers/{offerId}/applications/{applicationId}", produces = "application/hal+json")
    public ResponseEntity<Resources<ApplicationResource>>
        findByIdAndOfferId(@PathVariable final Long offerId,
                           @PathVariable final Long applicationId) throws OfferNotFoundException {
        final List<ApplicationResource> applications = applicationRepository.findByIdAndOffer_Id(applicationId, offerId)
                .stream().map(ApplicationResource::new).collect(Collectors.toList());

        final Resources<ApplicationResource> resources = new Resources<>(applications);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/offers/{offerId}/applications", produces = "application/hal+json")
    public ResponseEntity<Resources<ApplicationResource>> all(@PathVariable final Long offerId) throws OfferNotFoundException {
        final List<ApplicationResource> collection = getApplicationForOffer(offerId);

        final Resources<ApplicationResource> resources = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    private List<ApplicationResource> getApplicationForOffer(final Long offerId) throws OfferNotFoundException {
        return offerRepository.findById(offerId)
                .map(offer ->
                        offer.getApplications()
                                .stream()
                                .map(ApplicationResource::new)
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    @PostMapping("/offers/{offerId}/applications")
    public ResponseEntity<ApplicationResource> post(
            @PathVariable final Long offerId, @RequestBody final Application application) throws OfferNotFoundException {
        return offerRepository
                .findById(offerId)
                .map(
                        offer -> {
                            final Application app = saveApplication(offer, application);
                            final URI uri = createPostUri(app);
                            return ResponseEntity.created(uri).body(new ApplicationResource(app));
                        })
                .orElseThrow(() -> new OfferNotFoundException(offerId));
    }

    private Application saveApplication(final Offer offer, final Application application) {
        application.setOffer(offer);
        return applicationRepository.save(application);
    }

    private URI createPostUri(final Application application) {
        return MvcUriComponentsBuilder.fromController(getClass())
                .path("/{applicationId}")
                .buildAndExpand(application.getOffer().getId(), application.getId())
                .toUri();
    }

}
