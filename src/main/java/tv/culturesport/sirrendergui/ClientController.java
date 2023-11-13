package tv.culturesport.sirrendergui;

/*
 * Copyright (c) 2016, Dale Kubler. All rights reserved.
 *
 */

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tv.culturesport.sirrendergui.RenderFileStatus;
import tv.culturesport.sirrendergui.Server;
import javafx.stage.Stage;

public class ClientController {
	// Render Control Tab
	@FXML
	private Button btnBrowse1;
	@FXML
	private Button btnClear1;
	@FXML
	private Button btnStatus1;
	@FXML
	private Button btnAddToRenderQueue;
	@FXML
	private ListView<String> browseView1;
	@FXML
	private CheckBox chkServer11;
	@FXML
	private CheckBox chkServer21;
	@FXML
	private CheckBox chkServer31;
	@FXML
	private CheckBox chkServer41;
	@FXML
	private CheckBox chkServer51;
	@FXML
	private CheckBox chkServer61;
	@FXML
	private CheckBox chkServer71;
	@FXML
	private CheckBox chkServer81;
	@FXML
	private CheckBox chkServer91;
	@FXML
	private CheckBox chkServer101;
	@FXML
	private Label lblServer11;
	@FXML
	private Label lblServer21;
	@FXML
	private Label lblServer31;
	@FXML
	private Label lblServer41;
	@FXML
	private Label lblServer51;
	@FXML
	private Label lblServer61;
	@FXML
	private Label lblServer71;
	@FXML
	private Label lblServer81;
	@FXML
	private Label lblServer91;
	@FXML
	private Label lblServer101;
	@FXML
	private Label lblServerStatus11;
	@FXML
	private Label lblServerStatus21;
	@FXML
	private Label lblServerStatus31;
	@FXML
	private Label lblServerStatus41;
	@FXML
	private Label lblServerStatus51;
	@FXML
	private Label lblServerStatus61;
	@FXML
	private Label lblServerStatus71;
	@FXML
	private Label lblServerStatus81;
	@FXML
	private Label lblServerStatus91;
	@FXML
	private Label lblServerStatus101;
	@FXML
	private CheckBox chkKillSwitchServer11;
	@FXML
	private CheckBox chkKillSwitchServer21;
	@FXML
	private CheckBox chkKillSwitchServer31;
	@FXML
	private CheckBox chkKillSwitchServer41;
	@FXML
	private CheckBox chkKillSwitchServer51;
	@FXML
	private CheckBox chkKillSwitchServer61;
	@FXML
	private CheckBox chkKillSwitchServer71;
	@FXML
	private CheckBox chkKillSwitchServer81;
	@FXML
	private CheckBox chkKillSwitchServer91;
	@FXML
	private CheckBox chkKillSwitchServer101;
	@FXML
	private CheckBox chkSuspendServer11;
	@FXML
	private CheckBox chkSuspendServer21;
	@FXML
	private CheckBox chkSuspendServer31;
	@FXML
	private CheckBox chkSuspendServer41;
	@FXML
	private CheckBox chkSuspendServer51;
	@FXML
	private CheckBox chkSuspendServer61;
	@FXML
	private CheckBox chkSuspendServer71;
	@FXML
	private CheckBox chkSuspendServer81;
	@FXML
	private CheckBox chkSuspendServer91;
	@FXML
	private CheckBox chkSuspendServer101;

	@FXML
	private CheckBox chkBackgroundRender1;
	@FXML
	private CheckBox chkBackgroundRender2;
	@FXML
	private CheckBox chkBackgroundRender3;
	@FXML
	private CheckBox chkBackgroundRender4;
	@FXML
	private CheckBox chkBackgroundRender5;
	@FXML
	private CheckBox chkBackgroundRender6;
	@FXML
	private CheckBox chkBackgroundRender7;
	@FXML
	private CheckBox chkBackgroundRender8;
	@FXML
	private CheckBox chkBackgroundRender9;
	@FXML
	private CheckBox chkBackgroundRender10;


	@FXML
	private CheckBox chkOverrideOutputPath;
	@FXML
	private CheckBox chkAddNewFilesTopOfQueue;
	@FXML
	private CheckBox chkOverrideRenderDevice;
	@FXML
	private CheckBox chkAllServers;
	@FXML
	private CheckBox chkAllSuspend;
	@FXML
	private CheckBox chkAllKill;
	@FXML
	private CheckBox chkAllBackground;

	@FXML
	private RadioButton radioCPU;
	@FXML
	private RadioButton radioGPU;

	@FXML
	private Label lblQueue1;
	@FXML
	private Button btnRemoveQueue1;
	@FXML
	private ListView<String> QueueView1;

	@FXML
	private Slider sliderRefreshRate;
	@FXML
	private TextField txtRefreshRate;

	// Render Log Tab
	@FXML
	private DatePicker logSearchStartDate;
	@FXML
	private DatePicker logSearchEndDate;
	@FXML
	private ChoiceBox<String> logSearchServer;
	@FXML
	private ChoiceBox<String> logSearchIpAddress;
	@FXML
	private ChoiceBox<String> logSearchMessage;
	@FXML
	private ChoiceBox<String> logSearchBlendFile;
	@FXML
	private TableView<ServerRenderLog> tblRenderLog;
	@FXML
	private TableColumn<ServerRenderLog, String> colRenderLogDate;
	@FXML
	private TableColumn<ServerRenderLog, String> colRenderLogTime;
	@FXML
	private TableColumn<ServerRenderLog, String> colRenderLogServerName;
	@FXML
	private TableColumn<ServerRenderLog, String> colRenderLogIpAddress;
	@FXML
	private TableColumn<ServerRenderLog, String> colRenderLogMessage;
	@FXML
	private Button btnLogSearch;
	@FXML
	private Button btnLogReset;
	@FXML
	private Button btnLogExport;

	// Servers tab
	@FXML
	private TableView<Server> tblServers;
	@FXML
	private TableColumn<Server, String> colServerName;
	@FXML
	private TableColumn<Server, String> colServerIpAddress;
	
	@FXML
	private TextField serverName;
	@FXML
	private TextField ipAddress;
	@FXML
	private TextField serverStatus;
	@FXML
	private TextField lastStatusUpdate;
	@FXML
	private TextField currentRenderFile;
	@FXML
	private TextField renderStartTime;
	@FXML
	private TextField renderEndTime;
	@FXML
	private TextField lastRenderUpdate;
	@FXML
	private TextField startFrame;
	@FXML
	private TextField endFrame;
	@FXML
	private TextField frameCount;
	@FXML
	private TextField currentFrame;
	@FXML
	private TextField cumulativeRenderFrameCount;
	@FXML
	private TextField renderOutputLocationAndFileName;
	@FXML
	private TextField hangTimeThreshold;
	@FXML
	private TextField suspendSwitch;
	@FXML
	private TextField killSwitch;
	@FXML
	private TextField backgroundSwitch;
	@FXML
	private Button btnServerNew;
	@FXML
	private Button btnServerDelete;
	@FXML
	private Button btnServerSave;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblRenderingFiles;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colRenderFile;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colRenderFileStatus;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblComputersStatus;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colRenderComputer;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colRenderComputerStatus;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblComputers;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colComputer;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colIpAddress;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tblFilesStatus;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colFile;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn colStatus;

	@FXML
	private Button btnRefresh;
	
	@FXML
	private Slider sliderRefreshRate1;
	@FXML
	private TextField txtRefreshRate1;
	
	@FXML
	private Slider sliderRefreshRate2;
	@FXML
	private TextField txtRefreshRate2;
	
	private ObservableList<ServerRenderLog> serverRenderLogData = FXCollections.observableArrayList();

    DoubleProperty refreshPeriod1d = new SimpleDoubleProperty(0);;
    DoubleProperty refreshPeriod2d = new SimpleDoubleProperty(0);;

	
	final ToggleGroup group = new ToggleGroup();

	private Stage savedStage;

	// Client Status Tab
	public void initialize(URL url, ResourceBundle rb) {
		Property<Number> i = sliderRefreshRate.valueProperty();
	    MainApp.refreshPeriod.bindBidirectional(i);
	    //When you're in need to pass the primitive forward, get it from the property

	    MainApp.getRefreshPeriod();
	    //MainApp.log.debug("logSearchDate="+logSearchStartDate.getValue());

	    Property<Number> j = sliderRefreshRate1.valueProperty();
	    MainApp.refreshPeriod1.bindBidirectional(j); 
	    //When you're in need to pass the primitive forward, get it from the property
	    //MainApp.getRefreshPeriod1();
	    
		Property<Number> k = sliderRefreshRate2.valueProperty();
	    MainApp.refreshPeriod2.bindBidirectional(k); 
	    //When you're in need to pass the primitive forward, get it from the property
	    //MainApp.getRefreshPeriod2();
	}

    static String portNumber = ApplicationConstants.DEFAULT_PORT_NUMBER;
	static String tempOutputPath = System.getenv(ApplicationConstants.ENV_SIRRENDER_TMP_PATH);
	static String blenderInputPath = System.getenv(ApplicationConstants.ENV_SIRRENDER_BLENDER_INPUT_PATH);
	static String renderOutputPath = System.getenv(ApplicationConstants.ENV_SIRRENDER_BLENDER_OUTPUT_PATH);
	List<File> selectedFiles = null;

	// Event Listener on MenuItem.onAction
    /**
     * Creates an empty server list.
     */
	@FXML
	public void handleNew(ActionEvent event) {
        MainApp.getServerData().clear();
        MainApp.getDefinedServerData().clear();
	}

    /**
     * The data as an observable list of Servers.
     */
    public static ObservableList<Server> serverData = FXCollections.observableArrayList();
    public static ObservableList<Server> definedServerData = FXCollections.observableArrayList();
    public static ObservableList<ServerStatus> serverStatusData = FXCollections.observableArrayList();

    /**
     * The data as an observable list of RenderLog Servers, RenderLog IpAddresses, and RenderLog Blend Files.
     */
    public static ObservableList<String> renderLogServerList = FXCollections.observableArrayList();
    public static ObservableList<String> renderLogIpAddressList = FXCollections.observableArrayList();
    public static ObservableList<String> renderLogBlendFileList = FXCollections.observableArrayList();
    public static ObservableList<RenderFileStatus> renderedFiles = FXCollections.observableArrayList();

	// Event Listener on MenuItem.onAction
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
    	Platform.exit();
    	System.exit(0);
    }

    // Event Listener on Menu.onAction
    /**
     * Opens the render statistics.
     * @throws SQLException
     */
    @FXML
    private void handleShowRenderStatistics()  throws IOException, SQLException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(ApplicationConstants.SIRRENDER_CLIENT_TITLE);
        alert.setHeaderText("Show Statistics");
        alert.setContentText("Author: Dale Kubler\nWebsite: http://www.culturesport.tv");

        alert.showAndWait();

    	pollServerStatus();
    }

	// Event Listener on MenuItem.onAction
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(ApplicationConstants.SIRRENDER_CLIENT_TITLE);
        alert.setHeaderText("About");
        alert.setContentText("Author: Dale Kubler\nWebsite: http://www.culturesport.tv");

        alert.showAndWait();
    }

    // Event Listener on Button[#btnBrowse1].onAction
	@FXML
	public void BrowseButtonAction(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button.onAction
	@FXML
	public void checkEvent(ActionEvent event) {
		// TODO Autogenerated
	}

    @FXML
	public void serversMouseClickAction(MouseEvent event) {
		try {
            TableViewSelectionModel<Server> selectionModel = tblServers.getSelectionModel();
            ObservableList selectedCells = selectionModel.getSelectedCells();
            // Get the first column in the table
            TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            // Get a single cell value
            // Get the row position and the column data at that position
            //Object val = tablePosition.getTableColumn().getCellData(tblServers.getSelectionModel().getSelectedIndex());
            // Get a specified cells value
            Object serverName = ((TableColumnBase) tblServers.getColumns().get(0)).getCellData(tblServers.getSelectionModel().getSelectedIndex());
            Object ipAddress = ((TableColumnBase) tblServers.getColumns().get(1)).getCellData(tblServers.getSelectionModel().getSelectedIndex());
			//MainApp.log.debug("tblServers serverName="+serverName);
			//MainApp.log.debug("tblServers ipAddress="+ipAddress);
            H2.readServerDataForDisplay(serverStatusData, (String)serverName, (String)ipAddress);
            displayServerStatusData(serverStatusData);
		} catch (IllegalArgumentException e) {
			;
		} catch (Exception e) {
			;
		}
	}
    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	/**
	 * Just add some sample data in the constructor.
	 */
    public ClientController() {
    	/*
    	serverRenderLogData.add(new ServerRenderLog("192.168.1.35", "Dale-HP","Completed processing \"V:\\SirRender\\tmpBlenderIn\\test2.blend\"","04/24/2017","01:16:36",4546));
    	serverRenderLogData.add(new ServerRenderLog("192.168.1.35", "Dale-HP","Started processing \"V:\\SirRender\\tmpBlenderIn\\test2.blend\"","04/24/2017","01:16:35",4545));
    	serverRenderLogData.add(new ServerRenderLog("192.168.1.35", "Dale-HP","Cancelled processing \"V:\\SirRender\\tmpBlenderIn\\test1.blend\"","04/24/2017","01:17:38",4548));
    	serverRenderLogData.add(new ServerRenderLog("192.168.1.35", "Dale-HP","Started processing \"V:\\SirRender\\tmpBlenderIn\\test1.blend\"","04/24/2017","01:17:37",4547));
    	*/
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
    private void initialize() {
    	try {
			MainApp.getServerListFromMasterServer();

			// Hide Server Status button (no longer needed)
			btnStatus1.setVisible(false);
			btnClear1.setDisable(true);
			btnAddToRenderQueue.setDisable(true);
			btnRemoveQueue1.setDisable(true);

			sliderRefreshRate.valueProperty().addListener(new ChangeListener<Object>() {
	            public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
	            	txtRefreshRate.textProperty().setValue(
	                        String.valueOf((int) sliderRefreshRate.getValue()));
	            	MainApp.refreshPeriod.set((int)sliderRefreshRate.getValue());
	            }
	        });
			txtRefreshRate.textProperty().setValue(MainApp.refreshPeriod.getValue().toString());

		    // Handle TextField enter key event.
			txtRefreshRate.setOnAction((event) -> {
		        MainApp.log.debug("txtRefreshRate="+txtRefreshRate.getText());
		        MainApp.refreshPeriod.set(Integer.valueOf(txtRefreshRate.getText()));
		    });
			
			// Initialize the Log Search IP Address list
			H2.getServerIpAddresses(renderLogIpAddressList);
			logSearchIpAddress.setItems(renderLogIpAddressList);
			
			// Initialize the Log Search Server Name list
		    H2.getServerNames(renderLogServerList);
		    logSearchServer.setItems(renderLogServerList);
		    
			// Initialize the Log Search File Name list
		    H2.getBlendFiles(renderLogBlendFileList);
		    logSearchBlendFile.setItems(renderLogBlendFileList);

		    // Initialize the Log Search Message Type list
		    logSearchMessage.setItems(FXCollections.observableArrayList(
		    		" ", "Started processing", "Completed processing", "Cancelled processing", "Processing terminated (kill switch)")
		    );
		    /*
		    logSearchMessage.getSelectionModel().selectedIndexProperty()
		    	.addListener(new ChangeListener<Number>() {
		    		public void changed(ObservableValue ov, Number value, Number new_value) {
		    		}
		    	});
 			*/

		    logSearchMessage.setTooltip(new Tooltip("Select the message type"));

			// Render Log Table
			// 0. Initialize the columns.
			colRenderLogDate.setCellValueFactory(cellData -> cellData.getValue().logDateProperty());
			colRenderLogTime.setCellValueFactory(cellData -> cellData.getValue().logTimeProperty());
			colRenderLogServerName.setCellValueFactory(cellData -> cellData.getValue().logServerNameProperty());
			colRenderLogIpAddress.setCellValueFactory(cellData -> cellData.getValue().logIpAddressProperty());
			colRenderLogMessage.setCellValueFactory(cellData -> cellData.getValue().logStatMsgProperty());

			// 1. Wrap the ObservableList in a FilteredList (initially display all data).
			FilteredList<ServerRenderLog> filteredRenderLogData = new FilteredList<>(serverRenderLogData, p -> true);

			/*
			// 2. Set the filter Predicate whenever the filter changes.
			filterField.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(person -> {
					// If filter text is empty, display all persons.
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}

					// Compare first name and last name of every person with filter text.
					String lowerCaseFilter = newValue.toLowerCase();

					if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches first name.
					} else if (person.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches last name.
					}
					return false; // Does not match.
				});
			});
			*/

			// 3. Wrap the FilteredList in a SortedList.
			SortedList<ServerRenderLog> sortedData = new SortedList<>(filteredRenderLogData);

			// 4. Bind the SortedList comparator to the TableView comparator.
			// 	  Otherwise, sorting the TableView would have no effect.
			sortedData.comparatorProperty().bind(tblRenderLog.comparatorProperty());

			// 5. Add sorted (and filtered) data to the table.
			tblRenderLog.setItems(sortedData);
			
			// 6. Set column resize policy.
			//tblRenderLog.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

/*
			logSearchDate.setOnAction(new EventHandler() {
				@Override
				public void handle(Event event) {
	                LocalDate date = logSearchDate.getValue();
	                System.out.println("Selected logSearchDate=" + date);
				}
	        });
*/
			
	    	/**********************************************
	    	 * Load the initial computers section
	    	 **********************************************
	    	 */
	    	// Get the current list of servers
			definedServerData = MainApp.getDefinedServerData();
//			H2.getServerListForDisplay(definedServerData, true);
			
	    	// Add the servers to the table
			tblServers.getItems().clear();
	    	tblServers.setItems(definedServerData);
	    	
	    	// Set the columns for display
	    	TableColumn<Server,String> colServerName = new TableColumn<Server,String>("Computer");
	    	colServerName.setCellValueFactory(new PropertyValueFactory<Server, String>("serverName"));
	    	TableColumn<Server,String> colServerIpAddress = new TableColumn<Server,String>("IP Address");
	    	colServerIpAddress.setCellValueFactory(new PropertyValueFactory<Server, String>("serverIpAddress"));
	    	colServerIpAddress.maxWidthProperty().bind(tblServers.widthProperty().multiply(.30));
	    	colServerIpAddress.minWidthProperty().bind(colServerIpAddress.maxWidthProperty());
	    	 
	    	tblServers.getColumns().setAll(colServerName, colServerIpAddress);
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setMainClientApp(mainApp);
    }

	public void OverrideRenderDeviceAction(ActionEvent event) {
		if (chkOverrideRenderDevice.isSelected()) {
			if (!radioCPU.isSelected() && !radioGPU.isSelected()) {
				radioCPU.setSelected(true);
				//radioCPU.requestFocus();
			}
		} else {
	    	radioCPU.setSelected(false);
	    	radioGPU.setSelected(false);
		}
	}

	public void BrowseButtonAction1(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(GlobalClass.getInitialDirectory()));
		fc.getExtensionFilters().addAll(new ExtensionFilter("Blender Files", "*.blend"));
		selectedFiles = fc.showOpenMultipleDialog(null);

		if (selectedFiles != null) {
			for (int i = 0; i < selectedFiles.size(); i++) {
				browseView1.getItems().add(selectedFiles.get(i).getAbsolutePath());
				//MainApp.log.debug( "selectedFilesPath="+selectedFiles.get(i).getPath());
				Path p = Paths.get(selectedFiles.get(i).getPath());
				Path folder = p.getParent();
				//MainApp.log.debug( "folder="+folder.toString());
				GlobalClass.setInitialDirectory(folder.toString());
				btnClear1.setDisable(false);
				btnAddToRenderQueue.setDisable(false);
			}
		} else {
			MainApp.log.debug("file is not valid");
		}
		selectedFiles = null;
		browseView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	public void ClearButtonAction1(ActionEvent event) {
		@SuppressWarnings("rawtypes")
		ObservableList selectedIndices = browseView1.getSelectionModel().getSelectedIndices();
    	//MainApp.log.debug( "ClearButtonAction1 after selectedIndices");

		List<String> fileList = new ArrayList<String>();
		List<String> removeList = new ArrayList<String>();

        for(Object i : selectedIndices){
            //MainApp.log.debug( "i = " + i);
			removeList.add((String) browseView1.getItems().get((int) i));
        }

        for(int i=0; i < removeList.size(); i++){
            //MainApp.log.debug( "i = " + i);
        	fileList.clear();
			String fileName = removeList.get(i);

			// Create new temporary list filtering out selected files
			for (int j = 0; j < browseView1.getItems().size(); j++) {
				//MainApp.log.debug( "fileName="+fileName);
				//MainApp.log.debug( "currentBrowseView(fileName)="+browseView1.getItems().get(j));
				if (!browseView1.getItems().get(j).equals(fileName)) {
					//MainApp.log.debug( "Adding currentBrowseView(fileName) to temp list");
					fileList.add((String) browseView1.getItems().get(j));
				}
			}
			// Clear existing list and replace with temporary list
			browseView1.getItems().clear();
			for (int j = 0; j < fileList.size(); j++) {
				browseView1.getItems().add(fileList.get(j));
			}
			if (fileList.size() <= 0) {
				btnClear1.setDisable(true);
				btnAddToRenderQueue.setDisable(true);
			}
        }
    	//MainApp.log.debug( "ClearButtonAction1 after for loop");
	}

	public void StatusButtonAction1(ActionEvent event) throws SQLException, ConnectException, IOException {
		pollServerStatus();
	}

	public void QueueButtonAction1(ActionEvent event) throws SQLException, ConnectException, IOException {

		ListView<String> listQueue = new ListView<String>();
		final Label labelQueue = new Label();
		ObservableList<String> dataQueue = FXCollections.observableArrayList();

		dataQueue = (ObservableList<String>) H2.readFileServerQueue(false);

		QueueView1.getItems().clear();
		QueueView1.getItems().addAll(dataQueue);
		QueueView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listQueue.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                        labelQueue.setText(new_val);
                        //label.setTextFill(Color.web(new_val));
                });

    	//MainApp.log.debug( "QueueButtonAction1 BEFORE IF STATEMENT QueueView1.getItems().size()="+QueueView1.getItems().size());
        if (QueueView1.getItems().size() == 1) {
        	if (QueueView1.getItems().get(0).isEmpty() || QueueView1.getItems().get(0).equals("")) {
        		//MainApp.log.debug( "QueueButtonAction1 dataQueue.isEmpty=true+1");
        		//MainApp.log.debug( "QueueButtonAction1 QueueView1.getItems().size()="+QueueView1.getItems().size());
        		btnRemoveQueue1.setDisable(true);
        	} else {
        		//MainApp.log.debug( "QueueButtonAction1 dataQueue.isEmpty=false+1");
            	//MainApp.log.debug( "QueueButtonAction1 QueueView1.getItems().size()="+QueueView1.getItems().size());
            	//MainApp.log.debug( "QueueButtonAction1 dataQueue.getItems().get(0)="+QueueView1.getItems().get(0));
    			btnRemoveQueue1.setDisable(false);
        	}
        } else {
        	//MainApp.log.debug( "QueueButtonAction1 dataQueue.isEmpty=false");
        	//MainApp.log.debug( "QueueButtonAction1 QueueView1.getItems().size()="+QueueView1.getItems().size());
			btnRemoveQueue1.setDisable(false);
        }
	}

	public void RemoveQueueButtonAction1(ActionEvent event) throws ConnectException, SQLException, IOException {
		int overrideOutputDir = 0;
		String overrideRenderDevice = "";
        ObservableList<?> selectedIndices = QueueView1.getSelectionModel().getSelectedIndices();
    	//MainApp.log.debug( "RemoveQueueButtonAction1 after selectedIndices");

		// Verify at least one server has selected
		Boolean serverSelected = false;
		if (chkServer11.isSelected() ||
			chkServer21.isSelected() ||
			chkServer31.isSelected() ||
			chkServer41.isSelected() ||
			chkServer51.isSelected() ||
			chkServer61.isSelected() ||
			chkServer71.isSelected() ||
			chkServer81.isSelected() ||
			chkServer91.isSelected() ||
			chkServer101.isSelected()) {
			serverSelected = true;
		}

		if (!serverSelected) {
			//Alert alert = new Alert(AlertType.ERROR, "No computers have been selected.\n\nSelect a computer to include in the render process.", ButtonType.OK);
			Alert alert = new Alert(AlertType.ERROR, "No computers have been selected.\n\nSelect a computer to remove its files from the queue.");
			alert.showAndWait();

			//if (alert.getResult() == ButtonType.YES) {
			    //do stuff
			//}
		} else {
	        for(Object i : selectedIndices){
	            //MainApp.log.debug( "i = " + i);
				String fileName = (String) QueueView1.getItems().get((int) i);
				if (fileName.contains(ApplicationConstants.OVERRIDE_OUTPUT_DIR)) {
					overrideOutputDir = 1;
					//MainApp.log.debug( "overrideOutputDir="+overrideOutputDir);
				} else {
					overrideOutputDir = 0;
					//MainApp.log.debug( "overrideOutputDir="+overrideOutputDir);
				}
				//MainApp.log.debug( "fileName1="+fileName);
				fileName = fileName.replace(ApplicationConstants.OVERRIDE_OUTPUT_DIR, "");
				MainApp.log.debug( "fileName="+fileName);

				if (fileName.contains(ApplicationConstants.OVERRIDE_RENDER_DEVICE_C)) {
					overrideRenderDevice = "C";
					fileName = fileName.replace(ApplicationConstants.OVERRIDE_RENDER_DEVICE_C, "");
					//MainApp.log.debug( "overrideRenderDevice="+overrideRenderDevice);
				} else if (fileName.contains(ApplicationConstants.OVERRIDE_RENDER_DEVICE_G)) {
					overrideRenderDevice = "G";
					fileName = fileName.replace(ApplicationConstants.OVERRIDE_RENDER_DEVICE_G, "");
					//MainApp.log.debug( "overrideRenderDevice="+overrideRenderDevice);
				} else {
					overrideRenderDevice = "D";
					//MainApp.log.debug( "overrideRenderDevice="+overrideRenderDevice);
				}
				//MainApp.log.debug( "fileName1="+fileName);
				MainApp.log.debug( "fileName="+fileName);

				if (chkServer11.isSelected() && !lblServer11.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer11.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer21.isSelected() && !lblServer21.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer21.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer31.isSelected() && !lblServer31.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer31.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer41.isSelected() && !lblServer41.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer41.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer51.isSelected() && !lblServer51.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer51.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer61.isSelected() && !lblServer61.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer61.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer71.isSelected() && !lblServer71.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer71.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer81.isSelected() && !lblServer81.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer81.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
				if (chkServer91.isSelected() && !lblServer91.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer91.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
/*				
				if (chkServer101.isSelected() && !lblServer101.getText().isEmpty()) {
					H2.deleteFileFromServerQueue(lblServer101.getText(), fileName, overrideOutputDir, overrideRenderDevice);
				}
*/				
	        }
		}
    	//MainApp.log.debug( "RemoveQueueButtonAction1 after for loop");
        QueueButtonAction1(event);
	}

	public void checkRenderEvent(ActionEvent event) throws SQLException, IOException {
		scheduleRenderEvent(event);
	}

	public void scheduleRenderEvent(ActionEvent event) throws SQLException, IOException {

		String fileName = null;
		int overrideOutputDir = 0;
		String overrideRenderDevice = "D";

		// Determine if internal Blender output path is to be overridden
		if (chkOverrideOutputPath.isSelected()) {
			overrideOutputDir = 1;
		}

		// Determine if internal Blender render device should be overridden
		if (chkOverrideRenderDevice.isSelected()) {
			if (radioCPU.isSelected()) {
				overrideRenderDevice = "C";
			} else {
				overrideRenderDevice = "G";
			}
		} else {
			overrideRenderDevice = "D";
		}

		// Verify at least one server has been selected
		Boolean serverSelected = false;
		if (chkServer11.isSelected() ||
			chkServer21.isSelected() ||
			chkServer31.isSelected() ||
			chkServer41.isSelected() ||
			chkServer51.isSelected() ||
			chkServer61.isSelected() ||
			chkServer71.isSelected() ||
			chkServer81.isSelected() ||
			chkServer91.isSelected() ||
			chkServer101.isSelected()) {
			serverSelected = true;
		}

        @SuppressWarnings("rawtypes")
		ObservableList selectedRenderIndices = browseView1.getSelectionModel().getSelectedIndices();

		// Verify at least one file has been selected
        if (selectedRenderIndices.isEmpty()) {
			//Alert alert = new Alert(AlertType.ERROR, "No computers have been selected.\n\nSelect a computer to include in the render process.", ButtonType.OK);
			Alert alert = new Alert(AlertType.ERROR, "No files have been selected.\n\nSelect a file to include in the render process.");
			alert.showAndWait();
        }

		if (!serverSelected) {
			//Alert alert = new Alert(AlertType.ERROR, "No computers have been selected.\n\nSelect a computer to include in the render process.", ButtonType.OK);
			Alert alert = new Alert(AlertType.ERROR, "No computers have been selected.\n\nSelect a computer to include in the render process.");
			alert.showAndWait();

			//if (alert.getResult() == ButtonType.YES) {
			    //do stuff
			//}
		} else {
			String[] selectedFileName = new String[browseView1.getItems().size()];
			int minCount = 0;
			int maxCount = 0;
			int i = 0;
			int j = 0;

			for(Object k : selectedRenderIndices) {
				selectedFileName[i] = (String) browseView1.getItems().get((int) k);
				//MainApp.log.debug( "i = " + i);
				//MainApp.log.debug( "selectedFileName[i] = " + selectedFileName[i]);
				i++;
			}

			if (chkAddNewFilesTopOfQueue.isSelected()) {
				minCount = --i;
				maxCount = 0;
				for (j = minCount;  j >= maxCount; j--) {
					fileName = selectedFileName[j];
		        	//MainApp.log.debug("fileName="+fileName);
		        	renderToAllSelectedFileServers(fileName, overrideOutputDir, overrideRenderDevice);
				}
			} else {
				minCount = 0;
				maxCount = --i;
				for(j = minCount;  j <= maxCount; j++) {
					fileName = selectedFileName[j];
		        	//MainApp.log.debug("fileName="+fileName);
		        	renderToAllSelectedFileServers(fileName, overrideOutputDir, overrideRenderDevice);
				}
			}
			// Refresh the screen with the current Queue
			QueueButtonAction1(event);

		}

	}

	public void renderToAllSelectedFileServers(String fileName, int overrideOutputDir, String overrideRenderDevice) {

		if (chkServer11.isSelected() && !lblServer11.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer11.isSelected()="+chkServer11.isSelected()+", lblServer11.getText().isEmpty()="+lblServer11.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer11.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer21.isSelected() && !lblServer21.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer21.isSelected()="+chkServer21.isSelected()+", lblServer21.getText().isEmpty()="+lblServer21.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer21.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer31.isSelected() && !lblServer31.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer31.isSelected()="+chkServer31.isSelected()+", lblServer31.getText().isEmpty()="+lblServer31.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer31.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer41.isSelected() && !lblServer41.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer41.isSelected()="+chkServer41.isSelected()+", lblServer41.getText().isEmpty()="+lblServer41.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer41.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer51.isSelected() && !lblServer51.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer51.isSelected()="+chkServer51.isSelected()+", lblServer51.getText().isEmpty()="+lblServer51.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer51.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer61.isSelected() && !lblServer61.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer61.isSelected()="+chkServer61.isSelected()+", lblServer61.getText().isEmpty()="+lblServer61.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer61.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer71.isSelected() && !lblServer71.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer71.isSelected()="+chkServer71.isSelected()+", lblServer71.getText().isEmpty()="+lblServer71.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer71.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer81.isSelected() && !lblServer81.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer81.isSelected()="+chkServer81.isSelected()+", lblServer81.getText().isEmpty()="+lblServer81.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer81.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
		if (chkServer91.isSelected() && !lblServer91.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer91.isSelected()="+chkServer91.isSelected()+", lblServer91.getText().isEmpty()="+lblServer91.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer91.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
/*
		if (chkServer101.isSelected() && !lblServer101.getText().isEmpty()) {
	        //MainApp.log.debug("chkServer101.isSelected()="+chkServer101.isSelected()+", lblServer101.getText().isEmpty()="+lblServer101.getText().isEmpty());
			try {
				H2.addFileToServerQueue(lblServer101.getText(), fileName, overrideOutputDir, chkAddNewFilesTopOfQueue.isSelected(), overrideRenderDevice);
			} catch (Exception e) {
				;
			}
		}
*/		
	}

	public void SuspendButtonAction1(ActionEvent event) throws SQLException {
    	if (!serverData.get(0).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer11.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer11.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender1.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(0).getServerName(), serverData.get(0).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction2(ActionEvent event) throws SQLException {
    	if (!serverData.get(1).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer21.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer21.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender2.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(1).getServerName(), serverData.get(1).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction3(ActionEvent event) throws SQLException {
    	if (!serverData.get(2).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer31.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer31.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender3.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(2).getServerName(), serverData.get(2).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction4(ActionEvent event) throws SQLException {
    	if (!serverData.get(3).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer41.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer41.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender4.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(3).getServerName(), serverData.get(3).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction5(ActionEvent event) throws SQLException {
    	if (!serverData.get(4).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer51.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer51.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender5.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(4).getServerName(), serverData.get(4).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction6(ActionEvent event) throws SQLException {
    	if (!serverData.get(5).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer61.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer61.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender6.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(5).getServerName(), serverData.get(5).getServerIpAddress(), killSwitch, suspend, background);
    	}
    }

	public void SuspendButtonAction7(ActionEvent event) throws SQLException {
    	if (!serverData.get(6).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer71.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer71.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender7.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(6).getServerName(), serverData.get(6).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction8(ActionEvent event) throws SQLException {
    	if (!serverData.get(7).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer81.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer81.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender8.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(7).getServerName(), serverData.get(7).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction9(ActionEvent event) throws SQLException {
    	if (!serverData.get(8).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer91.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer91.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender9.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(8).getServerName(), serverData.get(8).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void SuspendButtonAction10(ActionEvent event) throws SQLException {
    	if (!serverData.get(9).getServerIpAddress().isEmpty()) {
			int killSwitch = 0;
			int suspend = 0;
			int background = 0;
			if (chkKillSwitchServer101.isSelected()) {
				killSwitch = 1;
			}
			if (chkSuspendServer101.isSelected()) {
				suspend = 1;
			}
			if (chkBackgroundRender10.isSelected()) {
				background = 1;
			}
			H2.updateServerSuspendKillSwitchStatus(serverData.get(9).getServerName(), serverData.get(9).getServerIpAddress(), killSwitch, suspend, background);
    	}
	}

	public void checkAllServersEvent(ActionEvent event) throws SQLException {
		if (chkAllServers.isSelected()) {
			chkServer11.setSelected(true);
			chkServer21.setSelected(true);
			chkServer31.setSelected(true);
			chkServer41.setSelected(true);
			chkServer51.setSelected(true);
			chkServer61.setSelected(true);
			chkServer71.setSelected(true);
			chkServer81.setSelected(true);
			chkServer91.setSelected(true);
			chkServer101.setSelected(true);
		} else {
			chkServer11.setIndeterminate(false);
			chkServer11.setSelected(false);
			chkServer21.setIndeterminate(false);
			chkServer21.setSelected(false);
			chkServer31.setIndeterminate(false);
			chkServer31.setSelected(false);
			chkServer41.setIndeterminate(false);
			chkServer41.setSelected(false);
			chkServer51.setIndeterminate(false);
			chkServer51.setSelected(false);
			chkServer61.setIndeterminate(false);
			chkServer61.setSelected(false);
			chkServer71.setIndeterminate(false);
			chkServer71.setSelected(false);
			chkServer81.setIndeterminate(false);
			chkServer81.setSelected(false);
			chkServer91.setIndeterminate(false);
			chkServer91.setSelected(false);
			chkServer101.setIndeterminate(false);
			chkServer101.setSelected(false);
		}
	}

	public void checkAllKillEvent(ActionEvent event) throws SQLException {
		if (chkAllKill.isSelected()) {
			chkKillSwitchServer11.setSelected(true);
			SuspendButtonAction1(event);
			chkKillSwitchServer21.setSelected(true);
			SuspendButtonAction2(event);
			chkKillSwitchServer31.setSelected(true);
			SuspendButtonAction3(event);
			chkKillSwitchServer41.setSelected(true);
			SuspendButtonAction4(event);
			chkKillSwitchServer51.setSelected(true);
			SuspendButtonAction5(event);
			chkKillSwitchServer61.setSelected(true);
			SuspendButtonAction6(event);
			chkKillSwitchServer71.setSelected(true);
			SuspendButtonAction7(event);
			chkKillSwitchServer81.setSelected(true);
			SuspendButtonAction8(event);
			chkKillSwitchServer91.setSelected(true);
			SuspendButtonAction9(event);
			chkKillSwitchServer101.setSelected(true);
			SuspendButtonAction10(event);
		} else {
			chkKillSwitchServer11.setIndeterminate(false);
			chkKillSwitchServer11.setSelected(false);
			SuspendButtonAction1(event);
			chkKillSwitchServer21.setIndeterminate(false);
			chkKillSwitchServer21.setSelected(false);
			SuspendButtonAction2(event);
			chkKillSwitchServer31.setIndeterminate(false);
			chkKillSwitchServer31.setSelected(false);
			SuspendButtonAction3(event);
			chkKillSwitchServer41.setIndeterminate(false);
			chkKillSwitchServer41.setSelected(false);
			SuspendButtonAction4(event);
			chkKillSwitchServer51.setIndeterminate(false);
			chkKillSwitchServer51.setSelected(false);
			SuspendButtonAction5(event);
			chkKillSwitchServer61.setIndeterminate(false);
			chkKillSwitchServer61.setSelected(false);
			SuspendButtonAction6(event);
			chkKillSwitchServer71.setIndeterminate(false);
			chkKillSwitchServer71.setSelected(false);
			SuspendButtonAction7(event);
			chkKillSwitchServer81.setIndeterminate(false);
			chkKillSwitchServer81.setSelected(false);
			SuspendButtonAction8(event);
			chkKillSwitchServer91.setIndeterminate(false);
			chkKillSwitchServer91.setSelected(false);
			SuspendButtonAction9(event);
			chkKillSwitchServer101.setIndeterminate(false);
			chkKillSwitchServer101.setSelected(false);
			SuspendButtonAction10(event);
		}
	}

	public void checkAllSuspendEvent(ActionEvent event) throws SQLException {
		if (chkAllSuspend.isSelected()) {
			chkSuspendServer11.setSelected(true);
			SuspendButtonAction1(event);
			chkSuspendServer21.setSelected(true);
			SuspendButtonAction2(event);
			chkSuspendServer31.setSelected(true);
			SuspendButtonAction3(event);
			chkSuspendServer41.setSelected(true);
			SuspendButtonAction4(event);
			chkSuspendServer51.setSelected(true);
			SuspendButtonAction5(event);
			chkSuspendServer61.setSelected(true);
			SuspendButtonAction6(event);
			chkSuspendServer71.setSelected(true);
			SuspendButtonAction7(event);
			chkSuspendServer81.setSelected(true);
			SuspendButtonAction8(event);
			chkSuspendServer91.setSelected(true);
			SuspendButtonAction9(event);
			chkSuspendServer101.setSelected(true);
			SuspendButtonAction10(event);
		} else {
			chkSuspendServer11.setIndeterminate(false);
			chkSuspendServer11.setSelected(false);
			SuspendButtonAction1(event);
			chkSuspendServer21.setIndeterminate(false);
			chkSuspendServer21.setSelected(false);
			SuspendButtonAction2(event);
			chkSuspendServer31.setIndeterminate(false);
			chkSuspendServer31.setSelected(false);
			SuspendButtonAction3(event);
			chkSuspendServer41.setIndeterminate(false);
			chkSuspendServer41.setSelected(false);
			SuspendButtonAction4(event);
			chkSuspendServer51.setIndeterminate(false);
			chkSuspendServer51.setSelected(false);
			SuspendButtonAction5(event);
			chkSuspendServer61.setIndeterminate(false);
			chkSuspendServer61.setSelected(false);
			SuspendButtonAction6(event);
			chkSuspendServer71.setIndeterminate(false);
			chkSuspendServer71.setSelected(false);
			SuspendButtonAction7(event);
			chkSuspendServer81.setIndeterminate(false);
			chkSuspendServer81.setSelected(false);
			SuspendButtonAction8(event);
			chkSuspendServer91.setIndeterminate(false);
			chkSuspendServer91.setSelected(false);
			SuspendButtonAction9(event);
			chkSuspendServer101.setIndeterminate(false);
			chkSuspendServer101.setSelected(false);
			SuspendButtonAction10(event);
		}
	}

	public void checkAllBackgroundEvent(ActionEvent event) throws SQLException {
		if (chkAllBackground.isSelected()) {
			chkBackgroundRender1.setSelected(true);
			SuspendButtonAction1(event);
			chkBackgroundRender2.setSelected(true);
			SuspendButtonAction2(event);
			chkBackgroundRender3.setSelected(true);
			SuspendButtonAction3(event);
			chkBackgroundRender4.setSelected(true);
			SuspendButtonAction4(event);
			chkBackgroundRender5.setSelected(true);
			SuspendButtonAction5(event);
			chkBackgroundRender6.setSelected(true);
			SuspendButtonAction6(event);
			chkBackgroundRender7.setSelected(true);
			SuspendButtonAction7(event);
			chkBackgroundRender8.setSelected(true);
			SuspendButtonAction8(event);
			chkBackgroundRender9.setSelected(true);
			SuspendButtonAction9(event);
			chkBackgroundRender10.setSelected(true);
			SuspendButtonAction10(event);
		} else {
			chkBackgroundRender1.setIndeterminate(false);
			chkBackgroundRender1.setSelected(false);
			SuspendButtonAction1(event);
			chkBackgroundRender2.setIndeterminate(false);
			chkBackgroundRender2.setSelected(false);
			SuspendButtonAction2(event);
			chkBackgroundRender3.setIndeterminate(false);
			chkBackgroundRender3.setSelected(false);
			SuspendButtonAction3(event);
			chkBackgroundRender4.setIndeterminate(false);
			chkBackgroundRender4.setSelected(false);
			SuspendButtonAction4(event);
			chkBackgroundRender5.setIndeterminate(false);
			chkBackgroundRender5.setSelected(false);
			SuspendButtonAction5(event);
			chkBackgroundRender6.setIndeterminate(false);
			chkBackgroundRender6.setSelected(false);
			SuspendButtonAction6(event);
			chkBackgroundRender7.setIndeterminate(false);
			chkBackgroundRender7.setSelected(false);
			SuspendButtonAction7(event);
			chkBackgroundRender8.setIndeterminate(false);
			chkBackgroundRender8.setSelected(false);
			SuspendButtonAction8(event);
			chkBackgroundRender9.setIndeterminate(false);
			chkBackgroundRender9.setSelected(false);
			SuspendButtonAction9(event);
			chkBackgroundRender10.setIndeterminate(false);
			chkBackgroundRender10.setSelected(false);
			SuspendButtonAction10(event);
		}
	}

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param main
     */
    public void setMainClientApp(MainApp mainApp) {
        this.mainApp = mainApp;
		serverData = MainApp.getServerData();

        // Add button labels and IP addresses to the client screen checkboxes
        // Clear all labels
        chkServer11.setText("");
        chkServer21.setText("");
        chkServer31.setText("");
        chkServer41.setText("");
        chkServer51.setText("");
        chkServer61.setText("");
        chkServer71.setText("");
        chkServer81.setText("");
        chkServer91.setText("");
        chkServer101.setText("");
    	lblServer11.setText("");
    	lblServer21.setText("");
    	lblServer31.setText("");
    	lblServer41.setText("");
    	lblServer51.setText("");
    	lblServer61.setText("");
    	lblServer71.setText("");
    	lblServer81.setText("");
    	lblServer91.setText("");
    	lblServer101.setText("");
    	lblServerStatus11.setText("");
    	lblServerStatus21.setText("");
    	lblServerStatus31.setText("");
    	lblServerStatus41.setText("");
    	lblServerStatus51.setText("");
    	lblServerStatus61.setText("");
    	lblServerStatus71.setText("");
    	lblServerStatus81.setText("");
    	lblServerStatus91.setText("");
    	lblServerStatus101.setText("");
    	chkKillSwitchServer11.setText("");
    	chkKillSwitchServer21.setText("");
    	chkKillSwitchServer31.setText("");
    	chkKillSwitchServer41.setText("");
    	chkKillSwitchServer51.setText("");
    	chkKillSwitchServer61.setText("");
    	chkKillSwitchServer71.setText("");
    	chkKillSwitchServer81.setText("");
    	chkKillSwitchServer91.setText("");
    	chkKillSwitchServer101.setText("");
    	chkSuspendServer11.setText("");
    	chkSuspendServer21.setText("");
    	chkSuspendServer31.setText("");
    	chkSuspendServer41.setText("");
    	chkSuspendServer51.setText("");
    	chkSuspendServer61.setText("");
    	chkSuspendServer71.setText("");
    	chkSuspendServer81.setText("");
    	chkSuspendServer91.setText("");
    	chkSuspendServer101.setText("");
    	chkBackgroundRender1.setText("");
    	chkBackgroundRender2.setText("");
    	chkBackgroundRender3.setText("");
    	chkBackgroundRender4.setText("");
    	chkBackgroundRender5.setText("");
    	chkBackgroundRender6.setText("");
    	chkBackgroundRender7.setText("");
    	chkBackgroundRender8.setText("");
    	chkBackgroundRender9.setText("");
    	chkBackgroundRender10.setText("");

    	radioCPU.setToggleGroup(group);
    	radioGPU.setToggleGroup(group);
    	radioCPU.setSelected(false);
    	radioGPU.setSelected(false);

    	// Display current labels
    	try {
        	pollServerStatus();
    	} catch (Exception e)
    	{}

    	// Display Current Queue
    	try {
			QueueButtonAction1(new ActionEvent());
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// This is from the MonitorController
		sliderRefreshRate1.valueProperty().addListener(new ChangeListener<Object>() {
            public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
            	txtRefreshRate1.textProperty().setValue(
                        String.valueOf((int) sliderRefreshRate1.getValue()));
            	MainApp.refreshPeriod1.set((int)sliderRefreshRate1.getValue());
            }
        });
		txtRefreshRate1.textProperty().setValue(MainApp.refreshPeriod1.getValue().toString());
		
	    // Handle TextField enter key event.
		txtRefreshRate1.setOnAction((event) -> {
	        MainApp.log.debug("txtRefreshRate1="+txtRefreshRate1.getText());
	        MainApp.refreshPeriod1.set(Integer .valueOf(txtRefreshRate1.getText()));
	    });

		sliderRefreshRate2.valueProperty().addListener(new ChangeListener<Object>() {
            public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
            	txtRefreshRate2.textProperty().setValue(
                        String.valueOf((int) sliderRefreshRate2.getValue()));
            	MainApp.refreshPeriod2.set((int)sliderRefreshRate2.getValue());
            }
        });
		txtRefreshRate2.textProperty().setValue(MainApp.refreshPeriod2.getValue().toString());
		
	    // Handle TextField enter key event.
		txtRefreshRate2.setOnAction((event) -> {
	        MainApp.log.debug("txtRefreshRate2="+txtRefreshRate2.getText());
	        MainApp.refreshPeriod2.set(Integer.valueOf(txtRefreshRate2.getText()));
	    });    
		
    	/**********************************************
    	 * Load the initial rendered files section
    	 **********************************************
    	 */
		renderedFiles = MainApp.getRenderFileStatusData();

    	// Get the current list of rendering computers
		tblRenderingFiles.getItems().clear();
    	// Add the servers to the table
		tblRenderingFiles.setItems(renderedFiles);
    	
    	// Set the columns for display
    	TableColumn<Server,String> colRenderFile = new TableColumn<Server,String>("Rendered File");
    	colRenderFile.setCellValueFactory(new PropertyValueFactory("fileName"));
    	TableColumn<Server,String> colRenderFileStatus = new TableColumn<Server,String>("Status");
    	colRenderFileStatus.setCellValueFactory(new PropertyValueFactory("renderStatus"));
    	colRenderFileStatus.maxWidthProperty().bind(tblRenderingFiles.widthProperty().multiply(.22));
    	colRenderFileStatus.minWidthProperty().bind(colRenderFileStatus.maxWidthProperty());
    	 
    	tblRenderingFiles.getColumns().setAll(colRenderFile, colRenderFileStatus);    	
    	
    	/******************************************************
    	 * Create the initial computers rendering files section
    	 ******************************************************
    	 */
    	// Get the current list of rendering computers
    	tblComputersStatus.getItems().clear();

    	// Set the columns for display
    	TableColumn<Server,String> colRenderComputer = new TableColumn<Server,String>("Computer");
    	colRenderComputer.setCellValueFactory(new PropertyValueFactory("serverName"));
    	TableColumn<Server,String> colRenderComputerStatus = new TableColumn<Server,String>("Status");
    	colRenderComputerStatus.setCellValueFactory(new PropertyValueFactory("renderStatus"));
    	colRenderComputerStatus.maxWidthProperty().bind(tblComputersStatus.widthProperty().multiply(.40));
    	colRenderComputerStatus.minWidthProperty().bind(colRenderComputerStatus.maxWidthProperty());
    	 
    	tblComputersStatus.getColumns().setAll(colRenderComputer, colRenderComputerStatus);    	
    	
    	/**********************************************
    	 * Load the initial computers section
    	 **********************************************
    	 */
		//serverData = MainApp.getServerData();

    	// Get the current list of rendering computers
		tblComputers.getItems().clear();
    	// Add the servers to the table
    	tblComputers.setItems(serverData);
    	
    	// Set the columns for display
    	TableColumn<Server,String> colComputer = new TableColumn<Server,String>("Computer");
    	colComputer.setCellValueFactory(new PropertyValueFactory("serverName"));
    	TableColumn<Server,String> colIpAddress = new TableColumn<Server,String>("IP Address");
    	colIpAddress.setCellValueFactory(new PropertyValueFactory("serverIpAddress"));
    	colIpAddress.maxWidthProperty().bind(tblComputers.widthProperty().multiply(.30));
    	colIpAddress.minWidthProperty().bind(colIpAddress.maxWidthProperty());
    	 
    	tblComputers.getColumns().setAll(colComputer, colIpAddress);    	

    	/*******************************************
    	 * Create the initial computer files section
    	 *******************************************
    	 */
    	// Get the current list of rendering computers
    	tblFilesStatus.getItems().clear();

    	// Set the columns for display
    	TableColumn<Server,String> colFile = new TableColumn<Server,String>("Files");
    	colFile.setCellValueFactory(new PropertyValueFactory("fileName"));
    	TableColumn<Server,String> colStatus = new TableColumn<Server,String>("Status");
    	colStatus.setCellValueFactory(new PropertyValueFactory("renderStatus"));
        colStatus.maxWidthProperty().bind(tblFilesStatus.widthProperty().multiply(.20));
        colStatus.minWidthProperty().bind(colStatus.maxWidthProperty());
    	 
    	tblFilesStatus.getColumns().setAll(colFile, colStatus);    	
    	
    }

    @SuppressWarnings("rawtypes")
	@FXML
	public void renderingFilesMouseClickAction(MouseEvent event) {
		try {
			TableViewSelectionModel selectionModel = tblRenderingFiles.getSelectionModel();
            ObservableList selectedCells = selectionModel.getSelectedCells();
            // Get the first column in the table
			TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            // Get the row position and the column data at that position
            // Get a single cell value
            //Object val = tablePosition.getTableColumn().getCellData(tblRenderingFiles.getSelectionModel().getSelectedIndex());
            // Get a specified cells value
            Object fileName = ((TableColumnBase) tblRenderingFiles.getColumns().get(0)).getCellData(tblRenderingFiles.getSelectionModel().getSelectedIndex());
            //MainApp.log.debug("tblRenderingFiles fileName="+fileName);
            MainApp.getRenderedFileComputerListFromMasterServer(false, (String)fileName, tblComputersStatus);
        	// Set tooltip to represent the selected fileName
        	tblComputersStatus.setTooltip(new Tooltip((String)fileName));
		} catch (IllegalArgumentException e) {
			;
		} catch (Exception e) {
			;
		}
	}

    @FXML
	public void computersStatusMouseClickAction(MouseEvent event) {
		try {
			//MainApp.log.debug("I mouse clicked on an item in the tableview and this is it using the new computersStatusMouseClickAction method");
			MainApp.log.debug(tblComputersStatus.getSelectionModel().getSelectedItem().toString());
		} catch (IllegalArgumentException e) {
			;
		} catch (Exception e) {
			;
		}
	}
    
    @FXML
	public void computersMouseClickAction(MouseEvent event) {
		try {
            TableViewSelectionModel selectionModel = tblComputers.getSelectionModel();
            ObservableList selectedCells = selectionModel.getSelectedCells();
            // Get the first column in the table
            TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            // Get a single cell value
            // Get the row position and the column data at that position
            //Object val = tablePosition.getTableColumn().getCellData(tblComputers.getSelectionModel().getSelectedIndex());
            // Get a specified cells value
            Object computerName = ((TableColumnBase) tblComputers.getColumns().get(0)).getCellData(tblComputers.getSelectionModel().getSelectedIndex());
            Object ipAddress = ((TableColumnBase) tblComputers.getColumns().get(1)).getCellData(tblComputers.getSelectionModel().getSelectedIndex());
			//MainApp.log.debug("tblComputers ipAddress="+ipAddress);
            MainApp.getComputerIpAllFileListFromMasterServer((String)ipAddress, tblFilesStatus);
        	// Set tooltip to represent the selected fileName
            tblFilesStatus.setTooltip(new Tooltip((String)computerName+" ("+(String)ipAddress+")"));
		} catch (IllegalArgumentException e) {
			;
		} catch (Exception e) {
			;
		}
	}
    
    @FXML
	public void filesStatusMouseClickAction(MouseEvent event) {
		try {
			//MainApp.log.debug("I mouse clicked on an item in the tableview and this is it using the new filesStatusMouseClickAction method");
			MainApp.log.debug(tblFilesStatus.getSelectionModel().getSelectedItem().toString());
		} catch (IllegalArgumentException e) {
			;
		} catch (Exception e) {
			;
		}
	}
    
	public void RefreshButtonAction(ActionEvent event) throws SQLException, ConnectException, IOException {
		try {
			MainApp.getServerListFromMasterServerForMonitor();
			MainApp.getRenderedFileStatusListFromMasterServer(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pollServerStatus () throws IOException, ConnectException, SQLException {
    	//MainApp.log.debug("In pollServerStatus");
    	//MainApp.log.debug("chkServer11.getText()="+chkServer11.getText());
    	//MainApp.log.debug("lblServer11.getText()="+lblServer11.getText());
    	//MainApp.log.debug("portNumber="+portNumber);

    	// Determine who the Master Server is in case it changed
        try {
			//GlobalClass.setServerMasterIpAddress(H2.getMasterServerIpAddress());
			//H2.getMasterServerIpAddressFromLocal();
			H2.getMasterServerIpAddressFromFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	// Refresh the server list
    	MainApp.getServerListFromMasterServer();

    	// Display the server results
    	if (serverData.get(0).getServerIpAddress().isEmpty()) {
	    	lblServer11.setText("");
    		chkServer11.setVisible(false);
    		chkKillSwitchServer11.setVisible(false);
    		chkSuspendServer11.setVisible(false);
    		chkBackgroundRender1.setVisible(false);
    	} else {
    		chkServer11.setVisible(true);
    		chkKillSwitchServer11.setVisible(true);
    		chkSuspendServer11.setVisible(true);
    		chkBackgroundRender1.setVisible(true);

	    	chkServer11.setText(serverData.get(0).getServerName());
	    	lblServer11.setText(serverData.get(0).getServerIpAddress());
	    	lblServerStatus11.setText(serverData.get(0).getServerAvailable());
	    	if (!serverData.get(0).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer11.setText("Kill Switch");
	    		chkSuspendServer11.setText("Suspend");
	    		chkBackgroundRender1.setText("Render in Background");
	    	} else {
		    	lblServer11.setText("");
	    		chkKillSwitchServer11.setText("");
	    		chkSuspendServer11.setText("");
	    		chkBackgroundRender1.setText("");
	    	}
	    	if (serverData.get(0).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer11.setSelected(true);
	    	} else {
	    		chkKillSwitchServer11.setSelected(false);
	    	}
	    	if (serverData.get(0).getServerSuspend().equals("1")) {
	    		chkSuspendServer11.setSelected(true);
	    	} else {
	    		chkSuspendServer11.setSelected(false);
	    	}
	    	if (serverData.get(0).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender1.setSelected(true);
	    	} else {
	    		chkBackgroundRender1.setSelected(false);
	    	}
    	}

    	if (serverData.get(1).getServerIpAddress().isEmpty()) {
	    	lblServer21.setText("");
    		chkServer21.setVisible(false);
    		chkKillSwitchServer21.setVisible(false);
    		chkSuspendServer21.setVisible(false);
    		chkBackgroundRender2.setVisible(false);
    	} else {
    		chkServer21.setVisible(true);
    		chkKillSwitchServer21.setVisible(true);
    		chkSuspendServer21.setVisible(true);
    		chkBackgroundRender2.setVisible(true);

	    	chkServer21.setText(serverData.get(1).getServerName());
	    	lblServer21.setText(serverData.get(1).getServerIpAddress());
	    	lblServerStatus21.setText(serverData.get(1).getServerAvailable());
	    	if (!serverData.get(1).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer21.setText("Kill Switch");
	    		chkSuspendServer21.setText("Suspend");
	    		chkBackgroundRender2.setText("Render in Background");
	    	} else {
		    	lblServer21.setText("");
	    		chkKillSwitchServer21.setText("");
	    		chkSuspendServer21.setText("");
	    		chkBackgroundRender2.setText("");
	    	}
	    	if (serverData.get(1).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer21.setSelected(true);
	    	} else {
	    		chkKillSwitchServer21.setSelected(false);
	    	}
	    	if (serverData.get(1).getServerSuspend().equals("1")) {
	    		chkSuspendServer21.setSelected(true);
	    	} else {
	    		chkSuspendServer21.setSelected(false);
	    	}
	    	if (serverData.get(1).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender2.setSelected(true);
	    	} else {
	    		chkBackgroundRender2.setSelected(false);
	    	}
    	}

    	if (serverData.get(2).getServerIpAddress().isEmpty()) {
	    	lblServer31.setText("");
    		chkServer31.setVisible(false);
    		chkKillSwitchServer31.setVisible(false);
    		chkSuspendServer31.setVisible(false);
    		chkBackgroundRender3.setVisible(false);
    	} else {
    		chkServer31.setVisible(true);
    		chkKillSwitchServer31.setVisible(true);
    		chkSuspendServer31.setVisible(true);
    		chkBackgroundRender3.setVisible(true);

	    	chkServer31.setText(serverData.get(2).getServerName());
	    	lblServer31.setText(serverData.get(2).getServerIpAddress());
	    	lblServerStatus31.setText(serverData.get(2).getServerAvailable());
	    	if (!serverData.get(2).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer31.setText("Kill Switch");
	    		chkSuspendServer31.setText("Suspend");
	    		chkBackgroundRender3.setText("Render in Background");
	    	} else {
		    	lblServer31.setText("");
	    		chkKillSwitchServer31.setText("");
	    		chkSuspendServer31.setText("");
	    		chkBackgroundRender3.setText("");
	    	}
	    	if (serverData.get(2).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer31.setSelected(true);
	    	} else {
	    		chkKillSwitchServer31.setSelected(false);
	    	}
	    	if (serverData.get(2).getServerSuspend().equals("1")) {
	    		chkSuspendServer31.setSelected(true);
	    	} else {
	    		chkSuspendServer31.setSelected(false);
	    	}
	    	if (serverData.get(2).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender3.setSelected(true);
	    	} else {
	    		chkBackgroundRender3.setSelected(false);
	    	}
    	}

    	if (serverData.get(3).getServerIpAddress().isEmpty()) {
	    	lblServer41.setText("");
    		chkServer41.setVisible(false);
    		chkKillSwitchServer41.setVisible(false);
    		chkSuspendServer41.setVisible(false);
    		chkBackgroundRender4.setVisible(false);
    	} else {
    		chkServer41.setVisible(true);
    		chkKillSwitchServer41.setVisible(true);
    		chkSuspendServer41.setVisible(true);
    		chkBackgroundRender4.setVisible(true);

	    	chkServer41.setText(serverData.get(3).getServerName());
	    	lblServer41.setText(serverData.get(3).getServerIpAddress());
	    	lblServerStatus41.setText(serverData.get(3).getServerAvailable());
	    	if (!serverData.get(3).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer41.setText("Kill Switch");
	    		chkSuspendServer41.setText("Suspend");
	    		chkBackgroundRender4.setText("Render in Background");
	    	} else {
		    	lblServer41.setText("");
	    		chkKillSwitchServer41.setText("");
	    		chkSuspendServer41.setText("");
	    		chkBackgroundRender4.setText("");
	    	}
	    	if (serverData.get(3).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer41.setSelected(true);
	    	} else {
	    		chkKillSwitchServer41.setSelected(false);
	    	}
	    	if (serverData.get(3).getServerSuspend().equals("1")) {
	    		chkSuspendServer41.setSelected(true);
	    	} else {
	    		chkSuspendServer41.setSelected(false);
	    	}
	    	if (serverData.get(3).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender4.setSelected(true);
	    	} else {
	    		chkBackgroundRender4.setSelected(false);
	    	}
    	}

    	if (serverData.get(4).getServerIpAddress().isEmpty()) {
	    	lblServer51.setText("");
    		chkServer51.setVisible(false);
    		chkKillSwitchServer51.setVisible(false);
    		chkSuspendServer51.setVisible(false);
    		chkBackgroundRender5.setVisible(false);
    	} else {
    		chkServer51.setVisible(true);
    		chkKillSwitchServer51.setVisible(true);
    		chkSuspendServer51.setVisible(true);
    		chkBackgroundRender5.setVisible(true);

	    	chkServer51.setText(serverData.get(4).getServerName());
	    	lblServer51.setText(serverData.get(4).getServerIpAddress());
	    	lblServerStatus51.setText(serverData.get(4).getServerAvailable());
	    	if (!serverData.get(4).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer51.setText("Kill Switch");
	    		chkSuspendServer51.setText("Suspend");
	    		chkBackgroundRender5.setText("Render in Background");
	    	} else {
		    	lblServer51.setText("");
	    		chkKillSwitchServer51.setText("");
	    		chkSuspendServer51.setText("");
	    		chkBackgroundRender5.setText("");
	    	}
	    	if (serverData.get(4).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer51.setSelected(true);
	    	} else {
	    		chkKillSwitchServer51.setSelected(false);
	    	}
	    	if (serverData.get(4).getServerSuspend().equals("1")) {
	    		chkSuspendServer51.setSelected(true);
	    	} else {
	    		chkSuspendServer51.setSelected(false);
	    	}
	    	if (serverData.get(4).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender5.setSelected(true);
	    	} else {
	    		chkBackgroundRender5.setSelected(false);
	    	}
    	}

    	if (serverData.get(5).getServerIpAddress().isEmpty()) {
	    	lblServer61.setText("");
    		chkServer61.setVisible(false);
    		chkKillSwitchServer61.setVisible(false);
    		chkSuspendServer61.setVisible(false);
    		chkBackgroundRender6.setVisible(false);
    	} else {
    		chkServer61.setVisible(true);
    		chkKillSwitchServer61.setVisible(true);
    		chkSuspendServer61.setVisible(true);
    		chkBackgroundRender6.setVisible(true);

	    	chkServer61.setText(serverData.get(5).getServerName());
	    	lblServer61.setText(serverData.get(5).getServerIpAddress());
	    	lblServerStatus61.setText(serverData.get(5).getServerAvailable());
	    	if (!serverData.get(5).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer61.setText("Kill Switch");
	    		chkSuspendServer61.setText("Suspend");
	    		chkBackgroundRender6.setText("Render in Background");
	    	} else {
		    	lblServer61.setText("");
	    		chkKillSwitchServer61.setText("");
	    		chkSuspendServer61.setText("");
	    		chkBackgroundRender6.setText("");
	    	}
	    	if (serverData.get(5).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer61.setSelected(true);
	    	} else {
	    		chkKillSwitchServer61.setSelected(false);
	    	}
	    	if (serverData.get(5).getServerSuspend().equals("1")) {
	    		chkSuspendServer61.setSelected(true);
	    	} else {
	    		chkSuspendServer61.setSelected(false);
	    	}
	    	if (serverData.get(5).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender6.setSelected(true);
	    	} else {
	    		chkBackgroundRender6.setSelected(false);
	    	}
    	}

    	if (serverData.get(6).getServerIpAddress().isEmpty()) {
	    	lblServer71.setText("");
    		chkServer71.setVisible(false);
    		chkKillSwitchServer71.setVisible(false);
    		chkSuspendServer71.setVisible(false);
    		chkBackgroundRender7.setVisible(false);
    	} else {
    		chkServer71.setVisible(true);
    		chkKillSwitchServer71.setVisible(true);
    		chkSuspendServer71.setVisible(true);
    		chkBackgroundRender7.setVisible(true);

    		chkServer71.setText(serverData.get(6).getServerName());
	    	lblServer71.setText(serverData.get(6).getServerIpAddress());
	    	lblServerStatus71.setText(serverData.get(6).getServerAvailable());
	    	if (!serverData.get(6).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer71.setText("Kill Switch");
	    		chkSuspendServer71.setText("Suspend");
	    		chkBackgroundRender7.setText("Render in Background");
	    	} else {
		    	lblServer71.setText("");
	    		chkKillSwitchServer71.setText("");
	    		chkSuspendServer71.setText("");
	    		chkBackgroundRender7.setText("");
	    	}
	    	if (serverData.get(6).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer71.setSelected(true);
	    	} else {
	    		chkKillSwitchServer71.setSelected(false);
	    	}
	    	if (serverData.get(6).getServerSuspend().equals("1")) {
	    		chkSuspendServer71.setSelected(true);
	    	} else {
	    		chkSuspendServer71.setSelected(false);
	    	}
	    	if (serverData.get(6).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender7.setSelected(true);
	    	} else {
	    		chkBackgroundRender7.setSelected(false);
	    	}
    	}

    	if (serverData.get(7).getServerIpAddress().isEmpty()) {
	    	lblServer81.setText("");
    		chkServer81.setVisible(false);
    		chkKillSwitchServer81.setVisible(false);
    		chkSuspendServer81.setVisible(false);
    		chkBackgroundRender8.setVisible(false);
    	} else {
    		chkServer81.setVisible(true);
    		chkKillSwitchServer81.setVisible(true);
    		chkSuspendServer81.setVisible(true);
    		chkBackgroundRender8.setVisible(true);

	    	chkServer81.setText(serverData.get(7).getServerName());
	    	lblServer81.setText(serverData.get(7).getServerIpAddress());
	    	lblServerStatus81.setText(serverData.get(7).getServerAvailable());
	    	if (!serverData.get(7).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer81.setText("Kill Switch");
	    		chkSuspendServer81.setText("Suspend");
	    		chkBackgroundRender8.setText("Render in Background");
	    	} else {
		    	lblServer81.setText("");
	    		chkKillSwitchServer81.setText("");
	    		chkSuspendServer81.setText("");
	    		chkBackgroundRender8.setText("");
	    	}
	    	if (serverData.get(7).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer81.setSelected(true);
	    	} else {
	    		chkKillSwitchServer81.setSelected(false);
	    	}
	    	if (serverData.get(7).getServerSuspend().equals("1")) {
	    		chkSuspendServer81.setSelected(true);
	    	} else {
	    		chkSuspendServer81.setSelected(false);
	    	}
	    	if (serverData.get(7).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender8.setSelected(true);
	    	} else {
	    		chkBackgroundRender8.setSelected(false);
	    	}
    	}

    	if (serverData.get(8).getServerIpAddress().isEmpty()) {
	    	lblServer91.setText("");
    		chkServer91.setVisible(false);
    		chkKillSwitchServer91.setVisible(false);
    		chkSuspendServer91.setVisible(false);
    		chkBackgroundRender9.setVisible(false);
    	} else {
    		chkServer91.setVisible(true);
    		chkKillSwitchServer91.setVisible(true);
    		chkSuspendServer91.setVisible(true);
    		chkBackgroundRender9.setVisible(true);

	    	chkServer91.setText(serverData.get(8).getServerName());
	    	lblServer91.setText(serverData.get(8).getServerIpAddress());
	    	lblServerStatus91.setText(serverData.get(8).getServerAvailable());
	    	if (!serverData.get(8).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer91.setText("Kill Switch");
	    		chkSuspendServer91.setText("Suspend");
	    		chkBackgroundRender9.setText("Render in Background");
	    	} else {
		    	lblServer91.setText("");
	    		chkKillSwitchServer91.setText("");
	    		chkSuspendServer91.setText("");
	    		chkBackgroundRender9.setText("");
	    	}
	    	if (serverData.get(8).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer91.setSelected(true);
	    	} else {
	    		chkKillSwitchServer91.setSelected(false);
	    	}
	    	if (serverData.get(8).getServerSuspend().equals("1")) {
	    		chkSuspendServer91.setSelected(true);
	    	} else {
	    		chkSuspendServer91.setSelected(false);
	    	}
	    	if (serverData.get(8).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender9.setSelected(true);
	    	} else {
	    		chkBackgroundRender9.setSelected(false);
	    	}
    	}

    	if (serverData.get(9).getServerIpAddress().isEmpty()) {
	    	lblServer101.setText(" ");
    		chkServer101.setVisible(false);
    		chkKillSwitchServer101.setVisible(false);
    		chkSuspendServer101.setVisible(false);
    		chkBackgroundRender10.setVisible(false);
    	} else {
    		chkServer101.setVisible(true);
    		chkKillSwitchServer101.setVisible(true);
    		chkSuspendServer101.setVisible(true);
    		chkBackgroundRender10.setVisible(true);

	    	chkServer101.setText(serverData.get(9).getServerName());
	    	lblServer101.setText(serverData.get(9).getServerIpAddress());
	    	lblServerStatus101.setText(serverData.get(9).getServerAvailable());
	    	if (!serverData.get(9).getServerIpAddress().isEmpty()) {
	    		chkKillSwitchServer101.setText("Kill Switch");
	    		chkSuspendServer101.setText("Suspend");
	    		chkBackgroundRender10.setText("Render in Background");
	    	} else {
		    	lblServer101.setText("");
	    		chkKillSwitchServer101.setText("");
	    		chkSuspendServer101.setText("");
	    		chkBackgroundRender10.setText("");
	    	}
	    	if (serverData.get(9).getServerKillSwitch().equals("1")) {
	    		chkKillSwitchServer101.setSelected(true);
	    	} else {
	    		chkKillSwitchServer101.setSelected(false);
	    	}
	    	if (serverData.get(9).getServerSuspend().equals("1")) {
	    		chkSuspendServer101.setSelected(true);
	    	} else {
	    		chkSuspendServer101.setSelected(false);
	    	}
	    	if (serverData.get(9).getServerBackgroundRender().equals("1")) {
	    		chkBackgroundRender10.setSelected(true);
	    	} else {
	    		chkBackgroundRender10.setSelected(false);
	    	}
    	}
    }

	public void RenderLogResetAction(ActionEvent event) throws SQLException, ConnectException, IOException 
	{
		// Handle Start DatePicker on Render Log tab
		logSearchStartDate.setValue(null);
		
		// Handle End DatePicker on Render Log tab
		logSearchEndDate.setValue(null);
		
		// Handle Server on Render Log tab
		logSearchServer.setValue(null);
		
		// Handle IP Address on Render Log tab
		logSearchIpAddress.setValue(null);
		
		// Handle Message on Render Log tab
		logSearchMessage.setValue(null);

		// Handle Blend File on Render Log tab
		logSearchBlendFile.setValue(null);
	}

	public void RenderLogSearchAction(ActionEvent event) throws SQLException, ConnectException, IOException 
	{
		// Handle Start DatePicker on Render Log tab
		if (logSearchStartDate.getValue() == null) {
			//logSearchStartDate.setValue(Utils.NOW_LOCAL_DATE());
			logSearchStartDate.setValue(LocalDate.now());
		}
		String logStartDate = logSearchStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//System.out.println("logStartDate="+logStartDate);
		
		// Handle End DatePicker on Render Log tab
		if (logSearchEndDate.getValue() == null) {
			//logSearchEndDate.setValue(Utils.NOW_LOCAL_DATE());
			//logSearchEndDate.setValue(LocalDate.now());
			logSearchEndDate.setValue(logSearchStartDate.getValue());
		}
		String logEndDate = logSearchEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//System.out.println("logEndDate="+logEndDate);
		
		// Handle Server on Render Log tab
		String logServerName = null;
		if (logSearchServer.getValue() == null) {
			logServerName = " ";			
		} else {
			logServerName = logSearchServer.getValue().toString();
		}
		//System.out.println("logServerName="+logServerName);
		
		// Handle IP Address on Render Log tab
		String logIpAddress = null;
		if (logSearchIpAddress.getValue() == null) {
			logIpAddress = " ";			
		} else {
			logIpAddress = logSearchIpAddress.getValue().toString();
		}
		//System.out.println("logIpAddress="+logIpAddress);
		
		// Handle Message on Render Log tab
		String logMessage = null;
		if (logSearchMessage.getValue() == null) {
			logMessage = " ";			
		} else {
			logMessage = logSearchMessage.getValue().toString();
		}
		//System.out.println("logMessage="+logMessage);

		// Handle Blend File on Render Log tab
		String logBlendFile = null;
		if (logSearchBlendFile.getValue() == null) {
			logBlendFile = " ";			
		} else {
			logBlendFile = logSearchBlendFile.getValue().toString();
		}
		System.out.println("logBlendFile="+logBlendFile);

		H2.readServerRenderLogForDisplay(serverRenderLogData, logStartDate, logEndDate, logServerName, logIpAddress, logMessage, logBlendFile);
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<ServerRenderLog> filteredRenderLogData = new FilteredList<>(serverRenderLogData, p -> true);

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<ServerRenderLog> sortedData = new SortedList<>(filteredRenderLogData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tblRenderLog.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tblRenderLog.setItems(sortedData);
	}

	public void RenderLogExportCSVAction(ActionEvent event) throws SQLException, ConnectException, IOException {
		// Handle Start DatePicker on Render Log tab
		if (logSearchStartDate.getValue() == null) {
			//logSearchStartDate.setValue(Utils.NOW_LOCAL_DATE());
			logSearchStartDate.setValue(LocalDate.now());
		}
		String logStartDate = logSearchStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//System.out.println("logStartDate="+logStartDate);
		
		// Handle End DatePicker on Render Log tab
		if (logSearchEndDate.getValue() == null) {
			//logSearchEndDate.setValue(Utils.NOW_LOCAL_DATE());
			//logSearchEndDate.setValue(LocalDate.now());
			logSearchEndDate.setValue(logSearchStartDate.getValue());
		}
		String logEndDate = logSearchEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//System.out.println("logEndDate="+logEndDate);
		
		// Handle Server on Render Log tab
		String logServerName = null;
		if (logSearchServer.getValue() == null) {
			logServerName = " ";			
		} else {
			logServerName = logSearchServer.getValue().toString();
		}
		//System.out.println("logServerName="+logServerName);
		
		// Handle IP Address on Render Log tab
		String logIpAddress = null;
		if (logSearchIpAddress.getValue() == null) {
			logIpAddress = " ";			
		} else {
			logIpAddress = logSearchIpAddress.getValue().toString();
		}
		//System.out.println("logIpAddress="+logIpAddress);
		
		// Handle Message on Render Log tab
		String logMessage = null;
		if (logSearchMessage.getValue() == null) {
			logMessage = " ";			
		} else {
			logMessage = logSearchMessage.getValue().toString();
		}
		//System.out.println("logMessage="+logMessage);

		// Handle Blend File on Render Log tab
		String logBlendFile = null;
		if (logSearchBlendFile.getValue() == null) {
			logBlendFile = " ";			
		} else {
			logBlendFile = logSearchBlendFile.getValue().toString();
		}
		System.out.println("logBlendFile="+logBlendFile);

		FileChooser fc = new FileChooser();
		File savedFile = null;
		String csvFileName = null;

		fc.setTitle("Export to CSV file");
		fc.setInitialFileName(ApplicationConstants.DEFAULT_SERVERLOG_CSV_FILE_NAME);
		fc.setInitialDirectory(new File(ApplicationConstants.DEFAULT_SERVERLOG_CSV_DIRECTORY));
		fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
		savedFile = fc.showSaveDialog(savedStage);

		savedStage = MainApp.getPrimaryStage();
		
		if (savedFile != null) {
			csvFileName = savedFile.toString();
		}
		System.out.println("logBlendFile="+logBlendFile);

		H2.exportServerRenderLogToCsvFile(serverRenderLogData, logStartDate, logEndDate, logServerName, logIpAddress, logMessage, logBlendFile, csvFileName);
	}
	
	public void ServerStatusNewAction(ActionEvent event) throws SQLException, ConnectException, IOException 
	{
		serverName.setText("");
		ipAddress.setText("");
		serverStatus.setText("");
		lastStatusUpdate.setText("");
		currentRenderFile.setText("");
		renderStartTime.setText("");
		renderEndTime.setText("");
		lastRenderUpdate.setText("");
		startFrame.setText("");
		endFrame.setText("");
		frameCount.setText("");
		currentFrame.setText("");
		cumulativeRenderFrameCount.setText("");
		renderOutputLocationAndFileName.setText("");
		hangTimeThreshold.setText("1800");
		suspendSwitch.setText("1");
		killSwitch.setText("");
		backgroundSwitch.setText("");
	}

	public void ServerStatusSaveAction(ActionEvent event) throws SQLException, ConnectException, IOException 
	{
		// Save the server data
		H2.saveServerStatusData(
				serverName.getText(),
				ipAddress.getText(),
				serverStatus.getText(),
				lastStatusUpdate.getText(),
				currentRenderFile.getText(),
				renderStartTime.getText(),
				renderEndTime.getText(),
				lastRenderUpdate.getText(),
				startFrame.getText(),
				endFrame.getText(),
				frameCount.getText(),
				currentFrame.getText(),
				cumulativeRenderFrameCount.getText(),
				renderOutputLocationAndFileName.getText(),
				hangTimeThreshold.getText(),
				suspendSwitch.getText(),
				killSwitch.getText(),
				backgroundSwitch.getText());
		
    	// Get the current list of servers
		//definedServerData = MainApp.getDefinedServerData();
		MainApp.getServerListFromMasterServerForMonitor();
		
    	// Reset the servers in the table and display
    	tblServers.setItems(MainApp.getDefinedServerData());
	}

	public void ServerStatusDeleteAction(ActionEvent event) throws SQLException, ConnectException, IOException 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete "+serverStatusData.get(0).getServerName()+"?\n\nThis operation CANNOT be undone!", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Delete Server from ServerStatus Database");
		alert.setHeaderText("You are about to permanently delete "+serverStatusData.get(0).getServerName()+" from the database");
		
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			H2.deleteServerStatusData(serverStatusData.get(0).getServerName(), serverStatusData.get(0).getServerIpAddress());
        	serverStatusData.clear();
    		serverStatusData.add(new ServerStatus("", "", "", null, "", null, null, null, 0, 0, 0, 0, 0, "", 0, 0, 0, 0));
    		displayServerStatusData(serverStatusData);
    		
	    	// Get the current list of servers
			//definedServerData = MainApp.getDefinedServerData();
			MainApp.getServerListFromMasterServerForMonitor();
			
	    	// Reset the servers in the table and display
	    	tblServers.setItems(MainApp.getDefinedServerData());
		}
	}

	public void displayServerStatusData(ObservableList<ServerStatus> serverStatusData) {
		serverName.setText(serverStatusData.get(0).getServerName());
		ipAddress.setText(serverStatusData.get(0).getServerIpAddress());
		serverStatus.setText(serverStatusData.get(0).getServerStatus());

		if (serverStatusData.get(0).getServerStatusTimestamp() != null) {
			lastStatusUpdate.setText(Utils.timestampToString(serverStatusData.get(0).getServerStatusTimestamp()));
		} else {
			lastStatusUpdate.setText("null");
		}
		currentRenderFile.setText(serverStatusData.get(0).getServerCurrentFile());
		if (serverStatusData.get(0).getServerRenderStart() != null) {
			renderStartTime.setText(Utils.timestampToString(serverStatusData.get(0).getServerRenderStart()));
		} else {
			renderStartTime.setText("null");
		}
		if (serverStatusData.get(0).getServerRenderEnd() != null) {
			renderEndTime.setText(Utils.timestampToString(serverStatusData.get(0).getServerRenderEnd()));
		} else {
			renderEndTime.setText("null");
		}
		if (serverStatusData.get(0).getServerRenderLastUpdate() != null) {
			lastRenderUpdate.setText(Utils.timestampToString(serverStatusData.get(0).getServerRenderLastUpdate()));
		} else {
			lastRenderUpdate.setText("null");
		}
		startFrame.setText(Integer.toString(serverStatusData.get(0).getServerStartFrame()));
		endFrame.setText(Integer.toString(serverStatusData.get(0).getServerEndFrame()));
		frameCount.setText(Integer.toString(serverStatusData.get(0).getServerFrameCount()));
		currentFrame.setText(Integer.toString(serverStatusData.get(0).getServerCurrentFrame()));
		cumulativeRenderFrameCount.setText(Integer.toString(serverStatusData.get(0).getServerCumulativeFrameCount()));
		renderOutputLocationAndFileName.setText(serverStatusData.get(0).getServerOutputFile());
		hangTimeThreshold.setText(Integer.toString(serverStatusData.get(0).getServerHangTimeThreshold()));
		suspendSwitch.setText(Integer.toString(serverStatusData.get(0).getServerSuspendSwitch()));
		killSwitch.setText(Integer.toString(serverStatusData.get(0).getServerKillSwitch()));
		backgroundSwitch.setText(Integer.toString(serverStatusData.get(0).getServerBackgroundRender()));
	}
	
}
