package noteapp.jc.com.quicknotes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jayjeet Chakraborty on 08-12-2017.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<CustomList> mylist;

    public CustomListAdapter(Context mcontext, List<CustomList> mylist) {
        this.mcontext = mcontext;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mcontext,R.layout.custom_list,null);
        TextView t1 = v.findViewById(R.id.tid);
        TextView t2 = v.findViewById(R.id.nid);
        TextView t3 = v.findViewById(R.id.mid);
        TextView t4 = v.findViewById(R.id.dtid);/////code addition
        t1.setText(mylist.get(i).getTitleStore());
        t2.setText(mylist.get(i).getNoteStore());
        t3.setText(mylist.get(i).getMemoStore());
        t4.setText(mylist.get(i).getDateTime());
        return v;

    }
}
