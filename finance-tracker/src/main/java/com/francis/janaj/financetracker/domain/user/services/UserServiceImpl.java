package com.francis.janaj.financetracker.domain.user.services;

import com.francis.janaj.financetracker.domain.user.exceptions.UserException;
import com.francis.janaj.financetracker.domain.user.models.User;
import com.francis.janaj.financetracker.domain.user.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User create(User user) {
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public User getUserById(Integer id) throws UserException {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            logger.error("User with id {} does not exist", id);
            throw new UserException("User not found");
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return (List) userRepo.findAll();
    }

    @Override
    public User updateUser(Integer id, User user) throws UserException {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserException("User does not exists, cannot update");
        }

        User savedUser = userOptional.get();

        savedUser.setFirstname(user.getFirstname());
        savedUser.setLastname(user.getLastname());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());
        savedUser.setAccounts(user.getAccounts());
        return userRepo.save(savedUser);
    }

    @Override
    public Boolean deleteUser(Integer id) throws UserException {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserException("User does not exist, cannot delete");
        }

        User userToDelete = userOptional.get();
        userRepo.delete(userToDelete);
        return true;
    }
}
