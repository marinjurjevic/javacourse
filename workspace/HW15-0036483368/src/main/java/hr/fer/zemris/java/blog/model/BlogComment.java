package hr.fer.zemris.java.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a comment posted on one blog entry.
 * @author Marin Jurjevic
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * generated ID
	 */
	@Id @GeneratedValue
	private Long id;
	
	/**
	 * comment blog parent
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	private BlogEntry blogEntry;
	
	/**
	 * user mail which posted the comment
	 */
	@Column(length=100, nullable=false)
	private String usersEMail;
	
	/**
	 * comment message
	 */
	@Column(length=4096, nullable=false)
	private String message;
	
	/**
	 * time when comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date postedOn;
	
	/**
	 * Returns blog comment id.
	 * @return blog comment id.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets blog comment id.
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns instance of <tt>BlogEntry</tt> on which this comment was posted.
	 * @return blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets new parent of this comment.
	 * @param blogEntry instance of blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Email of user who posted comment on this blog.
	 * @return user email
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets user email for this comment.
	 * @param usersEMail new email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns comment message.
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets new comment message.
	 * @param message comment message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns date when comment was posted.
	 * @return date
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets comment post date.
	 * @param postedOn new date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}