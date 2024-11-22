package org.rentacarapi.repositories;

import org.rentacarapi.mappers.OfferRowMapper;
import org.rentacarapi.models.entities.Offer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferRepository {
    private final JdbcTemplate db;

    public OfferRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Offer offer) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO td_offers (client_id, car_id, start_date, end_date, total) ")
                .append("VALUES (?, ?, ?, ?, ?)");

        int resultCount = this.db.update(query.toString(),
                offer.getClientId(),
                offer.getCarId(),
                offer.getStartDate(),
                offer.getEndDate(),
                offer.getTotal());

        return resultCount == 1;
    }

    public List<Offer> fetchAll(int clientId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT o.id, o.client_id, cl.name, cl.has_accidents, o.car_id, c.make, c.model, c.is_available, c.daily_price, c.city_id, ct.name as city_name, o.start_date, o.end_date, o.total, o.is_accepted, o.is_deleted ")
                .append("FROM td_offers as o ")
                .append("JOIN td_clients as cl ON o.client_id = cl.id ")
                .append("JOIN td_cars as c ON o.car_id = c.id ")
                .append("JOIN td_cities as ct ON c.city_id = ct.id ")
                .append("WHERE o.is_deleted = FALSE AND o.client_id = ?");

        return this.db.query(query.toString(), new Object[]{clientId}, new OfferRowMapper());
    }

    public Offer fetch(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT o.id, o.client_id, cl.name, cl.has_accidents, o.car_id, c.make, c.model, c.is_available, c.daily_price, c.city_id, ct.name as city_name, o.start_date, o.end_date, o.total, o.is_accepted, o.is_deleted ")
                .append("FROM td_offers as o ")
                .append("JOIN td_clients as cl ON o.client_id = cl.id ")
                .append("JOIN td_cars as c ON o.car_id = c.id ")
                .append("JOIN td_cities as ct ON c.city_id = ct.id ")
                .append("WHERE o.id = ?");

        ArrayList<Offer> collection = (ArrayList<Offer>) this.db.query(query.toString(), new Object[]{id}, new OfferRowMapper());
        if (collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }

    public boolean delete(int id){
        String query = "UPDATE td_offers SET is_deleted = true WHERE id = ?";

        int resultCount = this.db.update(query.toString(), id);

        return resultCount == 1;
    }

    public boolean accept(int id) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_offers ")
                .append("SET is_accepted = true ")
                .append("WHERE is_deleted = false AND id = ?");

        int resultCount = this.db.update(query.toString(), id);
        return resultCount == 1;
    }
}
