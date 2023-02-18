package khanhdang.ueh.edu.vn.btbuoi1;

import static java.util.Objects.hash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView tvMath;
    private TextView tvResult;
    private ArrayList<String> math = new ArrayList<String>();
    private  ArrayList<String> result = new ArrayList<String>();

    /*Cách truyền dữ liệu với biến Bundle và ArrayList
                Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("math",(Serializable) math);
                args.putSerializable("result",(Serializable) result);
                intent.putExtra("Bundle",args);
                startActivity(intent);*/
    private int [] idbutton ={R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                            R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnDiv,
                            R.id.btnDot,R.id.btnMul,R.id.btnPlus,R.id.btnSub,R.id.btnLS,
                            R.id.btnResult,R.id.btnC,R.id.btnAC};
    private View.OnClickListener buttonclick= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            String smath=tvMath.getText().toString().trim();

            if (id == R.id.btnC) {
                if(!smath.equals("|"))
                {
                    if(smath.length()==1)
                    {
                        smath=smath.substring(0,smath.length()-1);
                        tvMath.setText(smath+"|");
                    }
                    else
                    {
                        smath=smath.substring(0,smath.length()-1);
                        tvMath.setText(smath);
                    }

                }
            }
            else if(id==R.id.btnAC)
            {
                Imp();
            }
            else if (id == R.id.btnResult) {
                tvResult.setText("="+cal());
                math.add(tvMath.getText().toString());
                result.add(tvResult.getText().toString());
            }
            else if(smath.equals("|") &&(id==R.id.btnDiv || id==R.id.btnDot || id==R.id.btnMul ||
                    id==R.id.btnSub || id==R.id.btnPlus))
            {
                tvMath.setText("0"+((Button) findViewById(id)).getText().toString());
            }
            else if(checkmath(smath)&&(id==R.id.btnDiv || id==R.id.btnDot || id==R.id.btnMul ||
                    id==R.id.btnSub || id==R.id.btnPlus))
            {
                tvMath.setText(cal()+((Button) findViewById(id)).getText().toString());
            } else if (id==R.id.btnLS)
            {
                if(math.isEmpty() && result.isEmpty())
                {
                    if (tvMath.getText().toString() == "|" && tvResult.getText().toString()=="0")
                    {
                        math.add("");
                        result.add("0");
                    }
                    else
                    {
                        math.add(tvMath.getText().toString());
                        result.add(tvResult.getText().toString());
                    }
                }
                Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("math",(Serializable) math);
                args.putSerializable("result",(Serializable) result);
                intent.putExtra("Bundle",args);
                startActivity(intent);
                if (math.get(0)=="|" && result.get(0)=="0")
                {
                    math.clear();
                    result.clear();
                }
            } else {
                for (int i = 0; i < idbutton.length; i++) {
                    if (id == idbutton[i]) {
                        String text = ((Button) findViewById(id)).getText().toString();

                        if (smath.equals("|")) {
                            tvMath.setText("");
                        }
                        tvMath.append(text);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectView();
    }

    private void ConnectView()
    {
        tvMath = findViewById(R.id.tvMath);
        tvResult=findViewById(R.id.tvResult);

        for (int i=0; i < idbutton.length;i++)
        {
            findViewById(idbutton[i]).setOnClickListener(buttonclick);
        }
        Imp();
    }

    private void Imp()
    {
        tvMath.setText("|");
        tvResult.setText("0");
    }
    private String cal()
    {
        String mathcal = tvMath.getText().toString();
        double Rs=0.0;
        if(mathcal.split("\\-").length==2)
        {
            try {
                String [] number = mathcal.split("\\-");
                Rs = Double.parseDouble(number[0])- Double.parseDouble(number[1]);
            }catch (Exception e)
            {
                tvResult.setText(e.getMessage());
            }

        }
        if(mathcal.split("\\+").length==2)
        {
            try {
                String [] number = mathcal.split("\\+");
                Rs = Double.parseDouble(number[0])+ Double.parseDouble(number[1]);
            }catch (Exception e)
            {
                tvResult.setText(e.getMessage());
            }

        }
        if(mathcal.split("\\*").length==2)
        {
            try {
                String [] number = mathcal.split("\\*");
                Rs = Double.parseDouble(number[0])* Double.parseDouble(number[1]);
            }catch (Exception e)
            {
                tvResult.setText(e.getMessage());
            }

        }
        if(mathcal.split("\\/").length==2)
        {
            try {
                String [] number = mathcal.split("\\/");
                Rs = Double.parseDouble(number[0])/ Double.parseDouble(number[1]);
            }catch (Exception e)
            {
                tvResult.setText(e.getMessage());
            }
        }
        return Rs+"";
    }
    private boolean checkmath(String smath)
    {
        if(smath.split("\\*").length==2||smath.split("\\/").length==2||
                smath.split("\\+").length==2 || smath.split("\\-").length==2)
        {
            return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("Math",String.valueOf(tvMath.getText()));
        outState.putString("Result",String.valueOf(tvResult.getText()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState !=null)
        {
            String math =savedInstanceState.getString("Math");
            String result =savedInstanceState.getString("Result");
            if (math !=null && result !=null)
            {
                tvResult.setText(result);
                tvMath.setText(math);
            }
        }
    }
}