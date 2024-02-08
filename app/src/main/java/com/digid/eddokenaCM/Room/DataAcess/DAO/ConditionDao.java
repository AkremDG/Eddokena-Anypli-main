package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.Conditionnement;

import java.util.List;


@Dao
public interface ConditionDao {

    /*@Query("SELECT * FROM Conditionnement WHERE AR_Ref=:id LIMIT 1")
    Conditionnement findById(String id);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Conditionnement> conditionnementList);

    @Insert
    long insert(Conditionnement conditionnement);

    @Update
    int update(Conditionnement conditionnement);

    @Delete
    int delete(Conditionnement conditionnement);

    @Query("DELETE FROM Conditionnement")
    int deleteAll();

    @Query("SELECT * FROM Conditionnement")
    List<Conditionnement> findAll();

    @Query("SELECT * FROM Conditionnement WHERE EC_Enumere = :ecEnumere AND cat = :clientCategorie AND AR_Ref=:arRef")
    Conditionnement findByConditionClient(String ecEnumere, int clientCategorie, String arRef);

}
