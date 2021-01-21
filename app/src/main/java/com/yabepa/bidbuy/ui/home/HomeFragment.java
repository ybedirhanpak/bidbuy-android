package com.yabepa.bidbuy.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonNavigateProductList.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productListFragment));

        binding.buttonNavigateLogin.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment));

        binding.buttonNavigateRegister.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}