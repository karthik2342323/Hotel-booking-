package com.example.hotel_book_basic.Models;

public class Bookings
{
    public int bookinID;
    // This is for back Tracking To search which user has booked
    public User user;
    public Bookings(int id,User x)
    {
        this.bookinID=id;
        this.user=x;
    }
}
