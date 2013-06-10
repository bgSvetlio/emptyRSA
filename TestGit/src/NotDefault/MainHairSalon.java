package NotDefault;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.svetlio.salon.api.SalonSvetlio;
import com.svetlio.salon.console.ConsoleClient;
import com.svetlio.salon.database.JDBCreservationsDAOimpl;
import com.svetlio.salon.database.SalonReservationDAO;
import com.svetlio.salon.databasesConnection.ConnectionProvider;
import com.svetlio.salon.databasesConnection.DerbyConnection;


public class MainHairSalon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionProvider connectionProvider = new DerbyConnection();
		SalonReservationDAO jdbCreservationsDAOimpl = new JDBCreservationsDAOimpl(connectionProvider);
		SalonSvetlio salonSvetlio = new SalonSvetlio(jdbCreservationsDAOimpl);
		ConsoleClient hss = new ConsoleClient(salonSvetlio);
		hss.showMenu();

		
		
	}

}
