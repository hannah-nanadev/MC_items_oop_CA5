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


    /**
     *  Feature 4 - insert new block into database
     *  Made by Ruby, copied from Ruby's github on 14.3.2024
     */
    @Override
    public void insertABlock(Block block) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            // Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "INSERT INTO blocks (name, hardness, blast_resistance, gravity_affected) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // Set parameters for the PreparedStatement
            preparedStatement.setString(1, block.getName());
            preparedStatement.setDouble(2, block.getHardness());
            preparedStatement.setDouble(3, block.getBlastResistance());
            preparedStatement.setBoolean(4, block.isGravityAffected());

            // Execute the update
            preparedStatement.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DaoException("insertABlock() " + e.getMessage());
        }
        finally
        {
            // Close resources in finally block
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException ex)
            {
                // Handle exception while closing resources
                throw new DaoException("Error closing resources: " + ex.getMessage());
            }
        }
    }
}