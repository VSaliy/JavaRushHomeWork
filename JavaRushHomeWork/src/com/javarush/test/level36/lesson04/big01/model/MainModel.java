package com.javarush.test.level36.lesson04.big01.model;

import com.javarush.test.level36.lesson04.big01.bean.User;
import com.javarush.test.level36.lesson04.big01.model.service.UserService;
import com.javarush.test.level36.lesson04.big01.model.service.UserServiceImpl;

import java.util.List;

/**
 * Created by dima on 10.05.16.
 */
public class MainModel implements Model
{
    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers()
    {
        modelData.setUsers(userService.getUsersBetweenLevels(1,100));
    }

    public void loadDeletedUsers()
    {
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
    }

}