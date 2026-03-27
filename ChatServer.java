import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server started...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(out);

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                    );

                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);

                        // send to all clients
                        for (PrintWriter client : clients) {
                            client.println(msg);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Client disconnected");
                }
            }).start();
        }
    }
}