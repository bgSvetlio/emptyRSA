package com.svetlio.salon.api;

import java.util.Calendar;


import java.util.List;



import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Reservation;

public interface Salon {
	public void setDataAccess(SalonReservationDAO salonReservationDAO);

	public boolean addReservation(Reservation reservation) throws ReservationCollision;
	
	public Reservation removeReservation(Calendar reservationDate);
	
	public List<Reservation> listReservations(int daysForward);
}
