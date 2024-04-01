package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase usuario.
 * 
 * Representa tabla T_USER.
 */
@Entity
@Table(name = "T_USER")
@Getter
@Setter
public class User implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_USER_ID")
	private Long id;

	/** Nombre */
	@Column(name = "C_NAME")
	private String name;

	/** Apellidos */
	@Column(name = "C_LAST_NAME")
	private String lastName;

	/** Usuario */
	@Column(name = "C_USER")
	private String user;

	/** Password */
	@Column(name = "C_PASSWORD")
	private String password;

//	@ManyToMany
//	@JoinTable(name = "T_USER_BOOK", joinColumns = @JoinColumn(name = "C_USER_ID"), inverseJoinColumns = @JoinColumn(name = "C_BOOK_ID"))
//	private Set<Book> books;

	@OneToMany(mappedBy = "user")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<UserBook> userBooks;

}
