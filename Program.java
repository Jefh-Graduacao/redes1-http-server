import java.io.*;
import java.net.*;

class Program {
    public static void main(String[] args) throws IOException {
        String fraseCliente;
        String fraseMaiusculas;

        ServerSocket socketRecepcao = new ServerSocket(6789);

        Socket socketConexao = socketRecepcao.accept();
        BufferedReader doCliente = new BufferedReader(new InputStreamReader(socketConexao.getInputStream()));
        DataOutputStream paraCliente = new DataOutputStream(socketConexao.getOutputStream());
        
        while(true) {

            if(socketConexao.isClosed())
            {
                break;
            }
            
            String rec = doCliente.readLine();
            while(rec != null) {
                System.out.println(rec);
                rec = doCliente.readLine();

                if(rec.isEmpty()) 
                {
                    paraCliente.writeBytes("HTTP/1.1 200 OK\nContent-Length: 400\nContent-Type: text/plain\n\nasdhaus");
                    paraCliente.flush();
                    paraCliente.close();
                    //doCliente.close();
                }
            }

            // System.out.print(fraseCliente);

            // if(fraseCliente.equals("FIM")){
            //     socketConexao.close();
            //     break;
            // }else{
            //     fraseMaiusculas= fraseCliente.toUpperCase() + '\n';
            //     paraCliente.writeBytes(fraseMaiusculas);
            // }
        }
    }
}