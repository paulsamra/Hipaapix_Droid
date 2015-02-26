package swipe.android.hipaapix.json;

public class UserData{
	//Login
	String access_key, access_token, account_id, id, status, user_id, username;

	//Else
	String attributes, created_at, created_by, created_from, external_key_hash;
	String[] group_ids;
	String modified_at, modified_by,modified_from;
	
	  
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_from() {
		return created_from;
	}

	public void setCreated_from(String created_from) {
		this.created_from = created_from;
	}

	public String getExternal_key_hash() {
		return external_key_hash;
	}

	public void setExternal_key_hash(String external_key_hash) {
		this.external_key_hash = external_key_hash;
	}

	public String[] getGroup_ids() {
		return group_ids;
	}

	public void setGroup_ids(String[] group_ids) {
		this.group_ids = group_ids;
	}

	public String getModified_at() {
		return modified_at;
	}

	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public String getModified_from() {
		return modified_from;
	}

	public void setModified_from(String modified_from) {
		this.modified_from = modified_from;
	}

	public String getAccess_key() {
		return access_key;
	}

	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}