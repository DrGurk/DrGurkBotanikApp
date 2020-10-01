package com.example.botanikapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    Button normalMode;
    Button blitzMode;
    Button survivalMode;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        view.findViewById(R.id.normalGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizMaster.gameMode = 0;
                Intent challengeIntent = new Intent(getActivity() , FourChoiceQuizActivity.class);
                startActivity(challengeIntent);
            }
        });
        view.findViewById(R.id.blitzButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizMaster.gameMode = 1;
                Intent challengeIntent = new Intent(getActivity() , FourChoiceQuizActivity.class);
                startActivity(challengeIntent);
            }
        });
        view.findViewById(R.id.survivalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizMaster.gameMode = 2;
                Intent challengeIntent = new Intent(getActivity() , FourChoiceQuizActivity.class);
                startActivity(challengeIntent);
            }
        });
    }
}