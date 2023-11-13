package tv.culturesport.sirrendergui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;


public class GetServerListTask extends Task<ObservableList<Server>> {

//	private Boolean active = false;
//	private long sleepTimeInMillis = 15000;
	
	
    public GetServerListTask()
    {
    }

//    public GetServerListTask(Boolean active,long sleepTimeInMillis)
//    {
//        this.active = active;
//        this.sleepTimeInMillis = sleepTimeInMillis;
//    }

    // The task implementation
    @Override
    protected ObservableList<Server> call()
    {
    	// An observable list to represent the results
    	final ObservableList<Server> results = FXCollections.<Server>observableArrayList();
	
		//MainApp.log.debug("Starting GetServerListTask");
	
		String serverCmd = ApplicationConstants.SERVER_GET_SERVERS;
		String fromServer = null;
		String serverName = null;
	    String ipAddress = null;
	    //String serverStatus = null;
	    //String killSwitch = null;
	    //String suspendSwitch = null;
	    //String backgroundSwitch = null;
	
		try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
	        	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_SERVERS.length());
	        	String delims = "[|]+";
	        	String[] tokens = phrase.split(delims);
	        	
	        	results.clear();
	
	        	for (int i = 0; i < tokens.length/6; i++) {
	        		serverName = tokens[i*6];
	        		ipAddress = tokens[i*6+1];
	        		//serverStatus = tokens[i*6+2];
	        		//killSwitch = tokens[i*6+3];
	        		//suspendSwitch = tokens[i*6+4];
	        		//backgroundSwitch = tokens[i*6+5];
	        		//MainApp.log.debug("serverName="+serverName);
	        		//MainApp.log.debug("ipAddress="+ipAddress);
	        		//MainApp.log.debug("serverStatus="+serverStatus);
	        		//MainApp.log.debug("killSwitch="+killSwitch);
	        		//MainApp.log.debug("suspendSwitch="+suspendSwitch);
	        		//MainApp.log.debug("backgroundSwitch="+backgroundSwitch);
	        		results.add(new Server(serverName, ipAddress, null, null, null, null, null));
	        	}
	        }
	        mcScoket.close();
	        // Publish the read-only list to give the Client access to the partial results
	        /*
	        for (Server renderFile : results) {
	        	MainApp.log.debug("renderFile.getServerName()="+renderFile.getServerName());
	        	MainApp.log.debug("renderFile.getServerIpAddress()="+renderFile.getServerIpAddress());
	        }
	        */
	        Platform.runLater(new Runnable()
	        {
	        	@Override
	        	public void run()
	        	{
	        		MainApp.getServerData().setAll(results);
	        		//serverData.setAll(results);
	        	}
	        });
		} catch (ConnectException e) {
		      //throw e;
		} catch (IOException e) {
		      e.printStackTrace();
		}
		return results;
	}
}

