package DAOs;

import DTOs.Block;
import Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Base code taken from oop-data-access-layer-sample-1
 *  Rewritten by Jakub Polacek
 *  Methods/features added over time by the group
 */

public class MySqlBlockDao extends MySqlDao implements BlockDaoInterface
{

    /**
     * Feature 1 - get all blocks
     * Made by Ruby, copied from github on 14.3.2024
     */
    @Override
    public List<Block> findAllBlocks() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Block> blockList = new ArrayList<>();

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM blocks";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double hardness = resultSet.getDouble("hardness");
                double blastResistance = resultSet.getDouble("blast_resistance");
                boolean gravityAffected = resultSet.getBoolean("gravity_affected");
                Block blockToAdd = new Block(id, name, hardness, blastResistance, gravityAffected);
                blockList.add(blockToAdd);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUseresultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return blockList;     // may be empty
    }


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
     *  Feature 3 - Delete block from database by ID
     *  Method implementation by Hannah Kellett, written 18/03/2024 (late but shhh)
     **/

    public void deleteBlockByID(int blockID) throws DaoException
    {
        Connection connection = null;

        try{
            connection = this.getConnection();

            Statement st = connection.createStatement();
            st.executeQuery("delete * from blocks where id = " + blockID);
        }
        catch(SQLException e)
        {
            throw new DaoException("deleteBlockByID() " + e.getMessage());
        }
        finally
        {
            try
            {
                if(connection!=null) {
                    freeConnection(connection);
                }
            }
            catch(SQLException e)
            {
                throw new DaoException("deleteBlockByID() " + e.getMessage());
            }
        }
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
            preparedStatement.setBoolean(4, block.getGravityAffected());

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

    /**
     *  Feature 6 - get filtered list of blocks
     *  Made by Jakub Polacek
     *  Uses java streams, pipeline of object references and methods for use with bigger data sets
     *  Takes in a predicate - a lambda function or method reference to filter the blocks by
     *  example of method ref: 'Block::getGravityAffected'
     *  example of lambda: '(e) -> e.getHardness() == 0.6' or '(e) -> e.getName().equals("Cobble")'
     */

    @Override
    public List<Block> findBlocksUsingFilter(Predicate<Block> filter) throws DaoException
    {
        return findAllBlocks().stream().filter(filter).collect(Collectors.toList());
    }
}