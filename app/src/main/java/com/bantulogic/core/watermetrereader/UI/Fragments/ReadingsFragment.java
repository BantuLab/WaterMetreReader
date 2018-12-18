package com.bantulogic.core.watermetrereader.UI.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadingsFragment extends Fragment {


    public ReadingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_readings, container, false);
    }

}
