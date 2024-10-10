import java.net.*;
import java.io.*;
import java.util.Date;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        
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

            // Get the input stream (since client opens outputstream first, we open
            // inputstream first on server
            // prevent any issues that may arise)
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Get the output stream
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // Read the result from the server
            String fromClient = br.readLine();

            System.out.printf(">>>> SERVER: %s\n", fromClient);

            // Transform the message - add date + transform to uppercase
            fromClient = (new Date()).toString() + " " + fromClient.toUpperCase();

            // Write result back to client
            bw.write(fromClient + "\n");
            bw.flush();
            os.flush();

            os.close();
            is.close();
            sock.close();
        }

    }

}


// Takeaways: the first connection (the first socket) will have to run finish the entire code before another connection 
//  can be accepted because the code has not reached server.accept() a 2nd time
