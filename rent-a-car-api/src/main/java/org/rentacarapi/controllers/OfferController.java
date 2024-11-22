package org.rentacarapi.controllers;

import org.rentacarapi.http.AppResponse;
import org.rentacarapi.models.entities.Offer;
import org.rentacarapi.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
        try {
            if(this.offerService.createOffer(offer)) {
                return AppResponse.success()
                        .withMessage("Offer created successfully")
                        .build();
            }

            return AppResponse.error()
                    .withMessage("Offer could not be created")
                    .build();
        } catch (IllegalArgumentException e){
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/offers")
    public ResponseEntity<?> getAllCars(@RequestParam int clientId) {
        ArrayList<Offer> offers = (ArrayList<Offer>) this.offerService.getAllOffersForClient(clientId);
        if (offers == null || offers.isEmpty()) {
            return AppResponse.notFound()
                    .withMessage("No offers found for the user!")
                    .build();
        }

        return AppResponse.success()
                .withData(offers)
                .build();
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable int id) {
        Offer offer = this.offerService.getOfferById(id);

        if (offer == null) {
            return AppResponse.notFound()
                    .withMessage("Offer not found!")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(offer)
                .build();
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        boolean isUpdateSuccessful = this.offerService.removeOffer(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Offer not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Remove successful!")
                .build();
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<?> acceptOffer(@PathVariable int id) {
        boolean isUpdateSuccessful = this.offerService.acceptOffer(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Offer not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Offer accepted!")
                .build();
    }
}
