package com.svetlio.salon.api;

import java.util.Calendar; 

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollisionExcetion;
import com.svetlio.salon.model.Reservation;


public class SalonSvetlio implements Salon {
	private SalonReservationDAO salonReservationDAO;
	
	public SalonSvetlio(SalonReservationDAO databaseDAO) {
		salonReservationDAO = databaseDAO;
	}

	@Override
	public boolean addReservation(Reservation reservation) throws ReservationCollisionExcetion {
		
		if(collisionReservation(reservation)){
			throw new ReservationCollisionExcetion(reservation);
		}

		if(salonReservationDAO.saveReservationInDB(reservation)!=null){
			return true;
		}
		else return false;

	}

	@Override
	public Reservation removeReservation(Calendar reservationDate) {

		Reservation reservation = salonReservationDAO.deleteReservationFromDB(reservationDate);
		
		return reservation;
	}

	@Override
	public List<Reservation> listReservations(int daysForward) {
		Calendar dateToday = new GregorianCalendar();
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
				return arg0.getCalendar().compareTo(arg1.getCalendar());
			}   
			});
		
		return listForPrint;
	}
	
	private boolean collisionReservation(Reservation reservation){

		List<Reservation> listFromDB = salonReservationDAO.selectReservationsFromDB();
		
		if(!listFromDB.isEmpty()){
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
		}
		return false;
		
	}

}
