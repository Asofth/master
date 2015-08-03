package controle.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SistemaControlado implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Computador> computadores = new ArrayList<Computador>();
	private List<Programa> programas = new ArrayList<Programa>();

	public List<Computador> getComputadores() {
		return computadores;
	}

	public void setComputadores(List<Computador> computadores) {
		this.computadores = computadores;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public boolean validar() throws IllegalArgumentException {

		List<String> identificadores = new ArrayList<>();

		if (this.computadores != null && this.computadores.size() > 0) {
			for (Computador computador : this.computadores) {
				if (computador.getAtributos() != null
						&& computador.getAtributos().size() > 0) {
					for (Atributo atributo : computador.getAtributos()) {
						atributo.setComputador(computador);
						if (computador.getIdentificador() == null
								|| atributo.getIdentificador() == null) {
							throw new IllegalArgumentException(
									"Identificador do computador ou do atributo nao pode ser nulo: "
											+ "computador.identificador="
											+ computador.getIdentificador()
											+ "atributo.identificador="
											+ atributo
													.getIdentificador());
						}
						if (identificadores.contains(computador
								.getIdentificador()
								+ "|"
								+ atributo.getIdentificador())) {
							throw new IllegalArgumentException("Identificador "
									+ atributo.getIdentificador()
									+ " duplicado!");
						} else {
							identificadores
									.add(computador.getIdentificador()
											+ "|"
											+ atributo
													.getIdentificador());
						}
					}
				}
			}
		}

		if (this.programas != null && this.programas.size() > 0) {
			for (Programa programa : this.programas) {
				if (programa.getAtributos() != null
						&& programa.getAtributos().size() > 0) {
					for (Atributo atributo : programa.getAtributos()) {
						atributo.setPrograma(programa);
						if (programa.getIdentificador() == null
								|| atributo.getIdentificador() == null) {
							throw new IllegalArgumentException(
									"Identificador do programa ou do atributo nao pode ser nulo: "
											+ "programa.identificador="
											+ programa.getIdentificador()
											+ "atributo.identificador="
											+ atributo
													.getIdentificador());
						}
						if (identificadores.contains(programa
								.getIdentificador()
								+ "|"
								+ atributo.getIdentificador())) {
							throw new IllegalArgumentException("Identificador "
									+ atributo.getIdentificador()
									+ " duplicado!");
						} else {
							identificadores
									.add(programa.getIdentificador()
											+ "|"
											+ atributo
													.getIdentificador());
						}
					}
				}
			}
		}

		return true;
	}

	public List<Atributo> getListaAtributosGerenciados() {

		List<Atributo> atributos = new ArrayList<>();

		if (this.computadores != null && this.computadores.size() > 0) {
			for (Computador computador : this.computadores) {
				if (computador.getAtributos() != null
						&& computador.getAtributos().size() > 0) {
					atributos.addAll(computador.getAtributos());
				}
			}
		}

		if (this.programas != null && this.programas.size() > 0) {
			for (Programa programa : this.programas) {
				if (programa.getAtributos() != null
						&& programa.getAtributos().size() > 0) {
					atributos.addAll(programa.getAtributos());
				}
			}
		}

		return atributos;
	}

}
