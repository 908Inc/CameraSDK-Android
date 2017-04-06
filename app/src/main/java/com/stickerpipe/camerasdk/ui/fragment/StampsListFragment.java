package com.stickerpipe.camerasdk.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stickerpipe.camerasdk.R;
import com.stickerpipe.camerasdk.Utils;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsCursor;
import com.stickerpipe.camerasdk.ui.CursorRecyclerViewAdapter;
import com.stickerpipe.camerasdk.ui.view.SquareImageView;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class StampsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ARGUMENT_SET_ID = "argument_set_id";
    protected StampAdapter adapter;

    protected RecyclerView rv;
    private View layout;

    private int currentLoaderId;
    private int setId;
    private StampSelectedListener stampSelectedListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getLoaderId() > 0) {
            getActivity().getSupportLoaderManager().initLoader(getLoaderId(), null, this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setId = getArguments().getInt(ARGUMENT_SET_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(getLayoutId(), container, false);
            rv = (RecyclerView) layout.findViewById(R.id.recycler_view);
            if (adapter != null) {
                rv.setAdapter(adapter);
            } else {
                // set empty adapter to avoid errors
                rv.setAdapter(emptyAdapter);
            }
            GridLayoutManager lm = (new GridLayoutManager(getContext(), 4));
            rv.setLayoutManager(lm);
        }
        return layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        layout = null;
    }

    protected int getLayoutId() {
        return R.layout.fragment_stamps_list;
    }

    public void setStampSelectedListener(StampSelectedListener listener) {
        stampSelectedListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                StampsColumns.CONTENT_URI,
                new String[]{StampsColumns._ID, StampsColumns.STAMP_ID, StampsColumns.LINK, StampsColumns.STAMPS_SET_ID},
                StampsColumns.STAMPS_SET_ID + "=?",
                new String[]{String.valueOf(setId)},
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (getContext() == null) {
            if (adapter != null) {
                adapter.changeCursor(null);
            }
            return;
        }
        if (adapter == null) {
            adapter = createStampAdapter(cursor);
            rv.setAdapter(adapter);
        } else {
            adapter.changeCursor(cursor);
        }
    }

    protected StampAdapter createStampAdapter(Cursor cursor) {
        return new StampAdapter(this, cursor, stampSelectedListener);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.changeCursor(null);
        }
    }

    protected int getLoaderId() {
        if (currentLoaderId == 0) {
            currentLoaderId = Utils.atomicInteger.incrementAndGet();
        }
        return currentLoaderId;
    }

    static class StampAdapter extends CursorRecyclerViewAdapter<StampAdapter.ViewHolder> {

        private Fragment mAdapterFragment;
        private final int padding;
        private final StampSelectedListener stampSelectedListener;

        StampAdapter(Fragment fragment, Cursor cursor, StampSelectedListener stampSelectedListener) {
            super(cursor);
            mAdapterFragment = fragment;
            this.stampSelectedListener = stampSelectedListener;
            padding = fragment.getContext().getResources().getDimensionPixelSize(R.dimen.material_8);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView iv = new SquareImageView(mAdapterFragment.getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            iv.setPadding(padding, padding, padding, padding);
            return new ViewHolder(iv);
        }

        @Override
        public int getItemCount() {
            return getCursor().getCount();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
            StampsCursor stampsCursor = new StampsCursor(cursor);
            viewHolder.stampId = stampsCursor.getStampId();
            viewHolder.link = stampsCursor.getLink();
            Glide.with(mAdapterFragment)
                    .load(viewHolder.link)
                    .placeholder(android.R.color.transparent)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.iv);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv;
            private int stampId;
            private String link;

            ViewHolder(View itemView) {
                super(itemView);
                this.iv = (ImageView) itemView;
                iv.setOnClickListener(v -> {
                    if (stampSelectedListener != null) {
                        stampSelectedListener.onStampSelected(stampId, link);
                    }
                });
            }
        }
    }

    private RecyclerView.Adapter emptyAdapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    };

    public interface StampSelectedListener {
        void onStampSelected(int stampId, String link);
    }

}
