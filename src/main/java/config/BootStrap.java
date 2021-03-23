package config;

import org.h2.tools.Server;

import java.sql.SQLException;

public class BootStrap {
    final static String[] args = new String[]{ "-tcp", "-tcpAllowOthers", "-tcpPort", "8093", "-tcpDaemon"};

    static Server server;

    public static void Init(){
        try {
            server = Server.createTcpServer(args).start();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void destroy(){
            server.stop();
    }
}

