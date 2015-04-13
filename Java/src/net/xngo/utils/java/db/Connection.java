package net.xngo.utils.java.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class Connection
{
  public java.sql.Connection  connection        = null;
  public PreparedStatement    preparedStatement = null;
  
  private String            query   = "";
  private ArrayList<String> values  = new ArrayList<String>();

  public Connection()
  {
  }
  /****************************************************************************
   * 
   *                             GENERIC FUNCTIONS
   * 
   ****************************************************************************/
  
  /**
   * @param jdbcClassLoader e.g. "org.sqlite.JDBC"
   * @param url e.g. "jdbc:sqlite:<database_file_path>" or "jdbc:sqlite::memory:"
   */
  public void connect(String jdbcClassLoader, String dbUrl)
  {
    try
    {
      // Load the JDBC driver using the current class loader
      Class.forName(jdbcClassLoader);
      
      // Create a database connection
      this.connection = DriverManager.getConnection(dbUrl);
      
      // By default, force set of transactions atomic.
      this.setAutoCommit(false); 
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
    // commit() only if autocommit is off.
    if(!this.connection.getAutoCommit())
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
    //  Make NULL or empty value more explicit.
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
  
  public void setObject(int parameterIndex, Object x) throws SQLException
  {
    // Log query value.
    this.values.add(x.toString());
    
    this.preparedStatement.setObject(parameterIndex, x);    
  }
  
  /**
   * Adds a set of parameters to this PreparedStatement object's batch of commands.
   * @throws SQLException
   */
  public void addBatch() throws SQLException
  {
    this.preparedStatement.addBatch();
  }

  public int[] executeBatch() throws SQLException
  {
    return this.preparedStatement.executeBatch();
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
  
  public boolean isClose() throws SQLException
  {
    return this.connection.isClosed();
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
  
  public boolean isColumnExists(String tableName, String columnName)
  {
    // Validate input values.
    if(tableName==null)
      return false;
    if(tableName.isEmpty())
      return false;
    
    if(columnName==null)
      return false;
    if(columnName.isEmpty())
      return false;
    
    // Main logic start here.
    try
    {
      DatabaseMetaData metadata = this.connection.getMetaData();
      
      ResultSet resultSet = metadata.getColumns(null, null, tableName, columnName);
      if(resultSet.next())
        return true;
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
    return false;
  }
  
  public boolean isTableExists(String tableName)
  {
    // Validate input values.
    if(tableName==null)
      return false;
    if(tableName.isEmpty())
      return false;
    
    // Main logic start here.
    try
    {
      DatabaseMetaData metadata = this.connection.getMetaData();
      ResultSet resultSet = metadata.getTables(null, null, tableName, null);
      if(resultSet.next())
      {
        //System.out.println("Table Xuan"+resultSet.getString("TABLE_NAME"));
        return true;
      }
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
    return false;
  }
  
}
