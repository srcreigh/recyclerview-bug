package com.example.screightonyoung.testapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Object> {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_list);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ExampleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.add("Hello");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new ExampleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        mAdapter.remove();
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
    }

    public static class ExampleLoader extends AsyncTaskLoader<Object> {
        /**
         * Stores away the application context associated with context.
         * Since Loaders can be used across multiple activities it's dangerous to
         * store the context directly; always use {@link #getContext()} to retrieve
         * the Loader's Context, don't use the constructor argument directly.
         * The Context returned by {@link #getContext} is safe to use across
         * Activity instances.
         *
         * @param context used to retrieve the application context.
         */
        public ExampleLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onStartLoading() {
            forceLoad();
        }
    }

    public static class ExampleVH extends RecyclerView.ViewHolder {
        private TextView mHelloTextView;
        public ExampleVH(View v) {
            super(v);
            mHelloTextView = (TextView)v.findViewById(android.R.id.text1);
        }
        public void bind(String item) {
            mHelloTextView.setText(item);
        }
    }

    public static class ExampleAdapter extends RecyclerView.Adapter<ExampleVH> {
        private final List<String> mData = new ArrayList<>();
        private final LayoutInflater mInflater;

        public ExampleAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ExampleVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ExampleVH(mInflater.inflate(R.layout.list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ExampleVH vh, int i) {
            vh.bind(mData.get(i));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public void add(String str) {
            mData.add(str);
            notifyItemRangeInserted(mData.size(), 1);
        }

        public void remove() {
            mData.remove(mData.size()-1);
            notifyItemRangeRemoved(mData.size(), 1);
        }
    }
}
