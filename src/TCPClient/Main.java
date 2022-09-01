package TCPClient;

import RSA.RSA;
import RSA.PublicKeyPair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {

        TCPClient client = new TCPClient();
        client.startConnection("localhost", 8000);

        String serverResponse=null;
        try {
            RSA rsa = new RSA();
            serverResponse = client.sendMessage("Request Public Key");
            System.out.println(serverResponse);
            ObjectMapper om = new ObjectMapper();
            PublicKeyPair serverPublicKeyPair = om.readValue(serverResponse, PublicKeyPair.class);
            String hashedString = rsa.encryptValue("Hello from client", serverPublicKeyPair.getE(), serverPublicKeyPair.getN());
            client.sendMessage("Decript message;"+hashedString);

        }catch (Exception e) {
            System.out.println("Error to create a keys "+ e.getMessage());
        }
    }
}