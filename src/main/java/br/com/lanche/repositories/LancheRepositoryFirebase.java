package br.com.lanche.repositories;

import br.com.lanche.interfaces.LancheRepository;
import br.com.lanche.models.Lanche;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LancheRepositoryFirebase implements LancheRepository {
    private List<Lanche> lanches = new ArrayList<>();

    public LancheRepositoryFirebase() {
    }

    public List<Lanche> buscarTodos() {
        return this.lanches;
    }

    public Lanche buscarPorId(int id) {
        Optional<Lanche> lanche = this.lanches.stream()
                .filter(l -> l.getId() == id)
                .findFirst();

        return lanche.orElse(null);
    }

    public void adicionar(Lanche lanche) {
        this.lanches.add(lanche);
    }

    public void excluir(int id) {
        Lanche lancheExcluir = buscarPorId(id);

        if (lancheExcluir != null) {
            File imagem = new File(lancheExcluir.getCaminhoImagem());

            if (imagem.exists()) {
                if (imagem.delete()) {
                    System.out.println("Imagem excluída com sucesso. " + imagem.getName());
                } else {
                    System.out.println("Erro ao excluir a imagem. " + imagem.getName());
                }
            } else {
                System.out.println("Erro ao tentar localizar imagem.");
            }

            this.lanches.remove(lancheExcluir);
            System.out.println("Lanche com ID " + id + " foi excluído com sucesso.");

        } else {
            System.out.println("Lanche com ID " + id + " não encontrado.");
        }
    }

    public void atualizar(int id, Lanche lancheAtualizado) {
        Lanche lancheExistente = buscarPorId(id);

        if (lancheExistente != null) {
            if (!lancheExistente.getCaminhoImagem().equals(lancheAtualizado.getCaminhoImagem())) {
                File imagemAnterior = new File(lancheExistente.getCaminhoImagem());
                if (imagemAnterior.exists()) {
                    if (imagemAnterior.delete()) {
                        System.out.println("Imagem antiga excluída com sucesso.");
                    } else {
                        System.out.println("Não foi possível excluir a imagem antiga.");
                    }
                }
            }

            lancheExistente.setNome(lancheAtualizado.getNome());
            lancheExistente.setPreco(lancheAtualizado.getPreco());
            lancheExistente.setCaminhoImagem(lancheAtualizado.getCaminhoImagem());

            System.out.println("Lanche com ID " + id + " atualizado com sucesso.");
        } else {
            System.out.println("Lanche com ID " + id + " não pôde ser encontrado.");
        }
    }
}
