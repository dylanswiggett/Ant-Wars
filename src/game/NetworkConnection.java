package game;

import java.io.IOException;
import java.net.*;

public class NetworkConnection {
	DatagramSocket socket;
	
	public NetworkConnection(){
		try {
			socket = new DatagramSocket(9897);
		} catch(Exception e) {
			System.out.println("Network Connection Error.");
			System.out.println(e);
		}
	}
	
	public void establish() {
		if( socket != null ){
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				String sentence = "Request Connection.";
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
				clientSocket.send(sendPacket);
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modifiedSentence = new String(receivePacket.getData());
				System.out.println("FROM SERVER:" + modifiedSentence);
				clientSocket.close();
			} catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
