import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	
	public static Connection ConnectToDB()
 	{
		Connection mainConnection = null;
	  	try
	  	{
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String URL = "jdbc:oracle:thin:@localhost:1521:hw2";
    	String userName = "system";
    	String password = "hw2";
		

    	mainConnection = DriverManager.getConnection(URL, userName, password);
 
		}
		catch (Exception e)
		{
 		System.out.println( "Error while connecting to DB: "+ e.toString() );
 		e.printStackTrace();
 		System.exit(-1);
		}
	  	
	  	return mainConnection;
 	}
	
	public static void closeDB(Connection c)
	{
		if (c != null)
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
 	}
