package swipe.android.hipaapix.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import swipe.android.hipaapix.CustomActionBarActivity;
import swipe.android.hipaapix.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.edbert.library.containers.PositionalLinkedMap;

public abstract class HipaaPixTabsActivityContainer extends
		CustomActionBarActivity implements TabListener {
	String id = "0";
	public static final String TAB_A = "tab_a_identifier";
	public static final String TAB_B = "tab_b_identifier";

	protected PositionalLinkedMap<String, FragmentInfo> mapFragList = new PositionalLinkedMap<String, FragmentInfo>();

	/* Your Tab host */
	private TabHost mTabHost;

	/* A HashMap of stacks, where we use tab identifier as keys.. */
	private HashMap<String, Stack<FragmentInfo>> mStacks;

	/* Save current tabs identifier in this.. */
	private String mCurrentTab;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_main_tab_fragment_layout);

		/*
		 * Navigation stacks for each tab gets created.. tab identifier is used
		 * as key to get respective stack for each tab
		 */

		mStacks = new HashMap<String, Stack<FragmentInfo>>();
		addDefaultFragments();

		mStacks.put(TAB_A, new Stack<FragmentInfo>());
		mStacks.put(TAB_B, new Stack<FragmentInfo>());

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();

		initializeTabs();
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(id));
		return view;
	}

	public void initializeTabs() {
		/* Setup your tab icons and content views.. Nothing special in this.. */
		TabHost.TabSpec spec = mTabHost.newTabSpec(TAB_A);
		mTabHost.setCurrentTab(-3);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_a_state_btn));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(TAB_B);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_b_state_btn));
		mTabHost.addTab(spec);
	}

	/* Comes here when user switch tab, or we do programmatically */
	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			/* Set current tab.. */
			mCurrentTab = tabId;

			if (mStacks.get(tabId).size() == 0) {
				/*
				 * First time this tab is selected. So add first fragment of
				 * that tab. Dont need animation, so that argument is false. We
				 * are adding a new fragment which is not present in stack. So
				 * add to stack is true.
				 */
				if (tabId.equals(TAB_A)) {

					pushFragments(tabId, mapFragList.getValue(0).getFragment(),
							false, true, null);
				} else if (tabId.equals(TAB_B)) {
					pushFragments(tabId, mapFragList.getValue(1).getFragment(),
							false, true,null);
				}
			} else {
				/*
				 * We are switching tabs, and target tab is already has atleast
				 * one fragment. No need of animation, no need of stack pushing.
				 * Just show the target fragment
				 */
				pushFragments(tabId, mStacks.get(tabId).lastElement().getFragment(), false,
						false,  mStacks.get(tabId).lastElement().getB());
			}
		}
	};

	/*
	 * Might be useful if we want to switch tab programmatically, from inside
	 * any of the fragment.
	 */
	public void setCurrentTab(int val) {
		mTabHost.setCurrentTab(val);
	}

	/*
	 * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
	 * show, in tab identified by tag shouldAnimate -> should animate
	 * transaction. false when we switch tabs, or adding first fragment to a tab
	 * true when when we are pushing more fragment into navigation stack.
	 * shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)).
	 * false when we are switching tabs (except for the first time) true in all
	 * other cases.
	 */
	public void pushFragments(String tag, Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd, Bundle b) {
		if (shouldAdd)
			mStacks.get(tag).push(new FragmentInfo(fragment, b));
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate)
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		
		fragment.setArguments(b);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	public void popFragments() {
		/*
		 * Select the second last fragment in current tab's stack.. which will
		 * be shown after the fragment transaction given below
		 */
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2).getFragment();
		fragment.setArguments(mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2).getB());

		/* pop current fragment from stack.. */
		mStacks.get(mCurrentTab).pop();

		/*
		 * We have the target fragment in hand.. Just show it.. Show a standard
		 * navigation animation
		 */

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
		if (mStacks.get(mCurrentTab).size() == 1) {
			setupActionBar();
		}
	}

	@Override
	public void onBackPressed() {
		if(!(mStacks.get(mCurrentTab).lastElement().getFragment() instanceof BaseFragment)){
			return;
		}
		if (((BaseFragment) mStacks.get(mCurrentTab).lastElement().getFragment())
				.onBackPressed() == false) {
			/*
			 * top fragment in current tab doesn't handles back press, we can do
			 * our thing, which is
			 * 
			 * if current tab has only one fragment in stack, ie first fragment
			 * is showing for this tab. finish the activity else pop to previous
			 * fragment in stack for the same tab
			 */

			if (mStacks.get(mCurrentTab).size() == 1) {

				super.onBackPressed(); // or call finish..
			} else {
				popFragments();
			}
		} else {
			// do nothing.. fragment already handled back button press.
		}
	}

	/*
	 * Imagine if you wanted to get an image selected using ImagePicker intent
	 * to the fragment. Ofcourse I could have created a public function in that
	 * fragment, and called it from the activity. But couldn't resist myself.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mStacks.get(mCurrentTab).size() == 0) {
			return;
		}

		/* Now current fragment on screen gets onActivityResult callback.. */
		mStacks.get(mCurrentTab).lastElement().getFragment()
				.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		FragmentManager manager = getSupportFragmentManager();
		fragmentTransaction = manager.beginTransaction();

		// need current fragment?
		Fragment fragment = mapFragList.getValue(tab.getPosition())
				.getFragment();
fragment.setArguments(mapFragList.getValue(tab.getPosition()).getB());
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
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	protected String createTag(Fragment fragment) {
		return fragment.getClass().getName();
	}

	protected abstract void addDefaultFragments();

}