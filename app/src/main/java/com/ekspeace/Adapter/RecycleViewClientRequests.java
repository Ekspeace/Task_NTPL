package com.ekspeace.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ekspeace.Model.AdminTransactionRequest;
import com.ekspeace.Model.Transaction;
import com.ekspeace.Model.UserAccount;
import com.ekspeace.R;
import com.ekspeace.ViewModel.UserViewModel;

import static com.ekspeace.Model.Constants.Rand;

import java.util.List;


public class RecycleViewClientRequests extends RecyclerView.Adapter<RecycleViewClientRequests.MyViewHolder> {
   private Context context;
   private List<AdminTransactionRequest> List;
   private OnItemClickListener listener;

    public RecycleViewClientRequests(Context context, List<AdminTransactionRequest> List) {
        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public RecycleViewClientRequests.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recycleview_clients_requests, parent, false);
        return new RecycleViewClientRequests.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleViewClientRequests.MyViewHolder holder, int position) {
        holder.client_name.setText(List.get(position).getClientName());
        holder.client_amount.setText((Rand + List.get(position).getAmount()).toString());
        holder.client_account_number.setText(List.get(position).getAccountNumber());
        holder.client_transaction_type.setText(List.get(position).getTransactionType());
    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView client_name, client_amount,client_account_number, client_transaction_type;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            client_name = itemView.findViewById(R.id.client_name);
            client_amount = itemView.findViewById(R.id.client_price);
            client_account_number = itemView.findViewById(R.id.client_account_number);
            client_transaction_type = itemView.findViewById(R.id.client_transaction_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = MyViewHolder.this.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position);
                }
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
