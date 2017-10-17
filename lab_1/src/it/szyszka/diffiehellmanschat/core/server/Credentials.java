package it.szyszka.diffiehellmanschat.core.server;

/**
 * Created by rafal on 17.10.17.
 */
public class Credentials {

    private Integer port;
    private String name;

    public Credentials(Integer port, String name) {
        this.port = port;
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public String getName() {
        return name;
    }
}
