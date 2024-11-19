package org.rentacarapi.mappers;

import org.rentacarapi.models.entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setIs_available(rs.getBoolean("is_available"));
        car.setDailyPrice(rs.getBigDecimal("daily_price"));
        car.setCityId(rs.getInt("city_id"));
        car.setDeleted(rs.getBoolean("is_deleted"));

        return car;
    }
}
