package org.generation.italy.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movimenti {

	public int id;

	public String titolo, autore, genere, casa_editrice;
	public int quantita, isbn;

	@Override
		public String toString() {
			
			return "\n[titolo=" + titolo + "\nautore=" + autore + "\ngenere=" + genere + "\nquantita=" + quantita + "\ncasa_editrice=" + casa_editrice + "\nisbn=" + isbn + "]\n";
}
}
