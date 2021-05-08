package com.ekspeace.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekspeace.Model.Transaction;
import com.ekspeace.R;

import static com.ekspeace.Model.Constants.Rand;
import java.util.List;

public class RecycleViewUserTransaction extends RecyclerView.Adapter<RecycleViewUserTransaction.MyViewHolder> {

    Context context;
    java.util.List<Transaction> List;

    public RecycleViewUserTransaction(Context context, List<Transaction> List) {
        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public RecycleViewUserTransaction.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recycleview_transaction, parent, false);
        return new RecycleViewUserTransaction.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleViewUserTransaction.MyViewHolder holder, int position) {
        String transaction_type;
        if (List.get(position).getTransactionType().equals("Deposit")){
            transaction_type = "+" + Rand;
        }else {
            transaction_type = "-" + Rand;
        }
        holder.transaction_amount.setText((transaction_type + List.get(position).getAmount()).toString());
        holder.transaction_type.setText(List.get(position).getTransactionType());
    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction_amount, transaction_type;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_amount = itemView.findViewById(R.id.transaction_amount);
            transaction_type = itemView.findViewById(R.id.transaction_type);
        }

    }
}
