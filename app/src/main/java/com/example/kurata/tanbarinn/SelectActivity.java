package com.example.kurata.tanbarinn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Button tambourine_Button=(Button)findViewById(R.id.tambourine);
        Button castanets_Button=(Button)findViewById(R.id.castanets);
        Button maraca_Button=(Button)findViewById(R.id.maraca);

        tambourine_Button.setOnClickListener(click);
        castanets_Button.setOnClickListener(click);
        maraca_Button.setOnClickListener(click);
    }

    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(SelectActivity.this,TanbarinActivity.class);
            switch (view.getId()){
                case R.id.tambourine:
                    intent.putExtra("decision",1);
                    break;
                case R.id.castanets:
                    intent.putExtra("decision",2);
                    break;
                case R.id.maraca:
                    intent.putExtra("decision",3);
                    break;
            }
           startActivity(intent);
        }
    };
}
