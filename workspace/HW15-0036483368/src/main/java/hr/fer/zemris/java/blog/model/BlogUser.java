package hr.fer.zemris.java.blog.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Entity that represents a single blog user on blog website.
 * 
 * @author Marin Jurjevic
 *
 */
@Entity
@Table(name = "blog_users") 
public class BlogUser {

	/**
	 * generated ID
	 */
	@Id @GeneratedValue
	private Long id;
	
	/**
	 * blog user's first name
	 */
	@Column(length=30, nullable=false)
	private String firstName;
	
	/**
	 * blog user's last name
	 */
	@Column(length=30, nullable=false)
	private String lastName;
	
	/**
	 * blog user's nickname
	 */
	@Column(length=100, nullable=false, unique=true)
	private String nick;
	
	/**
	 * blog user's email address
	 */
	@Column(length=100, nullable=false)
	private String email;
	
	/**
	 * blog user's password hash
	 */
	@Column(length=100, nullable=false)
	private String passwordHash;
	
	/**
	 * list of all entries posted by this blog user
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	private List<BlogEntry> blogEntries;

	/**
	 * Returns blog user id.
	 * @return entry id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets blog user id.
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets first name of this blog user.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets new first name of this blog user.
	 * @param firstName new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets last name of this blog user.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets new last name of this blog user.
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets nickname name of this blog user.
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets new nickname of this blog user.
	 * @param nick new nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets email address of this blog user.
	 * @return email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets new email address of this blog user.
	 * @param email new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password hash.
	 * @return pass hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets new password hash for this blog user.
	 * @param passwordHash new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Fetches the list of all blog entries this blog user has created.
	 * @return list of blog entries
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets new list of blog entries for this user.
	 * @param blogEntries new list of blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	
}
