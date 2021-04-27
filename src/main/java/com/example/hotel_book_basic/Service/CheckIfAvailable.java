package com.example.hotel_book_basic.Service;
import com.example.hotel_book_basic.Models.Bookings;
import com.example.hotel_book_basic.Models.Hotels;
import com.example.hotel_book_basic.Models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 Since we Haven't Use the DB so Over here Its an DS
 Hotels List -> Booking ID -> User ID

 Now Booking ID is relinquish to User In
 order to denote User has booked or Other as Booked
 Both result has same
 */

@Service
public class CheckIfAvailable
{

    // Current hotel booking Register
    List<Hotels> hotels=new ArrayList<>();
    String message=" No message Availabe Yet";
    public boolean checkHotelBooked(int hotelID,int bookingID,String username)
    {
        if(hotels.size()>0)
        {
            // If Its found then Return true
            for(int i=0;i<hotels.size();i++)
            {
                // check the HotelID
                if(hotels.get(i).hotelID==hotelID)
                {
                    List<Bookings> booking=hotels.get(i).bookings;
                    // Check the bookingId
                    for(int j=0;j<booking.size();j++)
                    {
                        if(booking.get(j).bookinID==bookingID)
                        {
                            // This is booked
                            // Now to find out who had booked By same User or By Another one check on
                            // @link getMessage()
                            if(booking.get(j).user.username.toLowerCase().equals(username.toLowerCase()))
                            {
                                message="Can't Booked Because U had already Booked ";
                            }
                            else
                            {
                                message=" This one is reserved Choose another one ";
                            }
                            return true;
                        }
                    }

                }
            }
            // Else Not found
            return false;
        }
        else
        {
            return false;
        }
    }
    public void register(Hotels hotel)
    {
        // for debugging
        System.out.println(" See This initial Length : "+hotels.size());
        // Before
        List<Bookings> first=new ArrayList<>();
        if(hotels.size()>=1)
        {
            first = hotels.get(0).bookings;
        }
        if(first!=null && first.size()>0)
        {
            System.out.println(" Before Data ");
            for(int i=0;i<first.size();i++)
            {
                System.out.println(" See This : "+first.get(i).bookinID);
            }
        }
        // for debugging

        // Break the reference means Dereferencing
        List<Bookings> bookings=new ArrayList<>(hotel.bookings);
        Hotels hotel_1=new Hotels(hotel.hotelID);
        hotel_1.bookings=bookings;

        // If Hotel Exist then change the List of Booking
        for(int i=0;i<hotels.size();i++)
        {
            // Update the List
            if(hotels.get(i).hotelID==hotel.hotelID)
            {
                if(hotels.get(i).bookings.size()>0)
                {
                    hotels.get(i).bookings.clear();
                }
                hotels.get(i).bookings.addAll(hotel.bookings);
                return;
            }
        }
        // Else Part
        hotels.add(hotel_1);

        // for debugging
        System.out.println(" See this After length "+hotels.size());
        // for debugging
    }
    // Removing Hotel Means Removing
    public void cancel(int hotelID, int bookingID, User user)
    {
        // We cant verify the user on step 1 because we don't
        // know that booking since we have booking

        int signal=0;
        for(int i=0;i<hotels.size();i++)
        {
            if(hotels.get(i).hotelID==hotelID)
            {
                message=" Hotel Found <be> finding Booking ID : ";
                // Hotel Id is found then Find the Booking ID

                List<Bookings> booking=hotels.get(i).bookings;



                for(int j=0;j<booking.size();j++)
                {

                    if(booking.get(j).bookinID==bookingID)
                    {


                        // One Extra step Verify the User
                        // Thats the reason of Referencing  of User
                        if(user.username.toLowerCase().equals(booking.get(j).user.username.toLowerCase()))
                        {
                            // for debugging
                            System.out.println(" User ID Matched");
                            // for debugging

                            signal=1;
                            // since Over here we can't remove the hotel
                            // Because Over here If we do that then whole Booking will go
                            // So remove the Booking Id;
                            boolean x=booking.remove(booking.get(j));


                            // set message
                            message=" Removed   Hotel ID : "+hotelID+" , Booking ID "+bookingID;
                            //System.out.println(" Removed   Hotel ID : "+hotelID+" , Booking ID "+bookingID);

                            // for debugging
                            System.out.println(" Removed   Hotel ID : "+hotelID+" , Booking ID "+bookingID+" Success : "+x);
                            // for debugging

                            break;
                        }
                    }
                }

                // Now Since Hotel Id will remain Same means No 2 Hotels cannot have same ID
                // Yea So break;
                break;
            }
            if(signal==1)
            {
                break;
            }
            else
            {
                message=" Cant find the Booking ID . Make Sure Booking Id should remain As per Booking Order";
            }
        }


    }

    public String getMessage()
    {
        return  message;
    }
    public String listOfBooking()
    {
        message=" Not found";
        for(int i=0;i<hotels.size();i++)
        {
            List<Bookings> bookings=hotels.get(i).bookings;


            // for debugging
            if(i==0)
            {
                System.out.println(" See This Print Length  : "+hotels.get(0).bookings.size()+" Check Ref Length : "+bookings.size());
                System.out.println(" Check Val ");
                for(Bookings x:hotels.get(0).bookings)
                {
                    System.out.println(" See This Values :  "+x.bookinID);
                }
            }
            // for debugging



            // Atleast 1 element
            if(bookings.size()>=1)
            {
                // for debugging
                if(i==0)
                {
                    System.out.println(" Inside Executed Val : "+bookings.size());
                }
                // for debugging

                // refresh
                if(i==0)
                {
                    message = " ";
                }
                for(int j=0;j<bookings.size();j++)
                {
                    message+=" userID : "+bookings.get(j).user.username+" , hotelID : "+hotels.get(i).hotelID+" , bookingID : "+bookings.get(j).bookinID+"<br>";
                }
            }
        }
        return message;
    }

}
