package com.example.demo.dao;

import com.example.demo.entity.Account;
import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);

}
