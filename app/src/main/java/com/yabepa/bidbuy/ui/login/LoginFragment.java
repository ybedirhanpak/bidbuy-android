package com.yabepa.bidbuy.ui.login;

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
            Toast.makeText(requireActivity(), "There is already a user logged in: " + username, Toast.LENGTH_SHORT).show();
        }

        viewModel.getUser().observe(requireActivity(), user -> {
            if (user != null) {
                // A user is logged in
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.sp_username), user.username);
                editor.putInt(getString(R.string.sp_userId), user.id);
                editor.apply();
                // Navigate back
                Navigation.findNavController(view).navigateUp();
                Toast.makeText(requireActivity(), "Logged in successfully: " + user.username, Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonLogin.setOnClickListener(buttonView -> {
            String usernameInput = binding.editTextUsername.getText().toString();
            String passwordInput = binding.editTextPassword.getText().toString();

            viewModel.login(usernameInput, passwordInput);
        });
    }
}