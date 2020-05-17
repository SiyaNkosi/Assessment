package com.iLab.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLUtility {
	
	
	public static ResultSet getQueryResults(String strConnectionString,String strSqlQuery) 
	{
		
		try{  
				//Load the driver class  
				Class.forName("oracle.jdbc.driver.OracleDriver");  
	
				//Create  the connection object  
				Connection con=DriverManager.getConnection(strConnectionString);  
	
				//Create the statement object  
				Statement stmt=con.createStatement();  
	
				//Execute query  
				ResultSet rs=stmt.executeQuery(strSqlQuery);  
				
				//Close the connection object  
				con.close();  
	
				return rs;
				//while(rs.next())  
					
				//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	
			}
		catch(Exception e)
			{ 
			
				System.out.println(e);
			}
				
				return null;  

			}  
		
	}


