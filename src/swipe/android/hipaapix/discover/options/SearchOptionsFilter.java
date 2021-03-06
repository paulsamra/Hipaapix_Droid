package swipe.android.hipaapix.discover.options;

public class SearchOptionsFilter{
	boolean isSelected = false;
	int unselectedIcon, selectedIcon;
	String searchTerm;
	public SearchOptionsFilter(boolean isSelected, int unselectedIcon, int selectedIcon, String searchTerm){
		this.isSelected = isSelected;
		this.unselectedIcon = unselectedIcon;
		this.selectedIcon = selectedIcon;
		this.searchTerm = searchTerm;
	}
	public SearchOptionsFilter(boolean isSelected, int unselectedIcon, String searchTerm){
		this.isSelected = isSelected;
		this.unselectedIcon = unselectedIcon;
		this.selectedIcon = -1;
		this.searchTerm = searchTerm;
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public void setUnselectedIcon(int unselectedIcon) {
		this.unselectedIcon = unselectedIcon;
	}
	public void setSelectedIcon(int selectedIcon) {
		this.selectedIcon = selectedIcon;
	}
	public boolean isSelected(){
		return isSelected;
	}
	public int getUnselectedIcon(){
		return unselectedIcon;
	}
	public int getSelectedIcon(){
		return selectedIcon;
	}
	public void setSelected(boolean value){
		isSelected = value;
	}
}