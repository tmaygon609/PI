package com.nttdata.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.Genre;
import com.nttdata.persistence.model.Role;
import com.nttdata.persistence.model.Status;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.GenreRepositoryI;
import com.nttdata.persistence.repositories.StatusRepositoryI;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;

import jakarta.transaction.Transactional;

/**
 * Clase para inicializar datos en la aplicación al arrancar.
 */
@Component
@Transactional
public class InitializationData implements CommandLineRunner {

	@Autowired
	private UserRepositoryI userRepository;

	@Autowired
	private BookRepositoryI bookRepository;

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GenreRepositoryI genreRepository;

	@Autowired
	private StatusRepositoryI statusRepository;

	/**
	 * Método que se ejecuta al arrancar la aplicación para inicializar datos.
	 */
	@Override
	public void run(String... args) throws Exception {

		// Usuario 1
		User user1 = new User();
		user1.setName("Tibu");
		user1.setLastName("Mayo Gonzalez");
		user1.setUser("tmaygon");
		user1.setPassword(passwordEncoder.encode("hola22;"));
		user1.setRole(Role.ADMIN);
		user1.setGender("masculino");
		userRepository.save(user1);

		// Usuario 2
		User user2 = new User();
		user2.setName("Macarena");
		user2.setLastName("Campanario Manzano");
		user2.setUser("mcamman");
		user2.setPassword(passwordEncoder.encode("adios22;"));
		user2.setRole(Role.USER);
		user2.setGender("femenino");
		userRepository.save(user2);

		// Usuario 3
		User user3 = new User();
		user3.setName("Gabriela");
		user3.setLastName("Mayo Campanario");
		user3.setUser("gmaycam");
		user3.setPassword(passwordEncoder.encode("adios22;"));
		user3.setRole(Role.USER);
		user3.setGender("femenino");
		userRepository.save(user3);

		// Usuario 4
		User user4 = new User();
		user4.setName("Curro");
		user4.setLastName("Jimenez Diaz");
		user4.setUser("cjimdia");
		user4.setPassword(passwordEncoder.encode("adios22;"));
		user4.setRole(Role.USER);
		user4.setGender("masculino");
		userRepository.save(user4);

		// Libro 1
		Book book1 = new Book();
		book1.setTitle("El regalo");
		book1.setAuthor("Eloy Moreno");
		book1.setGenre("Novela");

		// Carga la imagen desde un recurso del classpath y la convierte en un array de
		// bytes
		ClassPathResource imgFile = new ClassPathResource("static/images/elregalo.jpg");
		byte[] imageBytes = FileCopyUtils.copyToByteArray(imgFile.getInputStream());

		// Guarda el array de bytes en el libro
		book1.setImagenBytes(imageBytes);

		bookRepository.save(book1);

		// Libro 2
		Book book2 = new Book();
		book2.setTitle("Mis raices");
		book2.setAuthor("Rafaela Santos");
		book2.setGenre("Autoayuda");

		// Carga la imagen desde un recurso del classpath y la convierte en un array de
		// bytes
		ClassPathResource imgFile2 = new ClassPathResource("static/images/misraices.jpg");
		byte[] imageBytes2 = FileCopyUtils.copyToByteArray(imgFile2.getInputStream());

		// Guarda el array de bytes en el libro
		book2.setImagenBytes(imageBytes2);

		bookRepository.save(book2);

		// Libro 3
		Book book3 = new Book();
		book3.setTitle("Naruto");
		book3.setAuthor("Masashi Kishimoto");
		book3.setGenre("Manga");

		// Carga la imagen desde un recurso del classpath y la convierte en un array de
		// bytes
		ClassPathResource imgFile3 = new ClassPathResource("static/images/naruto.jpg");
		byte[] imageBytes3 = FileCopyUtils.copyToByteArray(imgFile3.getInputStream());

		// Guarda el array de bytes en el libro
		book3.setImagenBytes(imageBytes3);

		bookRepository.save(book3);

		// Libro 4
		Book book4 = new Book();
		book4.setTitle("Kimetsu no Yaiba");
		book4.setAuthor("Koyoharu Gotouge");
		book4.setGenre("Manga");

		// Carga la imagen desde un recurso del classpath y la convierte en un array de
		// bytes
		ClassPathResource imgFile4 = new ClassPathResource("static/images/kimetsu.jpg");
		byte[] imageBytes4 = FileCopyUtils.copyToByteArray(imgFile4.getInputStream());

		// Guarda el array de bytes en el libro
		book4.setImagenBytes(imageBytes4);

		bookRepository.save(book4);

		// Libro 5
		Book book5 = new Book();
		book5.setTitle("Cien años de soledad");
		book5.setAuthor("Gabriel García Márquez");
		book5.setGenre("Novela");

		ClassPathResource imgFile5 = new ClassPathResource("static/images/100años.jpg");
		byte[] imageBytes5 = FileCopyUtils.copyToByteArray(imgFile5.getInputStream());

		// Guarda el array de bytes en el libro
		book5.setImagenBytes(imageBytes5);

		bookRepository.save(book5);

		// Libro 6
		Book book6 = new Book();
		book6.setTitle("El código Da Vinci");
		book6.setAuthor("Dan Brown");
		book6.setGenre("Thriller");

		ClassPathResource imgFile6 = new ClassPathResource("static/images/codigodavinci.jpg");
		byte[] imageBytes6 = FileCopyUtils.copyToByteArray(imgFile6.getInputStream());

		// Guarda el array de bytes en el libro
		book6.setImagenBytes(imageBytes6);
		bookRepository.save(book6);

		// Libro 7
		Book book7 = new Book();
		book7.setTitle("Neuromante");
		book7.setAuthor("William Gibson");
		book7.setGenre("Ciencia ficción");

		ClassPathResource imgFile7 = new ClassPathResource("static/images/neuromante.jpg");
		byte[] imageBytes7 = FileCopyUtils.copyToByteArray(imgFile7.getInputStream());

		book7.setImagenBytes(imageBytes7);

		bookRepository.save(book7);

		// Libro 8
		Book book8 = new Book();
		book8.setTitle("Orgullo y prejuicio");
		book8.setAuthor("Jane Austen");
		book8.setGenre("Romance");

		ClassPathResource imgFile8 = new ClassPathResource("static/images/orgullo.jpg");
		byte[] imageBytes8 = FileCopyUtils.copyToByteArray(imgFile8.getInputStream());

		book8.setImagenBytes(imageBytes8);
		bookRepository.save(book8);

		// Libro 9
		Book book9 = new Book();
		book9.setTitle("El misterio de la cripta embrujada");
		book9.setAuthor("Eduardo Mendoza");
		book9.setGenre("Misterio");

		ClassPathResource imgFile9 = new ClassPathResource("static/images/elmisterio.jpg");
		byte[] imageBytes9 = FileCopyUtils.copyToByteArray(imgFile9.getInputStream());

		book9.setImagenBytes(imageBytes9);
		bookRepository.save(book9);

		// Libro 10
		Book book10 = new Book();
		book10.setTitle("El señor de los anillos");
		book10.setAuthor("J.R.R. Tolkien");
		book10.setGenre("Fantasía");

		ClassPathResource imgFile10 = new ClassPathResource("static/images/señordelosanillos.jpg");
		byte[] imageBytes10 = FileCopyUtils.copyToByteArray(imgFile10.getInputStream());

		book10.setImagenBytes(imageBytes10);
		bookRepository.save(book10);

		// Crear relaciones y establecer atributos adicionales
		UserBook userBook1 = new UserBook();
		userBook1.setUser(user3);
		userBook1.setBook(book1);
		userBook1.setStatus("Leído");
		userBook1.setRate("5");
		userBook1.setComment("Me ha encantado");
		userBookRepository.save(userBook1);

		UserBook userBook2 = new UserBook();
		userBook2.setUser(user3);
		userBook2.setBook(book2);
		userBook2.setStatus("En curso");
		userBook2.setRate("3");
		userBook2.setComment("Todavia voy por la mitad.");
		userBookRepository.save(userBook2);

		UserBook userBook5 = new UserBook();
		userBook5.setUser(user3);
		userBook5.setBook(book5);
		userBook5.setStatus("Leído");
		userBook5.setRate("4");
		userBook5.setComment("Muy interesante, aunque un poco confuso al principio");
		userBookRepository.save(userBook5);

		UserBook userBook6 = new UserBook();
		userBook6.setUser(user3);
		userBook6.setBook(book6);
		userBook6.setStatus("En curso");
		userBook6.setRate("4");
		userBook6.setComment("Enganchado desde el primer capítulo");
		userBookRepository.save(userBook6);

		UserBook userBook7 = new UserBook();
		userBook7.setUser(user3);
		userBook7.setBook(book7);
		userBook7.setStatus("Sin empezar");
		userBook7.setRate("3");
		userBook7.setComment("Lo leeré después de terminar otro libro");
		userBookRepository.save(userBook7);

		// Usuario 2

		UserBook userBook3 = new UserBook();
		userBook3.setUser(user2);
		userBook3.setBook(book3);
		userBook3.setStatus("Sin empezar");
		userBook3.setRate("1");
		userBook3.setComment("Cuando lo empieze lo calificaré.");
		userBookRepository.save(userBook3);

		UserBook userBook4 = new UserBook();
		userBook4.setUser(user2);
		userBook4.setBook(book4);
		userBook4.setStatus("Leído");
		userBook4.setRate("5");
		userBook4.setComment("Me lo leí en 2 horas.");
		userBookRepository.save(userBook4);

		UserBook userBook11 = new UserBook();
		userBook11.setUser(user2);
		userBook11.setBook(book8);
		userBook11.setStatus("Leído");
		userBook11.setRate("4");
		userBook11.setComment("Interesante, pero algo perturbador");
		userBookRepository.save(userBook11);

		UserBook userBook12 = new UserBook();
		userBook12.setUser(user2);
		userBook12.setBook(book9);
		userBook12.setStatus("En curso");
		userBook12.setRate("3");
		userBook12.setComment("Esperaba más acción");
		userBookRepository.save(userBook12);

		UserBook userBook13 = new UserBook();
		userBook13.setUser(user2);
		userBook13.setBook(book10);
		userBook13.setStatus("Sin empezar");
		userBook13.setRate("5");
		userBook13.setComment("Un clásico que no puedo esperar a leer");
		userBookRepository.save(userBook13);

		// Crear generos literarios.
		List<String> namesGenres = Arrays.asList("Novela", "Thriller", "Ciencia ficción", "Romance", "Misterio",
				"Fantasía", "Terror", "Policíaca", "Aventura", "Histórica", "Biografía", "Drama", "Cuento", "Fábula",
				"Mitología", "Manga", "Cómic / Novela gráfica", "Literatura infantil", "Literatura juvenil", "Humor",
				"Viajes", "Filosofía", "Teatro", "Autoayuda");

		for (String nameGenre : namesGenres) {
			Genre genre = new Genre();
			genre.setGenreName(nameGenre);
			genreRepository.save(genre);
		}

		// Crear estados libros.

		List<String> namesStatus = Arrays.asList("Sin empezar", "En curso", "Leido");

		for (String nameStatus : namesStatus) {
			Status status = new Status();
			status.setStatusName(nameStatus);
			statusRepository.save(status);
		}
	}

}
