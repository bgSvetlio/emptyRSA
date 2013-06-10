package com.svetlio.salon.exceptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.databasesConnection.ConnectionProvider;
import com.svetlio.salon.databasesConnection.DerbyConnection;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;

// Zashto e takuv konstruktura?
// Tova ne si go gledal o6te mai ....
public class ReservationCollisionExcetion extends Exception {
	
	private Reservation reservation;
	ConnectionProvider connectionProvider = new DerbyConnection();
	private SalonReservationDAO salonResrvationDAO = new JDBCreservationsDAOimpl(connectionProvider);
	
	public void setDataAccess(SalonReservationDAO salonReservationDAO){
		this.salonResrvationDAO = salonReservationDAO;
	}
	
	public ReservationCollisionExcetion(Reservation reservation){
		this.reservation = reservation;
	}
	
	public List<Double> listFreeHourForTheDay(){
		double hour;
		
		List<Reservation> listFromDB = salonResrvationDAO.selectReservationsFromDB();
		ArrayList<Reservation> reservationsForThisDay = new ArrayList<Reservation>();
		ArrayList<Double> freeHours = new ArrayList<Double>();
		
		for(Reservation temp: listFromDB){
			if(temp.getCalendar().get(GregorianCalendar.YEAR)== reservation.getCalendar().get(GregorianCalendar.YEAR) &&
				temp.getCalendar().get(GregorianCalendar.MONTH)== reservation.getCalendar().get(GregorianCalendar.MONTH) &&
				temp.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)== reservation.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)){
				
				reservationsForThisDay.add(temp);
			}
		}
		Collections.sort(reservationsForThisDay , new Comparator<Reservation>() {

			@Override
			public int compare(Reservation arg0, Reservation arg1) {
				// TODO Auto-generated method stub
				return arg0.getCalendar().compareTo(arg1.getCalendar());
			}   
			});
		
			
			for(int i=0;i< reservationsForThisDay.size();i++){
				Calendar tempCalBefor = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MINUTE)- reservation.getService().getDurationInMinutes());
				
				Calendar tempCalAfter = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MINUTE)+ reservationsForThisDay.get(i).getService().getDurationInMinutes());
				
					
				
				Calendar beginHour = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						9, 0 );
				if(tempCalBefor.after(beginHour)){
					if(i==0){
						freeHours.add(9.00);
						hour = (double)tempCalBefor.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalBefor.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					if(i>0){
					Calendar tempCalAfter1 = new GregorianCalendar(reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.YEAR), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
							reservationsForThisDay.get(i-1).getCalendar().get(GregorianCalendar.MINUTE)+ reservationsForThisDay.get(i-1).getService().getDurationInMinutes());
				
					if(tempCalBefor.after(tempCalAfter1)){
						hour = (double)tempCalBefor.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalBefor.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					}
				}	
				
				
				Calendar finishHour = new GregorianCalendar(reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.YEAR), 
						reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
						18, 0-reservation.getService().getDurationInMinutes() );
				
				if(tempCalAfter.before(finishHour)){
					if(i==reservationsForThisDay.size()-1){
						hour = (double)tempCalAfter.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalAfter.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
						freeHours.add(18.00);
					}
					if(i<reservationsForThisDay.size()-1){
					Calendar tempCalBefor1 = new GregorianCalendar(reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.YEAR), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.MONTH), reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.DAY_OF_MONTH), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.HOUR_OF_DAY), 
							reservationsForThisDay.get(i+1).getCalendar().get(GregorianCalendar.MINUTE)- reservation.getService().getDurationInMinutes());
					if(tempCalAfter.before(tempCalBefor1)){
						hour = (double)tempCalAfter.get(GregorianCalendar.HOUR_OF_DAY) + (double)tempCalAfter.get(GregorianCalendar.MINUTE)/100;
						freeHours.add(hour);
					}
					}
					
				}
			}
			
			return freeHours;
		
	}

}
