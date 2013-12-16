package utils;

import fileops.AudioChooser;
import fileops.FileChooser;
import fileops.ImageChooser;
import fileops.VideoChooser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	
	int PAGE_COUNT = 4;
	ListFragment myFragment;
	/** Constructor of the class */
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/** This method will be invoked when a page is requested to create */
	@Override
	public Fragment getItem(int arg0) {
		
		switch(arg0){
		case 0:myFragment = new FileChooser();
			break;
		case 1:myFragment = new ImageChooser();
			break;
		case 2:myFragment = new AudioChooser();
			break;
		case 3:myFragment = new VideoChooser();
			break;
		}
		//ListFragment myFragment = new MyFragment();
		Bundle data = new Bundle();
		data.putInt("current_page", arg0+1);
		myFragment.setArguments(data);
		return myFragment;
	}

	/** Returns the number of pages */
	@Override
	public int getCount() {		
		return PAGE_COUNT;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {		
		String[] string = {"SD Card","Pictures","Music","Videos"};
		return string[position];
	}
	@Override
    public Object instantiateItem(View collection, int position) {
		return position;
        
    }
	public void onBackPressed(){
		Toast.makeText(myFragment.getActivity(), myFragment.getListAdapter().toString(),Toast.LENGTH_LONG).show();
	}
}
