package tv.culturesport.sirrendergui;

import java.io.IOException;
import java.sql.SQLException;

public class ServerStatusThread extends Thread {

    public ServerStatusThread() {
        super("ServerStatusThread");
    }

    public void run() {

    	ClientController cc = new ClientController();
		try {
			MainApp.log.debug("In ServerStatusThread");
			cc.pollServerStatus();
			MainApp.log.debug("Out ServerStatusThread");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}