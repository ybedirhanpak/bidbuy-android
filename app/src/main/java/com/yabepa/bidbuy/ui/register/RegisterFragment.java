package com.yabepa.bidbuy.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.databinding.RegisterFragmentBinding;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;
    private RegisterFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Check if there is already a user logged in
        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        if (!username.equals("") || userId != -1) {
            // There is already a user logged in
            Navigation.findNavController(view).navigateUp();
            Snackbar.make(view, "There is already a user logged in: " + username, Snackbar.LENGTH_SHORT).show();
        }

        binding.buttonRegister.setOnClickListener(buttonView -> {
            Editable usernameEditText = binding.editTextUsername.getText();
            Editable passwordEditText = binding.editTextPassword.getText();

            if (usernameEditText == null || passwordEditText == null) {
                Snackbar.make(view, "Please enter credentials", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String usernameInput = usernameEditText.toString();
            String passwordInput = passwordEditText.toString();

            if (usernameInput.equals("")) {
                Snackbar.make(view, "Please enter username", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (passwordInput.equals("")) {
                Snackbar.make(view, "Please enter password", Snackbar.LENGTH_SHORT).show();
                return;
            }

            viewModel.register(usernameInput, passwordInput,
                    user -> {
                        if (user != null) {
                            Navigation.findNavController(view).navigateUp();
                            Snackbar.make(view, "Registration is successful", Snackbar.LENGTH_SHORT).show();
                        }
                    },
                    error -> Snackbar.make(view, error.message, Snackbar.LENGTH_SHORT).show());
        });
    }
}