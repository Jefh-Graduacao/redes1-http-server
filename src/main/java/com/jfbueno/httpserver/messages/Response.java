package com.jfbueno.httpserver.messages;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.jfbueno.httpserver.helpers.*;

public class Response {
    private Map<String, String> headers;
    private StatusCode statusCode;
    
    private byte[] responseData;
    private int responseLength;

    private PrintWriter outputPrinter;
    private BufferedOutputStream outputDataStream;

    public Response() { 
        headers = new HashMap<String, String>();
    }

    public void setOutputStream(OutputStream outputStream) {        
        this.outputPrinter = new PrintWriter(outputStream);
        this.outputDataStream = new BufferedOutputStream(outputStream);
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }

    public void setStatusCode(int code) {
        this.statusCode = StatusCode.fromIntStatus(code);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addResponseData(byte[] responseData, int responseLength) {
        this.responseData = responseData;
        this.responseLength = responseLength;
    }

    public void send() {
        String firstLine = "HTTP/1.1 " + statusCode.getCode() + " " + statusCode.getDescription();

        try {
            outputPrinter.println(firstLine);
            headers.forEach((key, value) -> outputPrinter.println(key + ":" + value));
            outputPrinter.println();
            outputPrinter.flush();

            if(responseData.length > 0) {
                outputDataStream.write(responseData, 0, responseLength);
                outputDataStream.flush();
            }

            outputPrinter.close();
            outputDataStream.close();
        } catch (IOException ioex) {
            // TODO:
        }
    }
}