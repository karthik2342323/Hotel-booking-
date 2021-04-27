package com.example.hotel_book_basic.controller;

import com.example.hotel_book_basic.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel_book_basic.Service.BookingEngine;
import sun.java2d.loops.FillRect;

@RestController
public class control {
    @Autowired
    BookingEngine bookingEngine;

    /*
      Now One More Thing This Api Will Be hirarchical Structure
      We can see Over Here Like
      /a
      /a/b
      /a/b/c
      /a/b/c/d
      May Vary But almost same for Api
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String demo() {
        return "<B> 1) welcome to los angeles <br>  2) welcome to san francisco </B> ";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String getRegister() {
        return bookingEngine.getUsers();
    }

    // Now Over Here PathVar and that Var in Curly Braces should Remain Same
    @RequestMapping(value = "/register/{username}", method = RequestMethod.GET)
    String setRegister(@PathVariable String username) {
        User user = new User(username);
        bookingEngine.register(user);
        return bookingEngine.printMessage();
    }

    @RequestMapping(value = "/user/{username}/hotelID/{hotelID}/bookingID/{bookingID}/setBook",method = RequestMethod.GET)
    String setBook(@PathVariable String username,@PathVariable int hotelID,@PathVariable int bookingID)
    {
        bookingEngine.book(hotelID,bookingID,new User(username));
        return bookingEngine.printMessage();
    }

    @RequestMapping(value = "/user/{username}/getBook",method = RequestMethod.GET)
    String getBook(@PathVariable String username)
    {
        return bookingEngine.showBookings(new User(username));
    }

    // For verification we are taking User so not anyone can remove that Booked
    @RequestMapping(value = "/user/{username}/hotelID/{hotelID}/bookingID/{bookingID}/removeBook",method = RequestMethod.GET)
    String removeBooking(@PathVariable String username,@PathVariable int hotelID,@PathVariable int bookingID)
    {
        return bookingEngine.cancel(hotelID,bookingID,new User(username));
    }

    // Must remember that Removing the User means Removing the Whole Booked Hotels
    // This task must handle By DB But for demonstration Perspective We are On
    // Java So It Will be static means after server is off That Things no longer gonna Hold \
    @RequestMapping(value = "/user/{username}/remove",method = RequestMethod.GET)
    String removeUser(@PathVariable String username)
    {
        return bookingEngine.removeUser(new User(username));
    }

    @RequestMapping(value= "/getListOfBooking",method = RequestMethod.GET)
    String getListOfBooking()
    {
        return bookingEngine.getBookings();
    }

}
