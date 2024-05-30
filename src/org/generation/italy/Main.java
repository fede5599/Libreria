package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import org.generation.italy.model.Libro;

public class Main {

	public static void main(String[] args) {
		// Esercizio di gruppo: Ciro Aldorisio, Federica Zaccaro, Gabriel Spina, Marina
		// Perriera, Samuele Lanza.
		String url = "jdbc:mysql://localhost:3306/Libreria"; // stringa di connessione
		Scanner sc = new Scanner(System.in);
		ArrayList<Libro> elencoLibri = new ArrayList<Libro>();
		Libro m;
		String risposta, sql;
		boolean sceltaSbagliata = false, entrataSbagliata = false, uscitaSbagliata = false;
		boolean visualizzazione = false, inserimento = false, delete = false, ricerca = false, modifica = false,
				uscita = false;

		System.out.println("Tentativo di connessione al db...");
		try (Connection conn = DriverManager.getConnection(url, "root", "")) { // provo a connettermi

			// la connessione è andata a buon fine
			System.out.println("Connesso correttamente al database");

			// PRIMO PUNTO

			/*
			 * System.out.println("*** LIBRI PRESENTI NEL DATABASE ***");
			 * 
			 * String sql = "SELECT * FROM libri WHERE quantita > 0"; try (PreparedStatement
			 * ps = conn.prepareStatement(sql)) { try (ResultSet rs = ps.executeQuery()) {
			 * 
			 * // scorro tutte le righe while (rs.next()) { m = new Libro(); m.id =
			 * rs.getInt("id"); // recupero il valore della colonna "id" m.titolo =
			 * rs.getString("titolo"); m.autore = rs.getString("autore"); m.genere =
			 * rs.getString("genere"); m.quantita = rs.getInt("quantita"); m.casa_editrice =
			 * rs.getString("casa_editrice"); // m.isbn=rs.getInt("isbn");
			 * 
			 * elencoLibri.add(m); } } } // stampo i movimenti letti dal DB for (Libro mov :
			 * elencoLibri) System.out.println(mov.toString());
			 *
			 * 
			 */

			// SECONDO PUNTO

			System.out.println("Scegli tra le seguenti opzioni");

			// scelta del menù a tendina

			do {
				sceltaSbagliata = false;
				System.out.println(
						"Inserisci la tua azione: \nInserimento 1\nVisualizzazione 2\nCancellazione 3\nModifica 4\nRicerca 5\nUscita 6");
				risposta = sc.nextLine();

				if (risposta.equals("1")) {
					System.out.println("Inserisci nuovo libro");
					inserimento = true;
				} else if (risposta.equals("2")) {
					System.out.println("Visualizza lista libri");
					visualizzazione = true;
				} else if (risposta.equals("3")) {
					System.out.println("Cancellazione libro");
					delete = true;
				} else if (risposta.equals("4")) {
					System.out.println("Modifica libro");
					modifica = true;
				} else if (risposta.equals("5")) {
					System.out.println("Ricerca");
					ricerca = true;
				} else if (risposta.equals("6")) {
					System.out.println("Uscita");
					uscita = true;
				} else {
					System.out.println("Scelta errata, inserire nuovamente");
					sceltaSbagliata = true;
				}
			} while (sceltaSbagliata == true);

			// MENÙ A TENDINA SCELTA 1 - INSERIMENTO
			if (inserimento == true) {
				do {
					System.out.println("*** INSERIMENTO MOVIMENTO ***");

					m = new Libro();

					// leggo i dati del movimento

					System.out.print("titolo: ");
					m.titolo = sc.nextLine();

					System.out.print("autore: ");
					m.autore = sc.nextLine();

					System.out.print("genere: ");
					m.genere = sc.nextLine();

					System.out.print("quantita: ");
					m.quantita = sc.nextInt();
					sc.nextLine();

					System.out.print("casa_editrice: ");
					m.casa_editrice = sc.nextLine();

					// System.out.print("isbn: "); m.isbn=sc.nextInt(); sc.nextLine();

					sql = "INSERT INTO libri( titolo, autore, genere, quantita, casa_editrice) "
							+ "VALUE( ?, ?, ?, ?, ?)"; // il ? indica un parametro (segnaposto)

					System.out.println("Tentativo di esecuzione INSERT");
					try (PreparedStatement ps = conn.prepareStatement(sql)) { // provo a creare l'istruzione sql

						// imposto i valori dei parametri
						ps.setString(1, m.titolo); // il primo parametro è l'id. NB
						ps.setString(2, m.autore);
						ps.setString(3, m.genere);
						ps.setInt(4, m.quantita);
						ps.setString(5, m.casa_editrice);
						// ps.setInt(8, m.isbn);

						int righeInteressate = ps.executeUpdate(); // eseguo l'istruzione
						System.out.println("Righe inserite: " + righeInteressate);

					}
				} while (entrataSbagliata == true);
			}

			// MENÙ A TENDINA SCELTA 2 - VISUALIZZAZIONE
			if (visualizzazione == true) {
				do {
					System.out.println("\n\n\n\n");
					System.out.println("*** ELENCO MOVIMENTI ***");

					sql = "SELECT * FROM libri";
					try (PreparedStatement ps = conn.prepareStatement(sql)) {
						try (ResultSet rs = ps.executeQuery()) {

							// scorro tutte le righe
							while (rs.next()) {
								m = new Libro();
								m.id = rs.getInt("id"); // recupero il valore della colonna "id"
								m.titolo = rs.getString("titolo");
								m.autore = rs.getString("autore");
								m.genere = rs.getString("genere");
								m.quantita = rs.getInt("quantita");
								m.casa_editrice = rs.getString("casa_editrice");
								// m.isbn=rs.getInt("isbn");

								elencoLibri.add(m);
							}
						}
					}
				} while (entrataSbagliata == true);
			}

			// MENÙ A TENDINA SCELTA 3 - CANCELLAZIONE
			if (delete == true) {
				do {
					System.out.println("Inserisci l'Id da eliminare: ");
					int id = sc.nextInt();
					sc.nextLine();
					sql = "DELETE FROM libri WHERE id= ?";
					try (PreparedStatement ps = conn.prepareStatement(sql)) {

						ps.setInt(1, id);
						int risultatoE = ps.executeUpdate();

						if (risultatoE > 0) {
							System.out.println("Libro eliminato con successo");
						} else {
							System.out.println("Nessun libro trovato con l'Id inserito");
						}
					}
				} while (entrataSbagliata == true);
			}

			// MENÙ A TENDINA SCELTA 4 - MODIFICA
			if (modifica == true) {
				do {
					System.out.println("Inserisci l'Id da MODIFICARE: ");
					int id = sc.nextInt();
					sc.nextLine();

					sql = "SELECT * FROM libri WHERE id=?";

					try (PreparedStatement ps = conn.prepareStatement(sql)) {
						ps.setInt(1, id);
						try (ResultSet rs = ps.executeQuery()) {

							if (rs.next()) {
								System.out.println("Inserisci il nuovo titolo: ");
								String nuovoTitolo = sc.nextLine();

								System.out.println("Inserisci il nuovo autore: ");
								String nuovoAutore = sc.nextLine();

								System.out.println("Inserisci il nuovo genere: ");
								String nuovoGenere = sc.nextLine();

								System.out.println("Inserisci la nuova quantità: ");
								int nuovaQuantita = sc.nextInt();
								sc.nextLine();

								System.out.println("Inserisci la nuova Casa editrice: ");
								String nuovaCasaed = sc.nextLine();

								sql = "UPDATE libri SET titolo = ?, autore= ?, genere= ?, quantita= ?, casa_editrice= ? WHERE id=?";

								try (PreparedStatement nuovops = conn.prepareStatement(sql)) {
									nuovops.setString(1, nuovoTitolo);
									nuovops.setString(2, nuovoAutore);
									nuovops.setString(3, nuovoGenere);
									nuovops.setInt(4, nuovaQuantita);
									nuovops.setString(5, nuovaCasaed);
									nuovops.setInt(6, id);

									int risultatoUp = nuovops.executeUpdate();

									if (risultatoUp > 0) {
										System.out.println("Libro aggiornato con successo");
									} else {
										System.out.println("Errore nell'aggiornamento del libro");
									}

								}

							} else {
								System.out.println("Nessun libro trovato con l'Id inserito");
							}

						}
					}

				} while (entrataSbagliata == true);

			}
			// MENÙ A TENDINA SCELTA 5 - RICERCA
			if (ricerca == true) {
				do {
					boolean risultatoTrovato = false;
					do {
						System.out.println(
								"Scegli il criterio di ricerca:\n1. Per Titolo\n2. Per Autore\n3. Per Genere\n4. Per Casa Editrice");
						String criterio = sc.nextLine();
						String valoreRicerca = "";

						switch (criterio) {
						case "1":
							System.out.print("Inserisci il titolo: ");
							valoreRicerca = sc.nextLine();
							sql = "SELECT * FROM libri WHERE titolo LIKE ?";
							break;
						case "2":
							System.out.print("Inserisci l'autore: ");
							valoreRicerca = sc.nextLine();
							sql = "SELECT * FROM libri WHERE autore LIKE ?";
							break;
						case "3":
							System.out.print("Inserisci il genere: ");
							valoreRicerca = sc.nextLine();
							sql = "SELECT * FROM libri WHERE genere LIKE ?";
							break;
						case "4":
							System.out.print("Inserisci la casa editrice: ");
							valoreRicerca = sc.nextLine();
							sql = "SELECT * FROM libri WHERE casa_editrice LIKE ?";
							break;
						default:
							System.out.println("Criterio non valido.");
							continue;
						}
						try (PreparedStatement ps = conn.prepareStatement(sql)) {
							ps.setString(1, "%" + valoreRicerca + "%");
							try (ResultSet rs = ps.executeQuery()) {
								elencoLibri.clear();
								while (rs.next()) {
									m = new Libro();
									m.id = rs.getInt("id");
									m.titolo = rs.getString("titolo");
									m.autore = rs.getString("autore");
									m.genere = rs.getString("genere");
									m.quantita = rs.getInt("quantita");
									m.casa_editrice = rs.getString("casa_editrice");
									elencoLibri.add(m);
								}
							}
						}
						if (!elencoLibri.isEmpty()) {
							risultatoTrovato = true;
							System.out.println("*** RISULTATI RICERCA ***");
							for (Libro libro : elencoLibri) {
								System.out.println(libro.toString());
							}
						} else {
							System.out.println("Nessun risultato trovato. Riprova.");
						}
					} while (!risultatoTrovato);

				} while (entrataSbagliata == true);
			}

			// MENÙ A TENDINA SCELTA 6 - USCITA
			if (uscita == true) {
				do {
					break;
				} while (entrataSbagliata == true);
			}

			// stampo i movimenti letti dal DB
			for (Libro mov : elencoLibri)
				System.out.println(mov.toString());
		} catch (Exception e) {
			// si è verificato un problema. L'oggetto e (di tipo Exception) contiene
			// informazioni sull'errore verificatosi
			System.err.println("Si è verificato un errore: " + e.getMessage());
		}
		sc.close();

	}
}
