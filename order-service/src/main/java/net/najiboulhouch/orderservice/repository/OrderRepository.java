package net.najiboulhouch.orderservice.repository;

import net.najiboulhouch.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {
}
