package com.svetlio.salon.database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.databasesConnection.Database;
import com.svetlio.salon.databasesConnection.DerbyDBConnect;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;
import com.svetlio.salon.model.ServiceFactory;

public class JDBCreservationsDAOimpl implements SalonReservationDAO {
	
	
	private Connection conn = null;
    private PreparedStatement prestat = null;
    private Statement stat = null;
    private ResultSet pw = null;
    private Database dbConn;
	
	public JDBCreservationsDAOimpl(){
		dbConn = new DerbyDBConnect();
		
		try {
            Class.forName(dbConn.getClientDriver()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public boolean saveReservationInDB(Reservation reservation) {
		createConnection(dbConn.getUser(), dbConn.getPassword());
		/*
		 * Tuk user input-a se save-a direktno kakvo shte se slu4i ako nqkoi se opita da priloji SQL injection
		 */
		String addStrRes = "INSERT INTO reservations (reservationTime, serviceType) VALUES ("+
				fromCalToTimeStamp(reservation.getCalendar())+ ", \'" +reservation.getService()+"\')";
		
		String addStrCus = "INSERT INTO customers (firstName, lastName, telephoneNumber) VALUES (\'"
		+ reservation.getCustomer().getFirstName() +"\', \'"+
				reservation.getCustomer().getLastName()+ "\', "+ reservation.getCustomer().getPhoneNumber() +")";
		
		
		try {
			stat = conn.createStatement(); 
			
			int a = stat.executeUpdate(addStrRes);
			
			int b = stat.executeUpdate(addStrCus);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
            if (stat != null){
                try { stat.close();} catch (SQLException e){;}
                stat = null;
            }
            if (conn != null){
                try {conn.close();} catch(SQLException e) {;}
            conn = null;
            }
        }
		
		// TODO Auto-generated method stub
		 return true;
	}

	@Override
	public Reservation deleteReservationFromDB(Calendar calendar) {
		createConnection(dbConn.getUser(), dbConn.getPassword());
		// TODO Auto-generated method stub
		Reservation reservation = null;
		String delStr = "DELETE FROM reservationS WHERE reservationTime = "+ fromCalToTimeStamp(calendar);
		
		try {
			prestat = conn.prepareStatement("SELECT customers.firstName , customers.lastName," +
					" customers.telephoneNumber, reservations.reservationTime," +
					"reservations.serviceType from customers join reservations on customers.id = reservations.id " +
					"WHERE reservations.reservationTime = "+ fromCalToTimeStamp(calendar));

            pw = prestat.executeQuery();
            while (pw.next())
            {
                
                Customer customer = new Customer(pw.getString(1), pw.getString(2), pw.getLong(3));
                Calendar cal = Calendar.getInstance();
                calendar.setTime(pw.getTimestamp(4));
                Service service = ServiceFactory.getServiceInstance(pw.getString(5));
                reservation = new Reservation(customer, service, cal);
                
            }
			
			stat = conn.createStatement(); 
			int a = stat.executeUpdate(delStr);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (pw != null){
                try { pw.close();} catch (SQLException e){;}
                pw = null;
            }
            if (stat != null){
                try { stat.close();} catch (SQLException e){;}
                stat = null;
            }
            if (prestat != null){
                try { prestat.close();} catch (SQLException e){;}
                prestat = null;
            }
            if (conn != null){
                try {conn.close();} catch(SQLException e) {;}
            conn = null;
            }
            
        }
		
		return reservation;
	}

	@Override
	public List<Reservation> selectReservationsFromDB() {
		createConnection(dbConn.getUser(), dbConn.getPassword());
		// TODO Auto-generated method stub
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		try {
			prestat = conn.prepareStatement("SELECT customers.firstName , customers.lastName," +
					" customers.telephoneNumber, reservations.reservationTime," +
					"reservations.serviceType from customers join reservations on customers.id = reservations.id ");

            pw = prestat.executeQuery();
             
            while (pw.next())
            {
                
                Customer customer = new Customer(pw.getString(1), pw.getString(2), pw.getLong(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(pw.getTimestamp(4));
                Service service = ServiceFactory.getServiceInstance(pw.getString(5));
                Reservation reservation = new Reservation(customer, service, calendar);
                list.add(reservation);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            if (pw != null){
                try { pw.close();} catch (SQLException e){;}
                pw = null;
            }
            if (prestat != null){
                try { prestat.close();} catch (SQLException e){;}
                prestat = null;
            }
            if (conn != null){
                try {conn.close();} catch(SQLException e) {;}
            conn = null;
            }
        }
		return list;
	}
	
	private String fromCalToTimeStamp(Calendar cal){
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
	
	private void createConnection(String userName, String password){
		try {
			conn = DriverManager.getConnection(dbConn.getConnection(), userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
