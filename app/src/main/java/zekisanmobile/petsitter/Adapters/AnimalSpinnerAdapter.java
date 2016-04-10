package zekisanmobile.petsitter.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.List;

import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.R;

public class AnimalSpinnerAdapter extends ArrayAdapter<Animal> {

    List<Animal> animals;

    public AnimalSpinnerAdapter(Context context, List<Animal> animals){
        super(context, 0, animals);
        this.animals = animals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal animal = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_animal_spinner, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(animal.getName());

        return convertView;
    }
}
