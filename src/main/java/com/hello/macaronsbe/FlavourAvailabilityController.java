package com.hello.macaronsbe;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flavours/{id}/availability")
@RequiredArgsConstructor
public class FlavourAvailabilityController {
    private final FlavourAvailabilityService flavourAvailabilityService;
    private final FlavourService flavourService;

    @Data
    public static class AvailabilityRequest {
        private int amount;
        private LocalDate date;
        private int expiration;
    }

    @PutMapping
    public ResponseEntity<FlavourAvailability> updateAvailability(
            @PathVariable Long id,
            @RequestBody AvailabilityRequest request) {

        Optional<Flavour> flavour = flavourService.getFlavourById(id);

        if (flavour.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FlavourAvailability availability = new FlavourAvailability();
        availability.setFlavour(flavour.get());
        availability.setAmount(request.getAmount());
        availability.setDate(request.getDate());
        availability.setExpirationDays(request.getExpiration());

        FlavourAvailability updatedAvailability = flavourAvailabilityService.saveAvailability(availability);
        return ResponseEntity.ok(updatedAvailability);
    }

    @GetMapping("/details")
    public ResponseEntity<List<FlavourAvailability>> getAvailabilityByDate(@RequestParam LocalDate date) {
        List<FlavourAvailability> availabilityList = flavourAvailabilityService.getAvailabilityByDate(date);
        return ResponseEntity.ok(availabilityList);
    }
}
