package com.example.neverforget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neverforget.controller.Data;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 */
public class PasswordFragment extends Fragment implements MyPasswordRecyclerViewAdapter.OnPasswordListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PasswordFragment() {
    }

    public static PasswordFragment newInstance(int columnCount) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPasswordRecyclerViewAdapter(Data.ITEMS, this));
        }
        return view;
    }

    @Override
    public void onPasswordClick(int position) {
        Log.d(TAG, "onPasswordClick: "+position);
        Bundle bundle = new Bundle();
        bundle.putInt("PasswordId", position);
        assert getParentFragment() != null;
        NavHostFragment.findNavController(getParentFragment())
                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
    }
}