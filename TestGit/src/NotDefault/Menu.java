package NotDefault;


import java.util.Scanner;

/*
 *  Ето този клас прави едно единствено нещо, което е ок. Може би тук трябва да е позиционирана цялата клиентска логика - тоест всичката работа с конзолата
 */
public class Menu {
	
	public static void showMenuForApp(){
		Scanner input = new Scanner(System.in);
		do{
			int choice;
			
			System.out.println("MENU:");
			System.out.println("0.  Exit");
			System.out.println("1.  Book an appointment");
			System.out.println("2.  Delete an appointment");
			System.out.println("3.  List the appointments for today");
			System.out.println("4.  List the appointmenst for the next 3 days");
			choice = input.nextInt();
			if(choice==0) break;
			
			switch(choice){
			case 1: Appointment a = new Appointment();
			break;
			case 2: List.deleteAppointmentFromTheList();
			break;
			case 3: List.showTheAppForToday();
			break;
			case 4: List.showTheAppForThreeDays();
			break;
			default : System.out.println("You have entered invalid operation. Try again.");
			}
			
		}while(true);
		
		
		
		//input.close();
	}
	
	

}
