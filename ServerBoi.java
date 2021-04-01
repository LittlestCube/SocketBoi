import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerBoi
{
	static BufferedReader userin;
	static BufferedReader in;
	
	static PrintWriter out;
	
	static boolean connected = false;
	
	public static void main(String[] args)
	{
		Thread readThread = new Thread()
		{
			public void run()
			{
				try
				{
					while (true)
					{
						Thread.sleep(0); // java loop glitch workaround
						
						while (connected)
						{
							if (in.ready())
							{
								System.out.println("Client: " + in.readLine());
							}
							
							else
							{
								in.mark(1);
								
								if (in.read() == -1)
								{
									System.out.println("N: Client disconnected.");
									connected = false;
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
			ServerSocket server = new ServerSocket(1234);
			
			Socket client = null;
			
			if (server == null)
			{
				System.out.println("E: Server is null");
				
				System.exit(1);
			}
			
			while (true)
			{
				client = server.accept();
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				userin = new BufferedReader(new InputStreamReader(System.in));
				
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
}