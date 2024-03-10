package DAOs;

import DTOs.Block;
import Exceptions.DaoException;
import java.util.List;

/** Base code taken from oop-data-access-layer-sample-1
 *  Rewritten by Jakub Polacek
 */

public interface BlockDaoInterface
{
    //List<Block> getAllBlocks() throws DaoException;
    Block getBlockById(int id) throws DaoException;
    Block deleteBlockById(int id) throws DaoException;
    Block insertBlock(Block b) throws DaoException;
}