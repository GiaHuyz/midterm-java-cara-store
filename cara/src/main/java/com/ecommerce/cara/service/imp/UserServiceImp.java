package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.UserDTO;
import com.ecommerce.cara.entity.Users;
import com.ecommerce.cara.payload.request.UserInfoRequest;
import com.ecommerce.cara.repository.UserRepository;
import com.ecommerce.cara.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.get().getUsername());
        userDTO.setFullName(user.get().getFullName());
        userDTO.setAddress(user.get().getAddress());
        userDTO.setPhone(user.get().getPhone());
        return userDTO;
    }

    @Override
    public UserDTO updateUserByUsername(String username, UserInfoRequest infoRequest) {
        Optional<Users> usersOptional = userRepository.findByUsername(username);
        Users user = usersOptional.get();
        user.setFullName(infoRequest.getFullName());
        user.setAddress(infoRequest.getAddress());
        user.setPhone(infoRequest.getPhone());

        Users updatedUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(updatedUser.getUsername());
        userDTO.setFullName(updatedUser.getFullName());
        userDTO.setAddress(updatedUser.getAddress());
        userDTO.setPhone(updatedUser.getPhone());

        return userDTO;
    }

    @Override
    public boolean changePassword(String username, String curPass, String newPassword) {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(curPass, user.get().getPass())) {
            user.get().setPass(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
