package com.svetlio.salon.api;

import java.util.Calendar;
import java.util.LinkedList;



import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Reservation;

public interface Salon {

	public void addReservation(Reservation reservation) throws ReservationCollision;
	
	public Reservation removeReservation(Calendar reservationDate);
	
	public LinkedList<Reservation> listReservations(int daysForward);
}
