package com.hello.macaronsbe;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Order saveOrder(Order order) {
        order.setId(counter.incrementAndGet());
        orders.add(order);
        return order;
    }
}
