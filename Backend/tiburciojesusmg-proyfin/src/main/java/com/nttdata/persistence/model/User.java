package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase usuario.
 * 
 * Representa tabla T_USER.
 */
@Entity
@Table(name = "T_USER")
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

	/** Relación con la entidad Registro de Lectura */
	@OneToMany(mappedBy = "user")
	private Set<ReadRegister> readRegisters;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the readRegisters
	 */
	public Set<ReadRegister> getReadRegisters() {
		return readRegisters;
	}

	/**
	 * @param readRegisters the readRegisters to set
	 */
	public void setReadRegisters(Set<ReadRegister> readRegisters) {
		this.readRegisters = readRegisters;
	}

}
