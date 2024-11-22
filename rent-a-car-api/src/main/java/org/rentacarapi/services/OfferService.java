package org.rentacarapi.services;

import org.rentacarapi.models.entities.Offer;
import org.rentacarapi.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ClientService clientService;
    private final CarService carService;

    public OfferService(
            OfferRepository offerRepository,
            ClientService clientService,
            CarService carService) {
        this.offerRepository = offerRepository;
        this.clientService = clientService;
        this.carService = carService;
    }

    public boolean createOffer(Offer offer) {
        var client = this.clientService.getById(offer.getClientId());
        if (client == null) {
            throw new IllegalArgumentException("Invalid client id");
        }

        var car = this.carService.getCarById(offer.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("Invalid car id");
        }

        if (!car.getIsAvailable()){
            throw new IllegalArgumentException("Car is not available");
        }

        car.setIsAvailable(false);
        this.carService.updateCar(car);

        offer.setClient(client);
        offer.setCar(car);
        offer.setTotal(calculateTotal(offer));

        return this.offerRepository.create(offer);
    }

    public List<Offer> getAllOffersForClient(int clientId) {
        return this.offerRepository.fetchAll(clientId);
    }

    public Offer getOfferById(int id) {
        return this.offerRepository.fetch(id);
    }

    public boolean removeOffer(int id){
        return this.offerRepository.delete(id);
    }

    public boolean acceptOffer(int id) {
        return this.offerRepository.accept(id);
    }

    private BigDecimal calculateTotal(Offer offer) {
        LocalDate startDate = LocalDate.parse(offer.getStartDate());
        LocalDate endDate = LocalDate.parse(offer.getEndDate());
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long weekendDays = startDate.datesUntil(endDate.plusDays(1))
                .filter(OfferService::isWeekend)
                .count();
        long weekdayDays = totalDays - weekendDays;

        BigDecimal carPricePerDay = offer.getCar().getDailyPrice();
        BigDecimal weekdaysPrice = carPricePerDay.multiply(BigDecimal.valueOf(weekdayDays));
        BigDecimal weekendPrice = carPricePerDay.multiply(BigDecimal.valueOf(weekendDays))
                .multiply(BigDecimal.valueOf(1.10));

        BigDecimal total = weekdaysPrice.add(weekendPrice);

        if (offer.getClient().getHasAccidents()) {
            total = total.add(BigDecimal.valueOf(200));
        }

        return total;
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
