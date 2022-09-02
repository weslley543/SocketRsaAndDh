package TCPServer;

public class Main {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        // Inicia o servidor no porta 8000
        tcpServer.start(8000);
    }
}