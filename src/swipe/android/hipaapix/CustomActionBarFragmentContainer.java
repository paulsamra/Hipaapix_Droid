package swipe.android.hipaapix;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import swipe.android.hipaapix.core.FragmentInfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.edbert.library.R;
import com.edbert.library.containers.PositionalLinkedMap;

public abstract class CustomActionBarFragmentContainer extends
		CustomActionBarActivity implements TabListener {
	String id = "0";
	protected PositionalLinkedMap<String, FragmentInfo> mapFragList = new PositionalLinkedMap<String, FragmentInfo>();
	protected abstract void addDefaultFragments();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addDefaultFragments();
		ActionBar bar = this.getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Iterator it = mapFragList.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, FragmentInfo> pairs = (Map.Entry<String, FragmentInfo> ) it.next();

			Tab tab = bar.newTab();
		//	tab.setText((String) pairs.getKey());

			tab.setTabListener(this);
			int unselected = pairs.getValue().getUnselected_icon();
			tab.setIcon(unselected);
		//	tab.setTag((String) pairs.getKey());
			bar.addTab(tab);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		FragmentManager manager = getSupportFragmentManager();
		fragmentTransaction = manager.beginTransaction();

		// need current fragment?
		Fragment fragment = mapFragList.getValue(tab.getPosition()).getFragment();

		// fragment = navDrawerSelected.getFragment();
		// replace the fragment once found
		if (fragment != null) {
			// save instances and pop stacks to save previous instances
			FragmentManager fragmentManager = getSupportFragmentManager();

			//
			String backStateName = createTag(fragment);
			boolean fragmentPopped = fragmentManager.popBackStackImmediate(
					backStateName, 0);

			if (!fragmentPopped
					&& fragmentManager.findFragmentByTag(backStateName) == null) {

				FragmentTransaction ft = fragmentManager.beginTransaction();

				ft.replace(android.R.id.content, fragment, backStateName);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

				ft.addToBackStack(backStateName);
				ft.commit();

			}
		}

		fragmentTransaction.replace(android.R.id.content, fragment);
		fragmentTransaction.commit();
		setSelectedUnselected(tab);
		// now we change tab
	}

	public void setSelectedUnselected(Tab tab) {
		int pos = tab.getPosition();
	
		//itterate through the 
		ActionBar bar =
				this.getSupportActionBar();
		bar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		for(int i = 0 ; i <	bar.getTabCount();i++){
			if(i != pos){
				int unselected = mapFragList.getValue(i).getUnselected_icon();
				bar.getTabAt(i).setIcon(unselected);
		
			}else{
				int selected = mapFragList.getValue(i).getSelected_icon();
				bar.getTabAt(i).setIcon(selected);
			}
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	protected String createTag(Fragment fragment) {
		return fragment.getClass().getName();
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

}