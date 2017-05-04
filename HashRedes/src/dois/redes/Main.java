package dois.redes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Luan Augusto, LAF
 * Date: 3 de mai de 2017
 * Time: 13:31:08
 *
 */
public class Main {

	/*
	 * Fiz pra converter v�rias Hash e uma s� vez pra ficar
	 * mais f�cil de ver que as Hash n�o est�o ficando iguais
	 * mesmo se a combina��o de letras da palavra for a mesma.
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.iniciarEntradas();
		main.iniciarHASH();
		main.resultado();
	}

	private List<String> entradas;
	private Map<String, Hash> tabela;

	public Main() {
		this.entradas = new ArrayList<String>();
		this.tabela = new HashMap<String, Hash>();
	}

	private void iniciarEntradas() {
		this.entradas.add("Luan");
		this.entradas.add("nauL");
		this.entradas.add("LuAn");
		this.entradas.add("nAuL");
		this.entradas.add("Erick");
		this.entradas.add("kcirE");
		this.entradas.add("ErIcK");
		this.entradas.add("KcIrE");
	}

	private void iniciarHASH() {
		for (String entrada : entradas) {
			Hash hash = new Hash(entrada);
			this.tabela.put(entrada, hash);
			hash.iniciar();
		}
	}

	private void resultado() {
		for (Map.Entry<String, Hash> hash : this.tabela.entrySet()) {
			String entrada = hash.getKey();
			String saida = hash.getValue().getHash();
			
			System.out.println("[" + entrada.length() + "] INPUT: '" + entrada + "'");
			System.out.println("[" + saida.length() +"] HASH: '" + saida + "'");
			System.out.println("SEMELHAN�A: ");
			
			for (Hash h : this.tabela.values()) {
				if ((hash.getValue() != h)) {
					System.out.println(saida + " == " + h.getHash() + ": " + hash.getValue().matches(h));
				}
			}
			
			System.out.println();
		}
	}

	

}
