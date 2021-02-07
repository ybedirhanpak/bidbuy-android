package com.yabepa.bidbuy.ui.login;

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
import com.yabepa.bidbuy.databinding.LoginFragmentBinding;


public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private LoginFragmentBinding binding;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        if (!username.equals("") || userId != -1) {
            // There is already a user logged in
            Navigation.findNavController(view).navigateUp();
            Snackbar.make(view, "There is already a user logged in: " + username, Snackbar.LENGTH_SHORT).show();
        }

        binding.buttonLogin.setOnClickListener(buttonView -> {
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

            viewModel.login(usernameInput, passwordInput,
                    user -> {
                        if (user != null) {
                            // A user is logged in
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.sp_username), user.username);
                            editor.putInt(getString(R.string.sp_userId), user.id);
                            editor.apply();
                            // Navigate back
                            Navigation.findNavController(view).navigateUp();
                            Snackbar.make(view, "Logged in successfully: " + user.username, Snackbar.LENGTH_SHORT).show();
                        }
                        resetForm();
                    },
                    error -> {
                        Snackbar.make(view, error.message, Snackbar.LENGTH_SHORT).show();
                        resetForm();
                    });
        });
    }

    private void resetForm() {
        binding.editTextUsername.setText("");
        binding.editTextPassword.setText("");
    }
}