package com.francis.janaj.financetracker.domain.user.services;

import com.francis.janaj.financetracker.domain.user.exceptions.UserException;
import com.francis.janaj.financetracker.domain.user.models.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User getUserById(Integer id) throws UserException;
    List<User> getAllUsers();
    User updateUser(Integer id, User user) throws UserException;
    Boolean deleteUser(Integer id) throws UserException;
}
