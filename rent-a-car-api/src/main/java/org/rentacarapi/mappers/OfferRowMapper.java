package org.rentacarapi.mappers;

import org.rentacarapi.models.entities.Car;
import org.rentacarapi.models.entities.Client;
import org.rentacarapi.models.entities.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();

        client.setId(rs.getInt("client_id"));
        client.setName(rs.getString("name"));
        client.setHasAccidents(rs.getBoolean("has_accidents"));

        Car car = new Car();
        car.setId(rs.getInt("car_id"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setIsAvailable(rs.getBoolean("is_available"));
        car.setDailyPrice(rs.getBigDecimal("daily_price"));
        car.setCityId(rs.getInt("city_id"));
        car.setCityName(rs.getString("city_name"));

        Offer offer = new Offer();
        offer.setId(rs.getInt("id"));
        offer.setClientId(client.getId());
        offer.setClient(client);
        offer.setCarId(car.getId());
        offer.setCar(car);
        offer.setStartDate(rs.getString("start_date"));
        offer.setEndDate(rs.getString("end_date"));
        offer.setTotal(rs.getBigDecimal("total"));
        offer.setIsAccepted(rs.getBoolean("is_accepted"));
        offer.setIsDeleted(rs.getBoolean("is_deleted"));

        return offer;
    }
}
