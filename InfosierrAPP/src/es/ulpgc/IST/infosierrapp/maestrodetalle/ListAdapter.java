package es.ulpgc.IST.infosierrapp.maestrodetalle;

import es.ulpgc.IST.infosierrapp.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ListAdapter extends ArrayAdapter {

    private Activity context;
    private ItemModel[] data;

    private static class ViewHolder {
        TextView title;
        
    }

    public ListAdapter(Activity context, ItemModel[] data) {
        super(context, R.layout.item_view, data);
        this.data = data;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder holder;

        if(item == null){
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.item_view, null);

            holder = new ViewHolder();
            holder.title = (TextView)item.findViewById(R.id.lblTitle);
            item.setTag(holder);
        } else {
            holder = (ViewHolder)item.getTag();
        }

        holder.title.setText(data[position].getNombre());
        return(item);
    }
}
