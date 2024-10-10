import java.net.*;
import java.io.*;
import java.util.Date;


// Work for a thread to perform
public class ClientHandler implements Runnable {

    private final Socket sock;

    public ClientHandler(Socket s) {
        this.sock = s;
    }

    // Entry point for the thread
    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();

        try {

        // Get the input stream (since client opens outputstream first, we open
        // inputstream first on server
        // prevent any issues that may arise)
        InputStream is = this.sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        // Get the output stream
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        // Read the result from the server
        String fromClient = br.readLine();

        System.out.printf(">>>> [%s] CLIENT: %s\n", threadName, fromClient);

        // Transform the message - add date + transform to uppercase
        fromClient = (new Date()).toString() + " " + fromClient.toUpperCase();

        // Write result back to client
        bw.write(fromClient + "\n");
        bw.flush();
        os.flush();

        os.close();
        is.close();
        sock.close();

        } catch (IOException ex) {
            // Exception handler
            ex.printStackTrace();
        }

    }

}
