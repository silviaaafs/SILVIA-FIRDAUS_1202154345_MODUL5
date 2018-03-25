package com.example.android.silviafirdaus_1202154345_modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //deklarasi variabel
    private Database dtbase;
    private RecyclerView rcview;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mengeset title menjadi To Do List
        setTitle("To Do List App");

        //Merefer semua variabel yang ada
        //mengakses recyclerview
        rcview = findViewById(R.id.rec_view);
        //membuat araylist
        data_list = new ArrayList<>();
        //membuat database
        dtbase = new Database(this);
        //memanggil method readdata
        dtbase.readdata(data_list);

        //isialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);

        //membuat adapter baru
        adapter = new Adapter(this,data_list, color);
        //mengeset size
        rcview.setHasFixedSize(true);
        rcview.setLayoutManager(new LinearLayoutManager(this));
        //inisiasi adapter untuk recycler view
        rcview.setAdapter(adapter);

        //menjalankan method hapus data pada list to do
        hapusgeser();
    }


    public void hapusgeser(){  //membuat method untuk menghapus item pada to do list
        //membuat touch helper baru untuk recycler view
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);
                //apabila item di geser ke arah kiri
                if(direction==ItemTouchHelper.LEFT){
                    //remove item yang dipilih dengan mengenali todonya sebagai primary key
                    if(dtbase.removedata(current.getTodo())){
                        //menghapus data
                        adapter.deleteData(position);
                        //membuat snack bar dan toast jika item terhapus
                        Snackbar.make(findViewById(R.id.coordinator), "List Telah Terhapus", 2000).show();
                    }
                }
            }
        };
        //menentukan itemtouchhelper untuk recycler view
        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //membuat menu pada activty
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //method yang dijalankan ketika item di pilih
        //mengambil id dari item
        int id = item.getItemId();
        //mengambil item setting dari xml
        if (id==R.id.action_settings){
            //membuat intent ke yang akan di tampilkan ke menu Settings
            Intent intent = new Intent(MainActivity.this, Settings.class);
            //memulai intent
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void addlist(View view) { //method yang akan dijalankan ketika tombol add di klik
        //intent ke class add to do
        Intent intent = new Intent(MainActivity.this, AddToDo.class);
        //memulai intent
        startActivity(intent);
    }
}