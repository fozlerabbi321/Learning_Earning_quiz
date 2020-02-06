package com.learnearning.learnnnge;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TopListActivity extends AppCompatActivity {

    Query top10query;
    static int i = 1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_list);
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }

            recyclerView=(RecyclerView)findViewById(R.id.recycler);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(mLayoutManager);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
            top10query   = ref.orderByChild("point").limitToLast(15);
        }

        public void onStart(){

            super.onStart();
            FirebaseRecyclerAdapter<datapojo,DepositViewHolder> adapter=new FirebaseRecyclerAdapter<datapojo, DepositViewHolder>(
                    datapojo.class,R.layout.leader_recycle,
                    DepositViewHolder.class,top10query
            )
            {
                protected  void  populateViewHolder(DepositViewHolder viewHolder,datapojo model,int position){
                    viewHolder.setupViews(model.getpoint(),model.getName());
                }
            };
            recyclerView.setAdapter(adapter);
        }
        public  static class DepositViewHolder extends  RecyclerView.ViewHolder{
            private View mview;

            public DepositViewHolder(View view){
                super(view);
                mview=view;
            }
            void setupViews(int user ,String Name){
                Log.i("123321",Name+"");

                TextView usertext=(TextView)mview.findViewById(R.id.lpoint);
                TextView Name_text=(TextView)mview.findViewById(R.id.lname);
                ImageView circle=(ImageView) mview.findViewById(R.id.limageView);
                usertext.setText(""+user+" point");
               Name_text.setText(Name);

               ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
                int color1 = generator.getRandomColor();
// generate color based on a key (same key returns the same color), useful for list/grid views


// declare the builder object once.
                TextDrawable.IBuilder builder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .round();

// reuse the builder specs to create multiple drawables
                TextDrawable ic1 = builder.build((Name.charAt(0)+"").toUpperCase(), color1);

                circle.setBackground(ic1);

                usertext.setText(user+" point");
                Name_text.setText(Name+"");

            }

        } @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId()==android.R.id.home){
                startActivity(new Intent(TopListActivity.this, HomeActivity.class));

                finish();
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
    }
    }
