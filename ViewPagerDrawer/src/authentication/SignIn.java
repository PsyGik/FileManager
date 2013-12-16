package authentication;

import com.tdl.viewpagerdrawer.MainActivity;
import com.tdl.viewpagerdrawer.R;
import com.tdl.viewpagerdrawer.R.id;
import com.tdl.viewpagerdrawer.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by PsyGik on 11/13/13.
 */
public class SignIn extends Activity implements View.OnFocusChangeListener,View.OnClickListener {

    EditText userName,password;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userName = (EditText)findViewById(R.id.userName_et);
        password =(EditText)findViewById(R.id.password_et);
        signin = (Button)findViewById(R.id.signin);
        password.setOnFocusChangeListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()){
            case R.id.password_et:
                if(userName.getText().toString().length()<2){
                    userName.setError("Invalid User Name");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.signin:
                Intent i = new Intent(SignIn.this,MainActivity.class);
                startActivity(i);
                this.finish();
                break;
        }
    }
}
