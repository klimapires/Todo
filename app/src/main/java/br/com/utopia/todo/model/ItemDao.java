package br.com.utopia.todo.model;

import java.util.List;

/**
 * Created by Kleber on 24/09/2016.
 */

public class ItemDao {
    private static ItemDao instace;
    private List<Item> lista;
    private static long id = 0;

    public ItemDao(List<Item> lista) {
        this.lista = lista;
    }

    public static ItemDao getInstace(List<Item> lista){
        ItemDao dao = new ItemDao(lista);
        ItemDao.instace = dao;
        return dao;
    }

    public static ItemDao getInstace(){
        return instace;
    }

    public void salvar(Item item){
        if(item.getId() == null){
            // Inclusão
            item.setId(id++);
            lista.add(item);
        }else{
            Item obj = localizar(item.getId());
            obj.setDescricao(item.getDescricao());
            obj.setFeito(item.isFeito());
        }
    }

    private Item localizar(long id) {
        Item item = null;

        for(Item obj : lista){
            if(obj.getId() == id){
                item = obj;
                break;
            }
        }
        return item;
    }

    public List<Item> listar(){
        return lista;
    }

    public void remover(long id){
        Item item = localizar(id);

        if(item != null)
            lista.remove(item);
    }

}
