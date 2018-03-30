package com.example.v_shevchyk.rx;

import com.example.v_shevchyk.rx.model.ArticleModel;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface IRepository {
    Observable<ArrayList<ArticleModel>> getArticles(String url);
}
