package tv.culturesport.sirrendergui;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.concurrent.Task;

@SuppressWarnings("rawtypes")
public class PollServerStatusTask extends Task {

	
    public PollServerStatusTask () 
    {
    }
	
    // The task implementation
    protected Object call() throws SQLException
    {
    	// Refresh the server list
        Platform.runLater(new Runnable()
        {
        	@Override
        	public void run()
        	{
        		ClientController myController = MainApp.getMyControllerHandle();
        		try {
					myController.pollServerStatus();
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
    	return 0;
    }

}
