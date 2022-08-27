package TCPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch (IOException ioException) {
            System.out.println("Error to create a client");
        }
    }

    public String sendMessage(String message) {
        String response = null;
        try {
            out.println(message);
            response = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public void closeConnection () {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
