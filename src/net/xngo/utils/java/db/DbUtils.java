package net.xngo.utils.java.db;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
/**
 * http://stackoverflow.com/questions/2708689/impact-of-java-sql-connection-close-on-java-sql-statement-objects-and-the-like
 * @author Xuan Ngo
 *
 */
public class DbUtils
{
  public static void close(Connection connection)
  {
    try
    {
      if(connection != null)
      {
        connection.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void close(ResultSet resultSet)
  {
    try
    {
      if(resultSet != null)
      {
        resultSet.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void close(Statement stmt)
  {
    try
    {
      if(stmt != null)
      {
        stmt.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
}
