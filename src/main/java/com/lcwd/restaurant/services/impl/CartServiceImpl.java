package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.AddItemToCartRequest;
import com.lcwd.restaurant.dtos.CartDto;
import com.lcwd.restaurant.entities.Cart;
import com.lcwd.restaurant.entities.CartItem;
import com.lcwd.restaurant.entities.Product;
import com.lcwd.restaurant.entities.User;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import com.lcwd.restaurant.repositories.CartItemRepository;
import com.lcwd.restaurant.repositories.CartRepository;
import com.lcwd.restaurant.repositories.ProductRepository;
import com.lcwd.restaurant.repositories.UserRepository;
import com.lcwd.restaurant.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ModelMapper mapper ;

    @Autowired
    private CartRepository cartRepository ;


    @Autowired
    private CartItemRepository cartItemRepository ;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity =request.getQuantity() ;
         String productId= request.getProductId() ;
         //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database "));
        /// fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id "));
        Cart cart1 = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user not found "));
        Cart cart =null;
        try{
            cart= cartRepository.findByUser(user).get() ;
        }catch (NoSuchElementException e) {
            cart = new Cart()  ;
            cart.setCreatedAt(new Date());
        }
        //perform  cart operations
        AtomicReference<Boolean> updated =  new AtomicReference(false) ;
        List<CartItem> items  = cart.getItems() ;
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProjectId().equals(productId)) {
                //item already present  in  cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems) ;

        //create items
        if(!updated.get()){
            CartItem cartItem = new CartItem() ;
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(quantity * product.getPrice());
            cartItem.setCart(cart);
            cartItem.setProduct(product) ;
            cart.getItems().add(cartItem) ;
        }
        //create items

        cart.setUser(user) ;
        Cart save =  cartRepository.save(cart) ;
        return mapper.map(save ,CartDto.class) ;
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

        //conditions
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("cartItem not found "));
        cartItemRepository.delete(cartItem1);  ;

    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("This user not found "));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart resource not found given  user"));
        cart.getItems().clear();
        cartRepository.save(cart) ;
    }
    @Override
    public CartDto  getCartByUser(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("This user is not found "))  ;
        Cart  cart =  cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("user have does not have items"));
        cart.getItems().clear();
        Cart save = cartRepository.save(cart);
        return mapper.map(save, CartDto.class) ;
    }


}
