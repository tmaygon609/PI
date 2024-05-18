package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable, UserDetails {

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

	@Column(name = "C_GENDER")
	private String gender;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<UserBook> userBooks;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {

		return user;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
