package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.CartDTO;
import com.ecommerce.cara.entity.Cart;
import com.ecommerce.cara.entity.Product;
import com.ecommerce.cara.entity.ProductDetails;
import com.ecommerce.cara.entity.Users;
import com.ecommerce.cara.payload.request.CartRequest;
import com.ecommerce.cara.repository.CartRepository;
import com.ecommerce.cara.repository.ProductDetailRepository;
import com.ecommerce.cara.repository.ProductRepository;
import com.ecommerce.cara.repository.UserRepository;
import com.ecommerce.cara.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public boolean addCartItems(List<CartRequest> cartItems, String username) {
        try {
            Users users = userRepository.findByUsername(username).orElseThrow();

            cartItems.forEach(item -> {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + item.getProductId()));

                ProductDetails productDetail = productDetailRepository.findById(item.getDetailId())
                        .orElseThrow(() -> new IllegalArgumentException("Product detail not found with id: " + item.getDetailId()));

                // Kiểm tra nếu đã có chi tiết sản phẩm trong giỏ hàng
                Optional<Cart> existingItem = cartRepository.findByUsersAndProductDetails(users, productDetail);

                if (existingItem.isPresent()) {
                    // Nếu đã có, cập nhật số lượng
                    existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
                    cartRepository.save(existingItem.get());
                } else {
                    // Nếu chưa có, thêm mới vào giỏ hàng
                    Cart cart = new Cart();
                    cart.setUsers(users);
                    cart.setProduct(product);
                    cart.setProductDetails(productDetail);
                    cart.setQuantity(item.getQuantity());
                    cart.setPrice(item.getPrice());

                    cartRepository.save(cart);
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CartDTO> getCartByUser(String username) {
        List<Cart> userCart = cartRepository.findByUsersUsername(username);
        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart : userCart) {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setId(cart.getCartId());
            cartDTO.setProductName(cart.getProduct().getProductName());
            cartDTO.setQuantity(cart.getQuantity());

            cartDTO.setImage(cart.getProduct().getImagesList().get(0).getImageName());
            cartDTO.setColor(cart.getProductDetails().getColor());
            cartDTO.setSize(cart.getProductDetails().getSize());
            cartDTO.setPrice(cart.getPrice());
            cartDTO.setSubtotal(cart.getPrice() * cart.getQuantity());

            cartDTOList.add(cartDTO);
        }

        return cartDTOList;
    }

    @Override
    public void deleteCartItem(int cartItemId, String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getUsers().equals(user)) {
            throw new IllegalArgumentException("Cart item does not belong to user: " + username);
        }

        cartRepository.delete(cartItem);
    }
}
