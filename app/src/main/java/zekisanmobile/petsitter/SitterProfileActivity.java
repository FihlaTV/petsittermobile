package zekisanmobile.petsitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zekisanmobile.petsitter.Adapters.SitterProfileAdapter;
import zekisanmobile.petsitter.Model.Header;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.SitterProfileListItem;

public class SitterProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Sitter sitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_profile);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("SITTER");

        recyclerView = (RecyclerView) findViewById(R.id.sitter_profile_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);

        SitterProfileAdapter adapter = new SitterProfileAdapter(getHeader(), getListItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public Header getHeader(){
        Header header = new Header();
        header.setHeaderText(sitter.getName());
        header.setBackgroundImage(sitter.getProfile_background());
        return header;
    }

    public List<SitterProfileListItem> getListItems(){
        List<SitterProfileListItem> listItems = new ArrayList<SitterProfileListItem>();
        for(int i = 0; i < 10; i++){
            SitterProfileListItem item = new SitterProfileListItem();
            item.setName("pet");
            if(i % 2 == 0){
                item.setId(R.drawable.sitter2);
            }
            else{
                item.setId(R.drawable.sitter4);
            }
            listItems.add(item);
        }
        return listItems;
    }

}
