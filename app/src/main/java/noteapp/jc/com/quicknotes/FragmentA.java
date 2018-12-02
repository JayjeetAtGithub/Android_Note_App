package noteapp.jc.com.quicknotes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FragmentA extends Fragment {
    public int temp;

    public int di;
    public static SQLiteDatabase  db ;
    //public static ArrayList<String> title_notes = new ArrayList<String>();
    public CustomListAdapter adapter;
    public static List<CustomList> mylist;
    public static ListView listView;


    public FragmentA() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        final FrameLayout frameLayout = view.findViewById(R.id.frame);///new code added
        db = getContext().openOrCreateDatabase("mydatabase", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS memo(id INTEGER PRIMARY KEY AUTOINCREMENT ,title VARCHAR,notes VARCHAR,idn INTEGER,dtime VARCHAR);");//NEW ID COLUMN ADDED
        listView = view.findViewById(R.id.list);
        mylist = new ArrayList<CustomList>();
        mylist.clear();//clears the array


        populateList();


        adapter = new CustomListAdapter(getContext(), mylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showMemo(i+1);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                temp = i;
               /* di = i;
                mylist.remove(i);
                deleteMemo(i+1);
                adapter.notifyDataSetChanged();
                changeids();
                mylist.clear();
                populateList();*/
               AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
               alert.setCancelable(false);//can be removed if needed
               alert.setMessage("Are You Sure, You Want To Delete The Memo");
               alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       di = temp;
                       mylist.remove(temp);
                       deleteMemo(temp+1);
                       adapter.notifyDataSetChanged();
                       changeids();
                       mylist.clear();
                       populateList();
                       if (mylist.size() == 0) {////////////////new code added

                           frameLayout.setBackgroundResource(R.drawable.nolistbackground);
                       } else {
                           frameLayout.setBackgroundColor(getResources().getColor(R.color.background));

                       }


                   }
               });
               alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Toast.makeText(getContext(), "Memo Not Deleted", Toast.LENGTH_SHORT).show();

                   }
               });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;

            }



        });//---------------------------------------------------------------------------------------



        if (mylist.size() == 0) {

            frameLayout.setBackgroundResource(R.drawable.nolistbackground);
        } else {
            frameLayout.setBackgroundColor(getResources().getColor(R.color.background));

        }//-----------------------------------------------------------------------------------------


        return view;
    }

    public void changeids() {
        for(int k = (di+1) ; k<=(mylist.size());k++){


            db.execSQL("UPDATE memo SET idn = " + k + " WHERE idn = " + (k+1) + ";");


        }
        Toast.makeText(getContext(), "Memo Deleted", Toast.LENGTH_SHORT).show();
    }


    public static void populateList()
    {
        Cursor cursor = db.rawQuery("SELECT * FROM memo", null);
        //final int ids = cursor.getColumnIndex("id");
        final int index2 = cursor.getColumnIndex("idn");
        final int index = cursor.getColumnIndex("title");
        final int index1 = cursor.getColumnIndex("notes");
        final int index3 = cursor.getColumnIndex("dtime");
        cursor.moveToFirst();
        if (cursor != null && (cursor.getCount() > 0)) {
            do {
                String showid = "#" + String.valueOf(cursor.getInt(index2));
                String showdt = "Created/Updated On:" + String.valueOf(cursor.getString(index3));

                mylist.add(new CustomList(String.valueOf(cursor.getString(index)),String.valueOf(cursor.getString(index1)),showid,showdt));//String.valueOf(cursor.getInt(index2))));
            } while (cursor.moveToNext());
        }
        cursor.close();//closes the cursor
    }

    public void showMemo(int j)
    {
        Cursor c = db.rawQuery("SELECT * FROM memo WHERE idn = " + j,null);
        int idget = c.getColumnIndex("id");
        int titleget = c.getColumnIndex("title");
        int contentget = c.getColumnIndex("notes");
        int lo = c.getColumnIndex("idn");
        c.moveToFirst();
        //int valid = c.getInt(idget);////////////////should be commented
        int jY = c.getInt(lo);
        String a = String.valueOf(c.getString(titleget));
        String b = String.valueOf(c.getString(contentget));
        Intent intent1 = new Intent(getContext(),Entry.class);
        intent1.putExtra("titleval",a);
        intent1.putExtra("noteval",b);
       // intent1.putExtra("idvalue",valid);///////////can be commented
        intent1.putExtra("newid",jY);
        intent1.putExtra("upd",1);//new code added
        startActivity(intent1);
        c.close();


    }
    public static void updateMemo(String ntitle,String nname,int idv,String ktm)////////////changes
    {
        String tryup = "UPDATE memo SET title = '" + ntitle + "'," + " notes = '" + nname + "'," + " dtime = '" + ktm + "' WHERE idn = " + idv + ";";
        db.execSQL(tryup);

    }
    public static void deleteMemo(int idv)
    {


        db.execSQL("DELETE FROM memo WHERE idn = " + idv + ";");

    }

}
