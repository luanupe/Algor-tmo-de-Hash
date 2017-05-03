package dois.redes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Luan Augusto, LAF
 * Date: 3 de mai de 2017
 * Time: 13:31:08
 *
 */
public class Main {

	/*
	 * Fiz pra converter várias Hash e uma só vez pra ficar
	 * mais fácil de ver que as Hash não estão ficando iguais
	 * mesmo se a combinação de letras da palavra for a mesma.
	 */
	public static void main(String[] args) {
		List<String> entradas = new ArrayList<String>();
		entradas.add("Luan");
		entradas.add("nauL");
		entradas.add("LuAn");
		entradas.add("nAuL");

		entradas.add("Erick");
		entradas.add("kcirE");
		entradas.add("ErIcK");
		entradas.add("KcIrE");

		for (String entrada : entradas) {
			Hash hash = new Hash(entrada);
			String saida = hash.getHash();

			System.out.println("[" + entrada.length() + "] INPUT: '" + entrada + "'");
			System.out.println("[" + saida.length() +"] HASH: '" + saida + "'");
			System.out.println();
		}
	}

}
