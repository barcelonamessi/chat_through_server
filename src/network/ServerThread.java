package network;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream; 
import java.io.PrintWriter; 
import java.net.Socket;
import java.util.Vector;

import javafx.collections.ObservableList; 

public class ServerThread implements Runnable{
    private String clientID = null;
    private Socket socket = null;
    private BufferedReader br = null;
    private PrintWriter out = null;
    private ObjectOutputStream oos=null;
    private ObjectInputStream ois=null;
    public ServerThread(Socket socket){ 
        this.socket = socket; 
    } 
    public void joinClient() {
        try{ 
        	CustomObject cur_obj=null;
        	try {
				cur_obj=(CustomObject)ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            clientID =cur_obj.text; 
            if(clientID==null||clientID.equals(" ")) { 
                clientID = "�մ�_" + Math.random(); 
            } 
        }catch(IOException ioe) { 
        }finally{ 
            SimpleServer.addClient(this); 

            String msg = SimpleServer.msgKey + clientID + "���� �����ϼ̽��ϴ�.";
            SimpleServer.broadCasting(msg);
            SimpleServer.broadCasting_user(clientID, 2);
            //SimpleServer.init_users();
            //SimpleServer.init_users();
            //SimpleServer.getItems().add(clientID);
            
            
            
            
            System.out.println(msg); 
            
        } 
    } 
    public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	
	
	public void exitClient() {
        SimpleServer.removeClient(this); 

        String msg = SimpleServer.msgKey + clientID + "���� �����ϼ̽��ϴ�."; 
        SimpleServer.broadCasting(msg); 
        SimpleServer.broadCasting_user(clientID, 3);
       // SimpleServer.init_users();
        //SimpleServer.getItems().remove(clientID);
        //SimpleServer.init_users();
        System.out.println(msg); 
    } 
    //Ŭ���̾�Ʈ�� ����� ��½�Ʈ���� ���� ����ϴ� �޼ҵ� 
    public void sendMessage(String message) {
    	
			try {
				oos.writeObject(new CustomObject(1, message));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        //out.println(message); 
    } 
    public void sendInfo(String id, int ver){
		
		try {
			oos.writeObject(new CustomObject(ver, id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    public void set_init(){
    	try{
    		oos.writeObject(new CustomObject(4, null, SimpleServer.getItems()));
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    
    public void run(){         
        try{ 
            //1. ������ ���� ��Ʈ�� ����(Input, Output) 
           // br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           // out = new PrintWriter(socket.getOutputStream(), true);
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
            
            oos.writeObject(new CustomObject(4, null, SimpleServer.getItems()));
            joinClient(); 
            //SimpleServer.init_users();
            //2. Ŭ���̾�Ʈ�� ���� ���� �о� ���δ�. 
            CustomObject cur_obj=null;
            
            
           
				try {
					cur_obj=(CustomObject)ois.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
            while(cur_obj!=null){
            	if(cur_obj.version==1){
            		String text=cur_obj.text;
            		SimpleServer.broadCasting(text);
            	}else if(cur_obj.version==2){
            		
            	}else{
            		
            	}
            	try {
					cur_obj=(CustomObject)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            /*
            String str = br.readLine(); 
            String strResult = null; 
            while(str!=null) { 
                //3. Ŭ���̾�Ʈ�� ���� ���� �ִ� ���� SimpleServer�� broadCasting()�޼ҵ带 ���� ����� ��� Ŭ���̾�Ʈ�鿡�� ���� �����Ѵ�. 
                //strResult = SimpleServer.broadCasting(clientID, str);
            	System.out.print(str);
                SimpleServer.broadCasting(str);
                //System.out.println(strResult); 
                str = br.readLine(); 
            } 
            */
        }catch (IOException e) { 
//                e.printStackTrace(); 
        }finally{ 
            //4. io, socket ������ �����Ѵ�. 
            if(br!=null) { 
                try{br.close();}catch(IOException ioe){} 
            } 
            if(out!=null) {
                out.close(); 
            } 
            if(socket!=null) { 
                try{socket.close();}catch(IOException ioe){} 
            } 

            //5. SimpleServer�� removeClient() �� ���� �ڱ� �ڽ��� ��ȭ��󿡼� ���� �Ѵ�.
           // SimpleServer.init_users();
            exitClient(); 

        }//end of finally 
    } 
} 

