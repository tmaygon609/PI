package com.nttdata.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.Genre;
import com.nttdata.persistence.model.Status;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.GenreRepositoryI;
import com.nttdata.persistence.repositories.StatusRepositoryI;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private GenreRepositoryI genreRepository;

	@Autowired
	private StatusRepositoryI statusRepository;

	@Override
	public void run(String... args) throws Exception {

		// Usuario 1
		User user1 = new User();
		user1.setName("Tibu");
		user1.setLastName("Mayo Gonzalez");
		user1.setUser("tmaygon");
		user1.setPassword(passwordEncoder.encode("hola22;"));
		userRepository.save(user1);

		// Usuario 1
		User user2 = new User();
		user2.setName("Macarena");
		user2.setLastName("Campanario Manzano");
		user2.setUser("mcamman");
		user2.setPassword(passwordEncoder.encode("adios22;"));
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
		book2.setGenre("Crónica");
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

		// Libro 5
		Book book5 = new Book();
		book5.setTitle("Cien años de soledad");
		book5.setAuthor("Gabriel García Márquez");
		book5.setGenre("Novela");
		bookRepository.save(book5);

		// Libro 6
		Book book6 = new Book();
		book6.setTitle("El código Da Vinci");
		book6.setAuthor("Dan Brown");
		book6.setGenre("Thriller");
		bookRepository.save(book6);

		// Libro 7
		Book book7 = new Book();
		book7.setTitle("Neuromante");
		book7.setAuthor("William Gibson");
		book7.setGenre("Ciencia ficción");
		bookRepository.save(book7);

		// Libro 8
		Book book8 = new Book();
		book8.setTitle("Orgullo y prejuicio");
		book8.setAuthor("Jane Austen");
		book8.setGenre("Romance");
		bookRepository.save(book8);

		// Libro 9
		Book book9 = new Book();
		book9.setTitle("El misterio de la cripta embrujada");
		book9.setAuthor("Eduardo Mendoza");
		book9.setGenre("Misterio");
		bookRepository.save(book9);

		// Libro 10
		Book book10 = new Book();
		book10.setTitle("El señor de los anillos");
		book10.setAuthor("J.R.R. Tolkien");
		book10.setGenre("Fantasía");
		bookRepository.save(book10);

		// Libro 11
		Book book11 = new Book();
		book11.setTitle("El resplandor");
		book11.setAuthor("Stephen King");
		book11.setGenre("Terror");
		bookRepository.save(book11);

		// Libro 12
		Book book12 = new Book();
		book12.setTitle("El caso de la viuda negra");
		book12.setAuthor("Margery Allingham");
		book12.setGenre("Policíaca");
		bookRepository.save(book12);

		// Libro 13
		Book book13 = new Book();
		book13.setTitle("La isla del tesoro");
		book13.setAuthor("Robert Louis Stevenson");
		book13.setGenre("Aventura");
		bookRepository.save(book13);

		// Libro 14
		Book book14 = new Book();
		book14.setTitle("Los pilares de la Tierra");
		book14.setAuthor("Ken Follett");
		book14.setGenre("Histórica");
		bookRepository.save(book14);

		// Libro 15
		Book book15 = new Book();
		book15.setTitle("Steve Jobs");
		book15.setAuthor("Walter Isaacson");
		book15.setGenre("Biografía");
		bookRepository.save(book15);

		// Libro 16
		Book book16 = new Book();
		book16.setTitle("Mi lucha");
		book16.setAuthor("Adolf Hitler");
		book16.setGenre("Autobiografía");
		bookRepository.save(book16);

		// Libro 17
		Book book17 = new Book();
		book17.setTitle("El arte de amar");
		book17.setAuthor("Erich Fromm");
		book17.setGenre("Ensayo");
		bookRepository.save(book17);

		// Libro 18
		Book book18 = new Book();
		book18.setTitle("Veinte poemas de amor y una canción desesperada");
		book18.setAuthor("Pablo Neruda");
		book18.setGenre("Poesía");
		bookRepository.save(book18);

		// Libro 19
		Book book19 = new Book();
		book19.setTitle("Hamlet");
		book19.setAuthor("William Shakespeare");
		book19.setGenre("Drama");
		bookRepository.save(book19);

		// Libro 20
		Book book20 = new Book();
		book20.setTitle("Los cuentos de Canterbury");
		book20.setAuthor("Geoffrey Chaucer");
		book20.setGenre("Cuento");
		bookRepository.save(book20);

		// Libro 21
		Book book21 = new Book();
		book21.setTitle("La liebre y la tortuga");
		book21.setAuthor("Esopo");
		book21.setGenre("Fábula");
		bookRepository.save(book21);

		// Libro 22
		Book book22 = new Book();
		book22.setTitle("La Ilíada");
		book22.setAuthor("Homero");
		book22.setGenre("Mitología");
		bookRepository.save(book22);

		// Libro 23
		Book book23 = new Book();
		book23.setTitle("One Piece");
		book23.setAuthor("Eiichiro Oda");
		book23.setGenre("Manga");
		bookRepository.save(book23);

		// Libro 24
		Book book24 = new Book();
		book24.setTitle("Watchmen");
		book24.setAuthor("Alan Moore y Dave Gibbons");
		book24.setGenre("Cómic / Novela gráfica");
		bookRepository.save(book24);

		// Libro 25
		Book book25 = new Book();
		book25.setTitle("Alicia en el país de las maravillas");
		book25.setAuthor("Lewis Carroll");
		book25.setGenre("Literatura infantil");
		bookRepository.save(book25);

		// Libro 26
		Book book26 = new Book();
		book26.setTitle("Harry Potter y la piedra filosofal");
		book26.setAuthor("J.K. Rowling");
		book26.setGenre("Literatura juvenil");
		bookRepository.save(book26);

		// Libro 27
		Book book27 = new Book();
		book27.setTitle("El código del dinero");
		book27.setAuthor("Raimon Samsó");
		book27.setGenre("Humor");
		bookRepository.save(book27);

		// Libro 28
		Book book28 = new Book();
		book28.setTitle("En el camino");
		book28.setAuthor("Jack Kerouac");
		book28.setGenre("Viajes");
		bookRepository.save(book28);

		// Libro 29
		Book book29 = new Book();
		book29.setTitle("Crítica de la razón pura");
		book29.setAuthor("Immanuel Kant");
		book29.setGenre("Filosofía");
		bookRepository.save(book29);

		// Libro 30
		Book book30 = new Book();
		book30.setTitle("Romeo y Julieta");
		book30.setAuthor("William Shakespeare");
		book30.setGenre("Teatro");
		bookRepository.save(book30);

		// Libro 31
		Book book31 = new Book();
		book31.setTitle("Crónica de una muerte anunciada");
		book31.setAuthor("Gabriel García Márquez");
		book31.setGenre("Crónica");
		bookRepository.save(book31);

		// Libro 32
		Book book32 = new Book();
		book32.setTitle("Cartas a Lucilio");
		book32.setAuthor("Séneca");
		book32.setGenre("Epistolar");
		bookRepository.save(book32);

		// Libro 33
		Book book33 = new Book();
		book33.setTitle("Historia de la filosofía");
		book33.setAuthor("Julián Marías");
		book33.setGenre("Didáctico");
		bookRepository.save(book33);

		// Libro 34
		Book book34 = new Book();
		book34.setTitle("Enciclopedia Británica");
		book34.setAuthor("Gobierno Británico");
		book34.setGenre("Enciclopedia");
		bookRepository.save(book34);

		// Libro 35
		Book book35 = new Book();
		book35.setTitle("Matar un ruiseñor");
		book35.setAuthor("Harper Lee");
		book35.setGenre("Novela");
		bookRepository.save(book35);

		// Libro 36
		Book book36 = new Book();
		book36.setTitle("El juego de Ripper");
		book36.setAuthor("Isabel Allende");
		book36.setGenre("Thriller");
		bookRepository.save(book36);

		// Libro 37
		Book book37 = new Book();
		book37.setTitle("Ready Player One");
		book37.setAuthor("Ernest Cline");
		book37.setGenre("Ciencia ficción");
		bookRepository.save(book37);

		// Libro 38
		Book book38 = new Book();
		book38.setTitle("Bajo el sol de medianoche");
		book38.setAuthor("Sarah Jio");
		book38.setGenre("Romance");
		bookRepository.save(book38);

		// Libro 39
		Book book39 = new Book();
		book39.setTitle("El visitante");
		book39.setAuthor("Stephen King");
		book39.setGenre("Misterio");
		bookRepository.save(book39);

		// Libro 40
		Book book40 = new Book();
		book40.setTitle("Harry Potter y el prisionero de Azkaban");
		book40.setAuthor("J.K. Rowling");
		book40.setGenre("Fantasía");
		bookRepository.save(book40);

		// Libro 41
		Book book41 = new Book();
		book41.setTitle("El exorcista");
		book41.setAuthor("William Peter Blatty");
		book41.setGenre("Terror");
		bookRepository.save(book41);

		// Libro 42
		Book book42 = new Book();
		book42.setTitle("El asesinato de Roger Ackroyd");
		book42.setAuthor("Agatha Christie");
		book42.setGenre("Policíaca");
		bookRepository.save(book42);

		// Libro 43
		Book book43 = new Book();
		book43.setTitle("La vuelta al mundo en 80 días");
		book43.setAuthor("Julio Verne");
		book43.setGenre("Aventura");
		bookRepository.save(book43);

		// Libro 44
		Book book44 = new Book();
		book44.setTitle("Los reyes malditos I: El rey de hierro");
		book44.setAuthor("Maurice Druon");
		book44.setGenre("Histórica");
		bookRepository.save(book44);

		// Libro 45
		Book book45 = new Book();
		book45.setTitle("Elon Musk: Tesla, SpaceX y la búsqueda de un futuro fantástico");
		book45.setAuthor("Ashlee Vance");
		book45.setGenre("Biografía");
		bookRepository.save(book45);

		// Libro 46
		Book book46 = new Book();
		book46.setTitle("Memorias de una geisha");
		book46.setAuthor("Arthur Golden");
		book46.setGenre("Autobiografía");
		bookRepository.save(book46);

		// Libro 47
		Book book47 = new Book();
		book47.setTitle("Sobre la brevedad de la vida");
		book47.setAuthor("Séneca");
		book47.setGenre("Ensayo");
		bookRepository.save(book47);

		// Libro 48
		Book book48 = new Book();
		book48.setTitle("La Divina Comedia");
		book48.setAuthor("Dante Alighieri");
		book48.setGenre("Poesía");
		bookRepository.save(book48);

		// Libro 49
		Book book49 = new Book();
		book49.setTitle("Otelo");
		book49.setAuthor("William Shakespeare");
		book49.setGenre("Drama");
		bookRepository.save(book49);

		// Libro 50
		Book book50 = new Book();
		book50.setTitle("Los cuentos de Hoffmann");
		book50.setAuthor("E.T.A. Hoffmann");
		book50.setGenre("Cuento");
		bookRepository.save(book50);

		// Libro 51
		Book book51 = new Book();
		book51.setTitle("La cigarra y la hormiga");
		book51.setAuthor("Jean de la Fontaine");
		book51.setGenre("Fábula");
		bookRepository.save(book51);

		// Libro 52
		Book book52 = new Book();
		book52.setTitle("La Odisea");
		book52.setAuthor("Homero");
		book52.setGenre("Mitología");
		bookRepository.save(book52);

		// Libro 53
		Book book53 = new Book();
		book53.setTitle("Dragon Ball");
		book53.setAuthor("Akira Toriyama");
		book53.setGenre("Manga");
		bookRepository.save(book53);

		// Libro 54
		Book book54 = new Book();
		book54.setTitle("V de Vendetta");
		book54.setAuthor("Alan Moore y David Lloyd");
		book54.setGenre("Cómic / Novela gráfica");
		bookRepository.save(book54);

		// Libro 55
		Book book55 = new Book();
		book55.setTitle("El principito");
		book55.setAuthor("Antoine de Saint-Exupéry");
		book55.setGenre("Literatura infantil");
		bookRepository.save(book55);

		// Libro 56
		Book book56 = new Book();
		book56.setTitle("Los juegos del hambre");
		book56.setAuthor("Suzanne Collins");
		book56.setGenre("Literatura juvenil");
		bookRepository.save(book56);

		// Libro 57
		Book book57 = new Book();
		book57.setTitle("El club de la comedia");
		book57.setAuthor("Joaquín Reyes");
		book57.setGenre("Humor");
		bookRepository.save(book57);

		// Libro 58
		Book book58 = new Book();
		book58.setTitle("En el camino");
		book58.setAuthor("Jack Kerouac");
		book58.setGenre("Viajes");
		bookRepository.save(book58);

		// Libro 59
		Book book59 = new Book();
		book59.setTitle("El contrato social");
		book59.setAuthor("Jean-Jacques Rousseau");
		book59.setGenre("Filosofía");
		bookRepository.save(book59);

		// Libro 60
		Book book60 = new Book();
		book60.setTitle("La Casa de Bernarda Alba");
		book60.setAuthor("Federico García Lorca");
		book60.setGenre("Teatro");
		bookRepository.save(book60);

		// Libro 61
		Book book61 = new Book();
		book61.setTitle("Las venas abiertas de América Latina");
		book61.setAuthor("Eduardo Galeano");
		book61.setGenre("Crónica");
		bookRepository.save(book61);

		// Libro 62
		Book book62 = new Book();
		book62.setTitle("Cartas a un joven poeta");
		book62.setAuthor("Rainer Maria Rilke");
		book62.setGenre("Epistolar");
		bookRepository.save(book62);

		// Libro 63
		Book book63 = new Book();
		book63.setTitle("El arte de la guerra");
		book63.setAuthor("Sun Tzu");
		book63.setGenre("Didáctico");
		bookRepository.save(book63);

		// Libro 64
		Book book64 = new Book();
		book64.setTitle("Enciclopedia del saber relativo y absoluto");
		book64.setAuthor("Bernard Werber");
		book64.setGenre("Enciclopedia");
		bookRepository.save(book64);

		// Libro 65
		Book book65 = new Book();
		book65.setTitle("Cien años de soledad");
		book65.setAuthor("Gabriel García Márquez");
		book65.setGenre("Novela");
		bookRepository.save(book65);

		// Libro 66
		Book book66 = new Book();
		book66.setTitle("El código Da Vinci");
		book66.setAuthor("Dan Brown");
		book66.setGenre("Thriller");
		bookRepository.save(book66);

		// Libro 67
		Book book67 = new Book();
		book67.setTitle("Neuromante");
		book67.setAuthor("William Gibson");
		book67.setGenre("Ciencia ficción");
		bookRepository.save(book67);

		// Libro 68
		Book book68 = new Book();
		book68.setTitle("Orgullo y prejuicio");
		book68.setAuthor("Jane Austen");
		book68.setGenre("Romance");
		bookRepository.save(book68);

		// Libro 69
		Book book69 = new Book();
		book69.setTitle("El misterio de la cripta embrujada");
		book69.setAuthor("Eduardo Mendoza");
		book69.setGenre("Misterio");
		bookRepository.save(book69);

		// Libro 70
		Book book70 = new Book();
		book70.setTitle("El señor de los anillos");
		book70.setAuthor("J.R.R. Tolkien");
		book70.setGenre("Fantasía");
		bookRepository.save(book70);

		// Libro 71
		Book book71 = new Book();
		book71.setTitle("El resplandor");
		book71.setAuthor("Stephen King");
		book71.setGenre("Terror");
		bookRepository.save(book71);

		// Libro 72
		Book book72 = new Book();
		book72.setTitle("El caso de la viuda negra");
		book72.setAuthor("Margery Allingham");
		book72.setGenre("Policíaca");
		bookRepository.save(book72);

		// Libro 73
		Book book73 = new Book();
		book73.setTitle("La isla del tesoro");
		book73.setAuthor("Robert Louis Stevenson");
		book73.setGenre("Aventura");
		bookRepository.save(book73);

		// Libro 74
		Book book74 = new Book();
		book74.setTitle("Los pilares de la Tierra");
		book74.setAuthor("Ken Follett");
		book74.setGenre("Histórica");
		bookRepository.save(book74);

		// Libro 75
		Book book75 = new Book();
		book75.setTitle("Steve Jobs");
		book75.setAuthor("Walter Isaacson");
		book75.setGenre("Biografía");
		bookRepository.save(book75);

		// Libro 76
		Book book76 = new Book();
		book76.setTitle("Mi lucha");
		book76.setAuthor("Adolf Hitler");
		book76.setGenre("Autobiografía");
		bookRepository.save(book76);

		// Libro 77
		Book book77 = new Book();
		book77.setTitle("El arte de amar");
		book77.setAuthor("Erich Fromm");
		book77.setGenre("Ensayo");
		bookRepository.save(book77);

		// Libro 78
		Book book78 = new Book();
		book78.setTitle("Veinte poemas de amor y una canción desesperada");
		book78.setAuthor("Pablo Neruda");
		book78.setGenre("Poesía");
		bookRepository.save(book78);

		// Libro 79
		Book book79 = new Book();
		book79.setTitle("Hamlet");
		book79.setAuthor("William Shakespeare");
		book79.setGenre("Drama");
		bookRepository.save(book79);

		// Libro 80
		Book book80 = new Book();
		book80.setTitle("Los cuentos de Canterbury");
		book80.setAuthor("Geoffrey Chaucer");
		book80.setGenre("Cuento");
		bookRepository.save(book80);

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

		UserBook userBook5 = new UserBook();
		userBook5.setUser(user1);
		userBook5.setBook(book5);
		userBook5.setStatus("Leído");
		userBook5.setRate("4");
		userBook5.setComment("Muy interesante, aunque un poco confuso al principio");
		userBookRepository.save(userBook5);

		UserBook userBook6 = new UserBook();
		userBook6.setUser(user1);
		userBook6.setBook(book6);
		userBook6.setStatus("En curso");
		userBook6.setRate("4");
		userBook6.setComment("Enganchado desde el primer capítulo");
		userBookRepository.save(userBook6);

		UserBook userBook7 = new UserBook();
		userBook7.setUser(user1);
		userBook7.setBook(book7);
		userBook7.setStatus("Sin empezar");
		userBook7.setRate("3");
		userBook7.setComment("Lo leeré después de terminar otro libro");
		userBookRepository.save(userBook7);

		UserBook userBook8 = new UserBook();
		userBook8.setUser(user1);
		userBook8.setBook(book8);
		userBook8.setStatus("En curso");
		userBook8.setRate("5");
		userBook8.setComment("¡Maravilloso, no puedo dejar de leerlo!");
		userBookRepository.save(userBook8);

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
		userBook11.setBook(book11);
		userBook11.setStatus("Leído");
		userBook11.setRate("4");
		userBook11.setComment("Interesante, pero algo perturbador");
		userBookRepository.save(userBook11);

		UserBook userBook12 = new UserBook();
		userBook12.setUser(user2);
		userBook12.setBook(book12);
		userBook12.setStatus("En curso");
		userBook12.setRate("3");
		userBook12.setComment("Esperaba más acción");
		userBookRepository.save(userBook12);

		UserBook userBook13 = new UserBook();
		userBook13.setUser(user2);
		userBook13.setBook(book13);
		userBook13.setStatus("Sin empezar");
		userBook13.setRate("5");
		userBook13.setComment("Un clásico que no puedo esperar a leer");
		userBookRepository.save(userBook13);

		UserBook userBook14 = new UserBook();
		userBook14.setUser(user2);
		userBook14.setBook(book14);
		userBook14.setStatus("Leído");
		userBook14.setRate("4");
		userBook14.setComment("Una historia épica, muy bien escrita");
		userBookRepository.save(userBook14);

		UserBook userBook15 = new UserBook();
		userBook15.setUser(user2);
		userBook15.setBook(book15);
		userBook15.setStatus("Leído");
		userBook15.setRate("5");
		userBook15.setComment("Una biografía fascinante, muy inspiradora");
		userBookRepository.save(userBook15);

//		UserBook userBook16 = new UserBook();
//		userBook16.setUser(user2);
//		userBook16.setBook(book16);
//		userBook16.setStatus("En curso");
//		userBook16.setRate("3");
//		userBook16.setComment("Interesante, aunque a veces difícil de leer");
//		userBookRepository.save(userBook16);
//
//		UserBook userBook17 = new UserBook();
//		userBook17.setUser(user2);
//		userBook17.setBook(book17);
//		userBook17.setStatus("Sin empezar");
//		userBook17.setRate("4");
//		userBook17.setComment("Lo empezaré pronto, tiene buena pinta");
//		userBookRepository.save(userBook17);
//
//		UserBook userBook18 = new UserBook();
//		userBook18.setUser(user2);
//		userBook18.setBook(book18);
//		userBook18.setStatus("En curso");
//		userBook18.setRate("5");
//		userBook18.setComment("¡Me encanta la poesía de Neruda!");
//		userBookRepository.save(userBook18);

		// Crear generos literarios.
		List<String> namesGenres = Arrays.asList("Novela", "Thriller", "Ciencia ficción", "Romance", "Misterio",
				"Fantasía", "Terror", "Policíaca", "Aventura", "Histórica", "Biografía", "Autobiografía", "Ensayo",
				"Poesía", "Drama", "Cuento", "Fábula", "Mitología", "Manga", "Cómic / Novela gráfica",
				"Literatura infantil", "Literatura juvenil", "Humor", "Viajes", "Filosofía", "Teatro", "Crónica",
				"Epistolar", "Didáctico", "Enciclopedia");

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
