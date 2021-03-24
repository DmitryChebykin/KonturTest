package repository;


import java.sql.*;
import java.util.List;


public class H2MemoryDatabase {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/base_measure;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;AUTO_SERVER=TRUE";
    private static final String TABLE_NAME = "measure_table";
    private static final String [] COLUMNS = {"first_measure", "second_measure", "ratio"};
    private static final String DB_NAME = "base_measure";
    private static Connection dbConnection = null;



    private Connection getDBConnection() {

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

    private String CreateSQLStringForCreateTable(String columns[], String db_name){
        String sql_string;
        sql_string = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id_ IDENTITY NOT NULL PRIMARY KEY, ";
        for (String column : columns){
            sql_string = sql_string + " " + column.toString() + "_" + " " + "VARCHAR NOT NULL" + ",";
        }
        sql_string = sql_string.substring(0,sql_string.length()-1) +")";
 //       System.out.println(sql_string);
        return sql_string;
}

    private void CreateTable() throws SQLException {
        Connection dbConnection = getDBConnection();
        Statement stmt = dbConnection.createStatement() ;
        String sql = CreateSQLStringForCreateTable(COLUMNS, DB_NAME);
        stmt.execute("DROP TABLE IF EXISTS " + TABLE_NAME);
        stmt.execute(sql);
    }
    private void InsertRow(String sql) throws SQLException {
        Connection dbConnection = getDBConnection();
        Statement stmt = dbConnection.createStatement() ;
        stmt.execute(sql);
    }
    public void AddArrayListToTable (List<String[]> list){

        try {
            CreateTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sqlInsertRow = new String();


        for (String[] row : list){
            sqlInsertRow = "INSERT INTO " + TABLE_NAME  + "( first_measure_ , second_measure_ , ratio_ ) " + "values"+ " " + "( ";

            for (String element: row){
                sqlInsertRow = sqlInsertRow + "'" + element + "'" + ", ";
            }
            sqlInsertRow = sqlInsertRow.substring(0,sqlInsertRow.length()-2) +")";
            try {
                InsertRow(sqlInsertRow);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
