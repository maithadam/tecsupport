package com.nanobite.nanobite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 5/10/17.
 */

public class NanoVisionFragment extends Fragment {

    View visionView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        visionView=inflater.inflate(R.layout.fragment_vision,container,false);

        return visionView;
    }
}
