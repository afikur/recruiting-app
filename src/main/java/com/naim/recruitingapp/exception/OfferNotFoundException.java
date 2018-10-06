package com.naim.recruitingapp.exception;

public class OfferNotFoundException extends Exception {
    public OfferNotFoundException(Long id) {
        super("Offer not found with id: " + id);
    }
}
