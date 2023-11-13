package tv.culturesport.sirrendergui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import tv.culturesport.sirrendergui.H2;
import tv.culturesport.sirrendergui.MainApp;

public class GetDistinctRenderedFileStatusListTask extends Task<ObservableList<RenderFileStatus>> {

//	private Boolean active = true;
//	private long sleepTimeInMillis = 15000;
	
    public GetDistinctRenderedFileStatusListTask()
    {
    }

//    public GetDistinctRenderedFileStatusListTask(Boolean active,long sleepTimeInMillis)
//    {
//        this.active = active;
//        this.sleepTimeInMillis = sleepTimeInMillis;
//    }

    // The task implementation
    @Override
    protected ObservableList<RenderFileStatus> call() throws SQLException
    {
    	// Determine who the Master Server is in case it changed
        try {
			//GlobalClass.setServerMasterIpAddress(H2.getMasterServerIpAddress());
			//H2.getMasterServerIpAddressFromLocal();
			H2.getMasterServerIpAddressFromFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// An observable list to represent the results
    	final ObservableList<RenderFileStatus> results = FXCollections.<RenderFileStatus>observableArrayList();
	
		//MainApp.log.debug("Starting GetDistinctRenderedFileStatusListTask");

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
            	
            	results.clear();

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
            		
            		//MainApp.log.debug("fileName="+fileName);
            		//MainApp.log.debug("renderStatus="+renderStatus);
            		//MainApp.log.debug("serverName="+serverName);
            		//MainApp.log.debug("ipAddress="+ipAddress);
            		//MainApp.log.debug("currentFile="+currentFile);
            		//MainApp.log.debug("frameStart="+frameStart);
            		//MainApp.log.debug("frameEnd="+frameEnd);
            		//MainApp.log.debug("frameCount="+frameCount);
            		//MainApp.log.debug("currentFrame="+currentFrame);
            		//MainApp.log.debug("renderFrameCount="+renderFrameCount);
            		//MainApp.log.debug("renderDefaultHangTime="+renderDefaultHangTime);
            		//MainApp.log.debug("masterSlavePriority="+masterSlavePriority);
            		//MainApp.log.debug("renderOutDir="+renderOutDir);
           			
            		results.add(new RenderFileStatus(fileName, renderStatus, serverName, ipAddress, currentFile, frameStart, frameEnd, 
            				frameCount, currentFrame, renderFrameCount, renderDefaultHangTime, masterSlavePriority, renderOutDir));
            		counter++;
            	}
	        }
	    	// Populate the file array renderStatus element with the correct counts
			for (RenderFileStatus renderFile : results){
				//MainApp.log.debug( "fileName="+renderFile.getCurrentFile());
				MainApp.getRenderedFileStatusCountsFromMasterServer(renderFile.getCurrentFile());
				renderFile.setRenderStatus(
						String.valueOf(MainApp.getRenderFileStatusCounts().get(0).getCurrentFrame()) + 
						" of " 
						+ String.valueOf(MainApp.getRenderFileStatusCounts().get(0).getFrameEnd()) +
						" [" +
						String.valueOf(MainApp.getRenderFileStatusCounts().get(0).getCurrentFrame() - MainApp.getRenderFileStatusCounts().get(0).getFrameStart() + 1) +
						" of " + 
						String.valueOf(MainApp.getRenderFileStatusCounts().get(0).getFrameCount()) +
						"]");
				MainApp.log.debug("renderStatus="+renderFile.getRenderStatus());
				//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame()) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameEnd()));
				//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame() - renderFileStatusCounts.get(0).getFrameStart() + 1) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameCount()));
			}
	        mcScoket.close();
	        /*
	        for (RenderFileStatus renderFile : results) {
	        	MainApp.log.debug("renderFile.getServerName()="+renderFile.getServerName());
	        	MainApp.log.debug("renderFile.getServerIpAddress()="+renderFile.getServerIpAddress());
	        }
	        */
	        // Publish the read-only list to give the Client access to the partial results
	        Platform.runLater(new Runnable()
	        {
	        	@Override
	        	public void run()
	        	{
	        		MainApp.getRenderFileStatusData().setAll(results);
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

