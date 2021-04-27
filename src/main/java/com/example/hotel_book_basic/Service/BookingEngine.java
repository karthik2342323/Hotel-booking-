package com.example.hotel_book_basic.Service;

import com.example.hotel_book_basic.Models.Bookings;
import com.example.hotel_book_basic.Models.Hotels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hotel_book_basic.Models.User;

import java.util.ArrayList;
import java.util.List;

/*
    Now Over Here Its Linkage
    User -> Hotels List -> Booking Id List
    Its an kind of like DashBoard
 */
@Service
public class BookingEngine
{
    public List<User> users=new ArrayList<>();
    String message=" No Message";

    /*
       available parameter
       false -> Boolking is not found so we can book
       true -> Already Booked By Itself or another User ID
     */

    @Autowired
    CheckIfAvailable available;


    // Since we are verry specific at this point so we needed Directly
    // Since over here Its an onject which consisting of name

     // Imp
    // Since we know that Hotel we are parceling to as It Is so
    // It will act as an reference so Any inserting/deleting will Change on any Referenced Var(Backend/Dashboard)
     // Imp
    public String book(int hotelID,int bookingID,User user)
    {
        // for debugging
           // Clear the message
        message=" No User Found";
        // for debugging

        // Check If User exist Because No Login System Had implemented
        if(!findUser(user))
        {
            return message;
        }


        boolean result=available.checkHotelBooked(hotelID,bookingID,user.username);
        // means we can't book


        // reverse means That one is available for US
        if(!result)
        {
            // Make Sure to Book to the Engine and Add to dashBoard

            // Add to dashBoard
            // since IN real Time we gonna add first on Booking Engine

            // Now over here we definitely gonna get since we have checked the condition
            User real=null;
            Hotels hotelForUpdate=null;

            // get User
            for(int i=0;i<users.size();i++)
            {
                if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
                {
                    real=users.get(i);
                }
            }

            List<Hotels> hotels=real.hotels;
            // Now It depends if List is empty or not
            if(hotels.size()>0)
            {
                // Now Check If Hotel is Exist or not If Not then Add Both
                // And If Hotel Is exist Then Add Booking Id
                Hotels hotel=null;
                for(int i=0;i<hotels.size();i++)
                {
                    if(hotels.get(i).hotelID==hotelID)
                    {
                        hotel=hotels.get(i);
                        break;
                    }
                }

                // If we wont found hotel
                if(hotel==null)
                {
                    hotel=new Hotels(hotelID);
                    Bookings booking=new Bookings(bookingID,real);
                    hotel.bookings.add(booking);
                    hotels.add(hotel);
                }
                // If we found Hotel
                else
                {
                    // Since Its an case arrise i.e If User had Booked
                    // Then Since we wont be in This Section
                    //On removal Time Make Sure to remove from
                    // Both Ends Backend vise and DashBoard Wise
                    List<Bookings> bookings=hotel.bookings;
                    // Find
                    bookings.add(new Bookings(bookingID,real));
                }

                // for update
                hotelForUpdate=hotel;
                // for update
            }
            // Over Here We are Sure That Its Not exist So fill that Hotel and Booking
            else
            {
                Hotels hotel=new Hotels(hotelID);
                hotel.bookings.add(new Bookings(bookingID,real));
                hotels.add(hotel);

                // for update
                hotelForUpdate=hotel;
                // for update
            }

            // For Update the Backed Registry for common check of Book
            updateBackend(hotelForUpdate);
            message=" Register successfull Username : "+user.username+" , HotelID : "+hotelID+" , BookingID : "+bookingID;
        }
        // This one means Either User had booked or Its Booked By Some Another User
        else
        {
            // for message @link printMessage()
            message=available.getMessage();
        }

        return message;


    }


    public void register(User user)
    {
        boolean found=findUser(user);
        if(!found)
        {
            users.add(user);
        }
        // message execution
        // Head In to the @link printMessage() for get message
        if(found)
        {
            message="Username already Exist check  Another one ";
        }
        else
        {
            message="Registered Successfull";
        }
    }

    // Now the complexity arises is User gonna remove from there
    public String removeUser(User user)
    {
        if(!findUser(user))
        {
            return "UserID Not found";
        }

        User real=null;

        // clean the Backend
        // If Bookings are exist then delete from backend
        List<Hotels> hotels=null;
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
            {
                hotels=users.get(i).hotels;
                real=users.get(i);
            }
        }
        if(hotels!=null)
        {
            for(int i=0;i<hotels.size();i++)
            {
                List<Bookings> bookings=hotels.get(i).bookings;
                for(int j=0;j<bookings.size();j++)
                {
                    // We are parcelling the User for verify
                    available.cancel(hotels.get(i).hotelID,bookings.get(j).bookinID,real);
                }
            }
        }

        // clean Dashboard
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
            {
                users.remove(users.get(i));
                return " Successfully Removed User  : "+user.username;
            }
        }
        // for debugging
        return " ";
        // for debugging
    }
    public boolean findUser(User user)
    {
        // If Its found
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
            {
                return true;
            }
        }

        // Else
        return false;
    }
    public void updateBackend(Hotels hotel)
    {
        available.register(hotel);
    }

    public String  printMessage()
    {
        return message;
    }

    public String showBookings(User user)
    {
        String message=" U Haven't Booked Yet ";
        User real=null;

        if(!findUser(user))
        {
            return " User Not found ";
        }

        // Over Here Means We User already Exist
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
            {
                real=users.get(i);
                break;
            }
        }

        // for debugging
        int signal=0;
        // for debugging

        // find the booking
        List<Hotels> hotels=real.hotels;
        for(int i=0;i<hotels.size();i++)
        {
            List<Bookings> bookings=hotels.get(i).bookings;
            // Because Booking Cannot Exist without ID
            for(int j=0;j<bookings.size();j++)
            {
                // Refresh Message
                if(signal==0)
                {
                    signal=1;
                    message=" ";
                }
                // add on Like concat
                message=message+" <br>  Hotel ID : "+hotels.get(i).hotelID+",  Booking ID: "+bookings.get(j).bookinID;
            }
        }
        return message;

    }

    // remove the booking
    // Both parallel first remove from system then dashboard
    public String  cancel(int hotelID,int bookingID,User user)
    {
        if(!findUser(user))
        {
            return " User Not exist";
        }

        // cancel from backed
        available.cancel(hotelID,bookingID,new User(user.username));

        // fetch the message
        message=" backend <br>";
        message+=available.getMessage()+"<br>";

        User real=null;
        // find the user
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).username.toLowerCase().equals(user.username.toLowerCase()))
            {
                real=users.get(i);
                break;
            }
        }

        List<Hotels> hotels=real.hotels;
        List<Bookings> bookings=null;
        // Now condition may Apply That Hotel Has not found
        for(int i=0;i<hotels.size();i++)
        {
            if(hotels.get(i).hotelID==hotelID)
            {
                bookings=hotels.get(i).bookings;
            }
        }
        if(bookings==null)
        {
            return " Hotel ID Not found <br>";
        }
        else
        {
            // If Booking Found
            message+=" Dashboard <br>";
            for(int i=0;i<bookings.size();i++)
            {
                if(bookings.get(i).bookinID==bookingID)
                {
                    // Just Remove the Booking
                    bookings.remove(bookings.get(i));
                    message+=" Removed Hotel ID : "+hotelID+" , Booking Id : "+bookingID;
                    return message;
                }
            }
            // If Its Not found
            return " See This :  Booking ID Not found <Br>";
        }

    }

    public String getUsers()
    {
        message=" ";
        // If Use found
        for(int i=0;i<users.size();i++)
        {
            message+=" "+(i+1)+") "+users.get(i).username+" <br> ";
            if(i==users.size()-1)
            {
                return message;
            }
        }
        // Else
        return " No User";
    }

    // This one need to check in to the System
    public String getBookings()
    {
        return available.listOfBooking();
    }

}




