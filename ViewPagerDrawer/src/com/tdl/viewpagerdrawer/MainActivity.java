package com.tdl.viewpagerdrawer;


import java.util.ArrayList;

import settings.Settings;
import utils.MyFragmentPagerAdapter;
import utils.NsMenuAdapter;
import utils.NsMenuItemModel;
import utils.SpinnerNavItem;
import utils.TitleNavigationAdapter;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.AppOpsManager.OnOpChangedListener;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements OnPageChangeListener, OnNavigationListener{

	private ListView mDrawerList;
	DrawerLayout mDrawer;
	private CustomActionBarDrawerToggle mDrawerToggle;
	ViewPager pager;
	Spinner spinner;
	String temp;
	private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;
	MyFragmentPagerAdapter pagerAdapter;
	private boolean isDrawerLocked;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drawer);
 
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		/** Getting a reference to the ViewPager defined the layout file */        
        pager = (ViewPager) findViewById(R.id.pager);
        
        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();
        
        /** Instantiating FragmentPagerAdapter */
        pagerAdapter = new MyFragmentPagerAdapter(fm);
        
		isDrawerLocked=false;
		
		//Toast.makeText(MainActivity.this, ""+((ViewGroup.MarginLayoutParams)pager.getLayoutParams()).leftMargin+" "+(int)getResources().getDimension(R.dimen.drawer_size)+" "+this.getResources().getConfiguration().orientation, Toast.LENGTH_LONG).show();
		if(((ViewGroup.MarginLayoutParams)pager.getLayoutParams()).leftMargin == (int)getResources().getDimension(R.dimen.drawer_size)) {
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			 //Toast.makeText(this, "Landscape Here", Toast.LENGTH_LONG).show();
			 mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
			 isDrawerLocked = true;
			 getActionBar().setDisplayHomeAsUpEnabled(false);
			 getActionBar().setHomeButtonEnabled(false);
		}
		
		initDrawer();
		mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
		mDrawer.setDrawerListener(mDrawerToggle);
		
        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(this);
        
        actionBar = getActionBar(); 
        
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
         
        // Spinner title navigation data
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem("Local", R.drawable.add));
        navSpinner.add(new SpinnerNavItem("Dropbox", R.drawable.add));
        navSpinner.add(new SpinnerNavItem("SugarSync", R.drawable.add));
        navSpinner.add(new SpinnerNavItem("SkyDrive", R.drawable.add));       
         
        // title drop down adapter
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
 
        // assigning the spinner navigation     
        actionBar.setListNavigationCallbacks(adapter, this);
        
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		//pagerAdapter.onBackPressed();
		if(pager.getCurrentItem()!=0)
		pager.setCurrentItem(pager.getCurrentItem()-1);
		//Toast.makeText(getApplicationContext(), "back", 500).show();
	}
	
	/**
	 * this is used to set the elements in the navigation drawer
	 * 
	 */
	private void initDrawer() {
		NsMenuAdapter mAdapter = new NsMenuAdapter(this);

		//Add Home
		NsMenuItemModel home = new NsMenuItemModel(R.string.menu_home, R.drawable.home);
		mAdapter.addItem(home);

		// Add Header for library
		mAdapter.addHeader(R.string.menu_library);
		//Add photos option
		NsMenuItemModel photos = new NsMenuItemModel(R.string.menu_photos, R.drawable.photos);
		mAdapter.addItem(photos);
		//Add music option
		NsMenuItemModel music = new NsMenuItemModel(R.string.menu_music, R.drawable.music);
		mAdapter.addItem(music);
		//Add music option
		NsMenuItemModel video = new NsMenuItemModel(R.string.menu_video, R.drawable.video);
		mAdapter.addItem(video);
		
		//Add Header for share
		mAdapter.addHeader(R.string.menu_share);
		//Add music option
		NsMenuItemModel shared_by_me = new NsMenuItemModel(R.string.menu_shared_by, R.drawable.share_me);
		mAdapter.addItem(shared_by_me);
		//Add music option
		NsMenuItemModel shared_with_me = new NsMenuItemModel(R.string.menu_shared_with, R.drawable.share_all);
		mAdapter.addItem(shared_with_me);
				
		// Add Header for app
		mAdapter.addHeader(R.string.menu_app);
		//Add photos option
		NsMenuItemModel settings = new NsMenuItemModel(R.string.action_settings, R.drawable.settings);
		mAdapter.addItem(settings);
		//Add music option
		NsMenuItemModel help = new NsMenuItemModel(R.string.menu_help, R.drawable.help);
		mAdapter.addItem(help);
		//Add music option
		NsMenuItemModel rate = new NsMenuItemModel(R.string.menu_rate, R.drawable.rate);
		mAdapter.addItem(rate);
		
		mDrawerList = (ListView) findViewById(R.id.drawer);
		if (mDrawerList != null)
			mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawer.isDrawerOpen(mDrawerList);
		if(!isDrawerLocked){
			menu.findItem(R.id.action_add).setVisible(!drawerOpen);
			menu.findItem(R.id.action_upload).setVisible(!drawerOpen);
			menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
			menu.findItem(R.id.action_settings).setVisible(!drawerOpen);}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * The action bar home/up should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}

	/*
	 * This is used to toggle the title on the action bar
	 */
	private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

		public CustomActionBarDrawerToggle(Activity mActivity,
				DrawerLayout mDrawerLayout) {
			super(mActivity, mDrawerLayout, R.drawable.ic_drawer,
					R.string.ns_menu_open, R.string.ns_menu_close);
		}

		@Override
		public void onDrawerClosed(View view) {
			getActionBar().setTitle(getString(R.string.app_name));
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			temp = (String) getActionBar().getTitle();
			getActionBar().setTitle(getString(R.string.ns_menu_open));
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}
	}

	/*
	 * Action to be performed when clicking item on the drawer
	 */
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Highlight the selected item, update the title, and close the
			// drawer
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			String text = "You clicked ";
			//Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
			switch(position){
			case 0: pager.setCurrentItem(0, true);
					break;
			case 2: pager.setCurrentItem(1, true);
					break;
			case 3: pager.setCurrentItem(2, true);
					break;
			case 4: pager.setCurrentItem(3, true);
					break;
			case 9: Intent i = new Intent(getApplicationContext(),Settings.class);
					startActivity(i);
					break;
			case 7: Toast.makeText(MainActivity.this, "Not implemented yet", Toast.LENGTH_LONG).show();
					break;
			case 8: Toast.makeText(MainActivity.this, "Not implemented yet", Toast.LENGTH_LONG).show();
					break;
			}
			if(!isDrawerLocked)
				mDrawer.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		//Toast.makeText(MainActivity.this, "Page "+arg0, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		switch(itemPosition){
		case 0: //Toast.makeText(MainActivity.this, "Account One", Toast.LENGTH_LONG).show();
				pager.setCurrentItem(0, true);
				break;
		case 1: //Toast.makeText(MainActivity.this, "Account Two", Toast.LENGTH_LONG).show();
				pager.setCurrentItem(1, true);
				break;
		case 2: //Toast.makeText(MainActivity.this, "Account Three", Toast.LENGTH_LONG).show();
				pager.setCurrentItem(2, true);
				break;
		case 3: //Toast.makeText(MainActivity.this, "Account Four", Toast.LENGTH_LONG).show();
				pager.setCurrentItem(3, true);
				break;
		}
		return false;
	}
}
