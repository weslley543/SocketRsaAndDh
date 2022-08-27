package TCPClient;

import RSA.RSA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.startConnection("localhost", 8000);
        if(args.length != 3) {
            throw new Error("É necessário informar 3 argumentos para enviar mensagem para o servidor");
        }
        try {
            BigInteger p = new BigInteger(args[1]);
            BigInteger q = new BigInteger(args[2]);

            RSA rsa = new RSA();
            rsa.generateKey();
            String hashedValue = rsa.encryptValue(args[0]);
            client.sendMessage(hashedValue);
        }catch (Exception e) {
            System.out.println("Error to create a keys"+ e.getMessage());
        }
    }
}