package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.Order;

import java.util.List;

@Dao
public interface FCmdEnteteDAO {


    @Query("SELECT * FROM `Order` WHERE idBo=:id LIMIT 1")
    Order findById(String id);

    @Query("SELECT * FROM `Order` WHERE coNo=:coNo AND status=:status")
    List<Order> findAllfact(long coNo, String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Order> orderList);

    @Query("SELECT * FROM `Order` WHERE coNo=:id AND db=:db")
    List<Order> findAllByIdCo(long id, String db);

    @Query("SELECT * FROM `Order` WHERE local=:local AND db=:db")
    List<Order> findAllLocal(boolean local, String db);

    @Query("SELECT * FROM `Order` WHERE clientId= :ctNum")
    List<Order> findAllByIdCl(String ctNum);

    @Query("SELECT * FROM `Order` WHERE idOrder=:id")
    Order findByIdLocal(long id);

    @Query("SELECT * FROM `Order` WHERE idBo = :doPiece")
    List<Order> findByDoPiece(String doPiece);


    //-------------------------- Entetes Local & Serveur -----------------------------------------------//

    @Query("SELECT * FROM `Order` ")
    List<Order> findByDateCoAll();
    @Query("SELECT * FROM `Order` WHERE orderDate= :date AND coNo= :id ORDER BY status DESC ")
    List<Order> findByDateCo(String date, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate= :date  AND  clientId= :ctNum ORDER BY status DESC")
    List<Order> findByDateCl(String date, String ctNum);

    @Query("SELECT * FROM `Order` WHERE orderDate = :lastweek AND coNo= :id ORDER BY status DESC ")
    List<Order> findByLastDateCo(String lastweek, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate = :lasttwoweek AND coNo= :id ORDER BY status DESC ")
    List<Order> findByLastTwoDateCo(String lasttwoweek, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate= :lastweek  AND  clientId= :ctNum ORDER BY status DESC")
    List<Order> findByLastDateCl(String lastweek, String ctNum);

    @Query("SELECT * FROM `Order` WHERE orderDate= :lasttwoweek  AND  clientId= :ctNum ORDER BY status DESC ")
    List<Order> findByLastTwoDateCl(String lasttwoweek, String ctNum);

    //----------------------------------------------------------------------------------------//

    //-------------------------- Entetes Local -----------------------------------------------//

    @Query("SELECT * FROM `Order` WHERE orderDate= :date AND coNo= :id AND idBo IS NULL ORDER BY status DESC ")
    List<Order> findLocalByDateCo(String date, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate= :date  AND  clientId= :ctNum AND idBo IS NULL ORDER BY status DESC")
    List<Order> findLocalByDateCl(String date, String ctNum);

    @Query("SELECT * FROM `Order` WHERE orderDate = :lastweek AND coNo= :id AND idBo IS NULL ORDER BY status DESC ")
    List<Order> findLocalByLastDateCo(String lastweek, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate = :lasttwoweek AND coNo= :id AND idBo IS NULL ORDER BY status DESC ")
    List<Order> findLocalByLastTwoDateCo(String lasttwoweek, Long id);

    @Query("SELECT * FROM `Order` WHERE orderDate= :lastweek  AND  clientId= :ctNum AND idBo IS NULL ORDER BY status DESC")
    List<Order> findLocalByLastDateCl(String lastweek, String ctNum);

    @Query("SELECT * FROM `Order` WHERE orderDate= :lasttwoweek  AND  clientId= :ctNum AND idBo IS NULL ORDER BY status DESC ")
    List<Order> findLocalByLastTwoDateCl(String lasttwoweek, String ctNum);

    //----------------------------------------------------------------------------------------//

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Order fOrder);

    @Update
    int update(Order fOrder);

    @Query("UPDATE `Order` SET idBo = :doPiece AND ref=:doPiece WHERE idOrder = :id")
    int updateEnteteDopiece(long id, String doPiece);

    @Query("UPDATE `Order` SET local=:local AND status=:status  WHERE idOrder = :id")
    int updateEnteteLocal(long id, boolean local, String status);

    @Query("UPDATE `Order` SET status = :valid WHERE idBo = :doPiece")
    int updateFactValid(String doPiece,String valid);

    @Delete
    int delete(Order fOrder);

    @Query("DELETE FROM `Order` WHERE orderDate= :date")
    int deleteByDate(String date);

    @Query("DELETE FROM `Order` WHERE idBo != 0")
    int deleteAll();

    @Query("DELETE FROM `Order` WHERE idOrder = :id")
    int deleteOrderById(long id);

    @Query("SELECT * FROM `Order`")
    List<Order> findAll();

    @Query("SELECT * FROM `Order` WHERE idOrder IN (:idList)")
    List<Order> getCmdListById(List<Long> idList);


}
