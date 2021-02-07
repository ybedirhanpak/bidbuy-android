package com.yabepa.bidbuy.ui.home;

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

import com.google.android.material.snackbar.Snackbar;
import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        updateUI(username, userId);

        binding.buttonNavigateProductList.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productListFragment));

        binding.buttonNavigateLogin.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment));

        binding.buttonNavigateRegister.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment));

        binding.buttonNavigateProductCreate.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productCreateFragment));

        binding.buttonLogout.setOnClickListener(buttonView -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.sp_username));
            editor.remove(getString(R.string.sp_userId));
            editor.apply();
            updateUI("", -1);
            Snackbar.make(view, "Logged out successfully", Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUI (String username, int userId) {
        if (!username.equals("") || userId != -1) {
            // There is already a user logged in
            binding.buttonLogout.setVisibility(View.VISIBLE);
            binding.layoutAfterLogin.setVisibility(View.VISIBLE);
            binding.layoutBeforeLogin.setVisibility(View.GONE);
        } else {
            // There is no user
            binding.buttonLogout.setVisibility(View.GONE);
            binding.layoutAfterLogin.setVisibility(View.GONE);
            binding.layoutBeforeLogin.setVisibility(View.VISIBLE);
        }
    }
}