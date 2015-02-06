package swipe.android.hipaapix.core;


import android.support.v4.app.Fragment;
public class FragmentInfo{
	Fragment fragment;
	public Fragment getFragment() {
		return fragment;
	}
	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
	int selected_icon, unselected_icon;
	
	public FragmentInfo(Fragment f, int selected_icon, int unselected_icon) {

		this.fragment = f;
		this.selected_icon = selected_icon;
		this.unselected_icon = unselected_icon;
	}
	public int getSelected_icon() {
		return selected_icon;
	}
	public void setSelected_icon(int selected_icon) {
		this.selected_icon = selected_icon;
	}
	public int getUnselected_icon() {
		return unselected_icon;
	}
	public void setUnselected_icon(int unselected_icon) {
		this.unselected_icon = unselected_icon;
	}
}