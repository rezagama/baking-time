package com.example.bakingtime.home;

import com.example.bakingtime.network.NetworkService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rezagama on 9/3/17.
 */

public class RecipeHomeViewModel {
    private RecipeHomeView view;
    private NetworkService service;
    private CompositeDisposable disposables;

    public RecipeHomeViewModel(NetworkService service, RecipeHomeView view) {
        this.view = view;
        this.service = service;
        disposables = new CompositeDisposable();
    }

    public void getRecipeList() {
        view.showProgressBar();
        Disposable disposable = service.getRecipeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    view.hideProgressBar();
                    view.loadRecipeList(recipes);
                }, throwable -> view.hideProgressBar());
        disposables.add(disposable);
    }

    public void onDestroy() {
        disposables.clear();
    }
}
