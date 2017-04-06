package com.stickerpipe.camerasdk.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stickerpipe.camerasdk.R;
import com.stickerpipe.camerasdk.Utils;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsCursor;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class StampsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private List<Integer> tabs = new ArrayList<>();
    private View contentView;
    private StampsPagerAdapter stampsPagerAdapter;
    private StampsListFragment.StampSelectedListener externalStampSelectedListener;
    private static final int LOADER_ID = Utils.atomicInteger.incrementAndGet();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                StampSetsColumns.CONTENT_URI,
                new String[]{StampSetsColumns.STAMPS_SET_ID, StampSetsColumns._ID},
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        StampSetsCursor stampSetsCursor = new StampSetsCursor(cursor);
        tabs.clear();
        if (cursor.moveToFirst()) {
            do {
                tabs.add(stampSetsCursor.getStampsSetId());
            } while (cursor.moveToNext());
        }
        stampsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // nothing to do
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_stamps, container, false);
        } else if (contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        ViewPager stampsPager = (ViewPager) contentView.findViewById(R.id.stamps_pager);
        stampsPager.setOffscreenPageLimit(1);
        stampsPagerAdapter = new StampsPagerAdapter(getChildFragmentManager());
        stampsPager.setAdapter(stampsPagerAdapter);
        CircleIndicator indicator = (CircleIndicator) contentView.findViewById(R.id.pager_indicator);
        indicator.setViewPager(stampsPager);
        stampsPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        return contentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (contentView != null && contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
    }

    private StampsListFragment.StampSelectedListener stampSelectedListener = (stampId, link) -> {
        if (externalStampSelectedListener != null) {
            externalStampSelectedListener.onStampSelected(stampId, link);
        }
    };

    public void setStampSelectedLister(StampsListFragment.StampSelectedListener listener) {
        externalStampSelectedListener = listener;
    }

    private class StampsPagerAdapter extends FragmentStatePagerAdapter {

        StampsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle data = new Bundle();
            data.putInt(StampsListFragment.ARGUMENT_SET_ID, tabs.get(position));
            StampsListFragment stampPageFragment = new StampsListFragment();
            stampPageFragment.setArguments(data);
            stampPageFragment.setStampSelectedListener(stampSelectedListener);
            return stampPageFragment;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }



        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
