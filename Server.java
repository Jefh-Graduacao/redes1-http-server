import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Date;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.StringTokenizer;

public class Server implements Runnable, AutoCloseable {

    private Socket connectionSocket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream dataOut;

    public Server(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        
        in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        // Usado para escrever os headers
        out = new PrintWriter(connectionSocket.getOutputStream());
        // Usado para escrever o arquivo
        dataOut = new BufferedOutputStream(connectionSocket.getOutputStream());
    }
    
    @Override
    public void close () {

    }

    @Override
    public void run() {
        try {
            
            String input = in.readLine();
            StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase();
            String fileRequested = parse.nextToken().toLowerCase();
            
            Request request = new Request(method, fileRequested);
            System.out.println(request.getMethod());
            System.out.println(request.getResource());

            File file = new File(new File("."), "File.html");
            int fileLength = (int) file.length();
            byte[] fileData = readFileData(file, fileLength);
            
            out.println("HTTP/1.1 200 VEM TRANQUILO");
            out.println("Server: Java HTTP Server: 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: text/html");
            out.println("Content-length: " + fileLength);
            out.println(); // blank line between headers and content, very important !
            out.flush(); // flush character output stream buffer
            // file
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();

            in.close();
            out.close();
            dataOut.close();
            connectionSocket.close(); 

        } catch (Exception e) {
            System.err.println("Error closing stream : " + e.getMessage());
        } 
    }

    private void process(Request request){ 
        /*try {
            
            System.out.println(request.getMethod());
            System.out.println(request.getResource());

            File file = new File(new File("."), "File.html");
            int fileLength = (int) file.length();
            byte[] fileData = readFileData(file, fileLength);
            
            out.println("HTTP/1.1 200 VEM TRANQUILO");
            out.println("Server: Java HTTP Server from SSaurel : 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: text/html");
            out.println("Content-length: " + fileLength);
            out.println(); // blank line between headers and content, very important !
            out.flush(); // flush character output stream buffer
            // file
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();

            
            in.close();
            out.close();
            dataOut.close();
            connectionSocket.close(); 

        } catch (Exception e) {
            System.err.println("Error closing stream : " + e.getMessage());
        } /*/
    }

    private static byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
}