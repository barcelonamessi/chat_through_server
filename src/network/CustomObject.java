package network;


import java.io.Serializable;
import java.util.Vector;



public class CustomObject implements Serializable{
	/**
	 * 
	 */
	
	public int version;
	public String text;
	public Vector<String> userlist;
	
	public CustomObject(int version, String text){
		this.version=version;
		this.text=text;
		this.userlist=null;
	}
	
	public CustomObject(int version, String text, Vector<String> userlist){
		this.version=version;
		this.text=text;
		this.userlist=userlist;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Vector<String> getUserlist() {
		return userlist;
	}
	public void setUserlist(Vector<String> userlist) {
		this.userlist = userlist;
	}
	
	
}