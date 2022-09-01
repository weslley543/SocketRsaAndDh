package TCPServer;

public class Main {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start(8000);
    }
}