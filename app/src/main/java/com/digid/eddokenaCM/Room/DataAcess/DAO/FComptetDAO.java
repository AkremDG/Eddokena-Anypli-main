package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Models.ClientScope;

import java.util.List;

@Dao
public interface FComptetDAO {

    @Query("SELECT * FROM Client WHERE id=:id LIMIT 1")
    Client findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Client> fComptetList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllScopes(List<ClientScope> fComptetScopeList);

    @Insert
    long insert(Client fComptet);
    @Update
    int update(Client fComptet);

    @Delete
    int delete(Client fComptet);

    @Query("DELETE FROM Client")
    int deleteAll();

    @Query("SELECT * FROM Client")
    List<Client> findAll();

    @Query("DELETE FROM ClientScope")
    int deleteAllClientScope();

    @Query("SELECT * FROM ClientScope WHERE clientId=:id")
    List<ClientScope> findAllScopes(long id);
}
