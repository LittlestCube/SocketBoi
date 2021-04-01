import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketBoi
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
					boolean end = false;
					
					while (!end)
					{
						Thread.sleep(0); // java loop glitch workaround
						
						while (connected)
						{
							if (in.ready())
							{
								System.out.println("Server: " + in.readLine());
							}
							
							else
							{
								in.mark(1);
								
								if (in.read() == -1)
								{
									System.out.println("N: Server disconnected.");
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
			userin = new BufferedReader(new InputStreamReader(System.in));
			
			Socket socket = new Socket(args[0], 1234);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out = new PrintWriter(socket.getOutputStream(), true);
			
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
			
			socket.close();
			
			return;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}