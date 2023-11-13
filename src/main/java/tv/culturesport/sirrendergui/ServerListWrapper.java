package tv.culturesport.sirrendergui;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the list of servers to XML.
 * 
 * @author Dale Kubler
 */
@XmlRootElement(name = "servers")
public class ServerListWrapper {

    private List<Server> servers;

    @XmlElement(name = "server")
    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}