package potter.project.cameramods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Sharan on 2/26/2018.
 */

public class ModsAdapter extends BaseAdapter {
    private Context context;
    private JSONArray list;
    private LayoutInflater inflater = null ;
    private RequestQueue queue;
    public ModsAdapter(Context context,JSONArray list)
    {
        this.context = context;
        this.list = list;
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
        return list.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return list.get(i);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            JSONObject item = list.getJSONObject(i);
            final ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.row_layout, null);
                holder = new ViewHolder();
                holder._title = view.findViewById(R.id.title);
                holder._desc = view.findViewById(R.id.description);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder._title.setText(item.getString("name"));
            holder._desc.setText(item.getString("description"));
            return view;
        }catch (Exception e){
            return null;
        }
    }
}
