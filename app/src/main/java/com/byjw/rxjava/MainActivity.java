package com.byjw.rxjava;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter
                .getItemPublishSubject()
                .subscribe(s -> toast(s.getTitle()));

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (recyclerViewAdapter == null) return;

        getItemObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    recyclerViewAdapter.updateItems(item);
                    recyclerViewAdapter.notifyDataSetChanged();
                });
    }

    private void toast(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    private Observable<RecyclerItem> getItemObservable() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return Observable.fromIterable(packageManager.queryIntentActivities(intent, 0))
        .sorted(new ResolveInfo.DisplayNameComparator(packageManager))
        .subscribeOn(Schedulers.io())
                .map(item -> {
                    Drawable image = item.activityInfo.loadIcon(packageManager);
                    String title = item.activityInfo.loadLabel(packageManager).toString();
                    return new RecyclerItem(image, title);
                });
    }

}
