/*
 * Copyright (c) 2013-2014 Humesis Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package authentication;

import com.tdl.viewpagerdrawer.R;
import com.tdl.viewpagerdrawer.R.anim;
import com.tdl.viewpagerdrawer.R.id;
import com.tdl.viewpagerdrawer.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class LoginActivity extends Activity implements OnClickListener {

    Button sign_in, sign_up;
    private ViewFlipper viewFlipper;
    private float lastX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        viewFlipper.startFlipping();
        sign_in = (Button) findViewById(R.id.sigin_button);
        sign_up=(Button) findViewById(R.id.signup_button);
        sign_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
        // Method to handle touch event like left to right swap and right to left swap
        public boolean onTouchEvent(MotionEvent touchevent) 
        {			
        				
                     switch (touchevent.getAction())
                     {
                            // when user first touches the screen to swap
                             case MotionEvent.ACTION_DOWN: 
                             {
                                 lastX = touchevent.getX();
                                 break;
                            }
                             case MotionEvent.ACTION_UP: 
                             {
                                 float currentX = touchevent.getX();
                                 
                                 // if left to right swipe on screen
                                 if (lastX < currentX) 
                                 {
                                      // If no more View/Child to flip
                                     if (viewFlipper.getDisplayedChild() == 0)
                                         break;
                                     
                                     // set the required Animation type to ViewFlipper
                                     // The Next screen will come in form Left and current Screen will go OUT from Right 
                                     viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                     viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                     // Show the next Screen
                                       viewFlipper.showNext();
                                 }
                                 
                                 // if right to left swipe on screen
                                 if (lastX > currentX)
                                 {
                                     if (viewFlipper.getDisplayedChild() == 1)
                                         break;
                                     // set the required Animation type to ViewFlipper
                                     // The Next screen will come in form Right and current Screen will go OUT from Left 
                                     viewFlipper.setInAnimation(this, R.anim.in_from_right);
                                     viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                                     // Show The Previous Screen
                                     viewFlipper.showPrevious();
                                 }
                                 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                                 viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                                 break;
                             }
                     }
                     return false;
        }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sigin_button:
                Intent intent = new Intent(LoginActivity.this, SignIn.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.signup_button:
                Intent intent2 = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent2);
                break;
        }
    }
}