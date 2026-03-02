package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return this.converteDadosSerie(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return this.converteDadosSerie(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return this.converteDadosSerie(repositorio.lancamentosRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getCapa(),
                    s.getSinopse(),
                    s.getAtores());
        }
        return null;
    }

    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return converteDadosEpisodio(repositorio.topEpisodiosPorSerie(s));
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return converteDadosEpisodio(s.getEpisodios());
        }
        return null;
    }

    public List<EpisodioDTO> obterPorTemporada(Long id, Long numero) {
        return converteDadosEpisodio(repositorio.obterEpisodiosPorTemporada(id,numero));
    }

    public List<SerieDTO> obterSerieCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDadosSerie(repositorio.findByGenero(categoria));
    }

    private List<SerieDTO> converteDadosSerie(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getCapa(),
                        s.getSinopse(),
                        s.getAtores()))
                .collect(Collectors.toList());
    }

    private List<EpisodioDTO> converteDadosEpisodio(List<Episodio> episodios) {
        return episodios.stream()
                .map(e -> new EpisodioDTO(
                        e.getTemporada(),
                        e.getTitulo(),
                        e.getNumero()))
                .collect(Collectors.toList());
    }
}
