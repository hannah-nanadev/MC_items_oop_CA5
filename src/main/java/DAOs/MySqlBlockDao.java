package DAOs;

import DTOs.Block;
import Exceptions.DaoException;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

/** Base code taken from oop-data-access-layer-sample-1
 *  Rewritten by Jakub Polacek
 *  Methods/features added over time by the group
 */

public class MySqlBlockDao extends MySqlDao implements BlockDaoInterface
{

    /**
     * Feature 2 - finding entity by ID
     * by Jakub Polacek
     * Returns Block object if found, null in any other case
     */
    @Override
    public Block getBlockById(int id) throws DaoException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try
        {
            connection = this.getConnection();
            String query = "SELECT * FROM blocks WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            result = statement.executeQuery();

            if(result.next())
            {
                return new Block(id,
                        result.getString("name"),
                        result.getDouble("hardness"),
                        result.getDouble("blast_resistance"),
                        result.getBoolean("gravity_affected"));
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getBlockById() " + e.getMessage());
        }
        finally
        {
            try
            {
                if (result != null)
                {
                    result.close();
                }
                if (statement != null)
                {
                    statement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getBlockById() " + e.getMessage());
            }
        }
        return null;
    }
}