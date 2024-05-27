package com.hello.macaronsbe;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flavours")
public class FlavourController {
    private final FlavourService flavourService;
    private final FlavourAvailabilityService flavourAvailabilityService;

    public FlavourController(FlavourService flavourService, FlavourAvailabilityService flavourAvailabilityService) {
        this.flavourService = flavourService;
        this.flavourAvailabilityService = flavourAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<Flavour> addFlavour(@RequestBody Flavour flavour) {
        Flavour savedFlavour = flavourService.saveFlavour(flavour);
        return ResponseEntity.ok(savedFlavour);
    }

    @GetMapping
    public ResponseEntity<List<Flavour>> getAllFlavours() {
        List<Flavour> flavours = flavourService.getAllFlavours();
        return ResponseEntity.ok(flavours);
    }

    @GetMapping("/details")
    public ResponseEntity<List<FlavourDetails>> getFlavourDetailsByDate(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<FlavourAvailability> availabilities = flavourAvailabilityService.getAvailabilityByDate(parsedDate);

        List<FlavourDetails> details = availabilities.stream()
                .map(availability -> new FlavourDetails(
                        availability.getFlavour().getId(),
                        availability.getFlavour().getImage(),
                        availability.getFlavour().getName(),
                        availability.getFlavour().getDescription(),
                        availability.getAmount()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(details);
    }

    @Data
    @AllArgsConstructor
    public static class FlavourDetails {
        private Long id;
        private String image;
        private String name;
        private String description;
        private int availableAmount;
    }

}
