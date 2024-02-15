package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.OrderItem;

import java.util.List;

@Dao
public interface FCmdLigneDAO {

    @Query("UPDATE OrderItem SET isFirstOrderRelated=:value WHERE id=:id")
    int UpdateIsFirstCmd(boolean value,long id);



    @Query("SELECT * FROM OrderItem WHERE Id=:id LIMIT 1")
    OrderItem findById(int id);

    @Query("SELECT * FROM OrderItem WHERE idCmdLocal=:idLocalCmd")
    List<OrderItem> findAllByIdCmdLocal(long idLocalCmd);

    @Query("SELECT * FROM OrderItem WHERE idBo=:doPiece")
    List<OrderItem> findAllByDoPiece(String doPiece);

    @Query("UPDATE OrderItem SET idBo = :doPiece WHERE idCmdLocal = :id")
    int updateLigneDopiece(long id, String doPiece);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<OrderItem> fOrderItemList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(OrderItem fOrderItem);

    @Update
    int update(OrderItem fOrderItem);

    @Delete
    int delete(OrderItem fOrderItem);

    @Query("DELETE FROM OrderItem WHERE idCmdLocal= :idLocal")
    int deleteAllByID(long idLocal);


    @Query("DELETE FROM OrderItem WHERE idBo != 0")
    int deleteAll();


    @Query("DELETE FROM OrderItem WHERE idCmdLocal=:idCmdLocal")
    int deleteByID(long idCmdLocal);

    @Query("SELECT * FROM OrderItem")
    List<OrderItem> findAll();
}
