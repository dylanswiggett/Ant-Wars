package server;

import java.io.*;
import java.net.*;

public class Server {
	DatagramSocket socket;
	
	public Server(){
		try {
			socket = new DatagramSocket(9876);
		} catch(SocketException e) {
			System.out.println("Network Connection Error.");
		}
	}
	
	public void run(){
		try{
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);	
			// send the response to the client at "address" and "port"
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
		} catch(IOException e){
			System.out.println("IOException");
			System.out.println(e);
		}
	}
		
	
	public static void main(String[] args){
		Server server = new Server();
		server.run();
	}
}
