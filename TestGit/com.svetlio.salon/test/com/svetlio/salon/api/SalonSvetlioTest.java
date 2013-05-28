package com.svetlio.salon.api;

import static org.junit.Assert.*; 


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollision;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

/*
 * Ne mislq 4e pri starata implementaciq testa 6te e su6tiq prosto nqma takuv modul a sig 6te trqbva da simulirash i vhod ot konzola.
 */

public class SalonSvetlioTest {
	
	/*
	 * Zashto stati4ni ?? Ne e zaduljitelno da sa v BeforeClass moje samo v Before
	 * i pak sa bez private modifier - za6to ?
	 */
	//static SalonSvetlio ss;
	//static Reservation r;
//	private Salon testSalon;
//	
//	@Mock
//	private SalonReservationDAO mockedDao;
//	
//	@Mock
//	private Reservation r;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		testSalon = new SalonSvetlio();
//	}
//	
//	/*@BeforeClass
//	public static void createObject(){
//		ss = new SalonSvetlio();
//		
//		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 15);
//		Customer customer = new Customer("Svetlio", "Ivanov", 9206666);
//		Service service = new ManHairCut();
//	    r = new Reservation(customer, service, cal );
//	}*/
//
//	@Test
//	public void testAddReservation() throws ReservationCollision {
//		when(mockedDao.saveReservationInDB(r)).thenReturn(true);
//		//ss.addReservation(r);
//		/*
//		 * Nqma zashto da pravish getList() metod imash metod koito gi listva i shte vidish 4e nqma nikakvi.
//		 * Tova e grubo narushenie na enkapsulaciqta. Ako trqbva da napravim nov metod v inteface-a
//		 * koito listva absolutno vsi4ki rezervacii ...
//		 */
//		//Assert.assertEquals(r, ss.getList().getFirst());
//	}
//	
//	@Test(expected = ReservationCollision.class)
//	public void testAddReservationExc() throws ReservationCollision{
//		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 20);
//		Customer customer = new Customer("Rosi", "Ivanov", 9206666);
//		Service service = new ManHairCut();
//		Reservation r = new Reservation(customer, service, cal );
//		ss.addReservation(r);
//		
//	}
//
//	@Test
//	public void testRemoveReservation() {
//		Calendar cal = new GregorianCalendar(2013, 4, 7, 13 , 15);
//		
//		Assert.assertEquals(r, ss.removeReservation(cal));
//		//Assert.assertTrue(ss.getList().isEmpty());
//	}
//
//	
//	@Test
//	public void testListReservation() throws ReservationCollision{
//		
//	    ss.addReservation(r);
//	    
//	    Calendar cal2 = new GregorianCalendar(2013, 4, 8, 17 , 15);
//		Customer customer2 = new Customer("Rosi", "Ivanov", 9206666);
//		Service service2 = new ManHairCut();
//		Reservation r2 = new Reservation(customer2, service2, cal2 );
//		ss.addReservation(r2);
//		
//		
//		Assert.assertEquals(r, ((LinkedList<Reservation>) ss.listReservations(0)).getFirst());
//		
//		Assert.assertEquals(r, ((LinkedList<Reservation>) ss.listReservations(3)).getFirst());
//		Assert.assertEquals(r2, ((LinkedList<Reservation>) ss.listReservations(3)).getLast());
//	    
//	}

}
