import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastChatServer {
    public static void main(String[] args) throws Exception {
        //Default port number
        int portnumber = 50000;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        //Create MulticastSocket
        MulticastSocket serverMulticastSocket = new MulticastSocket(portnumber);
        System.out.println("MulticastSocket created at port " + portnumber);

        //Determine IP address of host, given host name
        InetAddress group = InetAddress.getByName("225.4.5.6");

        //getByName - returns IP address of given host
        serverMulticastSocket.joinGroup(group);
        System.out.println("joinGroup method called...");
        boolean infinite = true;

        //Continually receives and prints data
        while (infinite) {
            byte buf[] = new byte[1024];
            DatagramPacket data = new DatagramPacket(buf, buf.length);
            serverMulticastSocket.receive(data);
            String msg = new String(data.getData()).trim();
            System.out.println("Message from client: " + msg);
        }
        serverMulticastSocket.close();
    }
}