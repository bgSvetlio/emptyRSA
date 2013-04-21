package NotDefault;

/*	NOTES:
 * 
 * 1. Много добро владеене на езика
 * 2. Решението е чисто процедурно и няма много обектно ориентиран код и съответните принципи
 * 3. Доста дубликации на места
 * 4. Преплетена е клиентската логика с логиката на приложението (почти всеки клас ползва нещо от конзолата)
 * 5. Размита е структурата на класовете - не отговарят за едно единствено нещо (Single Responsibility Principle)
 * 6. Кодът не е лесен за подръжка и разширение - добавянето на функционалност ще коства модифицирането на голяма част от кода (Open Closed Principle)
 * 7. Overengineering на места - по-сложно отколкото описанието и идеята го изисква (тука и ние сме виновни щото дефиницията не е мн информативна)
 * 8. На места не съм съгласен чисто функционално - за избирането на часове и ограничаването им - 9, 10, 11, а защо не 9:45, 11:20 и т.н. ?
 * 9. Трябва хубаво да отделим модел - бизнес логика - презентационна логика
 * 
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;

// Този Лист клас играе ролята на нашия салон и пак той не би трябвало да знае за конзолата и как да кара потребителя да въвежда неща
// той трябва да разбира единствено от вече готови резервации и как да борави с тях.
public class List {
public static LinkedList<Appointment> list = new LinkedList<Appointment>();
	
	public static void addAppointment(Appointment a){
		int count =0;
		
		// Защо да го сортираме ръчно когата Java платформата ни дава достатъчно методи за това.
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
	
	// В двата метода showTheAppForToday и showTheAppForThreeDays има много код дупликация, което причинява много проблеми.
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
		
		// какво са 1,2,5,11 би трябвало да има подходящи константи в Date API-то, които да се ползват така кода е по-трудно разбираем
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
