package zekisanmobile.petsitter.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Interfaces.RecyclerViewOnClickListenerHack;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.R;

/**
 * Created by ezequiel on 09/10/15.
 */
public class SitterAdapter extends RecyclerView.Adapter<SitterAdapter.MyViewHolder> {

    private List<Sitter> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public SitterAdapter(Context c, List<Sitter> l){
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_sitter, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_sitter.setImageResource(mList.get(position).getPhoto());
        holder.tv_name.setText(mList.get(position).getName());
        holder.tv_descricao.setText("Cuidador de animais");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void setList(ArrayList<Sitter> receivedSitters){
        mList = receivedSitters;
    }

    public void addListItem(Sitter s, int position){
        mList.add(s);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public Sitter getListItem(int position){
        return mList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView iv_sitter;
        public TextView tv_name;
        public TextView tv_descricao;

        public MyViewHolder(View itemView) {
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
}
