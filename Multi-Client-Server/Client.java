// A Java program for a Client
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	// initialize socket and input output streams
	public Socket socket = null;

	// constructor to put ip address and port
	public Client(String address, int port)
	{	
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}
	}

	public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the IP address of the server: ");
		String ip_server = scanner.nextLine();
		Client client = new Client(ip_server, 3999);
		
		Sender s1=new Sender(client.socket);
		reciever r1=new reciever(client.socket);
		
		Thread t1= new Thread(s1);
		Thread t2=new Thread(r1);
		
		t1.start();
		t2.start();

	}

	public static class Sender implements Runnable {
		private Socket socket;

		// takes input from terminal 
		private DataInputStream input;

		// sends output to the socket
		private DataOutputStream out;

		public Sender(Socket socket){
			try {
				this.socket=socket;
				this.out = new DataOutputStream(socket.getOutputStream());
				this.input=new DataInputStream(System.in);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// run message writing process as a thread to solve the the problem of concurrent messaging 
		@Override
		public void run(){
			// string to read message from input
			String line = "";
		
			// keep reading until "Over" is input
			while (!line.equals("over")){
				try {
					// System.out.println("you: ");
					line = input.readLine();
					out.writeUTF(line);
				}
				catch (IOException i) {
					System.out.println(i);
				}
			}

			// close the connection
			try {
				input.close();
				out.close();
				socket.close();
			}
			catch (IOException i) {
				System.out.println(i);
			}
		}
		
	}
	public static class reciever implements Runnable{
		private Socket recieverSocket;

		private DataInputStream in;

		public reciever(Socket recieverSocket){
			this.recieverSocket=recieverSocket;

			try {
                in = new DataInputStream(new BufferedInputStream(recieverSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
		}

		@Override
		public void run(){
			String line;

			try {
				while(true){
					line=in.readUTF();
					System.out.println(line);
					if (line.equals("over")) {
                        break;
                    }
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
