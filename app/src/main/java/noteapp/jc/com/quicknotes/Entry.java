package noteapp.jc.com.quicknotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry extends AppCompatActivity {
    public static EditText title, notes;
    FloatingActionButton b,u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        title = (EditText) findViewById(R.id.editText);
        notes = (EditText) findViewById(R.id.editText2);
       // memono = (TextView)findViewById(R.id.textView);


        b = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        u = (FloatingActionButton)findViewById(R.id.floatingActionButton2);
        final int pass = getIntent().getExtras().getInt("autoidn");
        //memono.setText(String.valueOf(pass+1));
        int gf = getIntent().getExtras().getInt("btndis");//new code added
        if(gf==1)
        {
            u.setEnabled(false);/////new code added
        }
        int hyu = getIntent().getExtras().getInt("upd");////new code added
        if(hyu == 1)
        {
            b.setEnabled(false);//new code added
        }






        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                String mydate = dateFormat.format(date);
                //int gf = getIntent().getExtras().getInt("btndis");//new code added
                String titlev = title.getText().toString();
                String notesv = notes.getText().toString();
               // int umemono = Integer.parseInt(memono.getText().toString());//
                addRecord(titlev,notesv,pass+1,mydate);
                Intent u = new Intent(Entry.this,MainActivity.class);
                startActivity(u);

            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();///////////
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");/////////////////
                String mydate1 = dateFormat.format(date);///////////////
                String uTitle = title.getText().toString();
                String uNotes = notes.getText().toString();
               // int umemono = Integer.parseInt(memono.getText().toString());
                int idput = getIntent().getExtras().getInt("newid");
                FragmentA.updateMemo(uTitle,uNotes,idput,mydate1);///////////////////
                Toast.makeText(Entry.this, "Memo Updated", Toast.LENGTH_SHORT).show();


            }
        });

        String recTitle = getIntent().getStringExtra("titleval");
        String recnote = getIntent().getStringExtra("noteval");
        int recNewid = getIntent().getIntExtra("newid",1);
        String def = String.valueOf(recNewid).toString();//

        title.setText(recTitle);
        notes.setText(recnote);
       // memono.setText(def);//

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this,MainActivity.class);
        startActivity(intent2);
        FragmentA.mylist.clear();
        FragmentA.populateList();

    }

    public void addRecord(String title1, String notes1,int jc,String dt) {


        String sql = "INSERT INTO memo (title,notes,idn,dtime) VALUES ('" + title1 + "','" + notes1 + "','" + jc + "','" + dt + "');";
        FragmentA.db.execSQL(sql);
        Toast.makeText(this, "Memo Saved Successfully", Toast.LENGTH_LONG).show();

    }

}
