package com.example.hotel_book_basic.Models;

import java.util.ArrayList;
import java.util.List;


public class User
{
    // User name
    public String username;
    // List of hotels associate with Username may be dashboard or used as an booking tracker
    public List<Hotels> hotels=new ArrayList<>();
    public User(String username)
    {
        this.username=username;
    }



}
