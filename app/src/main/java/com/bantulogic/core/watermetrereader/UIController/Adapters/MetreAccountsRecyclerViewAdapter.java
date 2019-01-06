package com.bantulogic.core.watermetrereader.UIController.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MetreAccountsRecyclerViewAdapter extends RecyclerView.Adapter<MetreAccountsRecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MetreAccount> mMetreAccounts;

    public MetreAccountsRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_metre_account_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mMetreAccounts != null){
            MetreAccount metreAccount = mMetreAccounts.get(position);
            holder.mTextViewMetreId.setText(String.valueOf(metreAccount.getMetreId()));
            holder.mTextViewMetreStreetAddress.setText(metreAccount.getMetreStreetAddress());
        }
        else {
            holder.mTextViewMetreId.setText("Data not available");
            holder.mTextViewMetreStreetAddress.setText("Data not available");
        }


    }

    @Override
    public int getItemCount() {
        if(mMetreAccounts != null){
            return mMetreAccounts.size();
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTextViewMetreId;
        public final TextView mTextViewMetreStreetAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewMetreId = itemView.findViewById(R.id.text_view_metre_id);
            mTextViewMetreStreetAddress = itemView.findViewById(R.id.text_view_metre_street_address);
        }
    }

    public void setMetreAccounts(List<MetreAccount> metreAccounts){
        mMetreAccounts = metreAccounts;
        notifyDataSetChanged();
    }
}
