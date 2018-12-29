package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bantulogic.core.watermetrereader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadingDetailFragment extends Fragment {

    Button mViewReadingsDetails;

    public ReadingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewReadingsDetails = view.findViewById(R.id.btn_view_reading_details);

        mViewReadingsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("CHAIWA NAV",  Navigation.findNavController(view).toString());
//                Navigation.createNavigateOnClickListener(R.id.action_dest_readings_fragment_to_dest_reading_detail_fragment);
//                Navigation.findNavController(getView()).navigate(R.id.dest_reading_detail_fragment);
            }
        });
    }
}
