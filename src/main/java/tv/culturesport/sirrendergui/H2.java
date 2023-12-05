package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2016, Dale Kubler. All rights reserved.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.h2.message.DbException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tv.culturesport.sirrendergui.Utils;
import tv.culturesport.sirrendergui.ApplicationConstants;
import tv.culturesport.sirrendergui.MainApp;

public class H2 {

	Connection conection;

	public static Connection ConnectorRO() {
		try {
        	//MasterMain.log.debug("Opening database connection");
			Class.forName("org.h2.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:h2:file:U:/SirRender/databases/SirRenderDb;AUTO_SERVER=TRUE;TRACE_MAX_FILE_SIZE=2");
			//Connection conn = DriverManager.getConnection("jdbc:h2:file:U:/SirRender/databases/SirRenderDb;AUTO_SERVER=TRUE");
			Connection conn = DriverManager.getConnection("jdbc:h2:file:"+ApplicationConstants.DEFAULT_SIRRENDER_LAN_DRIVE+":/SirRender2/databases/SirRender2Db;AUTO_SERVER=TRUE");
        	//MasterMain.log.debug("Obtained a database conection");
			return conn;
		} catch (DbException dbe) {
			// Stops the "The connection was not closed by the application and is garbage collected [90018-194]" errors in the H2 trace.db file
			return null;
		} catch (Exception e) {
			MainApp.log.debug(e);
			return null;
			// TODO: handle exception
		}
	}

	public static String getMasterServerIpAddress() throws SQLException {
		Connection conection = H2.ConnectorRO();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select IpAddress from ServerStatus where Status = 'Available' order by MasterSlavePriority, IpAddress limit 1";
		try {
			preparedStatement = conection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String ipAddress = resultSet.getString("IpAddress");
				MainApp.log.debug(ipAddress);
				return ipAddress;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conection != null) {
				conection.close();
			}
		}
		return "";
	}

	public static void getMasterServerIpAddressFromLocal() throws SQLException {

    	String fromServer = null;
    	String serverCmd = ApplicationConstants.GET_MASTER_SERVER_IP_ADDRESS;
    	
    	try {
	        Socket mcScoket = new Socket(Utils.getLowIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug("Master Server IP Address from local server=" + fromServer);
	            GlobalClass.setServerMasterIpAddress(fromServer);
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

	public static void getMasterServerIpAddressFromFile() throws SQLException {
		String configFileInput = null;
		String newMasterServerName = null;
		String newMasterServerIpAddress = null;
				
		// Determine if U:\SirRender\databases\masterServerIpAddress.txt exists
		// Determine if V:\SirRender\databases\masterServerIpAddress.txt exists
		// If it exists, read the contents
		configFileInput = Utils.readMasterServerFile(ApplicationConstants.MASTER_SERVER_IP_ADDRESS_TXT_FILE);
	
		// If returned configFileInput is null or empty string, no masterServerIpAddress has been established
		// Access the local server and determine who the MasterServer is
		if (configFileInput == null || configFileInput.isEmpty()) {
			getMasterServerIpAddressFromLocal();
		} else {
			// Determine if you can ping the current MasterServer
        	String delims = "[|]+";
        	String[] tokens = configFileInput.split(delims);
        	newMasterServerName = tokens[0];
        	newMasterServerIpAddress = tokens[1];
            MainApp.log.debug("Master Server IP Address from file=" + newMasterServerIpAddress);
            GlobalClass.setServerMasterIpAddress(newMasterServerIpAddress);
		}
	}        	
        	
    public static void getDistinctRenderedFileStatusList(Boolean active, ObservableList<RenderFileStatus> renderFileStatusData) throws SQLException {

		//MainApp.log.debug("Starting getRenderedFileStatusList");

		String serverCmd = ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS;
    	String fromServer = null;
    	
    	// Send distinct indicator
   		serverCmd = serverCmd + "1";
    	
   		String fileName = null;
	    String renderStatus = null;
    	String serverName = null;
	    String ipAddress = null;
	    String currentFile = null;
	    int frameStart = 0;
	    int frameEnd = 0;
	    int frameCount = 0;
	    int currentFrame = 0;
	    int renderFrameCount = 0;
	    int renderDefaultHangTime = 0;
	    int masterSlavePriority = 0;
	    String renderOutDir = null;

    	try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	renderFileStatusData.clear();

            	for (int i = 0; i < tokens.length/13; i++) {
            		fileName = tokens[i*13];
            		//MainApp.log.debug("tokens[i*13]="+tokens[i*13]);
            		renderStatus = tokens[i*13+1];
            		//MainApp.log.debug("tokens[i*13+1]="+tokens[i*13+1]);
            		serverName = tokens[i*13+2];
            		//MainApp.log.debug("tokens[i*13+2]="+tokens[i*13+2]);
            		ipAddress = tokens[i*13+3];
            		//MainApp.log.debug("tokens[i*13+3]="+tokens[i*13+3]);
            		currentFile = tokens[i*13+4];
            		//MainApp.log.debug("tokens[i*13+4]="+tokens[i*13+4]);
            		frameStart = Integer.valueOf(tokens[i*13+5]);
            		//MainApp.log.debug("tokens[i*13+5]="+tokens[i*13+5]);
            		frameEnd = Integer.valueOf(tokens[i*13+6]);
            		//MainApp.log.debug("tokens[i*13+6]="+tokens[i*13+6]);
            		frameCount = Integer.valueOf(tokens[i*13+7]);
            		//MainApp.log.debug("tokens[i*13+7]="+tokens[i*13+7]);
            		currentFrame = Integer.valueOf(tokens[i*13+8]);
            		//MainApp.log.debug("tokens[i*13+8]="+tokens[i*13+8]);
            		renderFrameCount = Integer.valueOf(tokens[i*13+9]);
            		//MainApp.log.debug("tokens[i*13+9]="+tokens[i*13+9]);
            		renderDefaultHangTime = Integer.valueOf(tokens[i*13+10]);
            		//MainApp.log.debug("tokens[i*13+10]="+tokens[i*13+10]);
            		masterSlavePriority = Integer.valueOf(tokens[i*13+11]);
            		//MainApp.log.debug("tokens[i*13+11]="+tokens[i*13+11]);
            		renderOutDir = tokens[i*13+12];
            		//MainApp.log.debug("tokens[i*13+12]="+tokens[i*13+12]);
            		
           			fileName = currentFile;
           			//renderStatus = String.valueOf(currentFrame) + " fof " + String.valueOf(frameEnd - frameStart + 1);

           			/*
            		MainApp.log.debug("fileName="+fileName);
            		MainApp.log.debug("renderStatus="+renderStatus);
            		MainApp.log.debug("serverName="+serverName);
            		MainApp.log.debug("ipAddress="+ipAddress);
            		MainApp.log.debug("currentFile="+currentFile);
            		MainApp.log.debug("frameStart="+frameStart);
            		MainApp.log.debug("frameEnd="+frameEnd);
            		MainApp.log.debug("frameCount="+frameCount);
            		MainApp.log.debug("currentFrame="+currentFrame);
            		MainApp.log.debug("renderFrameCount="+renderFrameCount);
            		MainApp.log.debug("renderDefaultHangTime="+renderDefaultHangTime);
            		MainApp.log.debug("masterSlavePriority="+masterSlavePriority);
            		MainApp.log.debug("renderOutDir="+renderOutDir);
            		*/

            		renderFileStatusData.add(new RenderFileStatus(fileName, renderStatus, serverName, ipAddress, currentFile, frameStart, 
            				frameEnd, frameCount, currentFrame, renderFrameCount, renderDefaultHangTime, masterSlavePriority, renderOutDir));
            		counter++;
            	}
	        }
	        /*
			for (int i=counter; i < 10; i++) {
				serverData.add(new Server("", ""));
			}
			*/
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}
    
    public static void getRenderedComputerStatusList(Boolean active, String fileName, ObservableList<RenderComputerStatus> renderComputerStatusData) throws SQLException {

		//MainApp.log.debug("Starting getRenderedComputerStatusList");

		String serverCmd = ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS;
    	String fromServer = null;

    	// Send distinct indicator
   		serverCmd = serverCmd + fileName;
    	
	    String renderStatus = null;
    	String serverName = null;
	    String ipAddress = null;
	    String currentFile = null;
	    int frameStart = 0;
	    int frameEnd = 0;
	    int frameCount = 0;
	    int currentFrame = 0;
	    int renderFrameCount = 0;
	    int renderDefaultHangTime = 0;
	    int masterSlavePriority = 0;
	    String renderOutDir = null;

    	try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	renderComputerStatusData.clear();

            	for (int i = 0; i < tokens.length/13; i++) {
            		fileName = tokens[i*13];
            		renderStatus = tokens[i*13+1];
            		serverName = tokens[i*13+2];
            		ipAddress = tokens[i*13+3];
            		currentFile = tokens[i*13+4];
            		frameStart = Integer.valueOf(tokens[i*13+5]);
            		frameEnd = Integer.valueOf(tokens[i*13+6]);
            		frameCount = Integer.valueOf(tokens[i*13+7]);
            		currentFrame = Integer.valueOf(tokens[i*13+8]);
            		renderFrameCount = Integer.valueOf(tokens[i*13+9]);
            		renderDefaultHangTime = Integer.valueOf(tokens[i*13+10]);
            		masterSlavePriority = Integer.valueOf(tokens[i*13+11]);
            		renderOutDir = tokens[i*13+12];

            		/*
            		MainApp.log.debug("fileName="+fileName);
            		MainApp.log.debug("renderStatus="+renderStatus);
            		MainApp.log.debug("serverName="+serverName);
            		MainApp.log.debug("ipAddress="+ipAddress);
            		MainApp.log.debug("currentFile="+currentFile);
            		MainApp.log.debug("frameStart="+frameStart);
            		MainApp.log.debug("frameEnd="+frameEnd);
            		MainApp.log.debug("frameCount="+frameCount);
            		MainApp.log.debug("currentFrame="+currentFrame);
            		MainApp.log.debug("renderFrameCount="+renderFrameCount);
            		MainApp.log.debug("renderDefaultHangTime="+renderDefaultHangTime);
            		MainApp.log.debug("masterSlavePriority="+masterSlavePriority);
            		MainApp.log.debug("renderOutDir="+renderOutDir);
            		*/
            		renderComputerStatusData.add(new RenderComputerStatus(fileName, renderStatus, serverName, ipAddress, currentFile, frameStart, 
            				frameEnd, frameCount, currentFrame, renderFrameCount, renderDefaultHangTime, masterSlavePriority, renderOutDir));
            		counter++;
            	}
	        }
	        /*
			for (int i=counter; i < 10; i++) {
				serverData.add(new Server("", ""));
			}
			*/
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}
    

    public static void getRenderedFileStatusCounts(String fileName, ObservableList<RenderFileStatusCounts> renderFileStatusCounts) throws SQLException {

		MainApp.log.debug("Starting H2.getRenderedFileStatusCounts fileName="+fileName);

    	String serverCmd = ApplicationConstants.SERVER_GET_RENDERING_FILE_COUNTS + fileName;
    	String fromServer = null;
	    String renderStatus = null;
	    String currentFile = null;
	    int frameStart = 0;
	    int frameEnd = 0;
	    int frameCount = 0;
	    int currentFrame = 0;

	    try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
			MainApp.log.debug("serverCmd="+serverCmd);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_RENDERING_FILE_COUNTS.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	renderFileStatusCounts.clear();

            	for (int i = 0; i < tokens.length/5; i++) {
            		currentFile = tokens[i*5];
            		frameStart = Integer.valueOf(tokens[i*5+1]);
            		frameEnd = Integer.valueOf(tokens[i*5+2]);
            		frameCount = Integer.valueOf(tokens[i*5+3]);
            		currentFrame = Integer.valueOf(tokens[i*5+4]);
            		
           			//fileName = currentFile;
           			//renderStatus = String.valueOf(currentFrame) + " of " + String.valueOf(frameEnd - frameStart + 1);

            		/*
            		MainApp.log.debug("currentFile="+currentFile);
            		MainApp.log.debug("frameStart="+frameStart);
            		MainApp.log.debug("frameEnd="+frameEnd);
            		MainApp.log.debug("frameCount="+frameCount);
            		MainApp.log.debug("currentFrame="+currentFrame);
            		*/

            		renderFileStatusCounts.add(new RenderFileStatusCounts("", "", currentFile, frameStart, 
            				frameEnd, frameCount, currentFrame, 0, 0, 0));
            		counter++;
            	}
	        }
	        /*
			for (int i=counter; i < 10; i++) {
				serverData.add(new Server("", ""));
			}
			*/
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}
    
    public static void getRenderedSingleFileStatusCounts(String serverIpAddress, String fileName, ObservableList<RenderComputerStatus> renderComputerStatusData03) throws SQLException {

		//MainApp.log.debug("Starting H2.getRenderedSingleFileStatusCounts");

        ObservableList<RenderComputerStatus> renderComputerStatusData01= FXCollections.observableArrayList();

    	getRenderedComputerStatusList(true, fileName, renderComputerStatusData01);
    	
	    try {
			for (RenderComputerStatus computerFile : renderComputerStatusData01){
				//MainApp.log.debug("ipAddress="+computerFile.getServerIpAddress());
				//MainApp.log.debug("serverIpAddress="+serverIpAddress);
				// Test to determine if this is a file that is currently being rendered - indicated by a 0 in the serverName field
				if (computerFile.getServerIpAddress().equals(serverIpAddress)) {
					MainApp.log.debug("MATCH");
					MainApp.log.debug("currentFrame="+computerFile.getCurrentFrame());
					MainApp.log.debug("ipAddress="+computerFile.getServerIpAddress());
					MainApp.log.debug("serverIpAddress="+serverIpAddress);
					computerFile.setRenderStatus(
							String.valueOf(computerFile.getCurrentFrame()) + 
							" of " 
							+ String.valueOf(computerFile.getFrameEnd()) +
							" [" +
							String.valueOf(computerFile.getCurrentFrame() - computerFile.getFrameStart() + 1) +
							" of " + 
							String.valueOf(computerFile.getFrameCount()) +
							"]");
	    	
					renderComputerStatusData03.clear();

					renderComputerStatusData03.add(computerFile);
    				//MainApp.log.debug("ipAddress="+renderComputerStatusData03.get(0).getServerIpAddress());
					//MainApp.log.debug("currentFrame="+renderComputerStatusData03.get(0).getCurrentFrame());
            		//MainApp.log.debug("renderStatus="+renderComputerStatusData03.get(0).getRenderStatus());
            	}
	        }
    	} catch (Exception e) {
    	      e.printStackTrace();
    	}
	}
    
    public static void getRenderedComputerFileList(Boolean active, String fileName, ObservableList<RenderComputerStatus> renderComputerStatusData) throws SQLException {

		//MainApp.log.debug("Starting getRenderedComputerStatusList");

		String serverCmd = ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS;
    	String fromServer = null;

    	// Send distinct indicator
   		serverCmd = serverCmd + fileName;
    	
	    String renderStatus = null;
    	String serverName = null;
	    String ipAddress = null;
	    String currentFile = null;
	    int frameStart = 0;
	    int frameEnd = 0;
	    int frameCount = 0;
	    int currentFrame = 0;
	    int renderFrameCount = 0;
	    int renderDefaultHangTime = 0;
	    int masterSlavePriority = 0;
	    String renderOutDir = null;

    	try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_RENDERING_FILE_STATUS.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	renderComputerStatusData.clear();

            	for (int i = 0; i < tokens.length/13; i++) {
            		fileName = tokens[i*13];
            		renderStatus = tokens[i*13+1];
            		serverName = tokens[i*13+2];
            		ipAddress = tokens[i*13+3];
            		currentFile = tokens[i*13+4];
            		frameStart = Integer.valueOf(tokens[i*13+5]);
            		frameEnd = Integer.valueOf(tokens[i*13+6]);
            		frameCount = Integer.valueOf(tokens[i*13+7]);
            		currentFrame = Integer.valueOf(tokens[i*13+8]);
            		renderFrameCount = Integer.valueOf(tokens[i*13+9]);
            		renderDefaultHangTime = Integer.valueOf(tokens[i*13+10]);
            		masterSlavePriority = Integer.valueOf(tokens[i*13+11]);
            		renderOutDir = tokens[i*13+12];

            		/*
            		MainApp.log.debug("fileName="+fileName);
            		MainApp.log.debug("renderStatus="+renderStatus);
            		MainApp.log.debug("serverName="+serverName);
            		MainApp.log.debug("ipAddress="+ipAddress);
            		MainApp.log.debug("currentFile="+currentFile);
            		MainApp.log.debug("frameStart="+frameStart);
            		MainApp.log.debug("frameEnd="+frameEnd);
            		MainApp.log.debug("frameCount="+frameCount);
            		MainApp.log.debug("currentFrame="+currentFrame);
            		MainApp.log.debug("renderFrameCount="+renderFrameCount);
            		MainApp.log.debug("renderDefaultHangTime="+renderDefaultHangTime);
            		MainApp.log.debug("masterSlavePriority="+masterSlavePriority);
            		MainApp.log.debug("renderOutDir="+renderOutDir);
            		*/

            		renderComputerStatusData.add(new RenderComputerStatus(fileName, renderStatus, serverName, ipAddress, currentFile, frameStart, 
            				frameEnd, frameCount, currentFrame, renderFrameCount, renderDefaultHangTime, masterSlavePriority, renderOutDir));
            		counter++;
            	}
	        }
	        /*
			for (int i=counter; i < 10; i++) {
				serverData.add(new Server("", ""));
			}
			*/
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}
    
    public static void getComputerIpAllFileList(String ipAddress, ObservableList<RenderComputerStatus> renderComputerFiles) throws SQLException {

		//MainApp.log.debug("Starting getComputerIpAllFileList");

		String serverCmd = ApplicationConstants.RENDER_GET_COMPUTER_IP_ALL_FILES_LIST;
    	String fromServer = null;

    	// Send distinct indicator
   		serverCmd = serverCmd + ipAddress;
    	
	    String fileName = null;
	    String errorCountStr = null;
	    String rowIdStr = null;
	    String overrideOutputDir = null;
	    String overrideRenderDevice = null;

    	try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer;
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	renderComputerFiles.clear();

            	for (int i = 0; i < tokens.length/6; i++) {
            		ipAddress = tokens[i*6];
            		fileName = tokens[i*6+1];
            		errorCountStr = tokens[i*6+2];
            		rowIdStr = tokens[i*6+3];
            		overrideOutputDir = tokens[i*6+4];
            		overrideRenderDevice = tokens[i*6+5];

            		/*
            		MainApp.log.debug("ipAddress="+ipAddress);
            		MainApp.log.debug("fileName="+fileName);
            		MainApp.log.debug("errorCountStr="+errorCountStr);
            		MainApp.log.debug("rowIdStr="+rowIdStr);
            		MainApp.log.debug("overrideOutputDir="+overrideOutputDir);
            		MainApp.log.debug("overrideRenderDevice="+overrideRenderDevice);
            		*/

            		renderComputerFiles.add(new RenderComputerStatus(fileName, "", rowIdStr, ipAddress, overrideOutputDir+overrideRenderDevice, 0, 0, 0, 0, 0, 0, 0, errorCountStr));
            		counter++;
            	}
	        }
	        /*
			for (int i=counter; i < 10; i++) {
				serverData.add(new Server("", ""));
			}
			*/

	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

   	public static void updateServerSuspendKillSwitchStatus(String serverName, String serverIpAddress, int serverKillSwitch, int serverSuspendStatus, int backgroundRender) throws SQLException {

		Boolean pendingUpdate = true;
    	String fromServer = null;

    	String serverCmd = ApplicationConstants.RENDER_UPDATE_SUSPEND_KILL_SWITCH + serverName 
    			+ "|" + serverIpAddress 
    			+ "|" + Integer.toString(serverKillSwitch) 
    			+ "|" + Integer.toString(serverSuspendStatus)
    			+ "|" + Integer.toString(backgroundRender);
    	
    	MainApp.log.debug("Sending read server queue request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());

    	while (pendingUpdate) {
	    	try {
		        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
	    		mcScoket.setTcpNoDelay(true);
		        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
		    	out.println(serverCmd);
    	    	out.flush();
		        BufferedReader in = new BufferedReader(
		                new InputStreamReader(mcScoket.getInputStream()));
		        if ((fromServer = in.readLine()) != null) {
		            //MainApp.log.debug("[updateServerSuspendKillSwitchStatus] "+ApplicationConstants.SERVER + fromServer);
		            if (fromServer.equals(ApplicationConstants.DATABASE_UPDATE_OK)) {
		            	pendingUpdate = false;
			            //MainApp.log.debug("[updateServerSuspendKillSwitchStatus] Update completed");
		            }
		        }
    	        mcScoket.close();
	    	} catch (ConnectException e) {
	    	      //throw e;
	    	} catch (IOException e) {
	    	      e.printStackTrace();
	    	}
    	}
	}

	public static List<String> readFileServerQueue2(Boolean showErrors) throws IOException {

		List<String> fileList = new ArrayList<String>();
		
    	String serverCmd = ApplicationConstants.RENDER_READ_SERVER_QUEUE;
    	String fromServer = null;
    	
        if (showErrors) {
        	serverCmd = serverCmd + "1";
        } else {
        	serverCmd = serverCmd + "0";
        }

    	MainApp.log.debug("[readFileServerQueue] "+"Sending read server queue request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());

    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug("[readFileServerQueue] "+ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.RENDER_READ_SERVER_QUEUE.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	for (int i = 0; i < tokens.length; i++) {
            		//MainApp.log.debug(tokens[i]);
            		fileList.add(tokens[i]);
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
		return fileList;
    }
	
	public static  ObservableList<String> readFileServerQueue(Boolean showErrors) throws IOException {

		ObservableList<String> fileList = FXCollections.observableArrayList();
		
    	String serverCmd = ApplicationConstants.RENDER_READ_SERVER_QUEUE;
    	String fromServer = null;
    	
        if (showErrors) {
        	serverCmd = serverCmd + "1";
        } else {
        	serverCmd = serverCmd + "0";
        }

    	MainApp.log.debug("[readFileServerQueue] "+"Sending read server queue request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());

    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug("[readFileServerQueue] "+ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.RENDER_READ_SERVER_QUEUE.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	for (int i = 0; i < tokens.length; i++) {
            		//MainApp.log.debug(tokens[i]);
            		fileList.add(tokens[i]);
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
		return fileList;
    }
	
    public static void getServerListForDisplay(ObservableList<Server> serverData, boolean definedServers) throws SQLException {

		//MainApp.log.debug("Starting getServerListForDisplay");

		String serverCmd = ApplicationConstants.SERVER_GET_SERVERS;
    	String fromServer = null;
    	String serverName = null;
	    String ipAddress = null;
	    String serverStatus = null;
	    String killSwitch = null;
	    String suspendSwitch = null;
	    String backgroundSwitch = null;

    	try {
    		int counter = 0;
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_SERVERS.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
        		serverData.clear();

            	for (int i = 0; i < tokens.length/6; i++) {
            		serverName = tokens[i*6];
            		ipAddress = tokens[i*6+1];
            		serverStatus = tokens[i*6+2];
            		killSwitch = tokens[i*6+3];
            		suspendSwitch = tokens[i*6+4];
            		backgroundSwitch = tokens[i*6+5];
            		//MainApp.log.debug("serverName="+serverName);
            		//MainApp.log.debug("ipAddress="+ipAddress);
            		//MainApp.log.debug("serverStatus="+serverStatus);
            		//MainApp.log.debug("killSwitch="+killSwitch);
            		//MainApp.log.debug("suspendSwitch="+suspendSwitch);
            		//MainApp.log.debug("backgroundSwitch="+backgroundSwitch);
            		serverData.add(new Server(serverName, ipAddress, serverStatus, null, killSwitch, suspendSwitch, backgroundSwitch));
            		counter++;
            	}
	        }
	        if (!definedServers) {
	        	for (int i=counter; i < 10; i++) {
	        		serverData.add(new Server("", "", "", null,"0","0","0"));
	        	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

	
	public static void addFileToServerQueue(String serverIpAddress, String serverCurrentFile, int overrideOutputDir, Boolean topOfQueue, String overrideRenderDevice) throws SQLException {
		
    	Boolean pendingUpdate = true;
    	String fromServer = null;
    	int topOfQueueFlag = 0;
    	
    	if (topOfQueue) {
    		topOfQueueFlag = 1;
    	}

    	String serverCmd = ApplicationConstants.RENDER_ADD_SERVER_QUEUE_FILE + serverIpAddress 
    						+ "|" + serverCurrentFile 
    						+ "|" + Integer.toString(overrideOutputDir) 
    						+ "|" + Integer.toString(topOfQueueFlag)
    						+ "|" + overrideRenderDevice;
    	
    	MainApp.log.debug("[addFileToServerQueue] "+"Sending read server queue request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());

    	while (pendingUpdate) {
	    	try {
		        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
	    		mcScoket.setTcpNoDelay(true);
		        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
		    	out.println(serverCmd);
    	    	out.flush();
		        BufferedReader in = new BufferedReader(
		                new InputStreamReader(mcScoket.getInputStream()));
		        if ((fromServer = in.readLine()) != null) {
		            //MainApp.log.debug("[addFileToServerQueue] "+ApplicationConstants.SERVER + fromServer);
		            if (fromServer.equals(ApplicationConstants.DATABASE_UPDATE_OK)) {
		            	pendingUpdate = false;
			            //MainApp.log.debug("[addFileToServerQueue] Update completed");
		            }
		        }
    	        mcScoket.close();
	    	} catch (ConnectException e) {
	    	      //throw e;
	    	} catch (IOException e) {
	    	      e.printStackTrace();
	    	}
    	}
	}
		
	public static void deleteFileFromServerQueue(String serverIpAddress, String serverCurrentFile, int overrideOutputDir, String overrideRenderDevice) throws SQLException {
		
    	Boolean pendingUpdate = true;
    	String fromServer = null;
    	
    	// The "1" at the end of the serverCmd is a flag to indicate all IP addresses (GUI Client) rather than a single IP address (Blender)
    	String serverCmd = ApplicationConstants.RENDER_DELETE_SERVER_QUEUE_FILE + serverIpAddress 
    			+ "|" + serverCurrentFile 
    			+ "|" + Integer.toString(overrideOutputDir)
    			+ "|" + overrideRenderDevice;
    	
    	MainApp.log.debug("[deleteFileFromServerQueue] "+"Sending delete file from server queue request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());

    	while (pendingUpdate) {
	    	try {
		        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
	    		mcScoket.setTcpNoDelay(true);
		        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
		    	out.println(serverCmd);
    	    	out.flush();
		        BufferedReader in = new BufferedReader(
		                new InputStreamReader(mcScoket.getInputStream()));
		        if ((fromServer = in.readLine()) != null) {
		            //MainApp.log.debug("[deleteFileFromServerQueue] "+ApplicationConstants.SERVER + fromServer);
		            if (fromServer.equals(ApplicationConstants.DATABASE_UPDATE_OK)) {
		            	pendingUpdate = false;
			            //MainApp.log.debug("[deleteFileFromServerQueue] Update completed");
		            }
		        }
    	        mcScoket.close();
	    	} catch (ConnectException e) {
	    	      //throw e;
	    	} catch (IOException e) {
	    	      e.printStackTrace();
	    	}
    	}
	}
	
    public static void exportServerRenderLogToCsvFile(ObservableList<ServerRenderLog> serverRenderLogData, String logStartDate, String logEndDate, String logServerName, String logIpAddress, String logStatMsg, String logBlendFile, String csvFileName) throws SQLException {

		//MainApp.log.debug("Starting exportServerRenderLogToCsvFile");

		String serverCmd = ApplicationConstants.CREATE_SERVERLOG_CSV_FILE;
    	String fromServer = null;

	    serverCmd += "|" + logStartDate + "|" + logEndDate + "|" + logServerName + "|" + logIpAddress + "|" + logStatMsg + "|" + logBlendFile + "|" + csvFileName;
	    MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

    public static void readServerRenderLogForDisplay(ObservableList<ServerRenderLog> serverRenderLogData, String logStartDate, String logEndDate, String logServerName, String logIpAddress, String logStatMsg, String logBlendFile) throws SQLException {

		//MainApp.log.debug("Starting readServerRenderLogForDisplay");

		String serverCmd = ApplicationConstants.LOG_GET_SERVERLOG_LIST;
    	String fromServer = null;

	    String ipAddress = null;
	    String serverName = null;
	    String statMsg = null;
	    String logFileDate = null;
	    String logFileTime = null;
	    int seqNum = -1;

	    serverCmd += "|" + logStartDate + "|" + logEndDate + "|" + logServerName + "|" + logIpAddress + "|" + logStatMsg + "|" + logBlendFile;
	    MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.LOG_GET_SERVERLOG_LIST.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	serverRenderLogData.clear();

            	for (int i = 0; i < tokens.length/6; i++) {
            		ipAddress = tokens[i*6];
            		serverName = tokens[i*6+1];
            		statMsg = tokens[i*6+2];
            		logFileDate = tokens[i*6+3];
            		logFileTime = tokens[i*6+4];
            		seqNum = Integer.parseInt(tokens[i*6+5]); 
            		//MainApp.log.debug("ipAddress="+ipAddress);
            		//MainApp.log.debug("serverName="+serverName);
            		//MainApp.log.debug("statMsg="+statMsg);
            		//MainApp.log.debug("logFileDate="+logFileDate);
            		//MainApp.log.debug("logFileTime="+logFileTime);
            		//MainApp.log.debug("seqNum="+seqNum);
            		serverRenderLogData.add(new ServerRenderLog(ipAddress, serverName, statMsg, logFileDate, logFileTime, seqNum));
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

    public static void getServerIpAddresses(ObservableList<String> serverLogIpAddresses) throws SQLException {

		//MainApp.log.debug("Starting getServerIpAddresses");

		String serverCmd = ApplicationConstants.LOG_GET_SERVERLOG_IP_ADDRESS_LIST;
    	String fromServer = null;

	    String ipAddress = null;

	    //MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.LOG_GET_SERVERLOG_IP_ADDRESS_LIST.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	serverLogIpAddresses.clear();

            	for (int i = 0; i < tokens.length; i++) {
            		ipAddress = tokens[i];
            		//MainApp.log.debug("ipAddress="+ipAddress);
            		serverLogIpAddresses.add(ipAddress);
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}
	
    public static void getServerNames(ObservableList<String> serverLogServerNames) throws SQLException {

		//MainApp.log.debug("Starting getServerNames");

		String serverCmd = ApplicationConstants.LOG_GET_SERVERLOG_SERVER_LIST;
    	String fromServer = null;

	    String serverName = null;

	    //MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.LOG_GET_SERVERLOG_SERVER_LIST.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	serverLogServerNames.clear();

            	for (int i = 0; i < tokens.length; i++) {
            		serverName = tokens[i];
            		//MainApp.log.debug("ipAddress="+ipAddress);
            		serverLogServerNames.add(serverName);
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

    public static void getBlendFiles(ObservableList<String> serverLogBlendFiles) throws SQLException {

		//MainApp.log.debug("Starting getBlendFiles");

		String serverCmd = ApplicationConstants.LOG_GET_SERVERLOG_BLEND_FILE_LIST;
    	String fromServer = null;
	    String blendFile = null;

	    //MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.LOG_GET_SERVERLOG_BLEND_FILE_LIST.length());
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	serverLogBlendFiles.clear();

            	for (int i = 0; i < tokens.length; i++) {
            		blendFile = tokens[i];
            		//MainApp.log.debug("blendFile="+blendFile);
            		serverLogBlendFiles.add(blendFile);
            	}
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

   	public static void readServerDataForDisplay(ObservableList<ServerStatus> serverStatusData, String serverName, String serverIpAddress) throws SQLException {

		//MainApp.log.debug("Starting readServerRenderLogForDisplay");

		String serverCmd = ApplicationConstants.SERVER_GET_SERVER_DATA;
    	String fromServer = null;

	    serverCmd += serverName + "|" + serverIpAddress;
	    //MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug(ApplicationConstants.SERVER + fromServer);
            	String phrase = fromServer.substring(ApplicationConstants.SERVER_GET_SERVER_DATA.length());
        		//MainApp.log.debug("phrase1="+phrase);
            	phrase = phrase.replace("||", "| |");
        		//MainApp.log.debug("phrase2="+phrase);
            	phrase = phrase.replace("null", " ");
        		//MainApp.log.debug("phrase3="+phrase);
            	String delims = "[|]+";
            	String[] tokens = phrase.split(delims);
            	
            	serverStatusData.clear();

            	/*
            	for (int i=0; i<18; i++) {
            		MainApp.log.debug("tokens["+i+"]="+tokens[i]);
            	}
            	*/
            	
			    String name = tokens[0];
			    String ipAddress = tokens[1];
			    String serverStatus = tokens[2];
			    Timestamp serverStatusTimestamp = null;
			    if (!tokens[3].equals(" ")) {
			    	serverStatusTimestamp = Timestamp.valueOf(tokens[3]);
			    }
			    String currentFile = tokens[4];
			    Timestamp serverRenderStart = null;
			    if (!tokens[5].equals(" ")) {
			    	serverRenderStart = Timestamp.valueOf(tokens[5]);
			    }
			    Timestamp serverRenderEnd = null;
			    if (!tokens[6].equals(" ")) {
			    	serverRenderEnd = Timestamp.valueOf(tokens[6]);
			    }
			    Timestamp serverRenderLastUpdate = null;
			    if (!tokens[7].equals(" ")) {
			    	serverRenderLastUpdate = Timestamp.valueOf(tokens[7]);
			    }
			    int frameStart = Integer.parseInt(tokens[8]);
			    int frameEnd = Integer.parseInt(tokens[9]);
			    int frameCount = Integer.parseInt(tokens[10]);
			    int currentFrame = Integer.parseInt(tokens[11]);
			    int renderFrameCount = Integer.parseInt(tokens[12]);
			    String renderOutDir = tokens[13];
			    int killSwitch = Integer.parseInt(tokens[14]);
			    int suspendSwitch = Integer.parseInt(tokens[15]);
			    int backgroundRender = Integer.parseInt(tokens[16]);
			    int renderDefaultHangTime = Integer.parseInt(tokens[17]);
			    /*
			    MainApp.log.debug("serverName="+name);
			    MainApp.log.debug("serverIpAddress="+ipAddress);
        		MainApp.log.debug("serverStatus="+serverStatus);
        		MainApp.log.debug("serverStatusTimestamp="+serverStatusTimestamp);
        		MainApp.log.debug("currentFile="+currentFile);
        		MainApp.log.debug("serverRenderStart="+serverRenderStart);
        		MainApp.log.debug("serverRenderEnd="+serverRenderEnd);
        		MainApp.log.debug("serverRenderLastUpdate="+serverRenderLastUpdate);
        		MainApp.log.debug("frameStart="+frameStart);
        		MainApp.log.debug("frameEnd="+frameEnd);
        		MainApp.log.debug("frameCount="+frameCount);
        		MainApp.log.debug("currentFrame="+currentFrame);
        		MainApp.log.debug("renderFrameCount="+renderFrameCount);
        		MainApp.log.debug("renderOutDir="+renderOutDir);
        		MainApp.log.debug("killSwitch="+killSwitch);
        		MainApp.log.debug("suspendSwitch="+suspendSwitch);
        		MainApp.log.debug("backgroundRender="+backgroundRender);
        		MainApp.log.debug("renderDefaultHangTime="+renderDefaultHangTime);
				*/
			    
        		serverStatusData.add(new ServerStatus(name, ipAddress, serverStatus, serverStatusTimestamp, currentFile, 
			    		serverRenderStart, serverRenderEnd, serverRenderLastUpdate, frameStart, frameEnd, frameCount, currentFrame, renderFrameCount, 
			    		renderOutDir, killSwitch, suspendSwitch, backgroundRender, renderDefaultHangTime));
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

	public static void deleteServerStatusData(String serverName, String serverIpAddress) throws SQLException {
		//MainApp.log.debug("Starting deleteServerStatusData");

		String serverCmd = ApplicationConstants.SERVER_DELETE_SERVER_DATA;
    	String fromServer = null;

	    serverCmd += serverName + "|" + serverIpAddress;
	    MainApp.log.debug("serverCmd="+serverCmd);
	    
    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug("[deleteServerStatusData] "+ApplicationConstants.SERVER + fromServer);
	            if (fromServer.equals(ApplicationConstants.DATABASE_UPDATE_OK)) {
		            MainApp.log.debug("[deleteServerStatusData] " + serverName + "(" + serverIpAddress + ") Update complete");
	            }
	        }
		    mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

   	public static void saveServerStatusData(String serverName, String serverIpAddress, String status, String lastStatusUpdate, String currentRenderFile,
   											String renderStartTime, String renderEndTime, String lastRenderUpdate, String startFrame, String endFrame,
   											String frameCount, String currentFrame, String cumulativeRenderFrameCount, String renderOutputLocationAndFileName,
   											String hangTimeThreshold, String suspendSwitch, String killSwitch, String backgroundSwitch) throws SQLException {

//		Boolean pendingUpdate = true;
    	String fromServer = null;
    	
    	String serverCmd = ApplicationConstants.SERVER_SAVE_SERVER_DATA + serverName 
    			+ "|" + serverIpAddress 
    			+ "|" + status 
    			+ "|" + lastStatusUpdate
    			+ "|" + currentRenderFile
    			+ "|" + renderStartTime 
    			+ "|" + renderEndTime 
    			+ "|" + lastRenderUpdate
    			+ "|" + startFrame
    			+ "|" + endFrame 
    			+ "|" + frameCount 
    			+ "|" + currentFrame
    			+ "|" + cumulativeRenderFrameCount
    			+ "|" + renderOutputLocationAndFileName 
    			+ "|" + hangTimeThreshold 
    			+ "|" + suspendSwitch
    			+ "|" + killSwitch
    			+ "|" + backgroundSwitch
    			+ "|";
    	

    	//MainApp.log.debug("Sending save serverStatus data request for " + GlobalClass.getServerMasterIpAddress() + ":" + GlobalClass.getPortNumber());
    	//MainApp.log.debug("serverCmd=" + serverCmd);

    	try {
	        Socket mcScoket = new Socket(GlobalClass.getServerMasterIpAddress(), GlobalClass.getPortNumber());
    		mcScoket.setTcpNoDelay(true);
	        PrintWriter out = new PrintWriter(mcScoket.getOutputStream(), true);
	    	out.println(serverCmd);
	    	out.flush();
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(mcScoket.getInputStream()));
	        if ((fromServer = in.readLine()) != null) {
	            //MainApp.log.debug("[saveServerStatusData] "+ApplicationConstants.SERVER + fromServer);
	            if (fromServer.equals(ApplicationConstants.DATABASE_UPDATE_OK)) {
		            MainApp.log.debug("[saveServerStatusData] " + serverName + "(" + serverIpAddress + ") Update complete");
	            }
	        }
	        mcScoket.close();
    	} catch (ConnectException e) {
    	      //throw e;
    	} catch (IOException e) {
    	      e.printStackTrace();
    	}
	}

}
