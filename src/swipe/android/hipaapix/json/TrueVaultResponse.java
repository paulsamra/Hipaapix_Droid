package swipe.android.hipaapix.json;


import com.edbert.library.network.sync.JsonResponseInterface;

public abstract class TrueVaultResponse implements JsonResponseInterface{
	String result, transaction_id;
	Error error;
	
	@Override
	public boolean isValid() {
		return (result != null && result.equals("success"));
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}