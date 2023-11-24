package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.OrderDTO;
import com.ecommerce.cara.dto.OrderDetailDTO;
import com.ecommerce.cara.entity.*;
import com.ecommerce.cara.repository.*;
import com.ecommerce.cara.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public boolean addOrderFromUserCart(String username) {
        try {
            Users user = userRepository.findByUsername(username).orElseThrow();
            List<Cart> carts = cartRepository.findByUsersUsername(username);

            if(carts.isEmpty()) {
                return false;
            }

            Order order = new Order();
            order.setUsers(user);
            order.setOrderDate(new Date());
            order = orderRepository.save(order);

            for (Cart cartItem : carts) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setProductDetails(cartItem.getProductDetails());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setPrice(cartItem.getPrice());

                orderDetailRepository.save(orderDetail);
                cartRepository.deleteAll(carts);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<OrderDTO> getOrdersByUsername(String username) {
        List<Order> orderList = orderRepository.findByUsersUsername(username);
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

            orderDTO.setOrderId(order.getOrderId());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            orderDTO.setDate(outputFormat.format(order.getOrderDate()));

            double totalPrice = 0;
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                totalPrice += orderDetail.getPrice() * orderDetail.getQuantity();

                orderDetailDTO.setProductName(orderDetail.getProduct().getProductName());
                orderDetailDTO.setSize(orderDetail.getProductDetails().getSize());
                orderDetailDTO.setColor(orderDetail.getProductDetails().getColor());
                orderDetailDTO.setImage(orderDetail.getProduct().getImagesList().get(0).getImageName());
                orderDetailDTO.setQuantity(orderDetail.getQuantity());
                orderDetailDTO.setPrice(orderDetail.getPrice());

                orderDetailDTOList.add(orderDetailDTO);
            }

            orderDTO.setTotalPrice(totalPrice);
            orderDTO.setOrderDetails(orderDetailDTOList);

            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
}
