package com.naim.recruitingapp.resource;

import com.naim.recruitingapp.controller.ApplicationController;
import com.naim.recruitingapp.controller.OfferController;
import com.naim.recruitingapp.exception.OfferNotFoundException;
import com.naim.recruitingapp.model.Offer;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class OfferResource extends ResourceSupport {
    private final Offer offer;

    public OfferResource(Offer offer) {
        this.offer = offer;
        final Long id = offer.getId();
        add(linkTo(OfferController.class).withRel("offers"));
        try {
            add(linkTo(methodOn(OfferController.class).get(id)).withSelfRel());
            add(linkTo(methodOn(ApplicationController.class).all(id)).withRel("applications"));
        } catch (OfferNotFoundException e) {
            e.printStackTrace();
        }
    }
}
