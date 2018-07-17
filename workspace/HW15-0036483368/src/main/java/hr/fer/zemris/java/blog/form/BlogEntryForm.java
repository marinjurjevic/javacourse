package hr.fer.zemris.java.blog.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.blog.model.BlogEntry;

/**
 * BlogEntryForm encapsulates all attributes for easy form handling in JSP.
 * @author Marin Jurjevic
 * 
 */
public class BlogEntryForm {

	/**
	 * entry id
	 */
	private String id;
	
	/**
	 * blog entry title
	 */
	private String title;
	
	/**
	 * blog entry content text
	 */
	private String text;

	/**
	 * Map of found errors in blog entry, key is attribute which is error associated with, value is 
	 * error message.
	 */
	Map<String, String> errors = new HashMap<String, String>();

	/**
	 * Returns form id.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets form id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets map of errors.
	 * @return errors found in the form
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
	 * Gets blog entry form title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of this form.
	 * @param title form title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets form text for this blog entry.
	 * @return form text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets form text for this entry.
	 * @param text form text
	 */
	public void setText(String text) {
		this.text = text;
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
	public void fillFromHttpRequest(HttpServletRequest req) {
		this.title = req.getParameter("title") == null? "":req.getParameter("title").trim();
		this.text = req.getParameter("text") == null? "":req.getParameter("text").trim();
		this.id = req.getParameter("id") == null? "":req.getParameter("id").trim();
	}

	/**
	 * Fills blog entry with information from this form.
	 * @param entry entry to be filled
	 */
	public void fillBlogEntry(BlogEntry entry) {
		entry.setTitle(this.title);
		entry.setText(this.text);
	}

	/**
	 * Checks are all attributes properly set. If some attribute is not regular, error will be set in map of errors under
	 * attribute name key.
	 */
	public void validate() {
		if (this.title.isEmpty()) {
			errors.put("title", "Naslov mora biti zadan");
		}
	}

	/**
	 * Method for filling form from given blog entry object.
	 * @param entry blog entry 
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		this.text = entry.getText();
		this.title = entry.getTitle();
		if (entry.getId() != null) {
			this.id = String.valueOf(entry.getId());
		} else {
			this.id = "";
		}
	}

}