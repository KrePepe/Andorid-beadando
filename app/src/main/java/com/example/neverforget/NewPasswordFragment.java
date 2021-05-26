package com.example.neverforget;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.neverforget.controller.Data;
import com.example.neverforget.controller.Encryption;
import com.example.neverforget.controller.PasswordGenerator;
import com.example.neverforget.model.Password;

import static android.content.ContentValues.TAG;

public class NewPasswordFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_new_password, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Password currentPassword = null;

        final EditText editTextName = (EditText)view.findViewById(R.id.edit_name);
        final EditText editTextUserName = (EditText)view.findViewById(R.id.edit_userName);
        final EditText editTextPassword = (EditText)view.findViewById(R.id.edit_password);
        final Button removeButton = (Button)view.findViewById(R.id.button_remove);

        if (getArguments().getInt("PasswordId") >= 0){
            currentPassword = Data.GetPasswordById(getArguments().getInt("PasswordId"));

            editTextName.setText(currentPassword.getName());
            Log.d(TAG, "onViewCreated: password: "+currentPassword.getPassword());
            Log.d(TAG, "onViewCreated: password decrypted: "+Encryption.decrypt(currentPassword.getPassword(), Data.MyKey));
            editTextPassword.setText(Encryption.decrypt(currentPassword.getPassword(), Data.MyKey));
            editTextUserName.setText(currentPassword.getUserName());

            final Password finalCurrentPassword1 = currentPassword;
            //removeButton.setText(getString());
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data.RemoveItem(finalCurrentPassword1);
                    NavHostFragment.findNavController(NewPasswordFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            });
            removeButton.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NewPasswordFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.button_generate).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                PasswordGenerator pass = new PasswordGenerator();
                editTextPassword.setText(pass.generateRandomPassword(10));
            }
        });

        final Password finalCurrentPassword = currentPassword;
        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                if(finalCurrentPassword == null){
                    Password pass = new Password(editTextName.getText().toString(),editTextPassword.getText().toString(), editTextUserName.getText().toString());
                    Data.AddItem(pass);
                } else {
                    finalCurrentPassword.setName(editTextName.getText().toString());
                    finalCurrentPassword.setPassword(editTextPassword.getText().toString());
                    finalCurrentPassword.setUserName(editTextUserName.getText().toString());
                    Data.AddItem(finalCurrentPassword);
                }
                NavHostFragment.findNavController(NewPasswordFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}
