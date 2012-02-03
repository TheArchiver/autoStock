package com.autoStock;

import com.autoStock.comServer.ConnectionServer;
import com.autoStock.internal.ApplicationStates;
import com.autoStock.internal.Global;
import com.autoStock.internal.Global.Mode;
/**
 * @author Kevin Kowalewski
 *
 */
public class MainServer {
	public static volatile String appleState = "Oranges";
	public static Thread runningThread;
	
	public static void main(String[] args) {
		Global.mode = Mode.server;
		Co.println("Welcome to autoStock\n");
		
		ApplicationStates.startup();
		
		runningThread = new Thread(new Runnable(){
			@Override
			public void run() {
				while (true){
					try{Thread.sleep(1000);}catch(InterruptedException e){return;}
					//Co.println("Apples are: " + appleState);
				}
			}
		});
		
		runningThread.start();

		new ConnectionServer().startServer();
		
		Co.println("Done");
	}
}
