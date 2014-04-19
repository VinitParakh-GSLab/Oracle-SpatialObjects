import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class populate
{
	
	public static void main(String[] args)
    {	
		insert_into_buildings(args[0]);
		insert_into_students(args[1]);
		insert_into_as(args[2]);
    }

	
	public static void insert_into_as(String filename)
	{
		try {
			
			Connection connection = dbConnection.ConnectToDB();
			Statement mainStatement = connection.createStatement();

			mainStatement.executeQuery("truncate table vrp_announcement");
			System.out.println("AnnouncementSystems Table Truncated");
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str;
			
			while(( str = br.readLine())!= null)
			{
				String [] strLine = str.split(",");
				int point1 = Integer.parseInt(strLine[1].trim()) + Integer.parseInt(strLine[3].trim());
				int point2 = Integer.parseInt(strLine[1].trim()) - Integer.parseInt(strLine[3].trim());
				int point3 = Integer.parseInt(strLine[2].trim()) - Integer.parseInt(strLine[3].trim());
				
				String query = "Insert into vrp_announcement values ('" + strLine[0].trim() + "', SDO_GEOMETRY(2003, "
						+ "NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + point1 + "," + strLine[2] + "," + point2 + ","
						+ strLine[2] + "," +  strLine[1] + "," + point3 + "))," + strLine[3] + "," + strLine[1] +  "," + strLine[2] 
						+ ", SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + strLine[1].trim() + "," + strLine[2].trim() + ", NULL), NULL, NULL))";
				
				mainStatement.executeQuery(query);
				
			}
			
			System.out.println("AnnouncementSystems Data Inserted");
			br.close();
			dbConnection.closeDB(connection);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
	public static void insert_into_students(String filename)
	{
		try {
			
			Connection connection = dbConnection.ConnectToDB();
			Statement mainStatement = connection.createStatement();
			
			mainStatement.executeQuery("truncate table vrp_students");
			System.out.println("Students Table Truncated");
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str;
			while((str = br.readLine())!= null)
			{
				String [] strLine = str.split(",");
				String query = "Insert into vrp_students values ('" + strLine[0].trim() + "' , SDO_GEOMETRY(2001, "
						+ "NULL, SDO_POINT_TYPE(" + strLine[1].trim() + "," + strLine[2].trim() + ", NULL), NULL, NULL))";
				
				mainStatement.executeQuery(query);
				
			}
			
			System.out.println("Students Data Inserted");
			br.close();
			dbConnection.closeDB(connection);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void insert_into_buildings(String filename)
	{
		try {
			
			Connection connection = dbConnection.ConnectToDB();
			Statement mainStatement = connection.createStatement();
			
			mainStatement.executeQuery("truncate table vrp_buildings");
			System.out.println("Buildings Table Truncated");
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String str;
			
			while((str = br.readLine())!= null)
			{
				String [] strLine = str.split(",");
				String ordinates = new String();
				for (int i = 0; i < Integer.parseInt(strLine[2].trim())*2; i++ )
				{
					if(i == ((Integer.parseInt(strLine[2].trim())*2) - 1))
					{
						ordinates = ordinates.concat(strLine[i+3].trim()).concat(")");
					}
					else
					{
						ordinates = ordinates.concat(strLine[i+3].trim()).concat(", ");
					}
				}
				
				String query = "Insert into vrp_buildings values ('" + strLine[0].trim() + "', '" + strLine[1].trim() + "', " + strLine[2].trim() + ", SDO_GEOMETRY(2003,"
						+ "NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + ordinates + "))";
				
				mainStatement.executeQuery(query);
				
			}
			
			System.out.println("Buildings Table Inserted");
			br.close();
			dbConnection.closeDB(connection);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}

