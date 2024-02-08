package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.Catalog;

import java.util.List;

@Dao
public interface FCatalogueDAO {

    @Query("SELECT * FROM Catalog WHERE level=0")
    List<Catalog> findAll();
    @Query("SELECT * FROM Catalog WHERE level=0 and id IN (:list)")
    List<Catalog> findAllByScope(List<Long> list);
    @Query("SELECT * FROM Catalog WHERE idParent=:id")
    List<Catalog> findAllById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Catalog> fCatalogueList);

    @Insert
    long insert(Catalog fCatalogue);

    @Update
    int update(Catalog fCatalogue);

    @Delete
    int delete(Catalog fCatalogue);

    @Query("SELECT id from Catalog WHERE idArticle=:idArticle")
    long getIdCatalogByArticleId(long idArticle);

    @Query("DELETE FROM Catalog")
    int deleteAll();

}
