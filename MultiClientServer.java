import java.io.*;
import java.net.*;
import java.util.HashMap;

class Pair<K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public V setSecond(V second) {
        this.second = second;
        return second;
    }
}

public class MultiClientServer {
    private ServerSocket serverSocket;
    private static HashMap<String,Pair<Socket,String>>hashMap;

    public MultiClientServer(int port) {
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            hashMap = new HashMap<>();

            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                
                // Fetch the name from the client
                DataOutputStream getName=new DataOutputStream(socket.getOutputStream());
                getName.writeUTF("Enter your name: ");
                DataInputStream nameInput = new DataInputStream(socket.getInputStream());
                String name = nameInput.readUTF();
                
                // insert in hashmap to verify presence of user connection with server
                Pair<Socket,String> pair=new Pair<>(socket,"");
                hashMap.put(name,pair);
                
                // Create a new thread for each client
                ClientHandler clientHandler = new ClientHandler(socket,name);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MultiClientServer ex = new MultiClientServer(3999);
    }

    // ClientHandler class to handle communication with each client
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private Socket recieverSocket;
        private String clientName;

        
        private DataInputStream in;
        private DataOutputStream outp;
        private DataOutputStream sendToreciever;

        public ClientHandler(Socket socket,String name) {
            this.clientSocket = socket;
            this.clientName=name;

            try {
                in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                outp = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String line;
            
            try {
                // fetch name of the person with whom the client wishes to talk
                outp.writeUTF("With whom you wish to connect: ");
                String receiverName = in.readUTF();

                Pair<Socket,String> p=hashMap.get(receiverName);
                Socket reciever=null;
                if(p!=null){
                    reciever=p.getFirst();
                }
                if(p==null){
                    outp.writeUTF(receiverName+" is not connected to the server");
                    outp.writeUTF("press 1 if you want to wait \npress 2 if you want to connect to another user \nwrite 'over' if you want to exit");
                    String ans=in.readUTF();
                    if(ans.equals("over")){
                        outp.writeUTF("Exiting...");
                        in.close();
                        outp.close();
                        sendToreciever.close();
                        clientSocket.close();
                        return;
                    }
                    if(ans.equals("2")){
                        ClientHandler clientHandler = new ClientHandler(clientSocket,clientName);
                        Thread clientThread = new Thread(clientHandler);
                        clientThread.start();
                        return;
                    }
                    else if(ans.equals("1")){
                        outp.writeUTF("waiting for user to connect..");
                        // wait infinetly for the user to connect using while loop
                        while(hashMap.get(receiverName)==null || hashMap.get(receiverName).getFirst()==reciever);
                        
                        reciever=hashMap.get(receiverName).getFirst();
                    }
                }

                this.recieverSocket=reciever;

                // insert in hashmap name of user who our client is wishing to talk to 
                hashMap.get(clientName).setSecond(receiverName);
                System.out.println(hashMap.get(clientName).getSecond());
                System.out.println("Reciever: "+reciever);
                int x=0;
                // while(!receiverName.equals(hashMap.get(receiverName).getSecond())){
                //     if(x==0){
                //         System.out.println(hashMap.get(receiverName).getSecond()+"\n" +clientName);
                //         outp.writeUTF("waiting for user to connect connect / finish talking to other user...");
                //         x++;
                //     }
                // };

                this.sendToreciever=new DataOutputStream(recieverSocket.getOutputStream());
                outp.writeUTF("start chatting..... \n \n");
                
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                while (true) {
                    line = in.readUTF();

                    // send the messae to reciever using output stream of reciever
                    if (line.equals("over")) {
                        in.close();
                        outp.close();
                        sendToreciever.close();
                        clientSocket.close();
                        break;
                    }
                    sendToreciever.writeUTF(this.clientName + ": " +line);
                    
                    // Process client input and send response
                    
                    // out.writeUTF("Server received: " + line);

                }
                System.out.println("Closing connection for client: " + clientSocket);
                // Clean up resources
                in.close();
                outp.close();
                
                sendToreciever.close();
                clientSocket.close();            
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

// final commit 
