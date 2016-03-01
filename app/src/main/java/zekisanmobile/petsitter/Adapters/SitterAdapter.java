package zekisanmobile.petsitter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;

public class SitterAdapter extends RecyclerView.Adapter<SitterAdapter.ViewHolder> {

    private List<Sitter> sitters;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
    private Context parentContext;

    public SitterAdapter(List<Sitter> l, Context parentContext){
        this.parentContext = parentContext;
        sitters = l;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView iv_sitter;
        public TextView tv_name;
        public TextView tv_descricao;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_sitter = (ImageView) itemView.findViewById(R.id.iv_sitter);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_descricao = (TextView) itemView.findViewById(R.id.tv_descricao);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recyclerViewOnClickListenerHack != null){
                recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }

    @Override
    public SitterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_sitter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SitterAdapter.ViewHolder holder, int position) {
        Sitter sitter = sitters.get(position);

        int imageId = parentContext.getResources().getIdentifier(sitter.getProfile_background(), "drawable", parentContext.getPackageName());
        holder.iv_sitter.setImageResource(imageId);
        holder.tv_name.setText(sitter.getName());
        holder.tv_descricao.setText("Cuidador de animais");
    }

    @Override
    public int getItemCount() {
        return sitters.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack){
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }

    public void updateSittersList(List<Sitter> sitters){
        this.sitters = sitters;
        if(this.sitters != null) notifyDataSetChanged();
    }
}
