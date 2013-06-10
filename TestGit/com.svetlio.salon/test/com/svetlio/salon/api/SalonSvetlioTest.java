package com.svetlio.salon.api;

import static org.junit.Assert.*; 


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.exceptions.ReservationCollisionExcetion;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;



public class SalonSvetlioTest {
	
	private Salon testSalon;
	
	@Mock
	private SalonReservationDAO mockedDao;
	
	@Mock
	private Reservation r1;
	
	@Mock
	private Reservation r2;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		testSalon = new SalonSvetlio(mockedDao);
	}
	

	@Test
	public void testAddReservation() throws ReservationCollisionExcetion {
		LinkedList<Reservation> emptyList = new LinkedList<Reservation>();
		when(mockedDao.saveReservationInDB(r1)).thenReturn(true);
		when(mockedDao.selectReservationsFromDB()).thenReturn(emptyList);
		Assert.assertEquals(testSalon.addReservation(r1),true);
		verify(mockedDao).saveReservationInDB(r1);
		verify(mockedDao).selectReservationsFromDB();
		
	}
	
	@Test(expected = ReservationCollisionExcetion.class)
	public void testAddReservationExc() throws ReservationCollisionExcetion{
		Calendar cal = new GregorianCalendar();
		when(r1.getCalendar()).thenReturn(cal);
		when(r2.getCalendar()).thenReturn(cal);
		when(r1.getService()).thenReturn(new ManHairCut());
		when(r2.getService()).thenReturn(new ManHairCut());
		LinkedList<Reservation> returnedList = new LinkedList<Reservation>();
		returnedList.add(r2);
		when(mockedDao.selectReservationsFromDB()).thenReturn(returnedList);
		testSalon.addReservation(r1);
	}

	@Test
	public void testRemoveReservation() {
		Calendar cal = new GregorianCalendar();
		
		when(mockedDao.deleteReservationFromDB(cal)).thenReturn(r1);
		Assert.assertEquals(testSalon.removeReservation(cal),r1);
	}

	
	@Test
	public void testListReservation() throws ReservationCollisionExcetion{
		Calendar cal = new GregorianCalendar();
		Calendar cal1 = new GregorianCalendar(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH));
		Calendar cal2 = new GregorianCalendar(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH)+1);
		when(r1.getCalendar()).thenReturn(cal1);
		when(r2.getCalendar()).thenReturn(cal2);
		LinkedList<Reservation> returnedList = new LinkedList<Reservation>();
		returnedList.add(r2);
		returnedList.add(r1);
		when(mockedDao.selectReservationsFromDB()).thenReturn(returnedList);
		
		Assert.assertEquals(r1, testSalon.listReservations(0).get(0));
		
		Assert.assertEquals(r1, testSalon.listReservations(3).get(0));
		Assert.assertEquals(r2, testSalon.listReservations(3).get(1));
	    
	}

}
