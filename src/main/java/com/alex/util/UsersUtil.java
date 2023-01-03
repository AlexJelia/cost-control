package com.alex.util;

import com.alex.model.Role;
import com.alex.model.User;

import java.util.Arrays;
import java.util.List;


public class UsersUtil {
    public static final List<User> USER_LIST = Arrays.asList(
            new User(null, "Alex", "email1@mail.ru", "password1", Role.ROLE_ADMIN),
            new User(null, "Albert", "email2@mail.ru", "password2", Role.ROLE_USER),
            new User(null, "Cris", "email3@mail.ru", "password3", Role.ROLE_USER),
            new User(null, "John", "email4@mail.ru", "password4", Role.ROLE_USER),
            new User(null, "Alex", "email5@mail.ru", "password5", Role.ROLE_USER)
    );


}
