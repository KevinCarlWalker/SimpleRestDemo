package com.kevinCarlWalker.web.DataAccess;

import com.kevinCarlWalker.web.Model.Names;

import java.sql.*;

public class DatabaseHelpers {

  private static final String SELECT_NAME_BY_ID = "SELECT * from names where id = ?";
  private static final String INSERT_NAME = "Insert into names (firstName, lastName) values (?, ?)";
  private static final String UPDATE_NAME = "UPDATE names set firstName = ?, lastName = ? where id = ?";
  private static final String DELETE_NAME = "DELETE from names where id = ?";

  public static void initializeDatabase() {
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "simpleRestDemo", "");
      Statement stmt = con.createStatement();

      stmt.execute("drop table if exists names");
      stmt.execute("create table names(id int auto_increment primary key, firstName varchar(100), lastName varchar(100))");
      stmt.execute("insert into names (firstName, lastName) values ('Abraham', 'Lincoln')");
      stmt.execute("insert into names (firstName, lastName) values ('George', 'Washington')");

      ResultSet rs = stmt.executeQuery("SELECT * FROM names");
      while (rs.next()) {
        System.out.println("id :" + rs.getInt("id") + " name :" + rs.getString("firstName") + " " + rs.getString("lastName"));
      }
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static Names getName(Integer nameId) {
    Names name = new Names(0, "", "");
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "simpleRestDemo", "");

      PreparedStatement select = con.prepareStatement(SELECT_NAME_BY_ID);
      select.setInt(1, nameId);

      ResultSet rs = select.executeQuery();
      while (rs.next()) {
        name.setNameId(rs.getInt("id"));
        name.setFirstName(rs.getString("firstName"));
        name.setLastName(rs.getString("lastName"));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return name;
  }

  public static Names createName(Names name) {
    int newNameId = 0;
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "simpleRestDemo", "");

      PreparedStatement insert = con.prepareStatement(INSERT_NAME, Statement.RETURN_GENERATED_KEYS);
      insert.setString(1, name.getFirstName());
      insert.setString(2, name.getLastName());

      insert.executeUpdate();
      ResultSet rs = insert.getGeneratedKeys();
      boolean success = insert.execute();
      while (rs.next()) {
        newNameId = rs.getInt(1);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return getName(newNameId);
  }


  public static Names updateName(Names name) {
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "simpleRestDemo", "");

      PreparedStatement insert = con.prepareStatement(UPDATE_NAME);
      insert.setString(1, name.getFirstName());
      insert.setString(2, name.getLastName());
      insert.setInt(3, name.getNameId());

      insert.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return getName(name.getNameId());
  }

  public static void deleteName(int nameId) {
    try {
      Class.forName("org.h2.Driver");
      Connection con = DriverManager.getConnection("jdbc:h2:~/test", "simpleRestDemo", "");

      PreparedStatement insert = con.prepareStatement(DELETE_NAME);
      insert.setInt(1, nameId);
      insert.execute();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
