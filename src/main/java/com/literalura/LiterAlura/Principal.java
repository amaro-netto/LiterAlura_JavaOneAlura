package com.literalura;

import com.literalura.model.DadosAutor;
import com.literalura.model.DadosLivro;
import com.literalura.model.DadosRespostaAPI;
import com.literalura.model.Livro;
import com.literalura.model.Autor;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import com.literalura.service.ConsumoAPI;
import com.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.OptionalDouble;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO_BASE = "https://gutendex.com/books/";

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            String menu = """
                *************************************************
                LiterAlura - Seu Catálogo de Livros!

                Escolha uma opção:
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em determinado idioma
                6 - Gerar estatísticas dos livros
                7 - Gerar Top 10 livros mais baixados
                8 - Buscar autor pelo nome
                9 - Listar autores em ordem alfabética
                0 - Sair
                *************************************************
                """;
            System.out.println(menu);

            try {
                opcao = teclado.nextInt();
                teclado.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivroPeloTitulo();
                        break;
                    case 2:
                        listarLivrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEmAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 6:
                        gerarEstatisticasLivros();
                        break;
                    case 7:
                        gerarTop10LivrosMaisBaixados();
                        break;
                    case 8:
                        buscarAutorPeloNome();
                        break;
                    case 9:
                        listarAutoresEmOrdemAlfabetica();
                        break;
                    case 0:
                        System.out.println("Saindo do LiterAlura. Até mais!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
                if (opcao != 0) {
                    System.out.println("\nPressione ENTER para continuar...");
                    teclado.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro correspondente à opção.");
                teclado.nextLine();
                opcao = -1;
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Digite o título do livro que deseja buscar:");
        String tituloBusca = teclado.nextLine();

        String endereco = ENDERECO_BASE + "?search=" + tituloBusca.replace(" ", "%20");
        System.out.println("Buscando na API Gutendex: " + endereco);

        String json;
        try {
            json = consumo.obterDados(endereco);
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar dados na API: " + e.getMessage());
            return;
        }

        if (json == null || json.isEmpty()) {
            System.out.println("Não foi possível obter dados da API. Verifique o título ou sua conexão.");
            return;
        }

        DadosRespostaAPI dados = conversor.obterDados(json, DadosRespostaAPI.class);

        if (dados != null && dados.resultados() != null && !dados.resultados().isEmpty()) {
            DadosLivro dadosLivro = dados.resultados().get(0);

            Optional<Livro> livroExistente = livroRepository.findByTituloContainsIgnoreCase(dadosLivro.titulo());
            if (livroExistente.isPresent()) {
                System.out.println("\nERRO: Livro '" + dadosLivro.titulo() + "' já registrado no banco de dados.");
                System.out.println("Informações do livro já registrado:");
                System.out.println(livroExistente.get());
                System.out.println("-------------------------------------\n");
                return;
            }

            Autor autor = null;
            if (dadosLivro.autores() != null && dadosLivro.autores().length > 0) {
                DadosAutor dadosAutor = dadosLivro.autores()[0];

                Optional<Autor> autorExistente = Optional.ofNullable(autorRepository.findByNomeContainsIgnoreCase(dadosAutor.nome()));

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                    System.out.println("Autor '" + autor.getNome() + "' já registrado. Associando ao livro.");
                } else {
                    autor = new Autor(dadosAutor.nome(), dadosAutor.anoNascimento(), dadosAutor.anoFalecimento());
                    autorRepository.save(autor);
                    System.out.println("Novo autor '" + autor.getNome() + "' registrado.");
                }
            } else {
                System.out.println("Autor não encontrado na API para este livro. Livro não será salvo.");
                return;
            }

            String idiomaDoLivro = (dadosLivro.idiomas() != null && dadosLivro.idiomas().length > 0) ? dadosLivro.idiomas()[0] : "N/A";
            Livro livro = new Livro(dadosLivro.titulo(), autor, idiomaDoLivro.toLowerCase(), dadosLivro.numeroDownloads());

            if (autor != null) {
                autor.getLivros().add(livro);
            }

            livroRepository.save(livro);
            System.out.println("\n--- LIVRO ENCONTRADO E REGISTRADO COM SUCESSO ---");
            System.out.println(livro);
            System.out.println("---------------------------------------------------\n");

        } else {
            System.out.println("Nenhum livro encontrado com o título '" + tituloBusca + "' na API Gutendex. Por favor, tente um título exato ou verifique a ortografia.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("\n--- LIVROS REGISTRADOS ---");
            System.out.println("Nenhum livro registrado ainda no banco de dados.");
            System.out.println("---------------------------\n");
        } else {
            System.out.println("\n--- LIVROS REGISTRADOS ---");
            livros.forEach(System.out::println);
            System.out.println("---------------------------\n");
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            System.out.println("Nenhum autor registrado ainda no banco de dados.");
            System.out.println("---------------------------\n");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            autores.forEach(System.out::println);
            System.out.println("---------------------------\n");
        }
    }

    private void listarAutoresVivosEmAno() {
        System.out.println("Digite o ano para buscar autores que estavam vivos:");
        try {
            int ano = teclado.nextInt();
            teclado.nextLine();

            List<Autor> autoresVivos = autorRepository.findAutoresVivosEmAno(ano);

            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano " + ano + " no banco de dados.");
            } else {
                System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
                autoresVivos.forEach(System.out::println);
                System.out.println("-------------------------------------\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite um ano válido (número inteiro).");
            teclado.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            *************************************************
            Idiomas disponíveis para busca:
            es - Espanhol
            en - Inglês
            fr - Francês
            pt - Português
            *************************************************
            """);
        System.out.println("Digite a sigla do idioma (EX: PT, EN):");
        String idiomaBusca = teclado.nextLine().trim().toLowerCase();

        if (!idiomaBusca.matches("^(es|en|fr|pt)$")) {
            System.out.println("\nERRO: Idioma inválido. Por favor, digite uma de las siglas sugeridas: es, en, fr, pt.");
            return;
        }

        List<Livro> livrosPorIdioma = livroRepository.findByIdioma(idiomaBusca);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma '" + idiomaBusca.toUpperCase() + "' no banco de dados.");
        } else {
            System.out.println("\n--- LIVROS NO IDIOMA '" + idiomaBusca.toUpperCase() + "' ---");
            livrosPorIdioma.forEach(System.out::println);
            System.out.println("-----------------------------------------\n");
        }
    }

    private void gerarEstatisticasLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("\nNão há livros registrados para gerar estatísticas.");
            return;
        }

        long totalLivros = livros.size();
        System.out.println("\n--- Estatísticas dos Livros ---");
        System.out.println("Total de livros registrados: " + totalLivros);

        OptionalDouble mediaDownloads = livros.stream()
                .mapToDouble(Livro::getNumeroDownloads)
                .average();

        if (mediaDownloads.isPresent()) {
            System.out.printf("Média de downloads: %.2f\n", mediaDownloads.getAsDouble());
        } else {
            System.out.println("Não foi possível calcular a média de downloads (dados insuficientes).");
        }

        Optional<Livro> livroMostDownloaded = livros.stream()
                .max(Comparator.comparing(Livro::getNumeroDownloads));

        if (livroMostDownloaded.isPresent()) {
            System.out.println("Livro com mais downloads: " + livroMostDownloaded.get().getTitulo() +
                    " (" + livroMostDownloaded.get().getNumeroDownloads() + " downloads)");
        } else {
            System.out.println("Não foi possível identificar o livro com mais downloads.");
        }

        System.out.println("--------------------------------\n");
    }

    private void gerarTop10LivrosMaisBaixados() {
        List<Livro> top10Livros = livroRepository.findTop10ByOrderByNumeroDownloadsDesc();

        if (top10Livros.isEmpty()) {
            System.out.println("\nNão há livros registrados ou downloads suficientes para gerar um Top 10.");
            return;
        }

        System.out.println("\n--- Top 10 Livros Mais Baixados ---");
        for (int i = 0; i < top10Livros.size(); i++) {
            Livro livro = top10Livros.get(i);
            System.out.printf("%d. Título: %s, Downloads: %.0f\n", (i + 1), livro.getTitulo(), livro.getNumeroDownloads());
        }
        System.out.println("-----------------------------------\n");
    }

    private void buscarAutorPeloNome() {
        System.out.println("Digite o nome do autor que deseja buscar:");
        String nomeAutor = teclado.nextLine();

        Autor autorEncontrado = autorRepository.findByNomeContainsIgnoreCase(nomeAutor);

        if (autorEncontrado != null) {
            System.out.println("\n--- AUTOR ENCONTRADO ---");
            System.out.println(autorEncontrado);
            System.out.println("--------------------------\n");
        } else {
            System.out.println("\nAutor '" + nomeAutor + "' não encontrado no banco de dados.");
        }
    }

    private void listarAutoresEmOrdemAlfabetica() {
        List<Autor> autoresOrdenados = autorRepository.findByOrderByNomeAsc();

        if (autoresOrdenados.isEmpty()) {
            System.out.println("\nNenhum autor registrado ainda no banco de dados para listar em ordem alfabética.");
        } else {
            System.out.println("\n--- Autores em Ordem Alfabética ---");
            autoresOrdenados.forEach(autor -> {
                System.out.println("Nome: " + autor.getNome() +
                        ", Ano de Nascimento: " + (autor.getAnoNascimento() != null ? autor.getAnoNascimento() : "N/A") +
                        ", Ano de Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "N/A") +
                        ", Livros: [" + autor.getLivros().stream().map(Livro::getTitulo).collect(Collectors.joining(", ")) + "]");
            });
            System.out.println("-------------------------------------\n");
        }
    }
}