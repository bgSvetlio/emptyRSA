package com.svetlio.salon.exceptions;

import com.svetlio.salon.model.Reservation;


public class ReservationCollisionExcetion extends Exception {
	
	private Reservation reservation;
	
	public ReservationCollisionExcetion(Reservation reservation){
		this.reservation = reservation;
	}
	public Reservation getReservation(){
		return this.reservation;
	}

}
