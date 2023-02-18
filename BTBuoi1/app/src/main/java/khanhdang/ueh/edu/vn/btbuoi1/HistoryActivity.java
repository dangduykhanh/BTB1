package khanhdang.ueh.edu.vn.btbuoi1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    TextView tView1;
    TextView tView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        tView1 = findViewById(R.id.tView1);
        tView2=findViewById(R.id.tView2);
        Intent intent = getIntent();
        Bundle args =intent.getBundleExtra("Bundle");
        ArrayList<String> math =(ArrayList<String>) args.getSerializable("math");
        ArrayList<String> result = (ArrayList<String>) args.getSerializable("result");

        if(math.get(0)=="|" && result.get(0)=="0")
        {
            tView1.setText("");
            tView2.setText("0");
        }
        else
        {
            if (math.size()==1 && result.size()==1)
            {
                tView2.setText(math.get(0)+"\n"+result.get(0));
            }
            else
            {
                tView2.setText(math.get(math.size()-1)+"\n"+result.get(math.size()-1));
                for(int i=0;i <math.size()-1;i++)
                {
                    tView1.append("\n"+math.get(i)+"\n"+result.get(i)+"\n");
                }
            }
        }
    }
}