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

    private List<Sitter> mList;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public SitterAdapter(List<Sitter> l){
        mList = l;
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
            if (mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
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
        Sitter sitter = mList.get(position);

        holder.iv_sitter.setImageResource(sitter.getPhoto());
        holder.tv_name.setText(sitter.getName());
        holder.tv_descricao.setText("Cuidador de animais");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void setList(List<Sitter> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
}
