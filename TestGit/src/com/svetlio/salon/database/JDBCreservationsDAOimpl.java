package com.svetlio.salon.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.svetlio.salon.model.Reservation;

public class JDBCreservationsDAOimpl implements SalonReservationDAO {
	Connection conn = null;
    PreparedStatement prestat = null;
    Statement stat = null;
    ResultSet pw = null;
	
	public JDBCreservationsDAOimpl(){
		try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		try {
			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/SvetlioSalonReservation;user=APP;password=user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveReservationInDB(Reservation reservation) {
		String addStr = "INSERT INTO reservation (reservationTime, serviceType) VALUES (" +
				reservation.getCalendar().get(GregorianCalendar.YEAR)+"-"+reservation.getCalendar().get(GregorianCalendar.MONTH)
				+"-"+reservation.getCalendar().get(GregorianCalendar.DAY_OF_MONTH)+" "+
				reservation.getCalendar().get(GregorianCalendar.HOUR_OF_DAY)+":"
				+reservation.getCalendar().get(GregorianCalendar.MINUTE)+":00, "+reservation.getService();
		
		try {
			stat = conn.createStatement(); 
			int a = stat.executeUpdate(addStr);
			stat.close();
            stat = null;
            conn.close();
            conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
            if (stat != null){
                try { prestat.close();} catch (SQLException e){;}
                prestat = null;
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
	public boolean deleteReservationFromDB(Calendar calandar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> selectReservationsFromDB() {
		// TODO Auto-generated method stub
		return null;
	}

}
