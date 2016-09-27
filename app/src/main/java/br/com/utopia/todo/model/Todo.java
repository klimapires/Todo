package br.com.utopia.todo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kleber on 17/09/2016.
 */

public class Todo {
    Long id;
    String titulo;
    private List<Item> itens = new ArrayList<>();

    public static final boolean FEITO = true;
    public static final boolean A_FAZER = false;

    public Todo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isFeito() {
        boolean feito = true;

        for (Item item : itens){
            if(!item.isFeito()){
                feito = false;
                break;
            }
        }
        return feito && itens.size() > 0;
    }

    public List<Item> getItens() {
        List<Item> novaLista = null;

        try{
            novaLista = new ArrayList<>();
            for (Item item : itens){
                novaLista.add((Item)item.clone());
            }
        }catch (CloneNotSupportedException ex){
            novaLista = itens;
        }
        return novaLista;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
