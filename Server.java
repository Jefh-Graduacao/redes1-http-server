import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.util.Date;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.StringTokenizer;

public class Server implements Runnable, AutoCloseable {

    private Socket connectionSocket;

    public Server(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
    }    

    @Override
    public void run() {
        try {
            
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            String input = inputReader.readLine();
            Request request = new Request(input);
                        
            Response response = request.createResponse()
                                        .configureOutputStream(connectionSocket.getOutputStream())
                                        .addHeader("Server", "Server boladasso vem tranquilo")
                                        .addHeader("Date", String.valueOf(new Date()))
                                        .addHeader("Content-Type", "text/html")
                                        .build();
            
            response.send();
            inputReader.close();

        } catch (IOException ioex) {
            //TODO:
        }
        finally {
            try {
                connectionSocket.close();
            }catch (IOException ioex) {
                //TODO:
            }
        }
    }    
}