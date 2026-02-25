package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaMyMemory;

import java.util.Optional;
import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String capa;
    private String sinopse;
    private String atores;

    public Serie(DadosSerie dadosSerie) {
        this.atores = dadosSerie.atores();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();
        this.capa = dadosSerie.capa();
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0.0);
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.titulo = dadosSerie.titulo();
    }

    public Categoria getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                ", Total Temporadas: " + totalTemporadas +
                ", Avaliação: " + avaliacao +
                ", Gênero: " + genero +
                ", Capa: " + capa +
                ", Sinopse: " + sinopse +
                ", Atores: " + atores;
    }
}
