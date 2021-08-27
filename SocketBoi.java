import java.io.BufferedReader;
import java.io.PrintWriter;

import java.io.IOException;

import java.net.Socket;

import java.io.InputStreamReader;

public class SocketBoi
{
	static BufferedReader userin;
	static BufferedReader in;
	
	static PrintWriter out;
	
	static boolean connected = false;
	
	public static void main(String[] args) throws IOException
	{
		SocketGui.makeJFrame("SuperSocket");
		
		boolean exit = false;
		
		while (!exit)
		{
			Thread readThread = new Thread()
			{
				public void run()
				{
					try
					{
						boolean end = false;
						
						while (!end)
						{
							Thread.sleep(0);
							
							while (connected)
							{
								if (in.ready())
								{
									SocketGui.printText("Server: " + in.readLine());
								}
								
								else
								{
									in.mark(1);
									
									if (in.read() == -1)
									{
										SocketGui.printText("N: Server disconnected.");
										connected = false;
										end = true;
									}
									
									else
									{
										in.reset();
									}
								}
							}
						}
					}
					
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			};
			
			readThread.start();
			
			try
			{
				Socket socket;
				
				SocketGui.printText("Enter address to connect to!");
				
				while (SocketGui.lastMessage.isEmpty())
				{
					Thread.sleep(0);
				}
				
				String addr = SocketGui.lastMessage;
				SocketGui.lastMessage = "";
				
				socket = new Socket(SocketGui.lastMessage, 1234);
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), false);
				
				connected = true;
				
				SocketGui.jTextArea1.setText("");
				
				while (connected)
				{
					Thread.sleep(0);
					
					if (!SocketGui.lastMessage.isEmpty())
					{
						out.println(SocketGui.lastMessage);
						out.flush();
						
						SocketGui.lastMessage = "";
					}
				}
				
				in.close();
				
				socket.close();
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}