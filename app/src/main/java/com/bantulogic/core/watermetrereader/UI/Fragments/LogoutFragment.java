package com.bantulogic.core.watermetrereader.UI.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantulogic.core.watermetrereader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ExitDialogFragment exitDialogFragment = new ExitDialogFragment().newInstance(R.string.exit_dialog_question);
//        exitDialogFragment.show(getActivity().getSupportFragmentManager(),"test");
////        if (exitDialogFragment.getExitTransition() == Dialog.BUTTON_POSITIVE){
////            Navigation.
////        }
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_logout_fragment)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        getActivity().finish();
                        Navigation.findNavController(getView()).navigate(R.id.dest_login_fragment);
                    }

                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Navigation.findNavController(getView()).navigate(R.id.dest_readings_fragment);
                    }
                })
                .show();
    }
}
