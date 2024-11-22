package org.rentacarapi.controllers;

import org.rentacarapi.helpers.LocationHelpers;
import org.rentacarapi.http.AppResponse;
import org.rentacarapi.models.entities.Car;
import org.rentacarapi.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CarsController {
    private final CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(@RequestParam String location) {
        if (!LocationHelpers.isLocationAvailableByCityName(location)) {
            return AppResponse.notFound()
                    .withMessage("Invalid location!")
                    .build();
        }

        ArrayList<Car> cars = (ArrayList<Car>) this.carService.getAllCarsForLocation(LocationHelpers.getCityDbId(location));
        if (cars == null || cars.isEmpty()) {
            return AppResponse.notFound()
                    .withMessage("No cars found for location!")
                    .build();
        }

        return AppResponse.success()
                .withData(cars)
                .build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarById(@PathVariable int id) {
        Car car = this.carService.getCarById(id);

        if (car == null) {
            return AppResponse.notFound()
                    .withMessage("Car not found!")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(car)
                .build();
    }

    @PutMapping("/cars")
    public ResponseEntity<?> updateCar(@RequestBody Car car) {
        try{
            boolean isUpdateSuccessful = this.carService.updateCar(car);

            if(!isUpdateSuccessful) {
                return AppResponse.error()
                        .withMessage("Car not found!")
                        .build();
            }

            return AppResponse.success()
                    .withMessage("Update successful!")
                    .build();
        } catch (IllegalArgumentException e) {
            return AppResponse.badRequest()
                    .withMessage(e.getMessage())
                    .build();
        }

    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        boolean isUpdateSuccessful = this.carService.removeCar(id);

        if(!isUpdateSuccessful) {
            return AppResponse.error()
                    .withMessage("Car not found!")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Remove successful!")
                .build();
    }

    @PostMapping("/cars")
    public ResponseEntity<?> createNewCar(@RequestBody Car car) {
        if(this.carService.createCar(car)) {
            return AppResponse.success()
                    .withMessage("Car created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Car could not be created")
                .build();
    }
}
