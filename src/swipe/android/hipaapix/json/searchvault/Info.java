package swipe.android.hipaapix.json.searchvault;

public class Info{
	int current_page, num_pages, per_page, total_result_count;

	public int getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}

	public int getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}

	public int getPer_page() {
		return per_page;
	}

	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}

	public int getTotal_result_count() {
		return total_result_count;
	}

	public void setTotal_result_count(int total_result_count) {
		this.total_result_count = total_result_count;
	}
}