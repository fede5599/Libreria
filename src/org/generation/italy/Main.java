package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import org.generation.italy.model.Movimenti;

public class Main {

	public static void main(String[] args) {
		// Esercizio di gruppo: Ciro Aldorisio, Federica Zaccaro, Gabriel Spina, Marina Perriera, Samuele Lanza.
		String url="jdbc:mysql://localhost:3306/Libreria";	//stringa di connessione 
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
			
			System.out.print("genere: ");
			m.genere=sc.nextLine();
			
			System.out.print("quantita: ");
			m.quantita=sc.nextInt();
			sc.nextLine();
			
			System.out.print("casa_editrice: ");
			m.casa_editrice=sc.nextLine();
			
			/*System.out.print("isbn: ");
			m.isbn=sc.nextInt();
			sc.nextLine();*/
			
			
			String sql="INSERT INTO libri(id, titolo, autore, genere, quantita, casa_editrice) "
					+ "VALUE(?, ?, ?, ?, ?, ?)";		//il ? indica un parametro (segnaposto)
			
			System.out.println("Tentativo di esecuzione INSERT");
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		//provo a creare l'istruzione sql

				//imposto i valori dei parametri				
				ps.setInt(1, m.id);		//il primo parametro è l'id. NB
				ps.setString(2, m.titolo);	
				ps.setString(3, m.autore);
				ps.setString(4, m.genere);
				ps.setInt(5, m.quantita);
				ps.setString(6, m.casa_editrice);
				//ps.setInt(8, m.isbn);

				int righeInteressate=ps.executeUpdate();	//eseguo l'istruzione
				System.out.println("Righe inserite: "+righeInteressate);
				
				
			}
			System.out.println("\n\n\n\n");
			System.out.println("*** ELENCO MOVIMENTI ***");
			
			sql="SELECT * FROM libri"; 			
			try (PreparedStatement ps=conn.prepareStatement(sql)) {		
				try (ResultSet rs=ps.executeQuery()) {
					
					//scorro tutte le righe
					while (rs.next()) {		
						m=new Movimenti();
						m.id=rs.getInt("id");		//recupero il valore della colonna "id"
						m.titolo=rs.getString("titolo");
						m.autore=rs.getString("autore");
						m.genere=rs.getString("genere");
						m.quantita=rs.getInt("quantita");
						m.casa_editrice=rs.getString("casa_editrice");
						//m.isbn=rs.getInt("isbn");

						elencoMovimenti.add(m);		
		}
	}
			}
			//stampo i movimenti letti dal DB
 			for (Movimenti mov:elencoMovimenti)
 				System.out.println(mov.toString());
		}
		catch (Exception e) {
			//si è verificato un problema. L'oggetto e (di tipo Exception) contiene informazioni sull'errore verificatosi
			System.err.println("Si è verificato un errore: "+e.getMessage());
		}
		sc.close();
					
			}
}

