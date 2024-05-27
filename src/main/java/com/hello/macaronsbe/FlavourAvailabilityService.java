package com.hello.macaronsbe;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class FlavourAvailabilityService {
    private final List<FlavourAvailability> availabilities = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public FlavourAvailability saveAvailability(FlavourAvailability availability) {
        if (availability.getId() == null) {
            availability.setId(counter.incrementAndGet());
        } else {
            availabilities.removeIf(existingAvailability -> existingAvailability.getId().equals(availability.getId()));
        }
        availabilities.add(availability);
        return availability;
    }

    public List<FlavourAvailability> getAvailabilityByDate(LocalDate date) {
        return availabilities.stream()
                .filter(availability -> {
                    LocalDate endDate = availability.getDate().plusDays(availability.getExpirationDays());
                    return !date.isBefore(availability.getDate()) && !date.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }

    public Optional<FlavourAvailability> getAvailabilityForFlavourAndDate(Flavour flavour, LocalDate date) {
        return availabilities.stream()
                .filter(availability -> availability.getFlavour().getId().equals(flavour.getId()) &&
                        !date.isBefore(availability.getDate()) &&
                        !date.isAfter(availability.getDate().plusDays(availability.getExpirationDays())))
                .findFirst();
    }
}
