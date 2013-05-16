package com.svetlio.salon.api;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Reservation;


public class SalonSvetlio implements Salon {
	
	
	private LinkedList<Reservation> list= new LinkedList<Reservation>();
	
	public LinkedList<Reservation> getList(){
		return this.list;
	}

	@Override
	public void addReservation(Reservation reservation) throws ReservationCollision {
		int count = 0;

		
		if(collisionReservation(reservation)){
			throw new ReservationCollision(reservation.getCalendar());
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
			if(reservationDate.compareTo(temp.getCalendar())==0){
				
				list.remove(temp);
				return temp;
			}
					
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> listReservations(int daysForward) {
		// TODO Auto-generated method stub
		Calendar dateToday = new GregorianCalendar();
		
		LinkedList<Reservation> listForPrint= new LinkedList<Reservation>();
		
		for(int i=0;i<=daysForward;i++){
			for(Reservation temp: this.list){
				if(temp.getCalendar().get(GregorianCalendar.YEAR)== dateToday.get(GregorianCalendar.YEAR) &&
					temp.getCalendar().get(GregorianCalendar.MONTH)== dateToday.get(GregorianCalendar.MONTH) &&
					temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)== dateToday.get(GregorianCalendar.DAY_OF_MONTH)+i){
					
					listForPrint.add(temp);
				}
			}
		}
		
		//SalonReservationDAO salonReservationDAO = new JDBCreservationsDAOimpl();
		
		return listForPrint;//salonReservationDAO.selectReservationsFromDB();
	}
	
	private boolean collisionReservation(Reservation reservation){
		for(Reservation temp: list){
			Calendar tempCalBefor = new GregorianCalendar(temp.getCalendar().get(GregorianCalendar.YEAR), 
					temp.getCalendar().get(GregorianCalendar.MONTH), temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
					temp.getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
					temp.getCalendar().get(GregorianCalendar.MINUTE)- reservation.getService().getDurationInMinutes());
			Calendar tempCalAfter = new GregorianCalendar(temp.getCalendar().get(GregorianCalendar.YEAR), 
					temp.getCalendar().get(GregorianCalendar.MONTH), temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
					temp.getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
					temp.getCalendar().get(GregorianCalendar.MINUTE)+ temp.getService().getDurationInMinutes());
			
			if(reservation.getCalendar().after(tempCalBefor) && reservation.getCalendar().before(tempCalAfter)){
				return true;
			}
		}
		return false;
		
	}

}
