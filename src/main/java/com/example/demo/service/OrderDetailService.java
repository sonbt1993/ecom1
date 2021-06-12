package com.example.demo.service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.OrderDetailDAO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.model.CartItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailDAO orderDetailDAO;

    public void save(OrderDetail orderDetail){
         orderDetailDAO.save(orderDetail);
    };

    public List<OrderDetail> findOrderDetailsByOrder(Order oder) {
        return orderDetailDAO.findOrderDetailsByOrder(oder);
    };


}
