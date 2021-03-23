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
			
			while (true)
			{
				if (server == null)
				{
					System.out.println("something boinked, server is null");
					
					System.exit(1);
				}
				
				client = server.accept();
				
				
				
				BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
				
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), false);
				
				out.println("sup");
				out.flush();
				
				out.println("asdf");
				out.flush();
				
				String outStr = "";
				
				while (true)
				{
					if (in.ready())
					{
						System.out.println("Client: " + in.readLine());
					}
					
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