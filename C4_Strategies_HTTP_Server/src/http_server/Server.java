package http_server;

import java.io.*; 
import java.net.*;
import java.util.Scanner;

// WorkerThread

public class Server {
	public static void main(String[] args) {
		
		// defining Port Number 
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter Port Number"); 
		String port = scan.nextLine();
		int portNum = Integer.parseInt(port);
		
		// definining Root File
		System.out.println("Enter Root File Name"); 
		String root= scan.nextLine();
		
		try {
			// Initiating serversocket
			ServerSocket ss = new ServerSocket(portNum);
			ss.setReuseAddress(true); // allows socket to be bound even though previous connection is in a timeout state
			
			while(true ){
				// waiting for message from ss
				Socket s = ss.accept();
				System.out.println("Client connected");
				
				// starting thread ("proxie") to take care of client
				Proxies prox = new Proxies(s, root);
				new Thread(prox).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.close();
		
	}
		
		
		private static class Proxies implements Runnable{
			private final Socket s; 
			private final String root; 
			
			public Proxies(Socket s, String root) {
				this.s = s; 
				this.root = root; 
			}

			@Override
			public void run() {
				
				BufferedReader input = null;
				DataInputStream fileInput = null; 
				DataOutputStream fileOutput = null; 
				
				
				try {
					input = new BufferedReader( new InputStreamReader(s.getInputStream()));
					String fileName= input.readLine();
					
					while(fileName != null) {
					
					File file = new File(root + fileName ); 
				// 	byte[] buf =  new byte[(int) file.length()];
					
				// 	fileInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				//	fileInput.readFully(buf);
				//	fileInput.close();
					
					// should sen the file ?? 
					fileOutput = new DataOutputStream(s.getOutputStream());
					fileOutput.writeUTF(file.getName());
					fileOutput.flush();
					
					System.out.println("File sent to client");
					
					
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally {
					try {
						if ( input != null ) {
								input.close();
						}
					
						if( fileOutput != null ) {
							fileOutput.close();
						
						}
					
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
				
				
			}
			
		}
		
	}


/*
public class Server implements Runnable {
	private int portNum;
	private String fileName; 
	private ServerSocket ss; 
	private Thread currThread; 
	
	public Server(int portNum, String fileName) {
		this.portNum = portNum; 
		this.fileName = fileName; 
	}
 
		@Override
		public void run() {
			
		this.currThread = Thread.currentThread(); 
		
		try {
			this.ss = new ServerSocket(portNum);
			
			Socket s = ss.accept(); 
			
			InputStreamReader input = new InputStreamReader(s.getInputStream());
			BufferedReader reader = new BufferedReader(input); 
			
			String message = reader.readLine(); 
			System.out.println("client :" + message); 	
			
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			writer.println("hemskt mycket hej");
			writer.flush(); 
			
			// den här ska hitta filen
			// skicka tillbaka den till klienten
			
			// finns inte filen: throw exception
			
		}catch (Exception e) {
			System.out.println(e);
		}
			
		}

}
*/
