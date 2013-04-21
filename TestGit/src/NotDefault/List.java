package NotDefault;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;

public class List {
public static LinkedList<Appointment> list = new LinkedList<Appointment>();
	
	public static void addAppointment(Appointment a){
		int count =0;
		
		if(list.isEmpty()){
			list.add(a);
		} else{
			for(Appointment temp: list){
				if(a.getCal().getTime().before(temp.getCal().getTime())){
					list.add(count, a);
					break;
				}
				if(count==list.size()-1){
					
					list.addLast(a);
					break;
				}
				count++;
			}
		}
		System.out.println("appointment is added");
	
	}
	
	public static void showTheAppForToday(){
		Calendar date = new GregorianCalendar();
		boolean freeToday = true;
		for(Appointment a: list){
			if(date.get(1)== a.getCal().get(1)&& date.get(2)==a.getCal().get(2)&& date.get(5)==a.getCal().get(5)){
				a.displayAppointment();
				freeToday= false;
			}
		}
		if(freeToday){
			System.out.println("There is no appointments for today.");
		}
	}
	
	public static void showTheAppForThreeDays(){
		Calendar date = new GregorianCalendar();
		boolean freeNextThreeDays = true;
		for(Appointment a: list){
			if(date.get(1)== a.getCal().get(1)&& date.get(2)==a.getCal().get(2)&& date.get(5)==a.getCal().get(5)){
				a.displayAppointment();
				freeNextThreeDays = false;
			}
		}
		for(Appointment a: list){
			if(date.get(1)== a.getCal().get(1)&& date.get(2)==a.getCal().get(2)&& date.get(5)+1==a.getCal().get(5)){
				a.displayAppointment();
				freeNextThreeDays = false;
			}
		}
		for(Appointment a: list){
			if(date.get(1)== a.getCal().get(1)&& date.get(2)==a.getCal().get(2)&& date.get(5)+2==a.getCal().get(5)){
				a.displayAppointment();
				freeNextThreeDays = false;
			}
		}
		if(freeNextThreeDays){
			System.out.println("There is no appointments for the next three days.");
		}
	}
	
	public static void deleteAppointmentFromTheList(){
		boolean appExists= false;
		int year, month, day;
		double hour;
		int arHour, arMinutes;
		double dMinutes;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter year for the appointment you want to cancel:");
		year = input.nextInt();
		
		System.out.println("Enter month for the appointment you want to cancel:");
		month = input.nextInt();
		
		System.out.println("Enter day for the appointment you want to cancel:");
		day = input.nextInt();
		
		System.out.println("Enter hour for the appointment you want to cancel:");
		hour = input.nextDouble();
		
		arHour= (int)hour;
		dMinutes = (hour - (int)hour)*100;
		arMinutes = (int)dMinutes;
		
		for(Appointment a: list){
			if(a.getCal().get(1)==year && a.getCal().get(2)== (month-1) && a.getCal().get(5)==day && a.getCal().get(11)==arHour
					&& a.getCal().get(12)== arMinutes){
				list.remove(a);
				appExists = true;
				System.out.println("Your appointment is canceled.");
			}
		}
		if(!appExists){
			System.out.println("there is no such appointment in the list.");
		}
		
	}

}
