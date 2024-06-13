# Socket-Programming
<p>This repository contains a client-server model implemented using Java sockets, featuring basic multithreading capabilities. The system supports multiple clients connecting to the server simultaneously. Clients can specify the ID of another client they wish to communicate with, enabling sequential message exchanges between them.</p>

<h2>Features</h2>
<ul>
    <li><strong>Client-Server Architecture:</strong> Utilizes Java sockets to establish a robust client-server communication model.</li>
    <li><strong>Multithreading:</strong> Allows multiple clients to connect and interact with the server concurrently.</li>
    <li><strong>Client Identification:</strong> Clients can specify the ID of another client to establish direct communication.</li>
    <li><strong>Message Exchange:</strong> Facilitates sequential message exchange between clients.</li>
</ul>

## Usage

To run this multiclient server program, follow these steps:

### Server Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/MRIDUL2206/Socket-Programming.git
    ```
2. **Navigate to the project directory**:
    ```sh
    cd Multi-Client-Server
    ```
3. **Compile the server program**:
    ```sh
    javac MultiClientServer.java
    ```
4. **Run the server program**:
    ```sh
    java MultiClientServer
    ```

### Client Setup 
( To be done on a seperate terminal )

1. **Compile the client program**:
    ```sh
    javac Client.java
    ```
2. **Run the client program**:
    ```sh
    java Client
    ```

3. **Follow the prompts**:
   - Enter the IP address of the server.
   - Provide your name when prompted.
   - Enter the name of the person you wish to connect with.

   Clients can now send messages to each other. To end a session, type `over`.

## Conclusion

This multiclient server project demonstrates the use of socket programming and multithreading in Java to create a robust chat application. The server can handle multiple clients simultaneously, each running on its own thread, ensuring efficient communication.

The key features include:
- **Concurrent client handling**: Each client is managed in a separate thread, allowing for multiple simultaneous connections.
- **Dynamic user interaction**: Clients can specify whom they wish to chat with, and the server facilitates the connection.
- **Robust communication**: The implementation ensures seamless message transmission between clients, with the ability to handle connection terminations gracefully.

Feel free to explore the code, extend its functionality, and adapt it for your specific needs. Contributions and feedback are always welcome!
