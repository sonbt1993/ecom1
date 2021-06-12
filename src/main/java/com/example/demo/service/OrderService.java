package com.example.demo.service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Order;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartItemInfo;
import com.example.demo.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailService orderDetailService;

    public List<Order> findAll(){
        return orderDAO.findAll();
    }

    public Page<Order> listAll(int pageNum, String sortField, String sortDir) {

        Pageable pageable = PageRequest.of(pageNum - 1, 4,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return orderDAO.findAll(pageable);
    }

//    public Page<OrderInfo> listOrderInfo(int pageNum, String sortField, String sortDir) {
//        List<OrderInfo> orderInfoList = null;
//
//        for (Order order: orderDAO.findAll()
//             ) {
//            OrderInfo orderInfo = new OrderInfo(order);
//            orderInfoList.add(orderInfo);
//        }
//
//        Pageable pageable = PageRequest.of(pageNum - 1, 4,
//                sortDir.equals("asc") ? Sort.by(sortField).ascending()
//                        : Sort.by(sortField).descending()
//        );
//
//        return orderDAO.findAll(pageable);
//    }

    public Order findOrderById(Long id){
        return orderDAO.findOrderById(id);
    };

    public void save(Order order) {
        orderDAO.save(order);
    }

    @Transactional
    public void saveOrder(CartInfo cartInfo) {

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        order = orderDAO.save(order);

        List<CartItemInfo> items = cartInfo.getCartItems();
        List<OrderDetail> orderDetailList = order.getOrderDetailList();

        for (CartItemInfo item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setAmount(item.getAmount());
            detail.setPrice(item.getProductInfo().getPrice());
            detail.setQuantity(item.getQuantity());
            Product product = new Product();
            product.setId(item.getProductInfo().getId());
            detail.setProduct(product);
            orderDetailList.add(detail);
        }

        order = orderDAO.save(order);
    }
}
