package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.digid.eddokenaCM.Models.ClientCategories;
import com.digid.eddokenaCM.Models.ClientClasses;
import com.digid.eddokenaCM.Models.Clients;
import com.digid.eddokenaCM.Models.DealTarget;
import com.digid.eddokenaCM.Models.DiscountsTarget;

import java.util.List;

@Dao
public interface DealTargetDAO {

    @Insert
     void insertDealTarget(DealTarget dealTarget);
    @Insert
    void insertDiscountsTarget(DiscountsTarget discountsTarget);

    @Insert
    void insertClientClasses(ClientClasses clientClasses);

    @Insert
    void insertDealTargetClients(Clients clients);
    @Insert
    void isertClientCategories(ClientCategories clientCategories);

    @Query("SELECT * FROM DEALTARGET WHERE articleId_foreign =:articleId  ")
    List<DealTarget> getDealTargetsByArticleId(long articleId);

    @Query("SELECT * FROM DiscountsTarget WHERE pcsId_foreign =:pcsId  ")
    List<DiscountsTarget> getDiscountTargetsByPcsId(long pcsId);


    @Query("SELECT * FROM ClientClasses WHERE dealTargetId =:dealTargetId")
    List<ClientClasses> getClientClassesByDealTargedId(Integer dealTargetId);

    @Query("SELECT * FROM ClientCategories WHERE dealTargetId =:dealTargetId")
    List<ClientCategories> getClientCategoriesByDealTargetId(Integer dealTargetId);

    @Query("SELECT * FROM Clients WHERE dealTargeId =:dealTargetId")
    List<Clients> getClientsDealTargetByDealTargetId(Integer dealTargetId);




    @Query("SELECT COUNT(*) FROM ClientClasses WHERE dealTargetId =:dealTargetId AND id=:id")
    int getCountClass(Integer dealTargetId, Integer id);

    @Query("SELECT COUNT(*) FROM ClientCategories WHERE dealTargetId =:dealTargetId AND id=:id")
    int getCountCategorie(Integer dealTargetId, Long id);

    @Query("SELECT COUNT(*) FROM Clients WHERE dealTargeId =:dealTargetId AND id=:id")
    int getCountClients(Integer dealTargetId, Long id);








    @Query("SELECT COUNT(*) FROM ClientClasses WHERE discountsTargerId_Foreign =:discountsTargerId_Foreign AND id=:id")
    int getCountClassDiscounts(Integer discountsTargerId_Foreign, Integer id);

    @Query("SELECT COUNT(*) FROM ClientCategories WHERE discountsTargetId_Foreign =:discountsTargetId_Foreign AND id=:id")
    int getCountCategorieDiscounts(Integer discountsTargetId_Foreign, Long id);

    @Query("SELECT COUNT(*) FROM Clients WHERE discountsTargetId_Foreign =:discountsTargetId_Foreign AND id=:id")
    int getCountClientsDiscounts(Integer discountsTargetId_Foreign, Long id);
}
