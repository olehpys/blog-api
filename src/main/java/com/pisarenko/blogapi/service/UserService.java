package com.pisarenko.blogapi.service;

import com.pisarenko.blogapi.model.User;

public interface UserService {

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(long id);


}
