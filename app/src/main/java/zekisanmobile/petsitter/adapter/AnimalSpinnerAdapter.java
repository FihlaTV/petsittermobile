package zekisanmobile.petsitter.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.vo.Animal;

public class AnimalSpinnerAdapter extends RealmBaseAdapter<Animal> implements ListAdapter {

    public AnimalSpinnerAdapter(Context context, RealmResults<Animal> animals){
        super(context, animals, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_animal_spinner, parent, false);
            holder = new CustomViewHolder();
            convertView.setTag(holder);

            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        }else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        final Animal animal = adapterData.get(position);
        holder.tvName.setText(animal.getName());

        return convertView;
    }

    private static class CustomViewHolder {
        TextView tvName;
    }

}
