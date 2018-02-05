package http;

import main.ClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LogManager.getLogger(HttpRequest.class);

    private String method;
    private String url;
    private String version;
    private Map<String, String> headers = new HashMap<>();


    public HttpRequest(BufferedReader br) {
        try {
            //read the URL String
            String line = br.readLine();
            parseRequestString(line);

            logger.debug("Request: {}", line);

            do {
                line = br.readLine();
                parseHeaderLine(line);
            } while (!line.equals("")); // !"".equals("line"); - if line - null, nullPointerException. This is solution.

            headers.forEach((k, v) -> logger.debug("{} => {}", k, v));

        } catch (IOException e) {
            logger.error("", e);
        }
    }

    private void parseHeaderLine(String line) {
        if (line == null || "".equals(line)) return;
        logger.debug(line);
        String[] artifacts = line.split(":\\s+");
        headers.put(artifacts[0], artifacts[1]); //artifacts.length > 1 ? artifacts[1] : ""
    }

    private void parseRequestString(String line) {
        if (line == null) return;
        String[] artifacts = line.split("\\s+");

        method = artifacts[0];
        url = artifacts[1];
        version = artifacts[2];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
