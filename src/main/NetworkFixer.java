package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

public class NetworkFixer {
	
	public StreamWrapper getStreamWrapper(InputStream is, String type){
		return new StreamWrapper(is, type);
	}
	
	//Global
    static Runtime rt = Runtime.getRuntime();
    static NetworkFixer rte = new NetworkFixer();
    static StreamWrapper output;
    static ConsoleHandler r = new ConsoleHandler();
    //end of global
    
	private class StreamWrapper extends Thread {
	    InputStream is = null;
	    String type = null;          
	    String message = null;
	
	    public String getMessage() {
	            return message;
	    }
	
	    StreamWrapper(InputStream is, String type) {
	        this.is = is;
	        this.type = type;
	    }
	
	    public void run() {
	        try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            StringBuffer buffer = new StringBuffer();
	            String line = null;
	            while ( (line = br.readLine()) != null) {
	                buffer.append(line);//.append("\n");
	            }
	            message = buffer.toString();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();  
	        }
	    }
	}
	
	public static String MainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("///////////////////////////////");
		System.out.println("/Welcome to lods Network Fixer/");
		System.out.println("/1) Flush DNS                 /");
		System.out.println("/2) Renew IP                  /");
		System.out.println("/3) Exit                      /");
		System.out.println("/     More coming soon        /");
		System.out.println("///////////////////////////////");
		
		String reply = sc.next();
		return reply;
	}
	public static void Validate(String reply) {
		if(reply.equalsIgnoreCase("1")) {
			Command("ipconfig /flushdns");
		}
		else if(reply.equalsIgnoreCase("2")) {
			Command("ipconfig /renew");
		} 
		else if(reply.equalsIgnoreCase("3")) {
			int exitVal = 0;
			System.exit(exitVal);
		} else if(reply.equalsIgnoreCase("lod")) { 
			System.out.println("wow we got a sneaky salad thanks for the memes ^_^");
		} else {
			MainMenu();
			System.out.println("Incorrect input please see the list above!!");
		}
	}
	public static void Command(String Command) {
		 try {
             Process proc = rt.exec(Command);
             output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
             int exitVal = 0;

             output.start();
             output.join(3000);
             exitVal = proc.waitFor();
             System.out.println("Success: "+output.message);
			 } catch (IOException e) {
				 e.printStackTrace();
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
	} //end of flush

	
// this is where the action is
	public static void main(String[] args) {
		while(true) {
			Validate(MainMenu());
		
		}//end of while loop
	}//end of main
}//end of Class