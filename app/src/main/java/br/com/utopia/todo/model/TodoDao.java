package br.com.utopia.todo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kleber on 17/09/2016.
 */

public class TodoDao {

    public static TodoDao instancia = new TodoDao();

    private List<Todo> lista;
    private long id = 0;

    private TodoDao(){
        lista = new ArrayList<>();
        salvar(new Todo("Atividade1"));
        salvar(new Todo("Atividade2"));
    }

    public void salvar (Todo obj){
        if (obj.getId() == null) {
            //incluir
            obj.setId(id++);
            lista.add(obj);
        } else{
            //atualizar
            Todo todo = localizar(obj.getId());
            //todo.setFeito(obj.isFeito());
            todo.setTitulo(obj.getTitulo());
        }

    }

    public Todo localizar (long id){
        Todo obj = null;

        for(Todo todo : lista) {
            if(todo.getId() == id){
                obj = todo;
            }
        }
        return obj;
    }

    public List<Todo> listar (boolean feito){
        List<Todo> listaSaida = new ArrayList<>();

        for (Todo todo : lista){
            if (todo.isFeito() == feito){
                listaSaida.add(todo);
            }
        }

        return listaSaida;
    }

    public void remover (long id){
        Todo todo = localizar(id);

        if (todo != null)
            lista.remove(todo);
    }
}
