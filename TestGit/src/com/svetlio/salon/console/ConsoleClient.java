package com.svetlio.salon.console;

import java.util.ArrayList; 
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;


import com.svetlio.salon.api.Salon;
import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.databasesConnection.ConnectionProvider;
import com.svetlio.salon.databasesConnection.DerbyConnection;
import com.svetlio.salon.exceptions.ReservationCollisionExcetion;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;
import com.svetlio.salon.model.ServiceFactory;


public class ConsoleClient {
	
	private Salon salonSvetlio;
	
	public ConsoleClient(Salon salonSvetlio){
		this.salonSvetlio = salonSvetlio;
		
	}
	
	public void showMenu(){
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
		
		input.close();
	}
	
	private void addReservation(){
		String firstName,lastName;
		long telephoneNumber=0L;
		String telNumberStr;
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
		telNumberStr = input.nextLine();
		Scanner scanner = new Scanner(telNumberStr);
		if(scanner.hasNextLong()){
			telephoneNumber = scanner.nextLong();
		}else{
			System.out.println("You have entered invalid telephone number");
			return;
		}
		
		System.out.println();
		
		Customer customer = new Customer(firstName, lastName, telephoneNumber);
		
		System.out.println("Enter year for the reservation: 2013/2014");
		year = input.nextInt();
		if(year != 2013 && year != 2014){
			System.out.println("you have entered wrong year.");
			return;
		}
		
		System.out.println("Enter month for the reservation: ");
		month = input.nextInt();
		if(month<1 || month>12){
			System.out.println("you have entered invalid month.");
			return;
		}
		
		System.out.println("Enter day for the reservation: ");
		day = input.nextInt();
		if(day<1 || day>31){
			System.out.println("you have entered invalid day.");
			return;
		}
		
		Calendar cal = new GregorianCalendar(year, month-1, day);
		
		
		if(dateToday.get(GregorianCalendar.YEAR)!=year || dateToday.get(GregorianCalendar.MONTH)!=month-1 
				|| dateToday.get(GregorianCalendar.DAY_OF_MONTH)!=day){
			if(dateToday.after(cal)){
				System.out.println("the day you have entered for the appointment is in the past!");
				return;
			}
			
		}
		
		if(cal.getTime().getDay()==6 || cal.getTime().getDay()==0){
			System.out.println("This day is from weekend! We don't work this day. Try to make another appointment");
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
		

		if(ServiceFactory.getServiceFactory().createServiceInstance(serviceTypeChoice)== null){
			System.out.println("You have entered non-existing service");
			return;
		}else{
			service = ServiceFactory.getServiceFactory().createServiceInstance(serviceTypeChoice);
		}
		
		System.out.println();
		System.out.println("Our working time is between 9AM and 6PM.");
		System.out.printf("the procedure lasts %d minutes\n",service.getDurationInMinutes());
		System.out.println("Enter hour and minutes for the reservation: ");
		
		hourWithMinutes =input.nextDouble();
		if(hourWithMinutes<9 || hourWithMinutes>18-0.4-(double)service.getDurationInMinutes()/100){
			System.out.println("you have entered invalid hour.");
			return;
		}
		
		hour = (int)hourWithMinutes;
		doubleMinutes = (hourWithMinutes - (int)hourWithMinutes)*100;
		integerMinutes = (int)doubleMinutes;
		
		Calendar calendar = new GregorianCalendar(year, month-1, day, hour, integerMinutes);
	
		Reservation reservation = new Reservation(customer, service, calendar);
		
		try{
			if(salonSvetlio.addReservation(reservation)){
				System.out.println("you made a reservation!");
			}else{
				System.out.println("You failed to make a reservation!");
			}
		}catch(ReservationCollisionExcetion exc){
			//6te go dovur6a
			System.out.println("There is a duplication of reservation hours.");
			System.out.println("you can make a reservation:");
			List<Double> reservationForThisDay = listFreeHourForTheDay(exc.getReservation());
			
			for(int i=0;i< reservationForThisDay.size();i++){
				System.out.printf("From: %.2f",reservationForThisDay.get(i));
				i++;
				System.out.printf("   To: %.2f",reservationForThisDay.get(i));
				System.out.println();
			}
			System.out.println("this day.");
			System.out.println("Please try again.");
		}
		
	}
	
	private void displayReservationForDaysForward(int daysForward){
		
		List<Reservation> listForPrint = salonSvetlio.listReservations(daysForward);
		
		if(listForPrint.isEmpty()){
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
	
	private void deleteAReservation(){
		int year, month, day;
		double hourWithMinutes, doubleMinutes;
		int hour, integerMinutes;
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter year for the reservation you want to cancel:");
		year = input.nextInt();
		if(year != 2013 && year != 2014){
			System.out.println("you have entered wrong year.");
			return;
		}
		
		System.out.println("Enter month for the reservation you want to cancel:");
		month = input.nextInt();
		if(month<1 || month>12){
			System.out.println("you have entered invalid month.");
			return;
		}
		
		System.out.println("Enter day for the reservation you want to cancel:");
		day = input.nextInt();
		if(day<1 || day>31){
			System.out.println("you have entered invalid day.");
			return;
		}
		
		System.out.println("Enter hour and minutes for the reservation you want to cancel:");
		hourWithMinutes =input.nextDouble();
		
		
		if(hourWithMinutes<0 || hourWithMinutes>24){
			System.out.println("you have entered invalid hour.");
			return;
		}
		
		hour = (int)hourWithMinutes;
		doubleMinutes = (hourWithMinutes - (int)hourWithMinutes)*100;
		integerMinutes = (int)doubleMinutes;
		
		Calendar timeForCanceling = new GregorianCalendar(year, month-1, day, hour, integerMinutes);
		Reservation reservationRemoved = salonSvetlio.removeReservation(timeForCanceling);
		if(reservationRemoved!= null){
			System.out.println();
			System.out.println("Customer name:  "+ reservationRemoved.getCustomer().getFirstName() + "  "+ 
					reservationRemoved.getCustomer().getLastName());
			System.out.println("Customer telephone number:  "+ reservationRemoved.getCustomer().getPhoneNumber());
			System.out.println("Service type:  "+ reservationRemoved.getService());
			System.out.println("time for the appointment: ");
			System.out.println(reservationRemoved.getCalendar().getTime());
			System.out.println();
			System.out.println("you have cancle this reservation");
		}else {
			System.out.println("There is no reservation with such date!");
		}
	}
	
	private List<Double> listFreeHourForTheDay(Reservation reservation){
		ConnectionProvider connectionProvider = new DerbyConnection();
		SalonReservationDAO salonResrvationDAO = new JDBCreservationsDAOimpl(connectionProvider);
		double hour;
		
		List<Reservation> listFromDB = salonResrvationDAO.selectReservationsFromDB();
		ArrayList<Reservation> reservationsForThisDay = new ArrayList<Reservation>();
		ArrayList<Double> freeHours = new ArrayList<Double>();
		
		for(Reservation temp: listFromDB){
			if(temp.getCalendar().get(GregorianCalendar.YEAR)== reservation.getCalendar().get(GregorianCalendar.YEAR) &&
				temp.getCalendar().get(GregorianCalendar.MONTH)== reservation.getCalendar().get(GregorianCalendar.MONTH) &&
				temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)== reservation.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)){
				
				reservationsForThisDay.add(temp);
			}
		}
		Collections.sort(reservationsForThisDay , new Comparator<Reservation>() {

			@Override
			public int compare(Reservation arg0, Reservation arg1) {
				// TODO Auto-generated method stub
				return arg0.getCalendar().compareTo(arg1.getCalendar());
			}   
			});
		
			
			for(int i=0;i< reservationsForThisDay.size();i++){
				Calendar tempCalBefor = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MINUTE)- reservation.getService().getDurationInMinutes());
				
				Calendar tempCalAfter = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MINUTE)+ reservationsForThisDay.get(i).getService().getDurationInMinutes());
				
					
				
				Calendar beginHour = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						9, 0 );
				if(tempCalBefor.after(beginHour)){
					if(i==0){
						freeHours.add(9.00);
						hour = (double)tempCalBefor.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalBefor.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					if(i>0){
					Calendar tempCalAfter1 = new GregorianCalendar(reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.YEAR), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.MINUTE)+ reservationsForThisDay.get(i-1).getService().getDurationInMinutes());
				
					if(tempCalBefor.after(tempCalAfter1)){
						hour = (double)tempCalBefor.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalBefor.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					}
				}	
				
				
				Calendar finishHour = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						18, 0-reservation.getService().getDurationInMinutes() );
				
				if(tempCalAfter.before(finishHour)){
					if(i==reservationsForThisDay.size()-1){
						hour = (double)tempCalAfter.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalAfter.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
						freeHours.add(18.00);
					}
					if(i<reservationsForThisDay.size()-1){
					Calendar tempCalBefor1 = new GregorianCalendar(reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.YEAR), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.MINUTE)- reservation.getService().getDurationInMinutes());
					if(tempCalAfter.before(tempCalBefor1)){
						hour = (double)tempCalAfter.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalAfter.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					}
					
				}
			}
			
			return freeHours;
		
	}
	

}
