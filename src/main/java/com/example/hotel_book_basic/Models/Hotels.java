package com.example.hotel_book_basic.Models;

import java.util.ArrayList;
import java.util.List;


public class Hotels
{
    public int hotelID;
    public List<Bookings> bookings=new ArrayList<>();
    public Hotels(int id)
    {
        this.hotelID=id;
    }
}
