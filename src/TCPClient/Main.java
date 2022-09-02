package TCPClient;

import RSA.RSA;
import RSA.PublicKeyPair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;


public class Main {
    public static void main(String[] args) {

        // Instancia a classe TCPClientt
        TCPClient client = new TCPClient();

        // Conecta o cliente na porta 8000
        client.startConnection("localhost", 8000);

        String serverResponse=null;
        try {
            RSA rsa = new RSA();
            serverResponse = client.sendMessage("Request Public Key");
            System.out.println(serverResponse);
            ObjectMapper om = new ObjectMapper();
            PublicKeyPair serverPublicKeyPair = om.readValue(serverResponse, PublicKeyPair.class);
            Faker faker = new Faker();
            String hashedString = rsa.encryptValue(faker.name().fullName(), serverPublicKeyPair.getE(), serverPublicKeyPair.getN());
            client.sendMessage("Decript message;"+hashedString);

        }catch (Exception e) {
            System.out.println("Error to create a keys "+ e.getMessage());
        }
    }
}