package com.bantulogic.core.watermetrereader.UIController.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bantulogic.core.watermetrereader.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button mBtnLogin;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLogin = view.findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nested_home_nav_graph, null));
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //
//                //*After you retrieve a NavController, use its navigate() method to navigate to a destination.
//                //*The navigate() method accepts a resource ID. The ID can be the ID of a specific destination
//                //*in the navigation graph or of an action. Using the ID of the action, instead of the resource
//                //*ID of the destination, has advantages, such as associating transitions with your navigation.
//                //* For more on transitions, refer to Create a transition between destinations: https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing#Create-transition.
//                //
//
//                Navigation.findNavController(view).navigate(R.id.action_dest_login_fragment_to_dest_home_nav_graph);
//            }
//        });
    }
}
