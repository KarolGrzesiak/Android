package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by karolgrzesiak on 04.08.17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    String mUrl;
    public EarthquakeLoader(Context context,String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(mUrl==null)
            return null;
        List<Earthquake> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
