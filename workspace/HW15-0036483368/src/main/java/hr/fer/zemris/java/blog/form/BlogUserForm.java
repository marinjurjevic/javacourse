package hr.fer.zemris.java.blog.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * BlogUserForm encapsulates all attributes for easy form handling in JSP.
 * @author Marin Jurjevic
 * 
 */
public class BlogUserForm {

	/**
	 * user first name
	 */
	private String firstName;

	/**
	 * user last name
	 */
	private String lastName;

	/**
	 * user nickname
	 */
	private String nick;

	/**
	 * user email address
	 */
	private String email;

	/**
	 * user password
	 */
	private String password;

	/**
	 * Map of found errors in blog entry, key is attribute which is error associated with, value is 
	 * error message.
	 */
	Map<String, String> errors = new HashMap<String, String>();

	/**
	 * Returns user's first name.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name.
	 * @param firstName new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns user's last name.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user's last name.
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets user nickname. 
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets user's nickname.
	 * @param nick new nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets user email address. 
	 * @return email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's email address
	 * @param email new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets user's password.
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets user's password.
	 * @param password new user password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns all errors which maybe occured.
	 * @return map of errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets errors in this form
	 * @param errors errors
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/**
	 * Fetches error for given name.
	 * 
	 * @param name form attribute name
	 * @return error content for given name
	 */
	public String fetchError(String name) {
		return errors.get(name);
	}

	/**
	 * Checks if there is any errors in this form.
	 * 
	 * @return <code>true</code> if errors do exists, false otherwise.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Checks if there is mapped error for given attribute.
	 * 
	 * @param name attribute name
	 * @return true if error exists, false otherwise
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Method which fetches data from servlet request.
	 * @param req servlet request
	 */
	public void fillFromHttpRequesta(HttpServletRequest req) {
		this.firstName = req.getParameter("firstName") == null? "":req.getParameter("firstName").trim();
		this.lastName = req.getParameter("lastName") == null? "":req.getParameter("lastName").trim();
		this.email = req.getParameter("email") == null? "":req.getParameter("email").trim();
		this.nick = req.getParameter("nick") == null? "":req.getParameter("nick").trim();
		this.password = req.getParameter("password") == null? "":req.getParameter("password").trim();
	}
	
	/**
	 * Fills blog user object with information from this form.
	 * @param bu user object to be filled
	 */
	public void fillBlogUser(BlogUser bu) {
		bu.setFirstName(this.firstName);
		bu.setLastName(this.lastName);
		bu.setEmail(this.email);
		bu.setNick(this.nick);
		bu.setPasswordHash(this.password);
	}

	/**
	 * Checks are all attributes properly set. If some attribute is not regular, error will be set in map of errors under
	 * attribute name key.
	 * 
	 * @param nicknameUsed flag that marks if nickname has already been used.
	 */
	public void validate(boolean nicknameUsed) {
		if(firstName.isEmpty()){
			errors.put("firstName", "You must set first name");
		}
		if(firstName.isEmpty()){
			errors.put("lastName", "You must set last name");
		}
		if(password.isEmpty()){
			errors.put("password", "You must set password");
		}else if(password.length()<4){
			errors.put("password", "Password must contain at least 4 characters");
		}
		if (!checkEmail(this.email)) {
			errors.put("email", "E-mail address is not valid");
		}
		if (nick.isEmpty()) {
			errors.put("nick", "You must provide nickname");
		} else if (nicknameUsed) {
			errors.put("nick", "Nickname has already been used");
		}
	}

	/**
	 * Fills this blog user form from blog user object
	 * @param bu blog user containing information
	 */
	public void fillFromBlogUser(BlogUser bu) {
		this.firstName = bu.getFirstName();
		this.lastName = bu.getLastName();
		this.email = bu.getEmail();
		this.nick = bu.getNick();
		this.password = bu.getPasswordHash();
	}

	/**
	 * Method which checks email string against regular expression.
	 * @param email mail to be checked
	 * @return <code>true</code> if email is regular, false otherwise
	 */
	private boolean checkEmail(String email) {
		return email
				.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}
}