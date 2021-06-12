package com.example.demo.dao;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
   List<OrderDetail> findOrderDetailsByOrder(Order oder);

}
