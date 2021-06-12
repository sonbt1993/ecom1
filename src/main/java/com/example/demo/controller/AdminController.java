package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.form.ProductForm;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.validator.ProductFormValidator;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    private ProductFormValidator productFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/accountInfo")
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @GetMapping("/editProduct")
    public String editProduct(Model model, @RequestParam(value = "id", defaultValue = "")  Long id) {
        ProductForm productForm = new ProductForm();
        Product product = productService.findProductById(id);
        productForm.setId(product.getId());
        productForm.setName(product.getName());
        productForm.setBrand(product.getBrand());
        productForm.setPrice(product.getPrice());
        model.addAttribute("productForm", productForm);
        System.out.println(product);
        System.out.println(productForm);
        return "product";
    }

    @PostMapping("/editProduct")
    public String saveProduct(Model model,
                              @ModelAttribute("productForm") @Validated ProductForm productForm,
                              BindingResult result) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productService.saveProductForm(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            return "product";
        }
        return "redirect:/productList";
    }

    @GetMapping("/createProduct")
    public String createProduct(Model model) {
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        return "createProduct";
    }

    @PostMapping("/createProduct")
    public String createProduct(Model model,
                              @ModelAttribute("productForm") ProductForm productForm,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "createProduct";
        }
        try {
        productService.saveProductForm(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            return "createProduct";
        }
        return "redirect:/productList";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct( @RequestParam("id") Long id) {
        productService.delete(id);
        return "redirect:/productList";
    }

    @GetMapping("/orderList")
    public String listProduct()
    {
        return "redirect:/admin/orderList/1?sortField=id&sortDir=asc";
    }

    @GetMapping("/orderList/{pageNum}")
    public String listProductHandler(Model model, @PathVariable(name = "pageNum") int pageNum,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir){

        Page<Order> page = orderService.listAll(pageNum, sortField, sortDir);

        List<Order> orderList = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("orderList", orderList);
        return "orderList";
    }

    @GetMapping("/order")
    public String orderView(Model model, @RequestParam("orderId") Long orderId) {

        Order order = orderService.findOrderById(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailsByOrder(order);
        for(OrderDetail OrderDetail : orderDetailList) {
            System.out.println("OrderDetail:" + OrderDetail.getId());
        }
        model.addAttribute("order", order);
        model.addAttribute("orderDetailList",orderDetailList);
        return "order";
    }



}
