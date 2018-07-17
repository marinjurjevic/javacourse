package hr.fer.zemris.java.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents instance of a single blog entry.
 * @author Marin Jurjevic
 *
 */

@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * generated ID
	 */
	@Id @GeneratedValue
	private Long id;
	
	/**
	 * list of comments posted on this entry
	 */
	@OneToMany (mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * date when entry was created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date createdAt;
	
	/**
	 * date of entry last modification
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	private Date lastModifiedAt;
	
	/**
	 * blog entry title
	 */
	@Column(nullable=false, length=60)
	private String title;
	
	/**
	 * blog entry content
	 */
	@Column(nullable=false, length=4*1024)
	private String text;
	
	/**
	 * creator of this blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser creator;
	
	/**
	 * Returns blog entry id.
	 * @return entry id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets blog entry id.
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns list of all comments posted on this blog entry.
	 * @return list of comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets list of comments posted on this blog entry.
	 * @param comments new list of comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Fetches date when entry was created.
	 * @return creation time
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets date when entry was created.
	 * @param createdAt new creation time
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Fetches date of entry last modification.
	 * @return date of modification
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets new date of last entry modification.
	 * @param lastModifiedAt date of last modification
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Fetches blog entry title.
	 * @return blog entry title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets blog entry title.
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Fetches content of blog entry.
	 * @return blog entry content
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets blog entry content text.
	 * @param text new entry text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Fetches blog entry creator.
	 * @return blog user who created this entry
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Changes creator of this blog entry.
	 * @param creator new creator of this blog entry
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}