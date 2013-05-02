package com.svetlio.salon.api;

import java.util.Calendar; 
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import NotDefault.Appointment;

import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Reservation;

/*
 *  Nahvurlql sum nqkoi ne6ta, v pismoto 6te ti opisha o6te ne6ta za da se ponatrupa kod predi sledva6tata sre6ta.
 */

public class SalonSvetlio implements Salon {
	
	/*
	 *  Nqma nujda da se polzvat sobstveni konstanti, ako poglednesh Calendar klasa v nego ima vsi4ki konstanti koito sa ti neobhodimi.
	 *  Ti dori gi definirash na dve mesta, koeto e code duplication, koito trqbva da se mahne.
	 */
	public static final int ARG_FOR_YEAR_IN_CALENDAR=1;
	public static final int ARG_FOR_MONTH_IN_CALENDAR=2;
	public static final int ARG_FOR_DAY_IN_CALENDAR=5;
	public static final int ARG_FOR_HOURS_IN_CALENDAR=11;
	public static final int ARG_FOR_MINUTES_IN_CALENDAR=12;
	
	/*
	 *  Tova ne e ok. Za6to lista e publi4en i stati4en tova pak ne e obektno orientirano programirane. Taka se ubiva kapsulaciqta - 
	 *  vseki moje da pravi kakvot si pojelanie s lista ot rezervacii ... ne e tova ideqta.
	 */
	public static LinkedList<Reservation> list= new LinkedList<Reservation>();

	@Override
	public void addReservation(Reservation reservation) throws ReservationCollision {
		int count = 0;

		/*
		 *  Tuk pak se poqvqva code duplication - iz celiq klas se vikat ".getCalendar.get(..)" methodi pomisli kak moje da obosobish pove4eto
		 *  sravneniq na edno mqsto, koeto da e otgovorno samo za tova. Moje bi pomisli za komparatori. A tam kadeto e nujno da sravnqvash 4isti
		 *  dati kato pri delete, pro4eti dokumentacqta na Calendar i vij 4e klasa implementira Comparable - toest dve dati samo po sebe si sa
		 *  sravnimi.
		 */ 
		
		for(Reservation temp: list){
			Calendar tempCalBefor = new GregorianCalendar(temp.getCalendar().get(ARG_FOR_YEAR_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_MONTH_IN_CALENDAR), temp.getCalendar().get(ARG_FOR_DAY_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_HOURS_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_MINUTES_IN_CALENDAR)- reservation.getService().getDurationInMinutes());
			Calendar tempCalAfter = new GregorianCalendar(temp.getCalendar().get(ARG_FOR_YEAR_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_MONTH_IN_CALENDAR), temp.getCalendar().get(ARG_FOR_DAY_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_HOURS_IN_CALENDAR), 
					temp.getCalendar().get(ARG_FOR_MINUTES_IN_CALENDAR)+ temp.getService().getDurationInMinutes());
			
			if(reservation.getCalendar().after(tempCalBefor) && reservation.getCalendar().before(tempCalAfter)){
				throw new ReservationCollision(reservation.getCalendar());
			}
		}
		
		// Nujno li e sam da gi sortirash - ne 4e e mnogo losho, no pak govorim prosto za prostota i 4istota na koda, koeto e vajno
		if(list.isEmpty()){
			list.add(reservation);
		} else{
			for(Reservation temp: list){
				if(reservation.getCalendar().getTime().before(temp.getCalendar().getTime())){
					list.add(count, reservation);
					break;
				}
				if(count==list.size()-1){
					
					list.addLast(reservation);
					break;
				}
				count++;
			}
		}
		

	}

	@Override
	public Reservation removeReservation(Calendar reservationDate) {
		for(Reservation temp: list){
			
			//".getCalendar.get(..)"
			if(reservationDate.get(ARG_FOR_YEAR_IN_CALENDAR)== temp.getCalendar().get(ARG_FOR_YEAR_IN_CALENDAR) && 
					reservationDate.get(ARG_FOR_MONTH_IN_CALENDAR)== temp.getCalendar().get(ARG_FOR_MONTH_IN_CALENDAR) &&
					reservationDate.get(ARG_FOR_DAY_IN_CALENDAR) == temp.getCalendar().get(ARG_FOR_DAY_IN_CALENDAR) &&
					reservationDate.get(ARG_FOR_HOURS_IN_CALENDAR)== temp.getCalendar().get(ARG_FOR_HOURS_IN_CALENDAR) &&
					reservationDate.get(ARG_FOR_MINUTES_IN_CALENDAR)==temp.getCalendar().get(ARG_FOR_MINUTES_IN_CALENDAR)){
				
				list.remove(temp);
				return temp;
			}
					
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Reservation> listReservations(int daysForward) {
		// TODO Auto-generated method stub
		Calendar dateToday = new GregorianCalendar();
		LinkedList<Reservation> listForPrint= new LinkedList<Reservation>();
		
		//".getCalendar.get(..)"
		for(int i=0;i<=daysForward;i++){
			for(Reservation temp: SalonSvetlio.list){
				if(temp.getCalendar().get(ARG_FOR_YEAR_IN_CALENDAR)== dateToday.get(ARG_FOR_YEAR_IN_CALENDAR) &&
					temp.getCalendar().get(ARG_FOR_MONTH_IN_CALENDAR)== dateToday.get(ARG_FOR_MONTH_IN_CALENDAR) &&
					temp.getCalendar().get(ARG_FOR_DAY_IN_CALENDAR)== dateToday.get(ARG_FOR_DAY_IN_CALENDAR)+i){
					
					listForPrint.add(temp);
				}
			}
		}
		
		/*
		 * Ne e losho da vru6ta6 prazen list vmesto null, daje e super dobre ! Ne go predotvratqvai.
		 */
		if(listForPrint.isEmpty()){
			return null;
		}else{
			return listForPrint;
		}
	}

}
