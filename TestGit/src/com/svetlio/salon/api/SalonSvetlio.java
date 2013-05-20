package com.svetlio.salon.api;

import java.util.Calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;


public class SalonSvetlio implements Salon {
	
	
	private LinkedList<Reservation> list= new LinkedList<Reservation>();
	
	public LinkedList<Reservation> getList(){
		return this.list;
	}

	@Override
	public void addReservation(Reservation reservation) throws ReservationCollision {
		
		JDBCreservationsDAOimpl save = new JDBCreservationsDAOimpl();
		

		
		if(collisionReservation(reservation)){
			throw new ReservationCollision(reservation.getCalendar());
		}

		save.saveReservationInDB(reservation);
		

	}

	@Override
	public Reservation removeReservation(Calendar reservationDate) {
		SalonReservationDAO remove = new JDBCreservationsDAOimpl();
		Reservation reservation = remove.deleteReservationFromDB(reservationDate);
		
		return reservation;
	}

	@Override
	public List<Reservation> listReservations(int daysForward) {
		// TODO Auto-generated method stub
		Calendar dateToday = new GregorianCalendar();
		SalonReservationDAO salonReservationDAO = new JDBCreservationsDAOimpl();
		LinkedList<Reservation> listForPrint= new LinkedList<Reservation>();
		List<Reservation> listFromDB= salonReservationDAO.selectReservationsFromDB();
		
		for(int i=0;i<=daysForward;i++){
			for(Reservation temp: listFromDB){
				if(temp.getCalendar().get(GregorianCalendar.YEAR)== dateToday.get(GregorianCalendar.YEAR) &&
					temp.getCalendar().get(GregorianCalendar.MONTH)== dateToday.get(GregorianCalendar.MONTH) &&
					temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)== dateToday.get(GregorianCalendar.DAY_OF_MONTH)+i){
					
					listForPrint.add(temp);
				}
			}
		}
		Collections.sort(listForPrint , new Comparator<Reservation>() {

			@Override
			public int compare(Reservation arg0, Reservation arg1) {
				// TODO Auto-generated method stub
				return arg0.getCalendar().compareTo(arg1.getCalendar());
			}   
			});
		
		return listForPrint;
	}
	
	private boolean collisionReservation(Reservation reservation){
		JDBCreservationsDAOimpl collision = new JDBCreservationsDAOimpl();
		List<Reservation> listFromDB = collision.selectReservationsFromDB();
		
		for(Reservation temp: listFromDB){
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
