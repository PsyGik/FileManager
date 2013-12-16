/*
 * Copyright (c) 2013. Humesis, Inc.
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package fileops;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.tdl.viewpagerdrawer.R;
import com.tdl.viewpagerdrawer.R.id;
import com.tdl.viewpagerdrawer.R.layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import settings.Settings;
import utils.FileArrayAdapter;
import utils.Item;

public class FileChooser extends ListFragment {

	int mCurrentPage;
	Button b;
	String[] list_items;
	private File currentDir;
	private FileArrayAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

		/** Getting integer data of the key current_page from the bundle */
		mCurrentPage = data.getInt("current_page", 0);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_main, container,
				false);
		currentDir = new File(Environment.getExternalStorageDirectory()
				.getPath());
		setHasOptionsMenu(true);
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
	}
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 0, "Share");
		menu.add(0, 2, 0, "Delete");
		menu.add(0, 3, 0, "Rename");
		menu.add(0, 4, 0, "Move");
	}

    @Override
	public boolean onContextItemSelected(MenuItem item) {
       	switch(item.getItemId()){
       	case 1: Toast.makeText(getActivity(), "Share Logic Here", 1000).show();
       			break;
       	case 2: Toast.makeText(getActivity(), "Delete Logic Here", 1000).show();
       			break;
       	case 3: Toast.makeText(getActivity(), "Rename Logic Here", 1000).show();
       			break;
       	case 4: Toast.makeText(getActivity(), "Move Logic Here", 1000).show();
       			break;
       	}
	return true;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fill(currentDir);
	}

	/**
	 * Method to fill the current layout with files and folders from the
	 * external directory
	 * 
	 * @params: Path for the SD Card
	 */

	void fill(File f) {
		File[] dirs = f.listFiles();

		/*
		 * if(f.getName().equalsIgnoreCase(Environment.getExternalStorageDirectory
		 * ().getName())) getActivity().getActionBar().setTitle("SD Card"); else
		 * getActivity().getActionBar().setTitle(f.getName());
		 */

		List<Item> dir = new ArrayList<Item>();
		List<Item> fls = new ArrayList<Item>();
		try {
			for (File ff : dirs) {
				Date lastModDate = new Date(ff.lastModified());
				DateFormat formater = DateFormat.getDateTimeInstance();
				String date_modify = formater.format(lastModDate);
				if (ff.isDirectory()) {

					File[] fbuf = ff.listFiles();
					int buf = 0;
					if (fbuf != null) {
						buf = fbuf.length;
					} else
						buf = 0;
					String num_item = String.valueOf(buf);
					if (buf == 0)
						num_item = num_item + " item";
					else
						num_item = num_item + " items";

					// String formated = lastModDate.toString();
					dir.add(new Item(ff.getName(), num_item, date_modify, ff
							.getAbsolutePath(), "directory_icon"));
				} else {

					fls.add(new Item(ff.getName(), ff.length() + " Byte",
							date_modify, ff.getAbsolutePath(), "file_icon"));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase(
				Environment.getExternalStorageDirectory().getName()))
			dir.add(0, new Item("..", "Parent Directory", "", f.getParent(),
					"directory_up"));
		adapter = new FileArrayAdapter(getActivity(), R.layout.file_view, dir);
		this.setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO on list item click
		super.onListItemClick(l, v, position, id);
		Item o = adapter.getItem(position);
		if (o.getImage().equalsIgnoreCase("directory_icon")
				|| o.getImage().equalsIgnoreCase("directory_up")) {
			currentDir = new File(o.getPath());
			fill(currentDir);
		} else {
			onFileClick(o);
		}
	}

	private void onFileClick(Item o) {
		// TODO on file clicked
		Toast.makeText(getActivity(), "File Clicked: " + o.getPath(),
				1000).show();
		openFile(o.getPath());
	}

	private void openFile(String file) {
		// TODO Auto-generated method stub
		File mfile = new File(file);
		MimeTypeMap myMime = MimeTypeMap.getSingleton();

		Intent newIntent = new Intent(android.content.Intent.ACTION_VIEW);

		// Intent newIntent = new Intent(Intent.ACTION_VIEW);
		String mimeType = myMime.getMimeTypeFromExtension(fileExt(
				mfile.toString()).substring(1));
		newIntent.setDataAndType(Uri.fromFile(mfile), mimeType);
		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			getActivity().startActivity(newIntent);
		} catch (android.content.ActivityNotFoundException e) {
			Toast.makeText(getActivity(), "No handler for this type of file.",
					1000).show();
		}
	}

	private String fileExt(String url) {
		if (url.indexOf("?") > -1) {
			url = url.substring(0, url.indexOf("?"));
		}
		if (url.lastIndexOf(".") == -1) {
			return null;
		} else {
			String ext = url.substring(url.lastIndexOf("."));
			if (ext.indexOf("%") > -1) {
				ext = ext.substring(0, ext.indexOf("%"));
			}
			if (ext.indexOf("/") > -1) {
				ext = ext.substring(0, ext.indexOf("/"));
			}
			return ext.toLowerCase();

		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Menu Item Selection Action
		switch (item.getItemId()) {
		case R.id.action_add:
			createFolder();
			break;
		case R.id.action_settings:
			Intent i = new Intent(getActivity().getApplicationContext(), Settings.class);
			startActivity(i);
			break;
		case R.id.action_refresh:
			fill(currentDir);
			break;
		case R.id.action_upload:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
		    intent.setType("*/*"); 
		    intent.addCategory(Intent.CATEGORY_OPENABLE);
		    try {
		        startActivityForResult(
		                Intent.createChooser(intent, "Select a File to Upload"),
		                0);
		    } catch (android.content.ActivityNotFoundException ex) {
		        // Potentially direct the user to the Market with a Dialog
		        Toast.makeText(getActivity(), "Please install a File Manager.", 
		                1000).show();
		    }
		}
		return super.onOptionsItemSelected(item);
	}
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
            if (resultCode == getActivity().RESULT_OK) {
                //Do the upload part here
            	Toast.makeText(getActivity(), "Upload Logic here.", 
		                1000).show();
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

	private void createFolder() {
		// TODO create new folder
		
		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

		View promptView = layoutInflater.inflate(R.layout.dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);
		alertDialogBuilder.setTitle("Enter Folder Name");
		final EditText input = (EditText) promptView
				.findViewById(R.id.userInput);

		// setup a dialog window
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result
						makeFolder(input.getText().toString());
					}

					private void makeFolder(String string) {
						// TODO Auto-generated method stub
						File folder = new File(currentDir + "/" + string);
						boolean success = true;
						if (!folder.exists()) {
							success = folder.mkdir();
						}
						if (success) {
							fill(currentDir);
							Toast.makeText(getActivity(), "Folder Created",
									1000).show();
						} else {
							Toast.makeText(getActivity(),
									"Folder Already Exists", 1000)
									.show();
						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();

	}
}
