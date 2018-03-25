package com.example.android.silviafirdaus_1202154345_modul5;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 3/24/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.holder> {
    //deklarasi variabel
    private Context mcontex;
    private List<AddData> list;
    int color;

    //konstruktor
    public Adapter(Context context, List<AddData> list, int color){
        this.mcontex=context;
        this.list=list;
        this.color=color;
    }

    @Override //menentukan viewholder untuk recyclerview
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(mcontex).inflate(R.layout.cardview, parent, false);
        holder hldr = new holder(view);
        return hldr;
    }

    @Override //mengeset nilai yang didapatkan pada viewholder
    public void onBindViewHolder(holder holder, int position) {
        AddData data = list.get(position);
        holder.ToDo.setText(data.getTodo());
        holder.Desc.setText(data.getDesc());
        holder.Priority.setText(data.getPrior());
        holder.card_view.setCardBackgroundColor(mcontex.getResources().getColor(this.color));
    }

    @Override //mendapatkan jumlah list
    public int getItemCount() {
        return list.size();
    }

    //mendapatkan list dari adapter
    public AddData getData(int position){
        return list.get(position);
    }

    //untuk menghapus list pada todolist
    public void deleteData(int i){
        //hapus item yang terpilih
        list.remove(i);
        //menampilkan notifikasi item yang dihapus
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, list.size());
    }

    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable
        public TextView ToDo, Desc, Priority;
        public CardView card_view;
        public holder(View itemView){
            super(itemView);

            //mengambil id dari xml
            ToDo = itemView.findViewById(R.id.todo);
            Desc = itemView.findViewById(R.id.description);
            Priority = itemView.findViewById(R.id.number);
            card_view = itemView.findViewById(R.id.cardview);
        }
    }
}

