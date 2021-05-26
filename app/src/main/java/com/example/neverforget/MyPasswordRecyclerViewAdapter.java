package com.example.neverforget;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neverforget.model.Password;

import java.util.ArrayList;

public class MyPasswordRecyclerViewAdapter extends RecyclerView.Adapter<MyPasswordRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Password> mValues;
    private OnPasswordListener mOnPasswordListener;

    public MyPasswordRecyclerViewAdapter(ArrayList<Password> items, OnPasswordListener onPasswordListener) {
        this.mOnPasswordListener = onPasswordListener;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_password, parent, false);
        return new ViewHolder(view, mOnPasswordListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId().toString());
        holder.mContentView.setText(mValues.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Password mItem;

        OnPasswordListener onPasswordListener;

        public ViewHolder(View view, OnPasswordListener onPasswordListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);

            itemView.setOnClickListener(this);
            this.onPasswordListener = onPasswordListener;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            onPasswordListener.onPasswordClick(getAdapterPosition());
        }
    }

    public interface OnPasswordListener{
        void onPasswordClick(int position);
    }
}