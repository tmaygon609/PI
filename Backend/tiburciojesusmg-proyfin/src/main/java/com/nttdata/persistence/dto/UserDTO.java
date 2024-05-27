package com.nttdata.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object para la entidad User.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

	/** Nombre del usuario */
	private String name;

	/** Apellidos del usuario */
	private String lastName;

}
