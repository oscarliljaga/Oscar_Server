import java.net.*;
import java.io.*;

public class NetworkingServer {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        //Default port number
        int portnumber = 50000;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        //Create server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket " + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);

        //Wait for client data and reply
        while (true) {
            try {
                //Listens for connection and accepts it. Blocks until a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + "\nClient port = " + clientPort);

                //Read data from client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message from client: " + msgFromClient);

                //Send response
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);

                    String ansMsg = solveMaths(msgFromClient);

                    pw.println(ansMsg);
                }

                //Close sockets
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }
            } catch (IOException ie) {
                System.out.println("Something went wrong! " + ie);
            }
        }
    }

    private static String solveMaths(String problem) {
        problem = problem.trim();
        String[] problemTermsStrings;
        int[] problemTerms = new int[2];
        String[] calculations = {"\\+", "-", "*", "/"};

        for (String calculation : calculations) {
            if (problem.contains(calculation)) {
                problemTermsStrings = problem.split(calculation);

                problemTerms[0] = Integer.parseInt(problemTermsStrings[0]);
                problemTerms[1] = Integer.parseInt(problemTermsStrings[1]);
                System.out.println(problemTerms[0] + " and " + problemTerms[1]);

            }
        }

        if (problem.contains("+")) {
            return "The sum of " + problem + " is " + (problemTerms[0] + problemTerms[1]);
        } else if (problem.contains("-")) {
            return "The difference of " + problem + " is " + (problemTerms[0] - problemTerms[1]);
        } else if (problem.contains("*")) {
            return "The product of " + problem + " is " + (problemTerms[0] * problemTerms[1]);
        } else if (problem.contains("/")) {
            return "The division of " + problem + " is " + (problemTerms[0] / problemTerms[1]);
        } else {
            return "Please use proper syntax. Ex: [1+2] or [4*5]";
        }
    }
}