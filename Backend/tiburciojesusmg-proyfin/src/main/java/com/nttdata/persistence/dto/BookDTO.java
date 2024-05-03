package com.nttdata.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {

	private Long id;
	private String title;
	private String author;
	private String genre;
	private String base64Image;

}
