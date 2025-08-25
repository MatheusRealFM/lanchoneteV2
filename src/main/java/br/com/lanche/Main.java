package br.com.lanche;

import br.com.lanche.applications.LancheApplication;
import br.com.lanche.facades.LancheFacade;
import br.com.lanche.models.Lanche;
import br.com.lanche.repositories.LancheRepository;
import br.com.lanche.services.LancheService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static LancheRepository lancheRepository;
    private static LancheService lancheService;
    private static LancheApplication lancheApplication;
    private static LancheFacade lancheFacade;
    private static Scanner scanner;

    public static void injetarDependencias() {
        lancheRepository = new LancheRepository();
        lancheService = new LancheService();
        lancheApplication = new LancheApplication(lancheRepository, lancheService);
        lancheFacade = new LancheFacade(lancheApplication);
        scanner = new Scanner(System.in);
    }

    public static void exibirMenu() {
        System.out.println("1 - Listar Produtos");
        System.out.println("2 - Cadastrar Produto");
        System.out.println("3 - Editar Produto");
        System.out.println("4 - Excluir Produto");
        System.out.println("5 - Vender");
        System.out.println("0 - Sair do sistema");
    }

    public static int solicitaOpcaoMenu() {
        System.out.println("Informe a opção escoliha: ");
        return scanner.nextInt();
    }

    public static void listarLanches() throws IOException {
        System.out.println("Lista de Produtos:\n(ID -- Nome -- Preço)\n");
        lancheFacade.buscarTodos().forEach(l -> {
            System.out.println(l);
        });
    }

    public static void cadastrarLanche() throws IOException {
        System.out.println("ID do produto: ");
        int id = scanner.nextInt();

        System.out.println("Nome do produto: ");
        scanner.nextLine();
        String nome = scanner.nextLine();

        System.out.println("Valor do produto: ");
        double preco = scanner.nextFloat();

        String caminhoImagem;

        do {
            System.out.print("Digite o caminho completo da imagem (ex: C:\\pasta\\hamburguer.jpg): ");
            scanner.nextLine();
            caminhoImagem = scanner.nextLine();

            if (!new File(caminhoImagem).exists()) {
                System.out.println("Arquivo não encontrado! Digite novamente.");
            }
        } while (!new File(caminhoImagem).exists());

        Lanche lanche = new Lanche(id, nome, preco, caminhoImagem);
        lancheFacade.adicionar(lanche);
    }

    public static void iniciarSistema() throws IOException {
        int opcaoMenu = -1;

        do {
            exibirMenu();

            opcaoMenu = solicitaOpcaoMenu();

            switch (opcaoMenu) {
                case 1:
                    listarLanches();
                    break;
                case 2:
                    cadastrarLanche();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:
                    break;
            }
        } while (opcaoMenu != 0);
    }

    public static void main(String[] args) throws IOException {
        injetarDependencias();
        iniciarSistema();
    }
}