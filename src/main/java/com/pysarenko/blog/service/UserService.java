package com.pysarenko.blog.service;

import com.pysarenko.blog.model.User;

public interface UserService {

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(long id);

    User getUserByUsername(String username);

}
