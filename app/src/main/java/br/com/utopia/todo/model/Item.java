package br.com.utopia.todo.model;

/**
 * Created by Kleber on 24/09/2016.
 */

public class Item {
    private Long id;
    private String descricao;
    private boolean feito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Item novo = new Item();
        novo.id = (long)id;
        novo.feito = feito;
        novo.descricao = new String(descricao.getBytes());

        return novo;
    }
}
