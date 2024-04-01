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

}