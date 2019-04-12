import java.io.*;
import java.net.*;
import java.util.Date;

class Program {
    public static void main(String[] args) throws IOException {
        ServerSocket serverConnect = new ServerSocket(9001);

        while(true) {
            try {
                Server server = new Server(serverConnect.accept());

                Thread thread = new Thread(server);
                thread.start();
            }catch(IOException ioex) {
                System.out.println("Erro de conexão no servidor");
                ioex.printStackTrace();
            }
        }
    }
}