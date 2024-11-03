package TCP;
import java.io.*;
import java.net.*;
class TcpServer{
    public static void main(String argv[]) throws Exception
    {
        String ClientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(
                            connectionSocket.getInputStream()));
            DataOutputStream outToClient =
                    new DataOutputStream(
                            connectionSocket.getOutputStream());
            ClientSentence = inFromClient.readLine();
            capitalizedSentence =
                    ClientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
