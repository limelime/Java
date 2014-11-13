package net.xngo.utils.test.java.db;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.xngo.utils.java.db.Connection;
import net.xngo.utils.java.db.DbUtils;

import org.testng.annotations.Test;

public class ConnectionTest
{
  private Connection connection = new Connection();
  private final String jdbcClassLoader  = "org.sqlite.JDBC";
  private final String dbUrlMemory      = "jdbc:sqlite::memory:"; 

  @Test(description="CREATE a table, INSERT a row, SELECT a row.")
  public void createInsertSelect()
  {
    try
    {
      /** Create default database **/
      this.createDefaultDatabase();

      /** Creating test data **/
      String expectedFirstname = "Xuan";
      String expectedLastname = "Ngo";
      String queryInsert = String.format("INSERT INTO Person VALUES('%s', '%s')", expectedFirstname, expectedLastname);
      this.connection.prepareStatement(queryInsert);
      this.connection.executeUpdate();
      
      String querySelect = String.format("SELECT first_name, last_name FROM Person WHERE first_name='%s' AND last_name='%s'",  
                                                                                            expectedFirstname, expectedLastname);
      this.connection.prepareStatement(querySelect);
      ResultSet resultSet = connection.executeQuery();
      
      /** Main test **/
      String actualFirstname = "";
      String actualLastname = "";
      
      int rows = 0;
      while(resultSet.next())
      {
        int j=1;
        actualFirstname = resultSet.getString(j++);
        actualLastname  = resultSet.getString(j++);
        rows++;
      }
      assertEquals(rows, 1, "There should be only 1 row returned.");
      assertEquals(actualFirstname, expectedFirstname);
      assertEquals(actualLastname, expectedLastname);
      
      
      // Clean up.
      DbUtils.close(resultSet);
      connection.closePreparedStatement();
      
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  @Test(description="Test exception thrown without commit().")
  public void commitWithException()
  {
    String tmpDbUrl = "";
    final String commitFirstname    = "Xuan";
    final String commitLastname     = "Ngo";
    final String uncommitFirstname  = "John";
    final String uncommitLastname   = "Smith";    
    try
    {
      // Create database in a file.
      File tmpDbFile = File.createTempFile("test_commitWithException", ".db");
      tmpDbUrl = String.format("jdbc:sqlite:%s", tmpDbFile.getAbsolutePath());
      
      this.connection.connect(this.jdbcClassLoader, tmpDbUrl);
      this.connection.setAutoCommit(false);
      this.createDefaultTable();
      
      /** Creating test data **/
      this.addPerson(commitFirstname, commitLastname);
      this.connection.commit();
      this.addPerson(uncommitFirstname, uncommitLastname);
      throw new SQLException("Intended to throw an exception without commit().");

    }
    catch(SQLException ex)
    {
      // Close everything to simulate a crash.
      this.connection.close();
    }
    catch(IOException ex){ ex.printStackTrace(); } // Handling exception for File.createTempFile().
    finally
    {
      // Re-open database file.
      this.connection.connect(this.jdbcClassLoader, tmpDbUrl);
      
      try
      {
        /** Main test: (1)Committed person should still in the database whereas (2)un-committed person should not. **/
        // (1): Committed person should still in the database.
        ResultSet commitSet = this.getPerson(commitFirstname, commitLastname);
        String actualFirstname = "";
        String actualLastname  = "";      
        int rows = 0;
        while(commitSet.next())
        {
          int j=1;
          actualFirstname = commitSet.getString(j++);
          actualLastname  = commitSet.getString(j++);
          rows++;
        }
        assertEquals(rows, 1, "There should be only 1 row returned.");
        assertEquals(actualFirstname, commitFirstname);
        assertEquals(actualLastname, commitLastname);
        
        // (2): un-committed person should not be in database.
        ResultSet uncommitSet = this.getPerson(uncommitFirstname, uncommitLastname);
        rows = 0;
        while(uncommitSet.next())
        {
          rows++;
        }
        assertEquals(rows, 0, String.format("Data(%s, %s) are not committed yet. There should be no row returned.", uncommitFirstname, uncommitLastname));
      }
      catch(SQLException ex){ ex.printStackTrace(); }
    }
  }
  
  @Test(description="Commit without anything to commit.")
  public void commitEmpty()
  {
    this.createDefaultDatabase();
    try
    {
      this.connection.commit();
      this.connection.commit();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  @Test(description="Test rollback().")
  public void rollback()
  {
    final String commitFirstname    = "Xuan";
    final String commitLastname     = "Ngo";
    final String rollbackFirstname  = "John";
    final String rollbackLastname   = "Smith";    
    try
    {
      // Create default database.
      this.createDefaultDatabase();
      this.connection.setAutoCommit(false);
      
      /** Creating committed test data **/
      this.addPerson(commitFirstname, commitLastname);
      this.connection.commit();
      
      /** Main test **/
      // Add a new person and make sure you can query that person.
      this.addPerson(rollbackFirstname, rollbackLastname);
      ResultSet uncommitSet = this.getPerson(rollbackFirstname, rollbackLastname);
      int rows = 0;
      while(uncommitSet.next())
      {
        rows++;
      }      
      assertEquals(rows, 1, String.format("There should be only 1 row returned because %s, %s is added.", rollbackFirstname, rollbackLastname));
      
      // Rollback the new person.
      this.connection.rollback();
      uncommitSet = this.getPerson(rollbackFirstname, rollbackLastname);
      rows = 0;
      while(uncommitSet.next())
      {
        rows++;
      }      
      assertEquals(rows, 0, String.format("There should be no row returned because %s, %s is rolled back.", rollbackFirstname, rollbackLastname));      

    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
    
  }
  
  @Test(description="Test isTableExists(): Correct table name.")
  public void isTableExistsCorrectTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertTrue(this.connection.isTableExists("Person"));
  }
  
  @Test(description="Test isTableExists(): Incorrect table name.")
  public void isTableExistsIncorrectTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isTableExists("X987432BlaBla"));
  }
  
  @Test(description="Test isTableExists(): Empty table name.")
  public void isTableExistsEmptyTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isTableExists(""));
  }
  
  @Test(description="Test isTableExists(): Empty table name.")
  public void isTableExistsNullTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isTableExists(null));
  }  
  
  
  @Test(description="Test isColumnExists(): Correct column name.")
  public void isColumnExistsCorrectColumnName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertTrue(this.connection.isColumnExists("Person", "first_name"));
  }
  
  @Test(description="Test isColumnExists(): Incorrect column name.")
  public void isColumnExistsIncorrectColumnName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists("Person", "ff32irst_name32131231"));
  }
  
  @Test(description="Test isColumnExists(): Empty column name.")
  public void isColumnExistsEmptyColumnName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists("Person", ""));
  }
  
  @Test(description="Test isColumnExists(): Null column name.")
  public void isColumnExistsNullColumnName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists("Person", null));
  }
  
  @Test(description="Test isColumnExists(): Wrong table name.")
  public void isColumnExistsWrongTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists("P324er432son", "first_name"));
  }
  
  @Test(description="Test isColumnExists(): Empty table name.")
  public void isColumnExistsEmptyTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists("", "first_name"));
  }     
  
  @Test(description="Test isColumnExists(): Null table name.")
  public void isColumnExistsNullTableName()
  {
    /** Create default database **/
    this.createDefaultDatabase();
    
    //** Main test: Check Person should exist.
    assertFalse(this.connection.isColumnExists(null, "first_name"));
  }  
  

  /***********************************************************************************
   *                        Test data creation helpers
   ***********************************************************************************/
  
  /**
   * Create a default database in memory containing a Person table.
   */
  private void createDefaultDatabase()
  {
    /** Create database in memory **/
    this.connection.connect(this.jdbcClassLoader, this.dbUrlMemory);
    
    this.createDefaultTable();
  }
  
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
  
  private void addPerson(String firstname, String lastname)
  {
    try
    {
      String queryInsert = String.format("INSERT INTO Person VALUES('%s', '%s')", firstname, lastname);
      this.connection.prepareStatement(queryInsert);
      this.connection.executeUpdate();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }    
  }
  
  private ResultSet getPerson(String firstname, String lastname)
  {
    try
    {
      String querySelect = String.format("SELECT first_name, last_name FROM Person WHERE first_name='%s' AND last_name='%s'",  
                                              firstname, lastname);
      this.connection.prepareStatement(querySelect);
      ResultSet resultSet = connection.executeQuery();
      return resultSet;
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
    
    return null;
    
  }
}
