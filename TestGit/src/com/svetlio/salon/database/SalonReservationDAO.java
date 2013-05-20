package com.svetlio.salon.database;

import java.util.Calendar;
import java.util.List;

import com.svetlio.salon.model.Reservation;

public interface SalonReservationDAO {
	public boolean saveReservationInDB(Reservation reservation);
	public Reservation deleteReservationFromDB(Calendar calandar);
	public List<Reservation> selectReservationsFromDB();

}
