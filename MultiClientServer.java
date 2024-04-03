import java.io.*;
import java.net.*;

public class MultiClientServer {
    private ServerSocket serverSocket;

    public MultiClientServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                // Create a new thread for each client
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MultiClientServer ex = new MultiClientServer(3999);
    }

    // ClientHandler class to handle communication with each client
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String line;
            try {
                while (true) {
                    line = in.readUTF();
                    System.out.println("Client: " + line);
                    if (line.equals("Over")) {
                        break;
                    }
                    // Process client input and send response
                    out.writeUTF("Server received: " + line);
                }
                System.out.println("Closing connection for client: " + clientSocket);
                // Clean up resources
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
