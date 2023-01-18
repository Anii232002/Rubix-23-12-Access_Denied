package com.example.csiapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.csiapp.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackathon.chegg.services.authentication.AuthService;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;


    public SignUpFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText=binding.emailIdSignUp.getText().toString();
                String passwordText=binding.passwordSignUp.getText().toString();
                String name=binding.userName.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty())
                    Toast.makeText(getContext(), "Please enter the empty fields", Toast.LENGTH_SHORT).show();
                if(passwordText.length()<6)
                    Toast.makeText(getContext(), "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                AuthService authService=new AuthService(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());


                authService.signUpUser(emailText,passwordText,name,getContext());



            }
        });

    }
}