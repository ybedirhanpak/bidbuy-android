package com.yabepa.bidbuy.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.databinding.RegisterFragmentBinding;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;
    private RegisterFragmentBinding binding;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Check if there is already a user logged in
        String username = sharedPref.getString("username", "");
        int userId = sharedPref.getInt("userId", -1);

        if (!username.equals("") || userId != -1) {
            // There is already a user logged in
            Navigation.findNavController(view).navigateUp();
            Toast.makeText(requireActivity(), "There is already a user logged in: " + username, Toast.LENGTH_SHORT).show();
        }

        // Observe if registration is successful
        viewModel.registerSuccessful.observe(requireActivity(), successful -> {
            if(successful) {
                Navigation.findNavController(view).navigateUp();
                Toast.makeText(requireActivity(), "Registration is successful", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonRegister.setOnClickListener(buttonView -> {
            String usernameInput = binding.editTextUsername.getText().toString();
            String passwordInput = binding.editTextPassword.getText().toString();

            viewModel.register(usernameInput, passwordInput);
        });
    }
}