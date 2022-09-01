package TCPServer;

import RSA.RSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class TCPServer {
    private ServerSocket serverSocket;
    private static RSA rsa = new RSA();
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started in port: " + port);
        while (true) {
            try {
                new EchoClientHandler(serverSocket.accept(), this.rsa).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        private RSA rsa = null;

        public EchoClientHandler(Socket socket, RSA rsa) {
            this.clientSocket = socket; this.rsa = rsa;
        }
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String inputLine;
            while (true) {
                try {
                    if (!((inputLine = in.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                String [] inputOp = inputLine.split(";");
                System.out.println(inputOp[0]);
                String outputString = "";
                if(inputOp[0].equals("Request Public Key")){
                    outputString = rsa.getPublicKeyPair();
                    System.out.println(outputString);
                }

                if(inputOp[0].equals("Decript message")) {
                    System.out.printf("Encripted String: "+ inputOp[1]+ "\n");
                    outputString = rsa.decryptValue(inputOp[1]);
                    System.out.printf(outputString);
                }

                out.println(outputString);
            }

            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
