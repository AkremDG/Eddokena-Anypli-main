package com.digid.eddokenaCM.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.ArticleCatalog;
import com.digid.eddokenaCM.Models.ArticlePcsMesure;
import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.Models.ArticleStock;
import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Models.ClientCategories;
import com.digid.eddokenaCM.Models.ClientClasses;
import com.digid.eddokenaCM.Models.ClientScope;
import com.digid.eddokenaCM.Models.Clients;
import com.digid.eddokenaCM.Models.Deal;
import com.digid.eddokenaCM.Models.DealTarget;
import com.digid.eddokenaCM.Models.DiscountsTarget;
import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Models.Conditionnement;
import com.digid.eddokenaCM.Room.DataAcess.DAO.ConditionDao;
import com.digid.eddokenaCM.Room.DataAcess.DAO.DealTargetDAO;
import com.digid.eddokenaCM.Room.DataAcess.DAO.FArticleDAO;
import com.digid.eddokenaCM.Room.DataAcess.DAO.FCatalogueDAO;
import com.digid.eddokenaCM.Room.DataAcess.DAO.FCmdEnteteDAO;
import com.digid.eddokenaCM.Room.DataAcess.DAO.FCmdLigneDAO;
import com.digid.eddokenaCM.Room.DataAcess.DAO.FComptetDAO;

@Database(entities = {Article.class, ArticleStock.class, ArticleCatalog.class, ArticlePricePcs.class,
        ArticlePcsMesure.class, Deal.class,Conditionnement.class, Catalog.class,
        Order.class, OrderItem.class, Client.class, ClientScope.class, DealTarget.class,
        ClientCategories.class, ClientClasses.class, Clients.class, DiscountsTarget.class,
}, version =    17   , exportSchema = false)
public abstract class MyDB extends RoomDatabase {

    public abstract FArticleDAO fAritcleDAO();

    public abstract FCatalogueDAO fCatalogueDAO();

    public abstract FCmdEnteteDAO fCmdEnteteDAO();

    public abstract FCmdLigneDAO fCmdLigneDAO();

    public abstract FComptetDAO fComptetDAO();

    public abstract ConditionDao conditionDao();

    public abstract DealTargetDAO dealTargetDAO();

    private static MyDB ourInstance = null;

    public static final String DATABASE_NAME = "Digcom_database";

    public MyDB() {
    }

    public static synchronized MyDB getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = Room.databaseBuilder(context.getApplicationContext(), MyDB.class, "DigGlobalDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return ourInstance;
    }

}
