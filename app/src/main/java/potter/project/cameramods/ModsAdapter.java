package potter.project.cameramods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Sharan on 2/26/2018.
 */

public class ModsAdapter extends BaseAdapter {
    private Context context;
    private List<Mods> modsList;
    private LayoutInflater inflater = null ;
    private RequestQueue queue;
    public ModsAdapter(Context context,List <Mods> list)
    {
        this.context = context;
        this.modsList = list;
        inflater = LayoutInflater.from(context);
        queue = Volley.newRequestQueue(context);
    }
    public class ViewHolder
    {
        TextView _title;
        TextView _desc;
    }
    @Override
    public int getCount() {
        return modsList.size();
    }

    @Override
    public Object getItem(int i) {
        return modsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Mods mods = modsList.get(i);
        final ViewHolder holder;
        if(view == null)
        {
            view = inflater.inflate(R.layout.row_layout,null);
            holder = new ViewHolder();
            holder._title = (TextView) view.findViewById(R.id.title);
            holder._desc = (TextView) view.findViewById(R.id.description);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder._title.setText(mods.getTitle().toString());
        holder._desc.setText(mods.getDesc().toString());
        return view;
    }
}
