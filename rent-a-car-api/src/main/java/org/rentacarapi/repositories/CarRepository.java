package org.rentacarapi.repositories;

import org.rentacarapi.mappers.CarRowMapper;
import org.rentacarapi.models.entities.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    private final JdbcTemplate db;

    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<Car> fetchAll(int cityId) {
        String query = "SELECT * FROM td_cars WHERE city_id = ? AND is_deleted = false AND is_available = true";

        return this.db.query(query, new Object[]{cityId}, new CarRowMapper());
    }
}
