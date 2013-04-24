package com.svetlio.salon.console;

//import java.text.DecimalFormat; 
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;


import com.svetlio.salon.api.SalonSvetlio;
import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;
import com.svetlio.salon.model.WomanHairCut;


public class ConsoleClient {
	public static final int ARG_FOR_YEAR_IN_CALENDAR=1;
	public static final int ARG_FOR_MONTH_IN_CALENDAR=2;
	public static final int ARG_FOR_DAY_IN_CALENDAR=5;
	public static final int ARG_FOR_HOURS_IN_CALENDAR=11;
	public static final int ARG_FOR_MINUTES_IN_CALENDAR=12;
	
	public static void showMenu(){
		Scanner input = new Scanner(System.in);
		do{
			int choice;
			
			System.out.println("MENU:");
			System.out.println("0.  Exit");
			System.out.println("1.  Book a reservation");
			System.out.println("2.  Delete a reservation");
			System.out.println("3.  List the reservation for today");
			System.out.println("4.  List the reservation for the next 3 days");
			System.out.println();
			
			choice = input.nextInt();
			if(choice==0) break;
			
			switch(choice){
			case 1: addReservation();
			break;
			case 2: deleteAReservation();
			break;
			case 3: displayReservationForDaysForward(0);
			break;
			case 4: displayReservationForDaysForward(3);
			break;
			
			default : System.out.println("You have entered invalid operation. Try again.");
			}
			
		}while(true);
		//input.close();
	}
	
	private static void addReservation(){
		String firstName,lastName;
		long telephoneNumber;
		int year,month,day;
		double hourWithMinutes, doubleMinutes;
		int hour, integerMinutes;
		int serviceTypeChoice;
		Service service;
		Calendar dateToday= new GregorianCalendar();
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter your first name:");
		firstName= input.nextLine();
		
		System.out.println("Enter your last name:");
		lastName = input.nextLine();
		
		System.out.println("Enter your telephone number:");
		telephoneNumber = input.nextLong();
		System.out.println();
		
		Customer customer = new Customer(firstName, lastName, telephoneNumber);
		
		System.out.println("Enter year for the reservation: 2013/2014");
		year = input.nextInt();
		if(year != 2013 && year != 2014){
			System.out.println("you have entered wrong year.");
			//input.close();
			return;
		}
		
		System.out.println("Enter month for the reservation: ");
		month = input.nextInt();
		if(month<1 || month>12){
			System.out.println("you have entered invalid month.");
			//input.close();
			return;
		}
		
		System.out.println("Enter day for the reservation: ");
		day = input.nextInt();
		if(day<1 || day>31){
			System.out.println("you have entered invalid day.");
			//input.close();
			return;
		}
		
		Calendar cal = new GregorianCalendar(year, month-1, day);
		
		
		if(dateToday.get(ARG_FOR_YEAR_IN_CALENDAR)!=year || dateToday.get(ARG_FOR_MONTH_IN_CALENDAR)!=month-1 
				|| dateToday.get(ARG_FOR_DAY_IN_CALENDAR)!=day){
			//System.out.println("the date is not today");
			if(dateToday.after(cal)){
				System.out.println("the day you have entered for the appointment is in the past!");
				//input.close();
				return;
			}
			
		}
		
		if(cal.getTime().getDay()==6 || cal.getTime().getDay()==0){
			System.out.println("This day is from weekend! We don't work this day. Try to make another appointment");
			//input.close();
			return;
		}
		else {
			System.out.println("You have entered valid day for reservation.");
			System.out.println();
		}
		
		System.out.println("Enter service type:");
		System.out.println(" 1. Man hair cut.");
		System.out.println(" 2. Woman hair cut.");
		serviceTypeChoice = input.nextInt();
		
		switch(serviceTypeChoice){
		case 1: service = new ManHairCut();
		break;
		case 2: service = new WomanHairCut();
		break;
		default: System.out.println("You have entered non-existing service");
		//input.close();
		return;
		}
		
		System.out.println("Our working time is between 9AM and 6PM.");
		System.out.printf("the procedure lasts %d minutes\n",service.getDurationInMinutes());
		System.out.println("Enter hour and minutes for the reservation: ");
		
		hourWithMinutes =input.nextDouble();
		
		
		if(hourWithMinutes<9 || hourWithMinutes>18-0.4-(double)service.getDurationInMinutes()/100){
			System.out.println("you have entered invalid hour.");
			//input.close();
			return;
		}
		//hourWithMinutes = Double.parseDouble(new DecimalFormat("#.##").format(hourWithMinutes));
		//System.out.println("hour with minutes "+hourWithMinutes);
		
		hour = (int)hourWithMinutes;
		//System.out.println("hour "+hour);
		doubleMinutes = (hourWithMinutes - (int)hourWithMinutes)*100;
		//System.out.println("double minutes "+doubleMinutes);
		integerMinutes = (int)doubleMinutes;
		//System.out.println("int minutes "+integerMinutes);
		
		Calendar calendar = new GregorianCalendar(year, month-1, day, hour, integerMinutes);
		//System.out.println(calendar.getTime());
		
		Reservation reservation = new Reservation(customer, service, calendar);
		
		try{
			SalonSvetlio add = new SalonSvetlio();
			add.addReservation(reservation);
			System.out.println("you make a reservation!");
		}catch(ReservationCollision exc){
			//6te go dovur6a
			System.out.println("ima zase4ka na 4asove");
		}
		
		//input.close();
	}
	
	public static void displayReservationForDaysForward(int daysForward){
		SalonSvetlio add = new SalonSvetlio();
		LinkedList<Reservation> listForPrint = add.listReservations(daysForward);
		if(listForPrint==null){
			System.out.println();
			System.out.println("There is no reservation for this period.");
			return;
		}
		
		for(Reservation temp: listForPrint){
			System.out.println();
			System.out.println("Customer name:  "+ temp.getCustomer().getFirstName() + "  "+ 
					temp.getCustomer().getLastName());
			System.out.println("Customer telephone number:  "+ temp.getCustomer().getPhoneNumber());
			System.out.println("Service type:  "+ temp.getService());
			System.out.println("time for the appointment: ");
			System.out.println(temp.getCalendar().getTime());
			System.out.println();
		}
	}
	
	public static void deleteAReservation(){
		int year, month, day;
		double hourWithMinutes, doubleMinutes;
		int hour, integerMinutes;
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter year for the reservation you want to cancel:");
		year = input.nextInt();
		if(year != 2013 && year != 2014){
			System.out.println("you have entered wrong year.");
			//input.close();
			return;
		}
		
		System.out.println("Enter month for the reservation you want to cancel:");
		month = input.nextInt();
		if(month<1 || month>12){
			System.out.println("you have entered invalid month.");
			//input.close();
			return;
		}
		
		System.out.println("Enter day for the reservation you want to cancel:");
		day = input.nextInt();
		if(day<1 || day>31){
			System.out.println("you have entered invalid day.");
			//input.close();
			return;
		}
		
		System.out.println("Enter hour and minutes for the reservation you want to cancel:");
		hourWithMinutes =input.nextDouble();
		
		
		if(hourWithMinutes<0 || hourWithMinutes>24){
			System.out.println("you have entered invalid hour.");
			//input.close();
			return;
		}
		
		hour = (int)hourWithMinutes;
		doubleMinutes = (hourWithMinutes - (int)hourWithMinutes)*100;
		integerMinutes = (int)doubleMinutes;
		
		Calendar timeForCanceling = new GregorianCalendar(year, month-1, day, hour, integerMinutes);
		SalonSvetlio rm = new SalonSvetlio();
		
		if(rm.removeReservation(timeForCanceling)!= null){
			System.out.println("you have cancle this reservation");
		}
		//input.close();
	}

}
