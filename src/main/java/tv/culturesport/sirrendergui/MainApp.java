package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2016, Dale Kubler. All rights reserved.
 *
 */

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import tv.culturesport.sirrendergui.GlobalClass;
import tv.culturesport.sirrendergui.H2;
import tv.culturesport.sirrendergui.Utils;
import tv.culturesport.sirrendergui.GetServerListTask;
import tv.culturesport.sirrendergui.MainApp;
import tv.culturesport.sirrendergui.Server;
import tv.culturesport.sirrendergui.GetDistinctRenderedFileStatusListTask;
import tv.culturesport.sirrendergui.RenderComputerStatus;
import tv.culturesport.sirrendergui.RenderFileStatus;
import tv.culturesport.sirrendergui.RenderFileStatusCounts;


public class MainApp extends Application {

	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(MainApp.class.getName()); //LogManager.getLogger(MainApp.class.getName());

	public static IntegerProperty refreshPeriod = new SimpleIntegerProperty(ApplicationConstants.DEFAULT_REFRESH_SECONDS);
	public static IntegerProperty refreshPeriod1 = new SimpleIntegerProperty(ApplicationConstants.DEFAULT_REFRESH_SECONDS);
	public static IntegerProperty refreshPeriod2 = new SimpleIntegerProperty(ApplicationConstants.DEFAULT_REFRESH_SECONDS);
	
	private static Stage primaryStage;
    private BorderPane rootLayout;

	//Static global variable for the controller (where MyController is the name of your controller class
	static ClientController myControllerHandle;
	
    /**
     * The data as an observable list of files currently rendering or in a computers queue.
     */
    public static ObservableList<RenderComputerStatus> renderComputerFiles = FXCollections.observableArrayList();

    /**
     * The data as an observable list of currently rendering files.
     */
    public static ObservableList<RenderFileStatus> renderFileStatusData = FXCollections.observableArrayList();

    /**
     * The data as an observable list of currently rendering file statistics.
     */
    public static ObservableList<RenderFileStatusCounts> renderFileStatusCounts = FXCollections.observableArrayList();

    /**
     * The data as an observable list of currently rendering computers.
     */
    public static ObservableList<RenderComputerStatus> renderComputerStatusData = FXCollections.observableArrayList();

	// Create the scheduled service: serviceRenderedFileStatusList
	ScheduledService<ObservableList<RenderFileStatus>> serviceRenderedFileStatusList =
		new ScheduledService<ObservableList<RenderFileStatus>>()
		{
			@Override
			protected GetDistinctRenderedFileStatusListTask createTask()
			{
				return new GetDistinctRenderedFileStatusListTask();
			}
		};
	    
    /**
     * The data as an observable list of Servers.
     */
    public static ObservableList<Server> serverData = FXCollections.observableArrayList();
    public static ObservableList<Server> definedServerData = FXCollections.observableArrayList();

	// Create the scheduled service: serviceServerList
	ScheduledService<Object> servicePollServerList =
		new ScheduledService<Object>()
		{
			@Override
			protected PollServerStatusTask createTask()
			{
				return new PollServerStatusTask();
			}
		};
    
	// Create the scheduled service: serviceServerList
	ScheduledService<ObservableList<Server>> serviceServerList =
		new ScheduledService<ObservableList<Server>>()
		{
			@Override
			protected GetServerListTask createTask()
			{
				return new GetServerListTask();
			}
		};
	    
    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    	// serverData.add(new Server("Server 1", "192.168.1.10","true"));
    }

    /**
     * Returns the data as an observable list of rendering or queue files for a computer.
     * @return
     */
    public static ObservableList<RenderComputerStatus> getRenderComputerFiles() {
        return renderComputerFiles;
    }

    /**
     * Returns the data as an observable list of Rendered Files.
     * @return
     */
    public static ObservableList<RenderFileStatus> getRenderFileStatusData() {
        return renderFileStatusData;
    }

    /**
     * Returns the data as an observable list of Rendered Files.
     * @return
     */
    public static ObservableList<RenderComputerStatus> getRenderComputerStatusData() {
        return renderComputerStatusData;
    }

    /**
     * Returns the data as an observable list of rendering or queue files for a computer.
     * @return
     */
    public static ObservableList<RenderFileStatusCounts> getRenderFileStatusCounts() {
        return renderFileStatusCounts;
    }

    /**
     * Returns the data as an observable list of Servers.
     * @return
     */
    public static ObservableList<Server> getServerData() {
        return serverData;
    }

    /**
     * Returns the data as an observable list of Defined Servers. 
     * @return
     */
    public static ObservableList<Server> getDefinedServerData() {
        return definedServerData;
    }

	@Override
	public void start(Stage primaryStage) {

		// Configure the scheduled servicePollServerList service
		servicePollServerList.setDelay(Duration.seconds(1));
		servicePollServerList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
		servicePollServerList.setMaximumFailureCount(5);
		servicePollServerList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				if ((MainApp.getRefreshPeriod() != null) && (MainApp.getRefreshPeriod().get() > 0)) {
					MainApp.log.debug("MainApp.getRefreshPeriod().get() = " + MainApp.getRefreshPeriod().get());
			     	servicePollServerList.setPeriod(Duration.seconds(MainApp.getRefreshPeriod().get()));
				} else {
					servicePollServerList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
				}
			}
		});

		// Configure the scheduled serviceServerList service
		serviceServerList.setDelay(Duration.seconds(1));
		serviceServerList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
		serviceServerList.setMaximumFailureCount(5);
		serviceServerList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		     @Override
		     public void handle(WorkerStateEvent t) {
		          if ((MainApp.getRefreshPeriod2() != null) && (MainApp.getRefreshPeriod2().get() > 0)) {
			          //MainApp.log.debug("MainApp.getRefreshPeriod2().get()=" + MainApp.getRefreshPeriod2().get());
		        	  serviceServerList.setPeriod(Duration.seconds(MainApp.getRefreshPeriod2().get()));
		          } else {
			          //MainApp.log.debug("MainApp.getRefreshPeriod2().get() = ApplicationConstants.DEFAULT_REFRESH_SECONDS");
			          serviceServerList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
		          }
		     }
		});

		// Configure the scheduled serviceRenderedFileStatusList service
		serviceRenderedFileStatusList.setDelay(Duration.seconds(1));
		serviceRenderedFileStatusList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
		serviceRenderedFileStatusList.setMaximumFailureCount(5);
		serviceRenderedFileStatusList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		     @Override
		     public void handle(WorkerStateEvent t) {
		          if ((MainApp.getRefreshPeriod1() != null) && (MainApp.getRefreshPeriod1().get() > 0)) {
			          //MainApp.log.debug("MainApp.getRefreshPeriod1().get()=" + MainApp.getRefreshPeriod1().get());
			          serviceRenderedFileStatusList.setPeriod(Duration.seconds(MainApp.getRefreshPeriod1().get()));
		          } else {
			          //MainApp.log.debug("MainApp.getRefreshPeriod1().get() = ApplicationConstants.DEFAULT_REFRESH_SECONDS");
		        	  serviceRenderedFileStatusList.setPeriod(Duration.seconds(ApplicationConstants.DEFAULT_REFRESH_SECONDS));
		          }
		     }
		});

		try {
	        MainApp.primaryStage = primaryStage;
	        MainApp.primaryStage.setTitle(ApplicationConstants.SIRRENDER_CLIENT_TITLE);
			MainApp.primaryStage.initStyle(StageStyle.DECORATED);
	        // Set the application icon.
	        MainApp.primaryStage.getIcons().add(new Image("file:resources/images/blender_icon.png"));
	        
		    //Set up instance instead of using static load() method
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tv/culturesport/sirrendergui/Client.fxml"));
		    Parent root = loader.load();

		    //Now we have access to getController() through the instance... don't forget the type cast
		    myControllerHandle = (ClientController)loader.getController();
	        
			Scene scene = new Scene(root,1365,790);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			servicePollServerList.start();
			serviceServerList.start();
			serviceRenderedFileStatusList.start();
			
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    
	static class BrowseToolTipCell extends ListCell<String> {
        final Tooltip tip = new Tooltip();

        public BrowseToolTipCell() {
        	;
        }

        public void updateItem(String tooltipText, boolean empty) {
            super.updateItem(tooltipText, empty);
            setText("This is a sample SirRender tooltip");
            setTooltip(tip);
        }
    }
	
	public void QueueButtonAction1(ActionEvent event) throws SQLException, ConnectException, IOException {

		ListView<String> listQueue = new ListView<String>();
		final Label labelQueue = new Label();
		ObservableList<String> dataQueue = FXCollections.observableArrayList();

		dataQueue = (ObservableList<String>) H2.readFileServerQueue(false);

        labelQueue.setLayoutX(10);
        labelQueue.setLayoutY(115);
        labelQueue.setFont(Font.font("Verdana", 20));

		listQueue.setItems(dataQueue);

        listQueue.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> listQueue) {
                return new BrowseToolTipCell();
            }}
        );
	}

        /**
     * Shows the client inside the root layout.
     */
    public void showClient() {
        try {
            // Load client.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/tv/culturesport/sirrendergui/Client.fxml"));
            AnchorPane client = (AnchorPane) loader.load();

            // Set client into the center of root layout.
            rootLayout.setCenter(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {

    	String defaultPortNumberStr = "4444";
		//MainApp.log.debug("args.length = " + args.length);
		//MainApp.log.debug("args[0] = " + args[0]);
    	
    	// Determine who the Master Server is
        try {
			//GlobalClass.setServerMasterIpAddress(H2.getMasterServerIpAddress());
			//H2.getMasterServerIpAddressFromLocal();
			H2.getMasterServerIpAddressFromFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        GlobalClass.setLastPollTime(Utils.getCurrentTime());

		if (args.length > 1) {
			MainApp.log.debug("Usage: java -jar SirRender2Client.jar <port number>");
			MainApp.log.debug("or");
			MainApp.log.debug("Usage: java -jar SirRender2Client.jar");
			System.exit(1);
		} else if (args.length == 1) {
			GlobalClass.setPortNumber(Integer.valueOf(args[0]));
		} else {
			GlobalClass.setPortNumber(Integer.valueOf(defaultPortNumberStr));
		}			

		launch(args);
	}
	
    public static void getServerListFromMasterServer() throws SQLException {
    	H2.getServerListForDisplay(serverData, false);
    	H2.getServerListForDisplay(definedServerData, true);
    }
	
    public static void getServerListFromMasterServerForMonitor() throws SQLException {
    	H2.getServerListForDisplay(serverData, true);
    }
	
    public static void getRenderedFileStatusListFromMasterServer(Boolean active) throws SQLException {
    	
    	renderFileStatusData.clear();
    	
    	H2.getDistinctRenderedFileStatusList(active, renderFileStatusData);
    	
    	// Populate the file array renderStatus element with the correct counts
		for (RenderFileStatus renderFile : renderFileStatusData){
			//MainApp.log.debug( "fileName="+renderFile.getCurrentFile());
			getRenderedFileStatusCountsFromMasterServer(renderFile.getCurrentFile());
			renderFile.setRenderStatus(
					String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame()) + 
					" of " 
					+ String.valueOf(renderFileStatusCounts.get(0).getFrameEnd()) +
					" [" +
					String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame() - renderFileStatusCounts.get(0).getFrameStart() + 1) +
					" of " + 
					String.valueOf(renderFileStatusCounts.get(0).getFrameCount()) +
					"]");
			//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame()) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameEnd()));
			//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame() - renderFileStatusCounts.get(0).getFrameStart() + 1) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameCount()));
		}

    }
	
    public static void getRenderedFileStatusCountsFromMasterServer(String fileName) throws SQLException {
    	
    	// Empty the array as we only want to store a single element
    	renderFileStatusCounts.clear();
    	
		//MainApp.log.debug( "fileName="+fileName);
    	
    	// Get the single element
    	H2.getRenderedFileStatusCounts(fileName, renderFileStatusCounts);
    }
	
    public static void getRenderedFileComputerListFromMasterServer(Boolean active, String fileName, TableView<RenderComputerStatus> tblComputersStatus) throws SQLException {
    	
    	renderComputerStatusData.clear();
    	
    	H2.getRenderedComputerFileList(active, fileName, renderComputerStatusData);
    	
    	// Add the files  to the table
    	tblComputersStatus.setItems(renderComputerStatusData);

    	// Populate the file array renderComputer element with the correct counts
		for (RenderComputerStatus renderComputer : renderComputerStatusData){
			//MainApp.log.debug( "fileName="+renderComputer.getFileName());
			renderComputer.setRenderStatus(
					String.valueOf(renderComputer.getCurrentFrame()) + 
					" of " 
					+ String.valueOf(renderComputer.getFrameEnd()) +
					" [" +
					String.valueOf(renderComputer.getCurrentFrame() - renderComputer.getFrameStart() + 1) +
					" of " + 
					String.valueOf(renderComputer.getFrameCount()) +
					"]");
			//MainApp.log.debug( String.valueOf(renderComputer.getCurrentFrame()) + " of " + String.valueOf(renderComputer.getFrameEnd()));
			//MainApp.log.debug( String.valueOf(renderComputer.getCurrentFrame() - renderComputer.getFrameStart() + 1) + " of " + String.valueOf(renderComputer.getFrameCount()));
		}
    }
	
    public static void getComputerFileStatusListFromMasterServer(Boolean active) throws SQLException {
    	
    	renderFileStatusData.clear();
    	
    	H2.getDistinctRenderedFileStatusList(active, renderFileStatusData);
    	
    	// Populate the file array renderStatus element with the correct counts
		for (RenderFileStatus renderFile : renderFileStatusData){
			//MainApp.log.debug( "fileName="+renderFile.getCurrentFile());
			getRenderedFileStatusCountsFromMasterServer(renderFile.getCurrentFile());
			renderFile.setRenderStatus(
					String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame()) + 
					" of " 
					+ String.valueOf(renderFileStatusCounts.get(0).getFrameEnd()) +
					" [" +
					String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame() - renderFileStatusCounts.get(0).getFrameStart() + 1) +
					" of " + 
					String.valueOf(renderFileStatusCounts.get(0).getFrameCount()) +
					"]");
			//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame()) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameEnd()));
			//MainApp.log.debug( String.valueOf(renderFileStatusCounts.get(0).getCurrentFrame() - renderFileStatusCounts.get(0).getFrameStart() + 1) + " of " + String.valueOf(renderFileStatusCounts.get(0).getFrameCount()));
		}
    }

    public static void getComputerIpAllFileListFromMasterServer(String ipAddress, TableView<RenderComputerStatus> tblFilesStatus) throws SQLException {

    	ObservableList<RenderComputerStatus> renderComputerStatusData02= FXCollections.observableArrayList();
    	
		//MainApp.log.debug( "1 ipAddress="+ipAddress);
		renderComputerFiles.clear();
		
		//MainApp.log.debug( "1b renderComputerFiles.size()="+renderComputerFiles.size());
		H2.getComputerIpAllFileList(ipAddress, renderComputerFiles);
		//MainApp.log.debug( "renderComputerFiles.size()="+renderComputerFiles.size());
    	
    	// Add the files  to the table
    	tblFilesStatus.setItems(renderComputerFiles);

    	// Populate the file array renderStatus element with the correct counts
		for (RenderComputerStatus computerFile : renderComputerFiles){
			// Test to determine if this is a file that is currently being rendered - indicated by a 0 in th serverName field
			if (computerFile.getServerName().equals("0")) {
				H2.getRenderedSingleFileStatusCounts(ipAddress, computerFile.getFileName(), renderComputerStatusData02);
				computerFile.setRenderStatus(
						String.valueOf(renderComputerStatusData02.get(0).getCurrentFrame()) + 
						" of " 
						+ String.valueOf(renderComputerStatusData02.get(0).getFrameEnd()) +
						" [" +
						String.valueOf(renderComputerStatusData02.get(0).getCurrentFrame() - renderComputerStatusData02.get(0).getFrameStart() + 1) +
						" of " + 
						String.valueOf(renderComputerStatusData02.get(0).getFrameCount()) +
						"]");
			} else if (!computerFile.getRenderOutDir().equals("0")) {
				computerFile.setRenderStatus(
						"Error Count [" +
						computerFile.getRenderOutDir() + 
						"]");
				//MainApp.log.debug( "Error Count [" + computerFile.getRenderOutDir()+"]");
			}
			
			// Determine if this Output Override Directory option has been selected for this file
			// The CurrentFile location is being used to store this flag - 1 = Override
			if (computerFile.getCurrentFile().startsWith("1")) {
				computerFile.setFileName(computerFile.getFileName() + ApplicationConstants.OVERRIDE_OUTPUT_DIR);
			}
			if (computerFile.getCurrentFile().endsWith("C")) {
				computerFile.setFileName(computerFile.getFileName() + ApplicationConstants.OVERRIDE_RENDER_DEVICE_C);
			} else if (computerFile.getCurrentFile().endsWith("G")) {
				computerFile.setFileName(computerFile.getFileName() + ApplicationConstants.OVERRIDE_RENDER_DEVICE_G);
			}
		}
    }
	
	public static IntegerProperty getRefreshPeriod() {
		return refreshPeriod;
	}

	public static void setRefreshPeriod(IntegerProperty refreshPeriod) {
		MainApp.refreshPeriod = refreshPeriod;
	}

	public static IntegerProperty getRefreshPeriod1() {
		return refreshPeriod1;
	}

	public static void setRefreshPeriod1(IntegerProperty refreshPeriod) {
		MainApp.refreshPeriod1 = refreshPeriod;
	}

	public static IntegerProperty getRefreshPeriod2() {
		return refreshPeriod2;
	}

	public static void setRefreshPeriod2(IntegerProperty refreshPeriod) {
		MainApp.refreshPeriod2 = refreshPeriod;
	}

	public static ClientController getMyControllerHandle() {
		return myControllerHandle;
	}

	
}
