package com.svetlio.salon.api;

import static org.junit.Assert.*; 

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;

/*
 * Ne mislq 4e pri starata implementaciq testa 6te e su6tiq prosto nqma takuv modul a sig 6te trqbva da simulirash i vhod ot konzola.
 */

public class SalonSvetlioTest {
	static SalonSvetlio ss;
	static Reservation r;
	
	@BeforeClass
	public static void createObject(){
		ss = new SalonSvetlio();
		
		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 15);
		Customer customer = new Customer("Svetlio", "Ivanov", 9206666);
		Service service = new ManHairCut();
	    r = new Reservation(customer, service, cal );
	}

	@Test
	public void testAddReservation() throws ReservationCollision {
		
		
		ss.addReservation(r);
		Assert.assertEquals(r, ss.getList().getFirst());
	}
	
	@Test(expected = ReservationCollision.class)
	public void testAddReservationExc() throws ReservationCollision{
		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 20);
		Customer customer = new Customer("Rosi", "Ivanov", 9206666);
		Service service = new ManHairCut();
		Reservation r = new Reservation(customer, service, cal );
		ss.addReservation(r);
		
	}

	@Test
	public void testRemoveReservation() {
		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 15);
		
		Assert.assertEquals(r, ss.removeReservation(cal));
		Assert.assertTrue(ss.getList().isEmpty());
	}

	
	@Test
	public void testListReservation() throws ReservationCollision{
		
	    ss.addReservation(r);
	    
	    Calendar cal2 = new GregorianCalendar(2013, 4, 8, 17 , 15);
		Customer customer2 = new Customer("Rosi", "Ivanov", 9206666);
		Service service2 = new ManHairCut();
		Reservation r2 = new Reservation(customer2, service2, cal2 );
		ss.addReservation(r2);
		
		
		Assert.assertEquals(r, ss.listReservations(0).getFirst());
		
		Assert.assertEquals(r, ss.listReservations(3).getFirst());
		Assert.assertEquals(r2, ss.listReservations(3).getLast());
	    
	}

}
