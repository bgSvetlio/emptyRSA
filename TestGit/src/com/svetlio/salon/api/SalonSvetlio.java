package com.svetlio.salon.api;

import java.util.Calendar; 
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import NotDefault.Appointment;

import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Reservation;


/*
 *  Ima kakvo o6te da obsudim po implementaciqta, no tova 6te go pisha kato ima vreme. Ina4e otkum testove i struktura po4na da stava prili4no ve4e :)
 *  Mislex da ti ka4a edna moq reference implementaciq na salona bazirana vurhu tvoito reshenie, no reshix 4e ne e uda4no po-dobre sam da se pomu4ish
 *  i kogato stigne uzrql state 6te gi sravnim i 6te izgovorim predimstva i nedostatuci na dvete :). Zasega e tova o4akvai pak vklu4vane ;D
 */

public class SalonSvetlio implements Salon {
	public static final int ARG_FOR_YEAR_IN_CALENDAR=1;
	public static final int ARG_FOR_MONTH_IN_CALENDAR=2;
	public static final int ARG_FOR_DAY_IN_CALENDAR=5;
	public static final int ARG_FOR_HOURS_IN_CALENDAR=11;
	public static final int ARG_FOR_MINUTES_IN_CALENDAR=12;
	
	public static LinkedList<Reservation> list= new LinkedList<Reservation>();

	@Override
	public void addReservation(Reservation reservation) throws ReservationCollision {
		// TODO Auto-generated method stub
		int count = 0;
		
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
		
		for(int i=0;i<=daysForward;i++){
			for(Reservation temp: SalonSvetlio.list){
				if(temp.getCalendar().get(ARG_FOR_YEAR_IN_CALENDAR)== dateToday.get(ARG_FOR_YEAR_IN_CALENDAR) &&
					temp.getCalendar().get(ARG_FOR_MONTH_IN_CALENDAR)== dateToday.get(ARG_FOR_MONTH_IN_CALENDAR) &&
					temp.getCalendar().get(ARG_FOR_DAY_IN_CALENDAR)== dateToday.get(ARG_FOR_DAY_IN_CALENDAR)+i){
					
					listForPrint.add(temp);
				}
			}
		}
		
		if(listForPrint.isEmpty()){
			return null;
		}else{
			return listForPrint;
		}
	}

}
