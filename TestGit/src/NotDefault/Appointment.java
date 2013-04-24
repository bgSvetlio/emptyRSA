package NotDefault;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 * 	Тук са преплетени 3 различни неща регистрация, клиент, услуга и дори четене от конзола. Когато тръгнем да разширяваме 
 * 	всяко от 3-те неща класът ще стане невъобразим за поддръжка и разширение.
 */

public class Appointment { 
	
	// Трябва да помислим как правилно да разпределим всичко по-долу в класове, които отговарят само за 1 точно определено нещо (важи за всички полета)
	private String firstName;
	private String lastName;
	private String telephoneNumber;
	private ServiceForThisDay service;
	
	// самото наличие на някакъв флаг по-който разбираш какво един клас трябва да прави веднага алармира, че има нещо нередно и че имплементацията може би
	// трябва да се раздели в някаква подходяща йерархия
	private String serviceType;
	
	private Calendar cal;
	
	// Могъщ конструктор, който освен да инициализира полета знае прекалено много неща и има мн логика в него
	public Appointment(){
		Date date = new Date();
		int hairCut;
		int year,month,day; 
		boolean dayExists = false;
		
		//Един обект който просто знае какво съдържа една резервация (прост носител на данни) не би трябвало да знае как да ги чете и обработва от конзола
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
		
		// При така направена проверка мисля, че не мога да си направя резервация с днешна дата ?
		if(date.after(cal.getTime())){
			System.out.println("the day you have entered for the appointment is in the past!");
			return;
		}
		
		// Много ми харесва проверката с почивните дни не се бях сетил :)
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
		
		// На базата на флагове нещо се променя, което не е много ок
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
				
				// Тук самия патърн ми е малко странен, но това ще го обсъдим утре.

				// Модифицираш обект който вече е направен за друга резервация. Сега е добре защото не ползваш съответното поле, но
				// в бъдеще може да донесе проблеми като странно некоректно поведение на някой модул.
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
		
		// Парче логика, което е доста трудно разбираемо... Доколкото разбирам просто си подсигуряваш запазването на точния час на резервацията в Date обекта
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
		
		// Затваряне на ресурса ?
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
