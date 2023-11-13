package tv.culturesport.sirrendergui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Server
 *
 * @author Dale Kubler
 */

public class Server {
    private final StringProperty serverName;
    private final StringProperty serverIpAddress;
    private final StringProperty serverAvailable;
    private final StringProperty serverType;
    private final StringProperty serverKillSwitch;
    private final StringProperty serverSuspend;
    private final StringProperty serverBackgroundRender;


	/**
     * Default constructor.
     */
    public Server() {
        this(null, null, null, null, null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param serverName
     * @param serverIpAddress
     */
    public Server(String serverName, String serverIpAddress, String serverAvailable, String serverType, String serverKillSwitch, String serverSuspend, String serverBackgroundRender) {
        this.serverName = new SimpleStringProperty(serverName);
        this.serverIpAddress = new SimpleStringProperty(serverIpAddress);
        this.serverAvailable = new SimpleStringProperty(serverAvailable);
        this.serverType = new SimpleStringProperty(serverType);
        this.serverKillSwitch = new SimpleStringProperty(serverKillSwitch);
        this.serverSuspend = new SimpleStringProperty(serverSuspend);
        this.serverBackgroundRender = new SimpleStringProperty(serverBackgroundRender);
    }

	public final StringProperty serverNameProperty() {
		return this.serverName;
	}
	
	public final String getServerName() {
		return this.serverNameProperty().get();
	}
	
	public final void setServerName(final String serverName) {
		this.serverNameProperty().set(serverName);
	}
	
	public final StringProperty serverIpAddressProperty() {
		return this.serverIpAddress;
	}
	
	public final String getServerIpAddress() {
		return this.serverIpAddressProperty().get();
	}
	
	public final void setServerIpAddress(final String serverIpAddress) {
		this.serverIpAddressProperty().set(serverIpAddress);
	}

	public final StringProperty serverAvailableProperty() {
		return this.serverAvailable;
	}
	
	public final String getServerAvailable() {
		return this.serverAvailableProperty().get();
	}
	
	public final void setServerAvailable(final String serverAvailable) {
		this.serverAvailableProperty().set(serverAvailable);
	}
	
	public final StringProperty serverTypeProperty() {
		return this.serverType;
	}

	public final String getServerType() {
		return this.serverTypeProperty().get();
	}

	public final void setServerType(final String serverType) {
		this.serverTypeProperty().set(serverType);
	}
	
	public final StringProperty serverKillSwitchProperty() {
		return this.serverKillSwitch;
	}

	public final String getServerKillSwitch() {
		return this.serverKillSwitchProperty().get();
	}

	public final void setServerKillSwitch(final String serverKillSwitch) {
		this.serverKillSwitchProperty().set(serverKillSwitch);
	}
	
	public final StringProperty serverSuspendProperty() {
		return this.serverSuspend;
	}

	public final String getServerSuspend() {
		return this.serverSuspendProperty().get();
	}

	public final void setServerSuspend(final String serverSuspend) {
		this.serverSuspendProperty().set(serverSuspend);
	}

	public final StringProperty serverBackgroundRenderProperty() {
		return this.serverBackgroundRender;
	}

	public final String getServerBackgroundRender() {
		return this.serverBackgroundRenderProperty().get();
	}

	public final void setServerBackgroundRender(final String BackgroundRender) {
		this.serverBackgroundRenderProperty().set(BackgroundRender);
	}
	
	
}