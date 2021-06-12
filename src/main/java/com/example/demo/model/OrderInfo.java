//package com.example.demo.model;
//
//import com.example.demo.entity.Order;
//import com.example.demo.entity.OrderDetail;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OrderInfo {
//    private Long id;
//    private Date orderDate;
//    private double amount;
//
//    private String customerName;
//    private String customerAddress;
//    private String customerEmail;
//    private String customerPhone;
//
//    private List<OrderDetailInfo> details;
//
////    public OrderInfo(Order order) {
////        this.id = order.getId();
////        this.orderDate = order.getOrderDate();
////        this.amount = order.getAmount();
////        this.customerName = order.getCustomerName();
////        this.customerAddress = order.getCustomerAddress();
////        this.customerEmail = order.getCustomerEmail();
////        this.customerPhone = order.getCustomerPhone();
////        for (OrderDetail orderDetail: order.getOrderDetailList()
////             ) {
////            this.details.add(new OrderDetailInfo(orderDetail));
////        }
////    }
//}
