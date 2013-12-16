package authentication;

import com.tdl.viewpagerdrawer.R;
import com.tdl.viewpagerdrawer.SimpleEula;
import com.tdl.viewpagerdrawer.R.id;
import com.tdl.viewpagerdrawer.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by PsyGik on 11/13/13.
 */
public class SignUp extends Activity {
	Button submit,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        new SimpleEula(this).show();
        submit = (Button)findViewById(R.id.submit);
        cancel = (Button)findViewById(R.id.cancel);
        
        submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUp.this,SignIn.class);
                startActivity(i);
                SignUp.this.finish();
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUp.this,LoginActivity.class);
                startActivity(i);
                SignUp.this.finish();
			}
		});
        
    }
}
