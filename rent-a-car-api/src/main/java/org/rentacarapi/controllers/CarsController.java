package org.rentacarapi.controllers;

import org.rentacarapi.enums.Locations;
import org.rentacarapi.http.AppResponse;
import org.rentacarapi.models.entities.Car;
import org.rentacarapi.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CarsController {
    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{location}")
    public ResponseEntity<?> getAllCars(@PathVariable String location) {
        Locations locationEnum = Locations.fromString(location);

        if (locationEnum == null) {
            return AppResponse.notFound()
                    .withMessage("Invalid location!")
                    .build();
        }

        ArrayList<Car> cars = (ArrayList<Car>) this.carService.getAllCarsForLocation(locationEnum.getDbId());

        if (cars == null) {
            return AppResponse.notFound()
                    .withMessage("No cars found for location!")
                    .build();
        }

        return AppResponse.success()
                .withData(cars)
                .build();
    }
}
