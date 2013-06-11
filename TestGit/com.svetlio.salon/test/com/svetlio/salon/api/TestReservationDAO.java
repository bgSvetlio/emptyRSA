package com.svetlio.salon.api;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.databasesConnection.ConnectionProvider;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.ManHairCut;
import com.svetlio.salon.model.Reservation;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestReservationDAO {

	private SalonReservationDAO salonReservationDAO;
	
	@Mock
	private ConnectionProvider connectionProvider;
	
	@Mock
	private Connection conn;
	
	@Mock
	private PreparedStatement preparedStatement;
	
	@Mock
	private ResultSet pw; 
	
	Reservation reservation;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		salonReservationDAO = new JDBCreservationsDAOimpl(connectionProvider);
		reservation = new Reservation(new Customer("mi6o","birata",123456789L),new ManHairCut(),new GregorianCalendar());
	}
	
	@Test
	public void testSaveReservationInDB() throws SQLException{
	String addStrRes = "INSERT INTO reservations (reservationTime, serviceType) VALUES (?, ?)";
	String addStrCus = "INSERT INTO customers (firstName, lastName, telephoneNumber) VALUES (?, ?, ?)";
	when(connectionProvider.getConnection()).thenReturn(conn);
	when(conn.prepareStatement(addStrRes)).thenReturn(preparedStatement);
	when(conn.prepareStatement(addStrCus)).thenReturn(preparedStatement);
	when(preparedStatement.executeUpdate()).thenReturn(1);
	Assert.assertEquals(salonReservationDAO.saveReservationInDB(reservation),reservation);
	verify(connectionProvider).getConnection();
	verify(conn).prepareStatement(addStrRes);
	verify(conn).prepareStatement(addStrCus);
	}
	
	@Test
	public void testDeleteResrvationFromDB() throws SQLException{
		GregorianCalendar cal = new GregorianCalendar();
		Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
		String getStr = "SELECT customers.firstName , customers.lastName," +
					" customers.telephoneNumber, reservations.reservationTime," +
					"reservations.serviceType from customers join reservations on customers.id = reservations.id " +
					"WHERE reservations.reservationTime = " + fromCal2TimeStampStr(cal);
		String delStr = "DELETE FROM reservationS WHERE reservationTime = ?";
		when(connectionProvider.getConnection()).thenReturn(conn);
		when(conn.prepareStatement(getStr)).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(pw);
		when(pw.getString(1)).thenReturn("mi6o");
		when(pw.getString(2)).thenReturn("birata");
		when(pw.getLong(3)).thenReturn(123456789L);
		when(pw.getTimestamp(4)).thenReturn(timestamp);
		when(pw.getString(5)).thenReturn("man hair cut");
		
		when(conn.prepareStatement(delStr)).thenReturn(preparedStatement);
		Reservation reservationDel = salonReservationDAO.deleteReservationFromDB(cal);
		Assert.assertEquals(reservationDel.getCustomer().getFirstName(),reservation.getCustomer().getFirstName());
		Assert.assertEquals(reservationDel.getCustomer().getLastName(),reservation.getCustomer().getLastName());
		Assert.assertEquals(reservationDel.getCustomer().getPhoneNumber(),reservation.getCustomer().getPhoneNumber());
		Assert.assertEquals(reservationDel.getService().toString(),reservation.getService().toString());
		Assert.assertEquals(reservation.getCalendar().getTime().toString(),reservationDel.getCalendar().getTime().toString());
		
	}
	
	@Test
	public void testSelectReservationFromDB() throws SQLException{
		GregorianCalendar cal = new GregorianCalendar();
		Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
		String selectStr = "SELECT customers.firstName , customers.lastName," +
				" customers.telephoneNumber, reservations.reservationTime," +
				"reservations.serviceType from customers join reservations on customers.id = reservations.id ";
		when(connectionProvider.getConnection()).thenReturn(conn);
		when(conn.prepareStatement(selectStr)).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(pw);
		when(pw.next()).thenReturn(true, false);
		when(pw.getString(1)).thenReturn("mi6o");
		when(pw.getString(2)).thenReturn("birata");
		when(pw.getLong(3)).thenReturn(123456789L);
		when(pw.getTimestamp(4)).thenReturn(timestamp);
		when(pw.getString(5)).thenReturn("man hair cut");
		
		List<Reservation> list = salonReservationDAO.selectReservationsFromDB();
		
		Assert.assertEquals(list.get(0).getCustomer().getFirstName(),reservation.getCustomer().getFirstName());
		Assert.assertEquals(list.get(0).getCustomer().getLastName(),reservation.getCustomer().getLastName());
		Assert.assertEquals(list.get(0).getCustomer().getPhoneNumber(),reservation.getCustomer().getPhoneNumber());
		Assert.assertEquals(list.get(0).getService().toString(),reservation.getService().toString());
		Assert.assertEquals(reservation.getCalendar().getTime().toString(),list.get(0).getCalendar().getTime().toString());
		
		
	}
	
	private String fromCal2TimeStampStr(Calendar cal){
		String minutes;
		if(cal.get(GregorianCalendar.MINUTE)==0){
			minutes="00";
		}else if(cal.get(GregorianCalendar.MINUTE)/10<1){
			minutes = "0"+ cal.get(GregorianCalendar.MINUTE);
		}else {
			minutes =""+ cal.get(GregorianCalendar.MINUTE);
		}
		
		String timeStamp = "\'"+cal.get(GregorianCalendar.YEAR)+"-"+(cal.get(GregorianCalendar.MONTH)+1)
				+"-"+cal.get(GregorianCalendar.DAY_OF_MONTH)+" "+
				cal.get(GregorianCalendar.HOUR_OF_DAY)+":"
				+minutes+":00\'";
		
		return timeStamp;
	}
}
