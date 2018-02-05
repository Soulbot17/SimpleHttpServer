package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.*;

public class HttpResponse {
    private static final Logger logger = LogManager.getLogger(HttpResponse.class);
    private static final String VERSION = "HTTP/1.1";

    byte[] body;

    List<String> headers = new ArrayList<>();

    public HttpResponse(HttpRequest req) {
        String method = req.getMethod();

        switch (method) { // best practice: ENUM HERE
            case "GET":

                final String url = req.getUrl();
                final Path path = Paths.get(".", url);

                if (!Files.exists(path)) {
                    fillHeaders(HttpStatus.NOT_FOUND);
                    fillBody("<h1>The requested resource is not found</h1>");
                    return;
                }

                if (Files.isDirectory(path)) {
                    // TODO show html listing for directory with links to files
                    fillHeaders(HttpStatus.OK);
                    fillBody("<h1>Unsupported operation</h1>");
                } else {
                    sendFile(path);
                }

                break;
            case "POST":
                break;
            default:
                break;
        }
    }

    public void write(OutputStream os) throws IOException {
        // write headers
        headers.forEach(s -> writeString(os, s));
        // write empty line
        writeString(os, "");
        // write body
        os.write(body);
    }

    private void writeString(OutputStream os, String str) {
        try {
            os.write((str+"\r\n").getBytes(UTF_8));
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    private void fillHeaders(HttpStatus status) {
        headers.add(VERSION + " " + status);
        headers.add("Server: Simple Http Server");
        headers.add("Connection: close");
    }

    private void fillBody(String str) {
        body = str.getBytes(UTF_8);
    }

    private void sendFile(Path path) {

        try {

            body = Files.readAllBytes(path);
            fillHeaders(HttpStatus.OK);

        } catch (IOException e) {
            logger.error("",e);
            fillHeaders(HttpStatus.SERVER_ERROR);
            fillBody("<p>Error showing file "+path.toFile().getName()+"</p>");
        }
    }
}
