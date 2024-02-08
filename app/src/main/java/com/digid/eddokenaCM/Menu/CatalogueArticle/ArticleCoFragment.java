package com.digid.eddokenaCM.Menu.CatalogueArticle;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.R;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesByFirstIdCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesCallBack;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.SellectAllArticlesWithConditionTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.SellectAllCatalogueByLevelCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.SellectAllCatalogueByLevelTask;
import com.digid.eddokenaCM.Utils.ClearMemory;
import com.digid.eddokenaCM.Utils.DecompressCallback;
import com.digid.eddokenaCM.Utils.PopManager;
import com.digid.eddokenaCM.Utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleCoFragment extends Fragment implements DecompressCallback, ClearMemory,
        CatalogueAdapter.OnCatalogueListner, SellectAllCatalogueByLevelCallback, SellectAllArticlesCallBack,
        SellectAllArticlesByFirstIdCallback {

    private NavController navController;
    private PopManager popUp;
    private Dialog dataLoadDialog;
    private RecyclerView articlesRv, catalogueRv;
    private ArticlesAdapter articlesAdapter;
    private CatalogueAdapter cataloguesAdapter;
    private List<Catalog> selectCatalogueList = new ArrayList<>();
    private Catalog selectedCatalogue1, selectedCatalogue2, selectedCatalogue3, selectedCatalogue4;
    private List<Article> articleDataList = new ArrayList<Article>();
    private List<Catalog> catalogueDataList = new ArrayList<Catalog>();
    private List<String> clientSelectedArticles = new ArrayList<String>();
    private SearchView searchSv;
    private ImageView backIv;
    private ImageView navClientIv, navCommandeIv, navArticleIv;
    private LinearLayout firstFamilleLl;
    private TextView firstFamilleTv;
    private ImageView firstFamilleCloseIv, syncPhotoIv;


    public ArticleCoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_co, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        popUp = new PopManager(view.getContext());

        uiMapping(view);
        initListner();
        initData(view);

        if (navClientIv!=null){

            if (SessionManager.getInstance().getUserType(getContext()) == 1) {
                navClientIv.setVisibility(View.GONE);
            }
        }
    }

    /*
     * Data initialization
     */

    private void initData(View view) {
        popUp.showDialog("loadingDialog");
        new SellectAllArticlesWithConditionTask(view.getContext(), null, null, null, 0,null,"CatArticle", null, this::onSelectionSuccess).execute();
        new SellectAllCatalogueByLevelTask(view.getContext(), null, null, this::onSelectionCatalogueSuccess).execute();
    }


    /*
     * Mapping of layout's components
     */

    private void uiMapping(View view) {
        articlesRv = view.findViewById(R.id.articleco_articles_rv);
        catalogueRv = view.findViewById(R.id.articleco_catalogue_rv);
        backIv = view.findViewById(R.id.articleco_back_iv);
        searchSv = view.findViewById(R.id.articleco_search_sv);
        syncPhotoIv = view.findViewById(R.id.articleco_syncphoto_iv);

        firstFamilleLl = view.findViewById(R.id.articleco_firstfam_ll);
        firstFamilleTv = view.findViewById(R.id.firstfam_tv);
        firstFamilleCloseIv = view.findViewById(R.id.firstfam_close_iv);

        navArticleIv = view.findViewById(R.id.articleco_navarticle_iv);
        navClientIv = view.findViewById(R.id.articleco_navclient_iv);
        navCommandeIv = view.findViewById(R.id.articleco_navcommande_iv);

    }

    /*
     * Set components listners
     */

    private void initListner() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        if (navCommandeIv != null && navClientIv != null){

            navCommandeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_articleCoFragment_to_comClientFragment_profCO);

                }
            });

            navClientIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.action_articleCoFragment_to_clientCoFragment_profCO);
                }
            });

        }

        firstFamilleCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.showDialog("loadingDialog");

                if (selectCatalogueList.size() == 1){
                    selectCatalogueList.clear();
                    firstFamilleLl.setVisibility(View.GONE);
                    new SellectAllArticlesWithConditionTask(getContext(), null, null, null, 0,null,"CatArticle", null, ArticleCoFragment.this).execute();
                    new SellectAllCatalogueByLevelTask(getContext(), null, null, ArticleCoFragment.this).execute();
                }
                else {
                    selectCatalogueList.remove(selectCatalogueList.size()-1);
                    firstFamilleLl.setVisibility(View.GONE);
                    new SellectAllArticlesWithConditionTask(getContext(), null, selectCatalogueList.get(selectCatalogueList.size()-1).getId(),
                            selectCatalogueList.get(0).getId(), selectCatalogueList.get(selectCatalogueList.size()-1).getLevel(),null,
                            "CatArticle", null,ArticleCoFragment.this).execute();
                    new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(selectCatalogueList.size()-1).getId()
                            ,null, ArticleCoFragment.this).execute();
                }

//                if (selectedCatalogue1 != null && selectedCatalogue2 != null && selectedCatalogue3 == null &&
//                        selectedCatalogue4 == null){
//                    selectedCatalogue2 = null;
//                    firstFamilleTv.setText(selectedCatalogue1.getNameFr());
//                    new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue1.getId(), ArticleCoFragment.this).execute();
//                    new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue1.getId(), selectedCatalogue1.getLevel()
//                            ,null,ArticleCoFragment.this).execute();
//                }
//                else if (selectedCatalogue1 != null && selectedCatalogue2 != null && selectedCatalogue3 != null &&
//                        selectedCatalogue4 == null){
//                    selectedCatalogue3 = null;
//                    firstFamilleTv.setText(selectedCatalogue2.getNameFr());
//                    new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue2.getId(), ArticleCoFragment.this).execute();
//                    new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue2.getId(), selectedCatalogue2.getLevel()
//                            ,null,ArticleCoFragment.this).execute();
//                }
//                else if (selectedCatalogue1 != null && selectedCatalogue2 != null && selectedCatalogue3 != null &&
//                        selectedCatalogue4 != null){
//                    selectedCatalogue4 = null;
//                    firstFamilleTv.setText(selectedCatalogue3.getNameFr());
//                    new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue3.getId(), ArticleCoFragment.this).execute();
//                    new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue3.getId(), selectedCatalogue3.getLevel()
//                            ,null,ArticleCoFragment.this).execute();
//                }
            }
        });

    }

    /*
     * Decompress zip file's callback
     */

    @Override
    public void decompressSuccess() {

        if (android.os.Build.VERSION.SDK_INT >= 30){
            new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/ArticleImgs/Multimedia.zip").delete();
            File oldFolder = new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/ArticleImgs");
            File newFolder = new File(getContext().getExternalFilesDir("").getAbsolutePath(), "/.ArticleImgs");
            boolean success = oldFolder.renameTo(newFolder);
            new File(getContext().getExternalFilesDir("").getAbsolutePath() + "/ArticleImgs").delete();
        } else{
            new File(Environment.getExternalStorageDirectory(), "/ArticleImgs/Multimedia.zip").getAbsoluteFile().delete();
            File oldFolder = new File(Environment.getExternalStorageDirectory(), "/ArticleImgs");
            File newFolder = new File(Environment.getExternalStorageDirectory(), "/.ArticleImgs");
            boolean success = oldFolder.renameTo(newFolder);
            new File(Environment.getExternalStorageDirectory() + "/ArticleImgs").getAbsoluteFile().delete();
        }
        popUp.hideDialog("dataload");
        navController.popBackStack();
    }

    @Override
    public void decompressFailled() {
        popUp.hideDialog("dataload");
        Log.i("eeeeeeeee", "decompressFailled: ");
        Toast.makeText(getContext(), "Decompression echouée", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearAppMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        clearAppMemory();
    }

    @Override
    public void onResume() {
        super.onResume();

        articleDataList = new ArrayList<>();
        catalogueDataList = new ArrayList<>();

        uiMapping(getView());

        Calendar calendar = Calendar.getInstance();
        Long seconds=calendar.getTimeInMillis();
        if (seconds> SessionManager.getInstance().getExpireDate(getContext())){
            SessionManager.getInstance().setToken(getContext(), "#");
            SessionManager.getInstance().setUserId(getContext(), "-1");
            navController.navigate(R.id.action_menuCoFragment_to_loginFragment);
        }
    }

    /*
     * Cleaning variables
     */

    @Override
    public void clearAppMemory() {


        articlesRv = null;
        catalogueRv = null;
        backIv = null;
        searchSv = null;

        articleDataList = null;
        catalogueDataList = null;

        firstFamilleLl = null;
        firstFamilleTv = null;
        firstFamilleCloseIv = null;

        navArticleIv = null;
        navClientIv = null;
        navCommandeIv = null;

        System.gc();
    }

    /*
     * Articles's selection locally callback
     */

    @Override
    public void onSelectionSuccess(List<Article> articleList) {

        popUp.hideDialog("loadingDialog");

        for(Article article : articleList){
            Log.d("VALUE",article.toString());
        }

        articleDataList.clear();
        articleDataList.addAll(articleList);
        articlesAdapter = new ArticlesAdapter(articleDataList);
        articlesRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        articlesRv.setAdapter(articlesAdapter);

        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                articlesAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    /*
     * Catalog's selection locally callback
     */

    @Override
    public void onSelectionCatalogueSuccess(List<Catalog> catalogueList) {



        catalogueDataList.clear();
        catalogueDataList.addAll(catalogueList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        cataloguesAdapter = new CatalogueAdapter(catalogueDataList, this::onCatalogueClick);
        catalogueRv.setLayoutManager(layoutManager);
        catalogueRv.setHasFixedSize(true);
        catalogueRv.setAdapter(cataloguesAdapter);
    }

    /*
     * Selecting item from catalog
     */

    @Override
    public void onCatalogueClick(int position) {

        if (selectCatalogueList.size() == 0) {

            selectCatalogueList.add(catalogueDataList.get(position));
            firstFamilleLl.setVisibility(View.VISIBLE);
            firstFamilleTv.setText(selectCatalogueList.get(0).getNameFr());

            new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(0).getId()
                    ,null, this::onSelectionCatalogueSuccess).execute();
            new SellectAllArticlesWithConditionTask(getContext(), null, selectCatalogueList.get(0).getId(), selectCatalogueList.get(0).getId(), selectCatalogueList.get(0).getLevel()
                    ,null,"CatArticle", null,ArticleCoFragment.this).execute();

        }
        else {

            selectCatalogueList.add(catalogueDataList.get(position));
            firstFamilleLl.setVisibility(View.VISIBLE);
            firstFamilleTv.setText(selectCatalogueList.get(selectCatalogueList.size()-1).getNameFr());

            new SellectAllCatalogueByLevelTask(getContext(), selectCatalogueList.get(selectCatalogueList.size()-1).getId()
                    ,null, this::onSelectionCatalogueSuccess).execute();
            new SellectAllArticlesWithConditionTask(getContext(), null, selectCatalogueList.get(selectCatalogueList.size()-1).getId()
                    , selectCatalogueList.get(0).getId(), selectCatalogueList.get(selectCatalogueList.size()-1).getLevel()
                    ,null,"CatArticle",null,ArticleCoFragment.this).execute();
        }

//        if (selectedCatalogue2 == null) {
//            selectedCatalogue2 = catalogueDataList.get(position);
//            firstFamilleTv.setText(selectedCatalogue1.getNameFr());
//            new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue2.getId(), this::onSelectionCatalogueSuccess).execute();
//            new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue2.getId(), selectedCatalogue2.getLevel()
//                    ,null,ArticleCoFragment.this).execute();
//
//
//        } else if (selectedCatalogue3 == null) {
//            selectedCatalogue3 = catalogueDataList.get(position);
//            firstFamilleTv.setText(selectedCatalogue1.getNameFr());
//            new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue3.getId(), this::onSelectionCatalogueSuccess).execute();
//            new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue3.getId(), selectedCatalogue3.getLevel()
//                    ,null,ArticleCoFragment.this).execute();
//
//
//        } else {
//            selectedCatalogue4 = catalogueDataList.get(position);
//            firstFamilleTv.setText(selectedCatalogue1.getNameFr());
//            new SellectAllCatalogueByLevelTask(getContext(), selectedCatalogue3.getId(), this::onSelectionCatalogueSuccess).execute();
//            new SellectAllArticlesWithConditionTask(getContext(), selectedCatalogue3.getId(), selectedCatalogue3.getLevel()
//                    ,null,ArticleCoFragment.this).execute();
//
//
//        }

    }

    /*
     * Selecting articles locally with Cl_No1 callback
     */

    @Override
    public void onFirstSelectionSuccess(List<Article> articleList) {
        articleDataList.clear();
        articleDataList.addAll(articleList);
        articlesAdapter = new ArticlesAdapter(articleDataList);
        articlesRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        articlesRv.setAdapter(articlesAdapter);

        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                articlesAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    /*
     * Selecting articles locally with Cl_No4 callback
     */

//    @Override
//    public void onFourthSelectionSuccess(List<Article> articleList) {
//        articleDataList.clear();
//        articleDataList.addAll(articleList);
//        articlesAdapter = new ArticlesAdapter(articleDataList);
//        articlesRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        articlesRv.setAdapter(articlesAdapter);
//
//        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                articlesAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//    }
//
//    /*
//     * Selecting articles locally with Cl_No2 callback
//     */
//
//    @Override
//    public void onSecondSelectionSuccess(List<Article> articleList) {
//        articleDataList.clear();
//        Log.i("Article", "" + articleList.size());
//        articleDataList.addAll(articleList);
//        Log.i("Article", "" + articleDataList.size());
//        articlesAdapter = new ArticlesAdapter(articleDataList);
//        articlesRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        articlesRv.setAdapter(articlesAdapter);
//
//        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                articlesAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//    }
//
//    /*
//     * Selecting articles locally with Cl_No3 callback
//     */
//
//    @Override
//    public void onThirdSelectionSuccess(List<Article> articleList) {
//        articleDataList.clear();
//        Log.i("Article", "" + articleList.size());
//        articleDataList.addAll(articleList);
//        Log.i("Article", "" + articleDataList.size());
//        articlesAdapter = new ArticlesAdapter(articleDataList);
//        articlesRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        articlesRv.setAdapter(articlesAdapter);
//
//        searchSv.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                articlesAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//    }


}
