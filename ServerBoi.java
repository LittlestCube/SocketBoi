import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerBoi implements Runnable
{
	static BufferedReader userin;
	
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	static PrintWriter out;
	
	static boolean connected = false;
	
	public static void main(String[] args)
	{
		ServerBoi sb = new ServerBoi();
		
		Thread t = new Thread(sb);
		
		t.start();
		
		try
		{
			ServerSocket server = new ServerSocket(1234);
			
			Socket client = null;
			
			if (server == null)
			{
				System.out.println("something boinked, server is null");
				
				System.exit(1);
			}
			
			while (true)
			{
				client = server.accept();
				
				userin = new BufferedReader(new InputStreamReader(System.in));
				
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				out = new PrintWriter(client.getOutputStream(), false);
				
				out.println("Hello!");
				out.flush();
				
				System.out.println("N: New connection from " + client.getInetAddress().toString().substring(1) + "!");
				
				connected = true;
				
				while (connected)
				{
					if (userin.ready())
					{
						out.println(userin.readLine());
						out.flush();
					}
				}
				
				in.close();
				out.close();
				
				client.close();
			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			in.mark(0);
			
			String inStr;
			
			while (true)
			{
				Thread.sleep(0); // java loop glitch workaround
				
				while (connected)
				{
					if (in.ready())
					{
						inStr = in.readLine();
						
						System.out.println("Client: " + inStr);
					}
					
					else
					{
						in.mark(1);
						
						if (in.read() == -1)
						{
							System.out.println("N: Client disconnected.");
							connected = false;
							
							break;
						}
						
						in.reset();
					}
				}
			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}