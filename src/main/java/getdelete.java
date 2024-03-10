import java.sql.*;

public class getdelete {

    public static ResultSet getAllBlocks(Connection db)
    {
        try{
            Statement st = db.createStatement();
            return st.executeQuery("select * from blocks");
        }
        catch(SQLException e)
        {
            System.out.println("An error has occurred.");
            return null;
        }
    }

    public static void delete(Connection db, int blockID)
    {
        try{
            Statement st = db.createStatement();
            st.executeQuery("delete * from blocks where id = " + blockID);
        }
        catch(SQLException e)
        {
            System.out.println("An error has occurred.");
        }
    }

}
