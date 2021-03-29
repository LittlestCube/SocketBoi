import java.net.Socket;
import java.net.ServerSocket;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerBoi
{
	public static void main(String[] args)
	{
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
				
				BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
				
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), false);
				
				out.println("Hello!");
				out.flush();
				
				String inStr = "";
				String outStr = "";
				int c = -2;
				
				while (client.getInetAddress().isReachable(5))
				{
					if (userin.ready())
					{
						outStr = userin.readLine();
						
						if (outStr.equals(":quit"))
						{
							break;
						}
						
						out.println(outStr);
						out.flush();
					}
					
					if (in.ready())
					{
						if ((c = in.read()) == -1)
						{
							System.out.println("Client disconnected.");
							
							break;
						}
						
						inStr += (char) c;
						
						while ((c = in.read()) != '\n')
						{
							inStr += (char) c;
						}
						
						System.out.println("Client: " + inStr);
						
						inStr = "";
					}
				}
				
				userin.close();
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