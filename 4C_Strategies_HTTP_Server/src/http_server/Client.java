package http_server;

import java.util.*;
import java.io.*; 
import java.net.*;


public class Client  {
	
	public static void main(String[]args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter Port Number"); 
		String port = scan.nextLine();
		int portNum = Integer.parseInt(port);
		
		System.out.println("Enter Root Path"); 
		String root = scan.nextLine();
		
		try {
			Socket s = new Socket("localhost", portNum);
			
			// handles sending data to server
			PrintWriter output= new PrintWriter(s.getOutputStream());
			String fileName = null; 
			
			System.out.println("Enter File Name"); 
			while (! "end".equals( fileName)) {
				fileName = scan.nextLine();
				output.println(fileName);
				output.flush();
				
				// handles receiveing data
				DataInputStream input = new DataInputStream(s.getInputStream());
				String recFileName = input.readUTF();
				
				System.out.println("File with name " + recFileName + " recieved from server" );
				
			}
			
			scan.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		// String fileName = scan.nextLine(); 
		
		
	// 	Socket s = new Socket("localhost", portNum);
		
	}
	
}
	
	
/*	
	private int portNum; 
	private String fileName;
	
	public Client(int portNum, String fileName) {
		this.portNum = portNum; 
		this.fileName = fileName; 
	}
	
	@Override
	public void run() {
		
		try {
			Socket s = new Socket("localhost", portNum);
			System.out.println("Connecting");
			
			// Asking for file
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			writer.println("hej hej");
			writer.flush(); 
			
			InputStreamReader input= new InputStreamReader(s.getInputStream());
			BufferedReader reader = new BufferedReader(input); 
			
			String message = reader.readLine(); 
			System.out.println("server :" + message); 
			
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		
	}  
	
	

}
*/ 