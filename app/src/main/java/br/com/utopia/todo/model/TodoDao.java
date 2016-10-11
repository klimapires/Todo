package br.com.utopia.todo.model;

import android.util.Log;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.utopia.todo.Main;
import br.com.utopia.todo.lib.JSONParser;

/**
 * Created by Kleber on 17/09/2016.
 */

public class TodoDao implements Dao {
    public static TodoDao instancia = new TodoDao();

    private final String url = "http://192.168.1.81:8080/TodoServer/";

    private TodoDao() {

    }

    public void salvar(Todo obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(obj);

            if (obj.getId() == null) {
                // incluir
                new JSONParser.Incluir(url, json, new JSONParser.LocationAndDataCallBack() {
                    @Override
                    public void setResponse(int code, String location, String json) {
                        if (code != 201) {
                            Toast.makeText(Main.context, "Falha ao salvar o Todo", Toast.LENGTH_LONG).show();
                        }
                    }
                }).execute();
            } else {
                // atualizar
                new JSONParser.Alterar(url + "todo/" + obj.getId(), json, new JSONParser.LocationCallBack() {
                    @Override
                    public void setResponse(int code, String location) {
                        if (code != 200)
                            Toast.makeText(Main.context, "Falha em alterar o Todo", Toast.LENGTH_LONG).show();
                    }
                }).execute();
            }
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

    }

    public Todo localizar(long id) {
        try {

            String json = new JSONParser.Consultar(url + "todo/" + id, new JSONParser.DataCallBack() {
                @Override
                public void setResponse(int code, String json) {
                    if (code != 200) {
                        Toast.makeText(Main.context, "Falha em localiza o Todo", Toast.LENGTH_LONG).show();
                    }
                }
            }).execute().get();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new StringReader(json), Todo.class);

        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public List<Todo> listar(boolean feito) {
        List<Todo> listaSaida = new ArrayList<>();

        try {

            String json = new JSONParser.Consultar(url + "lista/" + (feito ? 1 : 0), new JSONParser.DataCallBack() {
                @Override
                public void setResponse(int code, String json) {
                    if (code != 200) {
                        Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
                    }

                }
            }).execute().get();
            Log.e("TESTE", "json: " + json);
            ObjectMapper mapper = new ObjectMapper();
            listaSaida = Arrays.asList(mapper.readValue(new StringReader(json), Todo[].class));


        } catch (Exception ex) {
            // ex.printStackTrace();
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

        return listaSaida;
    }

    public void remover(long id) {
        try{
            new JSONParser.Remover(url + "todo/" + id, new JSONParser.ResponseCodeCallBack() {
                @Override
                public void setResponse(int code) {
                    if(code != 204){
                        Toast.makeText(Main.context, "Falha ao excluir o Todo", Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();

        }catch (Exception ex){
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }


    }
}
