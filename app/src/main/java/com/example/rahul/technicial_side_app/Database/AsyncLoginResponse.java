package com.example.rahul.technicial_side_app.Database;


import com.example.rahul.technicial_side_app.DataTypes.User;

public interface AsyncLoginResponse{
    void userLoginSuccessful(User user);
    void userLoginUnsuccessful();
}
