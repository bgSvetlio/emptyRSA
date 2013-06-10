package com.svetlio.salon.database;

import java.sql.Connection; 

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.svetlio.salon.databasesConnection.ConnectionProvider;
import com.svetlio.salon.databasesConnection.Database;
import com.svetlio.salon.databasesConnection.DerbyDBConnect;
import com.svetlio.salon.model.Customer;
import com.svetlio.salon.model.Reservation;
import com.svetlio.salon.model.Service;
import com.svetlio.salon.model.ServiceFactory;

public class JDBCreservationsDAOimpl implements SalonReservationDAO {
	
	private ConnectionProvider connectionProvider;
	//private Connection conn = null;
   // private PreparedStatement prestat = null;
   // private Statement stat = null;
    //private ResultSet pw = null;
    //private Database dbConn;
	
	public JDBCreservationsDAOimpl(ConnectionProvider connectionProvider){
		//dbConn = new DerbyDBConnect();
		this.connectionProvider = connectionProvider;
		
//		
//		try {
//            Class.forName(dbConn.getClientDriver()).newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
		
	}

	@Override
	public boolean saveReservationInDB(Reservation reservation) {
		int a=0,b=0;
		Timestamp timeStamp=  fromCalendar2Timestamp(reservation.getCalendar());
		Connection connection = connectionProvider.getConnection();
		//createConnection();
		/*
		 * Tuk user input-a se save-a direktno kakvo shte se slu4i ako nqkoi se opita da priloji SQL injection
		 */
		PreparedStatement preparedStatementReservation= null;
		PreparedStatement preparedStatementCustomers= null;
		//ResultSet pw = null;
		
		String addStrRes = "INSERT INTO reservations (reservationTime, serviceType) VALUES (?, ?)";
		String addStrCus = "INSERT INTO customers (firstName, lastName, telephoneNumber) VALUES (?, ?, ?)";
		
		try{
			preparedStatementReservation = connection.prepareStatement(addStrRes);
			preparedStatementReservation.setTimestamp(1,timeStamp);
			preparedStatementReservation.setString(2, reservation.getService().toString());
			
			preparedStatementCustomers = connection.prepareStatement(addStrCus);
			preparedStatementCustomers.setString(1, reservation.getCustomer().getFirstName());
			preparedStatementCustomers.setString(2, reservation.getCustomer().getLastName());
			preparedStatementCustomers.setLong(3, reservation.getCustomer().getPhoneNumber());
			
			a=preparedStatementReservation.executeUpdate();
			b=preparedStatementCustomers.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (preparedStatementReservation != null){
                try { preparedStatementReservation.close();} catch (SQLException e){;}
                preparedStatementReservation = null;
            }
			if (preparedStatementCustomers != null){
                try { preparedStatementCustomers.close();} catch (SQLException e){;}
                preparedStatementCustomers = null;
            }
            if (connection != null){
                try {connection.close();} catch(SQLException e) {;}
            connection = null;
            }
		}
		/*String addStrRes = "INSERT INTO reservations (reservationTime, serviceType) VALUES ("+
				fromCalToTimeStamp(reservation.getCalendar())+ ", \'" +reservation.getService()+"\')";
		
		String addStrCus = "INSERT INTO customers (firstName, lastName, telephoneNumber) VALUES (\'"
		+ reservation.getCustomer().getFirstName() +"\', \'"+
				reservation.getCustomer().getLastName()+ "\', "+ reservation.getCustomer().getPhoneNumber() +")";
		
		
		try {
			stat = connection.createStatement(); 
			
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
            if (connection != null){
                try {connection.close();} catch(SQLException e) {;}
            connection = null;
            }
        }*/
		
		// TODO Auto-generated method stub
		if(a==1&&b==1){
			return true;
		}else return false;
		
	}

	@Override
	public Reservation deleteReservationFromDB(Calendar calendar) {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement prestatGetBeforeDel = null;
		PreparedStatement preparedStatementDel = null;
		ResultSet pw = null;
		//Timestamp timestamp=null;
		//createConnection();
		// TODO Auto-generated method stub
		Reservation reservation = null;
		String delStr = "DELETE FROM reservationS WHERE reservationTime = ?";//+ fromCalToTimeStamp(calendar);
		
		try {
			prestatGetBeforeDel = connection.prepareStatement("SELECT customers.firstName , customers.lastName," +
					" customers.telephoneNumber, reservations.reservationTime," +
					"reservations.serviceType from customers join reservations on customers.id = reservations.id " +
					"WHERE reservations.reservationTime = "+ fromCal2TimeStampStr(calendar));

            pw = prestatGetBeforeDel.executeQuery();
            while (pw.next())
            {
                
                Customer customer = new Customer(pw.getString(1), pw.getString(2), pw.getLong(3));
                Calendar cal = Calendar.getInstance();
                calendar.setTime(pw.getTimestamp(4));
                Service service = ServiceFactory.getServiceFactory().createServiceInstance(pw.getString(5));
                reservation = new Reservation(customer, service, cal);
                
            }
			preparedStatementDel = connection.prepareStatement(delStr);
			preparedStatementDel.setTimestamp(1, fromCalendar2Timestamp(calendar));
			preparedStatementDel.executeUpdate();
//			stat = connection.createStatement(); 
//			int a = stat.executeUpdate(delStr);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (pw != null){
                try { pw.close();} catch (SQLException e){;}
                pw = null;
            }
            if (preparedStatementDel != null){
                try { preparedStatementDel.close();} catch (SQLException e){;}
                preparedStatementDel = null;
            }
            if (prestatGetBeforeDel != null){
                try { prestatGetBeforeDel.close();} catch (SQLException e){;}
                prestatGetBeforeDel = null;
            }
            if (connection != null){
                try {connection.close();} catch(SQLException e) {;}
            connection = null;
            }
            
        }
		
		return reservation;
	}

	@Override
	public List<Reservation> selectReservationsFromDB() {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatementSelect = null;
		ResultSet pw = null;
		// TODO Auto-generated method stub
		LinkedList<Reservation> list = new LinkedList<Reservation>();
		try {
			preparedStatementSelect = connection.prepareStatement("SELECT customers.firstName , customers.lastName," +
					" customers.telephoneNumber, reservations.reservationTime," +
					"reservations.serviceType from customers join reservations on customers.id = reservations.id ");

            pw = preparedStatementSelect.executeQuery();
             
            while (pw.next())
            {
                
                Customer customer = new Customer(pw.getString(1), pw.getString(2), pw.getLong(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(pw.getTimestamp(4));
                Service service = ServiceFactory.getServiceFactory().createServiceInstance(pw.getString(5));
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
            if (preparedStatementSelect != null){
                try { preparedStatementSelect.close();} catch (SQLException e){;}
                preparedStatementSelect = null;
            }
            if (connection != null){
                try {connection.close();} catch(SQLException e) {;}
            connection = null;
            }
        }
		return list;
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
	
	private Timestamp fromCalendar2Timestamp(Calendar calendar) {
			        return (calendar == null ? null : new
			java.sql.Timestamp(calendar.getTimeInMillis()));
			}
	
//	private Connection createConnection() {
//		try {
//			conn = DriverManager.getConnection(dbConn.getConnection(), userName, password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	//return connectionProvider.getConnection();
	//}

}
