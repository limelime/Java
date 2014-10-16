package net.xngo.utils.java.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.xngo.utils.java.util.CircularArrayList;

public class Connection
{
  public java.sql.Connection  connection        = null;
  public PreparedStatement    preparedStatement = null;
  
  // Logging purposed only.
  private boolean           log         = false;
  private String            query       = "";
  private ArrayList<String> values      = new ArrayList<String>();
  private CircularArrayList<String> queries = null;
  
  
  public Connection(boolean log, int queryLogSize)
  {
    this.log      = log;
    this.queries  = new CircularArrayList<String>(queryLogSize);
  }
  
  public Connection()
  {
    this(false, 0);
  }
  
  
  /****************************************************************************
   * 
   *                             GENERIC FUNCTIONS
   * 
   ****************************************************************************/
  
  /**
   * @param jdbcClassLoader e.g. "org.sqlite.JDBC"
   * @param url e.g. "jdbc:sqlite:database_file_path"
   */
  public void connect(String jdbcClassLoader, String dbUrl)
  {
    try
    {
      // Load the JDBC driver using the current class loader
      Class.forName(jdbcClassLoader);
      
      // Create a database connection
      this.connection = DriverManager.getConnection(dbUrl);
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    
  }
  
  public void setAutoCommit(boolean autoCommit) throws SQLException
  {
    this.connection.setAutoCommit(autoCommit);
  }
  
  public void commit() throws SQLException
  {
    this.connection.commit();
  }
  
  public void rollback() throws SQLException
  {
    this.connection.rollback();
  }
  
  
  public PreparedStatement prepareStatement(String sql) throws SQLException
  {
    // New query.
    this.query = sql;     // Update query string. 
    this.values.clear();  // Reset query values.
    
    this.preparedStatement = this.connection.prepareStatement(sql);
    return this.preparedStatement;
  }
  
  /**
   * Executes the SQL query in this PreparedStatement object and returns the ResultSet object generated by the query.
   * @return a ResultSet object that contains the data produced by the query; never null
   * @throws SQLException
   */
  public ResultSet executeQuery() throws SQLException
  {
    if(this.log) { this.queries.add(this.getQueryString()); }
    
    return this.preparedStatement.executeQuery();    
  }
  
  /**
   * Executes the SQL statement in this PreparedStatement object, 
   * which must be an SQL Data Manipulation Language (DML) statement, 
   * such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, 
   * such as a DDL statement.
   * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
   */
  public int executeUpdate()
  {
    try
    {
      if(this.log) { this.queries.add(this.getQueryString()); }

      return this.preparedStatement.executeUpdate();
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
    return 0;
  }
  
  public ResultSet getGeneratedKeys() throws SQLException
  {
    return this.preparedStatement.getGeneratedKeys();
  }
  
  public void setString(int parameterIndex, String x) throws SQLException
  {
    // Log query value.
    //  Make NULL or empty value more explicite.
    if(x == null)
      this.values.add("<null>");
    else
    {
      if(x.isEmpty()) 
        this.values.add("<empty>"); 
      else
        this.values.add(x);
    }

    this.preparedStatement.setString(parameterIndex, x);
  }
  
  public void setInt(int parameterIndex, int x) throws SQLException
  {
    // Log query value.
    this.values.add(x+"");
    
    this.preparedStatement.setInt(parameterIndex, x);
  }
  
  public void setLong(int parameterIndex, long x) throws SQLException
  {
    // Log query value.
    this.values.add(x+"");
    
    this.preparedStatement.setLong(parameterIndex, x);
  }
  
  public void closePreparedStatement()
  {
    try
    {
      if(this.preparedStatement != null)
      {
        this.preparedStatement.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void close()
  {
    // Close prepared statement.
    this.closePreparedStatement();
    
    // Close connection.
    try
    {
      if(this.connection != null)
      {
        this.connection.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public String getQueryString()
  {
    if(values.size()>0)
    {
      StringBuilder valuesStr = new StringBuilder();
      for(int i=0; i<values.size()-1; i++)
      {
        valuesStr.append(values.get(i));
        valuesStr.append(", ");
      }
      valuesStr.append(values.get(values.size()-1));
      
      return String.format("%s : %s", this.query, valuesStr.toString());
    }
    else
    {
      return this.query;
    }
  }
  
  public ArrayList<String> getLoggedQueries()
  {
    return this.queries;
  }
  
}
