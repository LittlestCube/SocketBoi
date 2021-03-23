import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketBoi
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
			
			Socket socket = new Socket("localhost", 1234);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			String inStr = "";
			
			String outStr = "";
			
			while (true)
			{
				if (in.ready())
				{
					inStr = in.readLine();
					
					System.out.println("Server: " + inStr);
				}
				
				if (inStr.equals(":quit"))
				{
					break;
				}
				
				if (userin.ready())
				{
					outStr = userin.readLine();
					
					out.println(outStr);
					out.flush();
				}
			}
			
			in.close();
			out.close();
			
			socket.close();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}