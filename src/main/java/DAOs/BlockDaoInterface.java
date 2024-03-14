package DAOs;

import DTOs.Block;
import Exceptions.DaoException;
import java.util.List;

/** Base code taken from oop-data-access-layer-sample-1
 *  Rewritten by Jakub Polacek
 */

public interface BlockDaoInterface
{

    List<Block> findAllBlocks() throws DaoException;

    Block getBlockById(int id) throws DaoException;

    //TODO - Hannah
    //Block deleteBlockById(int id) throws DaoException;

    //TODO - possible rewrite to return int for displaying if the block was successfully inserted or not
    void insertABlock(Block block) throws DaoException;
}