package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;

import org.generation.italy.model.Movimenti;

public class Main {

	public static void main(String[] args) {
		// Esercizio di gruppo: Ciro Aldorisio, Federica Zaccaro, Gabriel Spina, Marina Perriera, Samuele Lanza.
		String url="jdbc:mysql://localhost:3306/magazzino";	//stringa di connessione 
		Scanner sc=new Scanner(System.in);
		ArrayList<Movimenti> elencoMovimenti=new ArrayList<Movimenti>();
		Movimenti m;
		
		System.out.println("Tentativo di connessione al db...");
		try (Connection conn=DriverManager.getConnection(url, "root", "")) {	//provo a connettermi
			
			//la connessione è andata a buon fine
			System.out.println("Connesso correttamente al database");
			
			System.out.println("*** INSERIMENTO MOVIMENTO ***");
			
			m=new Movimenti();
			
			//leggo i dati del movimento	
			System.out.print("id: ");
			m.id=sc.nextInt();
			sc.nextLine();
			
			System.out.print("titolo: ");
			m.titolo=sc.nextLine();
			
			System.out.print("autore: ");
			m.autore=sc.nextLine();
			
			System.out.print("quantita: ");
			m.quantita=sc.nextInt();
			sc.nextLine();
			
			System.out.print("casa_editrice: ");
			m.casa_editrice=sc.nextLine();
			
			System.out.print("isbn: ");
			m.isbn=sc.nextInt();
			sc.nextLine();
			
			
			String sql="INSERT INTO movimenti(id, titolo, autore, quantita, casa_editrice, isbn) "
					+ "VALUE(?, ?, ?, ?, ?, ?)";		//il ? indica un parametro (segnaposto)
			
			System.out.println("Tentativo di esecuzione INSERT");
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		//provo a creare l'istruzione sql

				//imposto i valori dei parametri				
				ps.setInt(1, m.id);		//il primo parametro è l'id. NB
				ps.setString(2, m.titolo);	
				ps.setString(3, m.autore);
				ps.setInt(4, m.quantita);
				ps.setString(5, m.casa_editrice);
				ps.setInt(6, m.isbn);

				
				
				
			}
		}
	}

}

