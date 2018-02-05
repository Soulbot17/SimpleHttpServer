package main;

import http.HttpRequest;
import http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSession implements Runnable {
    private Socket socket;

    private static final Logger logger = LogManager.getLogger(ClientSession.class);

    public ClientSession(Socket socket) {
        this.socket = socket;
        logger.debug("Connected client {}", socket);
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // parse request into request object
            HttpRequest req = new HttpRequest(reader);
            // generate response to the request
            HttpResponse resp = new HttpResponse(req);
            // write response into the socket output stream
            try (BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
                resp.write(bos);
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
