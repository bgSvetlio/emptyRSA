package NotDefault;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.svetlio.salon.api.SalonSvetlio;
import com.svetlio.salon.console.ConsoleClient;


public class MainHairSalon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SalonSvetlio salonSvetlio = new SalonSvetlio();
		ConsoleClient hss = new ConsoleClient(salonSvetlio);
		hss.showMenu();

	}

}
