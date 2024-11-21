package org.rentacarapi.repositories;

import org.rentacarapi.mappers.CarRowMapper;
import org.rentacarapi.models.entities.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {
    private final JdbcTemplate db;

    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<Car> fetchAll(int cityId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT td_cars.id, td_cars.make, td_cars.model, td_cars.is_available, td_cars.daily_price, td_cars.city_id, td_cities.name as city_name ")
                .append("FROM td_cars ")
                .append("JOIN td_cities ON td_cars.city_id= td_cities.id ")
                .append("WHERE td_cars.is_deleted = FALSE AND td_cities.id = ?");

        return this.db.query(query.toString(), new Object[]{cityId}, new CarRowMapper());
    }

    public Car fetch(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT td_cars.id, td_cars.make, td_cars.model, td_cars.is_available, td_cars.daily_price, td_cars.city_id, td_cities.name as city_name ")
                .append("FROM td_cars ")
                .append("JOIN td_cities ON td_cars.city_id= td_cities.id ")
                .append("WHERE td_cars.is_deleted = FALSE AND td_cars.id = ?");

        ArrayList<Car> collection = (ArrayList<Car>) this.db.query(query.toString(), new Object[]{id}, new CarRowMapper());
        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean update(Car car) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_cars ")
                .append("SET make = ?, model = ?, is_available = ?, daily_price = ?, city_id = ? ")
                .append("WHERE is_deleted = false AND id = ?");

        int resultCount = this.db.update(query.toString(),
                car.getMake(),
                car.getModel(),
                car.getIsAvailable(),
                car.getDailyPrice(),
                car.getCityId(),
                car.getId());

        return resultCount == 1;
    }

    public boolean delete(int id){
        String query = "UPDATE td_cars SET is_deleted = true WHERE id = ?";

        int resultCount = this.db.update(query.toString(), id);

        return resultCount == 1;
    }

    public boolean create(Car car) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO td_cars(make, model, daily_price, city_id) ")
                .append("VALUES (?, ?, ?, ?)");

        int resultCount = this.db.update(query.toString(),
                car.getMake(),
                car.getModel(),
                car.getDailyPrice(),
                car.getCityId());

        return resultCount == 1;
    }
}
