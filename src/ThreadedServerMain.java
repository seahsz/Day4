import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServerMain {

    public static void main(String[] args) throws IOException {

        // Create a thread pool
        ExecutorService thrPool = Executors.newFixedThreadPool(2);
        
        // Set the default port to 3000
        int port = 3000;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create a server port
        ServerSocket server = new ServerSocket(port);

        while (true) {
            // Wait for incoming connection
            System.out.println("Waiting for connection");
            Socket sock = server.accept(); // server.accept() returns a socket

            System.out.println("Got a new connection");

            // Create a thread (aka worker) to handle the work
            ClientHandler handler = new ClientHandler(sock);

            //Pass the work to the worker
            thrPool.submit(handler);
        }

    }

}


// Takeaways: the first connection (the first socket) will have to run finish the entire code before another connection 
//  can be accepted because the code has not reached server.accept() a 2nd time
