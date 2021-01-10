package pacManPackage;

import java.io.IOException;

public class PacManMainClass {

	public static void main(String[] args)
	{
		try
		{
			Player pacPlayer = new Player();
			PacManView pacView = new PacManView(pacPlayer);			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

}
