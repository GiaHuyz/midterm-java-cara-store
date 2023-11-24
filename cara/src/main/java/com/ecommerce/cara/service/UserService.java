package com.ecommerce.cara.service;

import com.ecommerce.cara.dto.UserDTO;
import com.ecommerce.cara.payload.request.UserInfoRequest;

public interface UserService {
    UserDTO getUserByUsername(String username);
    UserDTO updateUserByUsername(String username, UserInfoRequest infoRequest);
    boolean changePassword(String username, String currPass, String newPassword);
}
