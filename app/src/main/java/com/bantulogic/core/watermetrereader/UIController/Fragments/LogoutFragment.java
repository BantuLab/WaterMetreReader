package com.bantulogic.core.watermetrereader.UIController.Fragments;


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
                        Navigation.findNavController(view).navigate(R.id.global_action_logout);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Navigation.findNavController(view).popBackStack();
//                        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_dest_logout_fragment_to_dest_home_fragment);

                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
//                        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_dest_logout_fragment_to_dest_home_fragment);
//                        dialogInterface.cancel();
//                        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_dest_logout_fragment_to_dest_home_fragment);
//                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}
