package org.rentacarapi.services;

import org.rentacarapi.helpers.LocationHelpers;
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

    public Car getCarById(int id) {
        return this.carRepository.fetch(id);
    }

    public boolean updateCar(Car car) {
        if (!LocationHelpers.isLocationAvailableByCityId(car.getCityId()))
        {
            throw new IllegalArgumentException("Invalid city!");
        }
        return this.carRepository.update(car);
    }

    public boolean removeCar(int id){
        return this.carRepository.delete(id);
    }

    public boolean createCar(Car car) {
        return this.carRepository.create(car);
    }
}
