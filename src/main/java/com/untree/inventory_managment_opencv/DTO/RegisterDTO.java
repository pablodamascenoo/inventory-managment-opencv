package com.untree.inventory_managment_opencv.DTO;

import com.untree.inventory_managment_opencv.model.UserRoles;

public record RegisterDTO(String email, String password, String confirmPassword, UserRoles role) {

}
