package network;
import java.net.*;
import java.io.*;
import java.util.*;

public class Network_Server implements Runnable{
	ServerSocket server;
	PrintWriter out;
	BufferedReader in;
	Socket soc;
	Network_Server(){
		try{
			server=new ServerSocket(12345);
			
		}catch(IOException ee){
			
		}
	}
	@Override
	public void run() {
		try {
			soc=server.accept();
			out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
			while(true){
				String str=in.readLine();
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
}
