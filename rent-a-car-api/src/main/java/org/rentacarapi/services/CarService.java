package org.rentacarapi.services;

import org.rentacarapi.models.entities.Car;
import org.rentacarapi.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCarsForLocation(int cityId) {
        return this.carRepository.fetchAll(cityId);
    }
}
