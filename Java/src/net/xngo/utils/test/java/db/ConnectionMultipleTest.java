package net.xngo.utils.test.java.db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.xngo.utils.java.db.Connection;

import org.testng.annotations.Test;

public class ConnectionMultipleTest
{
  private final String jdbcClassLoader  = "org.sqlite.JDBC";
  private final Connection connection = new Connection();
  private final Connection connection2 = new Connection();
  private final File dbFile = new File("./ConnectionMultipleTest.db");
  
  public ConnectionMultipleTest()
  {
    String dbUrl = String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
    connection.connect(this.jdbcClassLoader, dbUrl);
    connection2.connect(this.jdbcClassLoader, dbUrl);
    
    this.createDefaultTable();
  }
  
  @Test(description="Test multiple connection to the same database file. SQLITE can't handle multiple connection.", expectedExceptions = {SQLException.class})
  public void connectionMultipleRead() throws SQLException
  {

    // Read Person table using the main connection.
    String querySelect = "SELECT * FROM Person";
    connection.prepareStatement(querySelect);
    ResultSet resultSet = connection.executeQuery();
    connection.closePreparedStatement();
    
    // Read Person table using the second connection.
    connection2.prepareStatement(querySelect);
    ResultSet resultSet2 = connection2.executeQuery();    
  }
  
  
  /***********************************************************************************
   *                        Test data creation helpers
   ***********************************************************************************/
  private void createDefaultTable()
  {
    try
    {
      /** Creating test table **/
      String queryCreate = "CREATE TABLE Person(first_name TEXT, last_name TEXT)";
      connection.prepareStatement(queryCreate);
      connection.executeUpdate();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }     
  }
}
