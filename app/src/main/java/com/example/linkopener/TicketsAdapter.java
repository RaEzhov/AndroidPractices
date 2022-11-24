package com.example.linkopener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.linkopener.screens.CheckTicketActivity;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {
    
    private Context mCtx;
    private List<TicketInfo> ticketList;

    public TicketsAdapter(Context mCtx, List<TicketInfo> ticketList) {
        this.mCtx = mCtx;
        this.ticketList = ticketList;
    }

    @Override
    public TicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.tickets_item, parent, false);
        return new TicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketsViewHolder holder, int position) {
        TicketInfo t = ticketList.get(position);

        holder.textViewNumber.setText(t.getNumber());
        holder.textViewOwner.setText(t.getOwner());
        holder.textViewPet.setText(t.getPet());
        holder.textViewDate.setText(t.getDate());

        if (t.getChecked()) {
            holder.textViewNumber.setPaintFlags(holder.textViewNumber.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textViewNumber.setPaintFlags(holder.textViewNumber.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class TicketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNumber, textViewOwner, textViewPet, textViewDate;

        public TicketsViewHolder(View itemView) {
            super(itemView);

            textViewNumber = itemView.findViewById(R.id.ticket_number);
            textViewOwner = itemView.findViewById(R.id.ticket_owner);
            textViewPet = itemView.findViewById(R.id.ticket_pet);
            textViewDate = itemView.findViewById(R.id.ticket_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TicketInfo ticket = ticketList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, CheckTicketActivity.class);
            intent.putExtra("ticket", ticket);

            mCtx.startActivity(intent);
        }
    }
}