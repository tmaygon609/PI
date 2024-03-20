package com.nttdata.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_USER_BOOK")
@Getter
@Setter
public class UserBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "C_USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "C_BOOK_ID")
	private Book book;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "RATE")
	private String rate;

	@Column(name = "COMMENT")
	private String comment;

//	/**
//	 * @return the id
//	 */
//	public Long getId() {
//		return id;
//	}
//
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	/**
//	 * @return the user
//	 */
//	public User getUser() {
//		return user;
//	}
//
//	/**
//	 * @param user the user to set
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	/**
//	 * @return the book
//	 */
//	public Book getBook() {
//		return book;
//	}
//
//	/**
//	 * @param book the book to set
//	 */
//	public void setBook(Book book) {
//		this.book = book;
//	}
//
//	/**
//	 * @return the status
//	 */
//	public String getStatus() {
//		return status;
//	}
//
//	/**
//	 * @param status the status to set
//	 */
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	/**
//	 * @return the rate
//	 */
//	public String getRate() {
//		return rate;
//	}
//
//	/**
//	 * @param rate the rate to set
//	 */
//	public void setRate(String rate) {
//		this.rate = rate;
//	}
//
//	/**
//	 * @return the comment
//	 */
//	public String getComment() {
//		return comment;
//	}
//
//	/**
//	 * @param comment the comment to set
//	 */
//	public void setComment(String comment) {
//		this.comment = comment;
//	}

}