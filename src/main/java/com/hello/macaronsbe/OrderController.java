package com.hello.macaronsbe;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final FlavourService flavourService;
    private final FlavourAvailabilityService flavourAvailabilityService;

    @Data
    public static class OrderRequest {
        private Long flavourId;
        private int amount;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        Optional<Flavour> flavourOptional = flavourService.getFlavourById(orderRequest.getFlavourId());

        if (!flavourOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flavour not found");
        }

        Flavour flavour = flavourOptional.get();
        LocalDate today = LocalDate.now();
        Optional<FlavourAvailability> availabilityOptional = flavourAvailabilityService.getAvailabilityForFlavourAndDate(flavour, today);

        if (!availabilityOptional.isPresent() || availabilityOptional.get().getAmount() < orderRequest.getAmount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient availability for the requested flavour");
        }

        FlavourAvailability availability = availabilityOptional.get();
        availability.setAmount(availability.getAmount() - orderRequest.getAmount());
        flavourAvailabilityService.saveAvailability(availability);

        Order order = new Order();
        order.setFlavour(flavour);
        order.setAmount(orderRequest.getAmount());
        orderService.saveOrder(order);

        return ResponseEntity.ok("Order placed successfully");
    }
}
