package NotDefault;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Appointment {
	private String firstName;
	private String lastName;
	private String telephoneNumber;
	private ServiceForThisDay service;
	private String serviceType;
	private Calendar cal;
	
	
	public Appointment(){
		Date date = new Date();
		int hairCut;
		int year,month,day; 
		boolean dayExists = false;
		Scanner input1 = new Scanner(System.in);
		
		System.out.println("Enter your first name:");
		this.firstName= input1.nextLine();
		
		System.out.println("Enter your last name:");
		this.lastName = input1.nextLine();
		
		System.out.println("Enter your telephone number:");
		this.telephoneNumber = input1.nextLine();
		System.out.println();
		
		
		
		System.out.println("Enter year for the appointment: 2013/2014");
		year = input1.nextInt();
		if(year != 2013 && year != 2014){
			
			System.out.println("you have entered wrong year.");
			return;
		}
		
		System.out.println("Enter month for the appointment: ");
		month = input1.nextInt();
		if(month<1 || month>12){
			System.out.println("you have entered invalid month.");
			return;
		}
		
		System.out.println("Enter day for the appointment: ");
		day = input1.nextInt();
		if(day<1 || day>31){
			System.out.println("you have entered invalid day.");
			return;
		}
		
		cal = new GregorianCalendar(year, month-1, day);
		//System.out.println(cal.getTime());
		if(date.after(cal.getTime())){
			System.out.println("the day you have entered for the appointment is in the past!");
			return;
		}
		
		
		if(cal.getTime().getDay()==6 || cal.getTime().getDay()==0){
			System.out.println("This day is from weekend! We don't work this day. Try to make another appointment");
			return;
		}
		else {
			System.out.println("You could make an appointment.");
		}
		
		
		System.out.println("Enter type of the service:");
		System.out.println("1.  woman hair cut");
		System.out.println("2.  man hair cut");
		hairCut = input1.nextInt();
		
		if(hairCut<1 ||hairCut>2){
			System.out.println("you have entered invalid type of service.");
			return;
		}
		
		if(hairCut==1){
			this.serviceType = "woman hair cut";
		}else if(hairCut==2){
			this.serviceType = "man hair cut";
		}
		
		for(Appointment a: List.list){
			
			if(this.cal.get(1)==a.cal.get(1)&&this.cal.get(2)==a.cal.get(2)&&this.cal.get(5)==a.cal.get(5)){
				this.service = a.service;
				
				//System.out.println();
				//System.out.println(this.service);
				//System.out.println("stara referenciq");
				//System.out.println();
				
				this.service.setService(hairCut);
				this.service.chooseHourForService();
				dayExists= true;
			}
		}
		
		if(!dayExists){
			this.service = new ServiceForThisDay(hairCut);
			
			//System.out.println();
			//System.out.println(this.service);
			//System.out.println("nova referenciq");
			//System.out.println();
			
			this.service.chooseHourForService();
		}
		
		if(hairCut==1){
			this.cal.set(year, month-1, day, this.service.getAppW(), 0);
		}else if(hairCut==2){
			if(this.service.getAppM()-(int)this.service.getAppM()!=0){
				this.cal.set(year, month-1, day, (int)this.service.getAppM(), 30);
			}else if(this.service.getAppM()-(int)this.service.getAppM()==0){
				this.cal.set(year, month-1, day, (int)this.service.getAppM(), 0);
			}
		}
		
		List.addAppointment(this);
		
		//input1.close();
	}
	
	public Calendar getCal(){
		return this.cal;
	}
	
	public void displayAppointment(){
		System.out.println();
		System.out.println("Customer name:  "+ this.firstName + "  "+ this.lastName);
		System.out.println("Customer telephone number:  "+ this.telephoneNumber);
		System.out.println("Service type:  "+ this.serviceType);
		System.out.println("time for the appointment: ");
		System.out.println(cal.getTime());
		System.out.println();
	}

}
