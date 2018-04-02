package com.example.v_shevchyk.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.v_shevchyk.rx.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Disposable disposable;
    private static final String TAG = MainActivity.class.getSimpleName();

//    ArticleRv articleRv = new ArticleRv(articleModels, MainActivity.this);
//                        posts.setAdapter(articleRv);

    @BindView(R.id.posts)
    RecyclerView posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        posts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        posts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        new RepositoryImpl().getArticles("https://www.instructables.com/technology/")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ArrayList<ArticleModel>, ObservableSource<ArticleModel>>() {
                    @Override
                    public ObservableSource<ArticleModel> apply(ArrayList<ArticleModel> articleModels) throws Exception {
                        return Observable.fromIterable(articleModels);
                    }
                })
                .filter(new Predicate<ArticleModel>() {
                    @Override
                    public boolean test(ArticleModel articleModel) throws Exception {
                        return articleModel.getName().startsWith("W");
                    }
                })
                .toList()
                .toObservable()
                .subscribe(new Observer<List<ArticleModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ArticleModel> articleModels) {
                        ArticleRv articleRv = new ArticleRv(articleModels, MainActivity.this);
                        posts.setAdapter(articleRv);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

