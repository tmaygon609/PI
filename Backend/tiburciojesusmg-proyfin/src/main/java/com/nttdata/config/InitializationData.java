package com.nttdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class InitializationData implements CommandLineRunner {

	@Autowired
	private UserRepositoryI userRepository;

	@Autowired
	private BookRepositoryI bookRepository;

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Override
	public void run(String... args) throws Exception {

		// Usuario 1
		User user1 = new User();
		user1.setName("Tibu");
		user1.setLastName("Mayo Gonzalez");
		user1.setUser("tmaygon");
		user1.setPassword("hola22;");
		userRepository.save(user1);

		// Usuario 1
		User user2 = new User();
		user2.setName("Macarena");
		user2.setLastName("Campanario Manzano");
		user2.setUser("mcamman");
		user2.setPassword("adios22;");
		userRepository.save(user2);

		// Libro 1
		Book book1 = new Book();
		book1.setTitle("El regalo");
		book1.setAuthor("Eloy Moreno");
		book1.setGenre("Novela");
		bookRepository.save(book1);

		// Libro 2
		Book book2 = new Book();
		book2.setTitle("Mis raices");
		book2.setAuthor("Rafaela Santos");
		book2.setGenre("Basado en hechos reales");
		bookRepository.save(book2);

		// Libro 3
		Book book3 = new Book();
		book3.setTitle("Naruto");
		book3.setAuthor("Masashi Kishimoto");
		book3.setGenre("Manga");
		bookRepository.save(book3);

		// Libro 4
		Book book4 = new Book();
		book4.setTitle("Kimetsu no Yaiba");
		book4.setAuthor("Koyoharu Gotouge");
		book4.setGenre("Manga");
		bookRepository.save(book4);

		// Crear relaciones y establecer atributos adicionales
		UserBook userBook1 = new UserBook();
		userBook1.setUser(user1);
		userBook1.setBook(book1);
		userBook1.setStatus("Leído");
		userBook1.setRate("5");
		userBook1.setComment("Me ha encantado");
		userBookRepository.save(userBook1);

		UserBook userBook2 = new UserBook();
		userBook2.setUser(user1);
		userBook2.setBook(book2);
		userBook2.setStatus("En curso");
		userBook2.setRate("3");
		userBook2.setComment("Todavia voy por la mitad.");
		userBookRepository.save(userBook2);

		UserBook userBook3 = new UserBook();
		userBook3.setUser(user2);
		userBook3.setBook(book3);
		userBook3.setStatus("Sin empezar");
		userBookRepository.save(userBook3);

		UserBook userBook4 = new UserBook();
		userBook4.setUser(user2);
		userBook4.setBook(book4);
		userBook4.setStatus("Leído");
		userBook4.setRate("5");
		userBook4.setComment("Me lo leí en 2 horas.");
		userBookRepository.save(userBook4);

	}

}
