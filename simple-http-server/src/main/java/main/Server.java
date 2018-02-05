package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);

    private static final int PORT = 80;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        Path cur = Paths.get(".").toAbsolutePath();
        logger.debug("Server started at port {} and showing directory at {}", PORT, cur);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientSession(socket)).start();
        }


    }

}
