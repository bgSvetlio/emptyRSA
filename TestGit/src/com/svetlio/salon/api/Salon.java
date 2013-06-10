package com.svetlio.salon.api;

import java.util.Calendar; 

import java.util.List;

import com.svetlio.salon.exceptions.ReservationCollisionExcetion;
import com.svetlio.salon.model.Reservation;

public interface Salon {
	
	public boolean addReservation(Reservation reservation) throws ReservationCollisionExcetion;
	
	public Reservation removeReservation(Calendar reservationDate);
	
	public List<Reservation> listReservations(int daysForward);
}
