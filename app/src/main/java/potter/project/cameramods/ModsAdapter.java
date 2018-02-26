package potter.project.cameramods;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharan on 2/26/2018.
 */

public class ModsAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ModsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(Mods object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ModHolder modHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            modHolder = new ModHolder();
            modHolder.title = row.findViewById(R.id.title);
            modHolder.description = row.findViewById(R.id.description);
            row.setTag(modHolder);
        }
        else
        {
            modHolder = (ModHolder) row.getTag();
        }

        Mods mods = (Mods) this.getItem(position);
        modHolder.title.setText(mods.getName());
        modHolder.description.setText(mods.getDesc());
        return row;
    }

    static class ModHolder
    {
        TextView title,description;
    }
}
