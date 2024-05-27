package com.hello.macaronsbe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class FlavourService {
    private final List<Flavour> flavours = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Flavour saveFlavour(Flavour flavour) {
        flavour.setId(counter.incrementAndGet());
        flavours.add(flavour);
        return flavour;
    }

    public List<Flavour> getAllFlavours() {
        return new ArrayList<>(flavours);
    }

    public Optional<Flavour> getFlavourById(Long id) {
        return flavours.stream().filter(flavour -> flavour.getId().equals(id)).findFirst();
    }
}
