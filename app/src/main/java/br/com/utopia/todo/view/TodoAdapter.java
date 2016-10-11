package br.com.utopia.todo.view;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.utopia.todo.R;
import br.com.utopia.todo.model.Todo;
import br.com.utopia.todo.model.TodoDao;

/**
 * Created by Kleber on 24/09/2016.
 */

public class TodoAdapter extends BaseAdapter implements View.OnClickListener {
    private static TodoDao dao = TodoDao.instancia;
    private SparseArray<Long> mapa;
    private boolean feito;
    private Activity activity;

    public TodoAdapter(Activity activity, boolean feito){
        this.feito = feito;
        this.activity = activity;
        criarMapa();
    }

    public void criarMapa(){
        mapa = new SparseArray<>();
        int id = 0;

        for(Todo todo : dao.listar(feito)){
            mapa.put(id++, todo.getId());
        }
    }

    @Override
    public void notifyDataSetChanged() {
        criarMapa();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.localizar(id);
    }

    @Override
    public long getItemId(int linha) {
        return mapa.get(linha);
    }

    @Override
    public View getView(int linha, View detalhe, ViewGroup parent) {
        ConstraintLayout layout;

        if(detalhe == null){
            // Construit o layout
            Context ctx = parent.getContext();
            layout = new ConstraintLayout(ctx);
            LayoutInflater service = (LayoutInflater)ctx.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            service.inflate(R.layout.adapter_todo, layout, true);


        }else{
            // Obter layout

            layout = (ConstraintLayout)detalhe;
        }

        // Atribuir os valores nos campos do Layout
        Todo todo = dao.localizar(mapa.get(linha));
        TextView tvTitulo = (TextView)layout.findViewById(R.id.tvTitulo);
        tvTitulo.setText(todo.getTitulo());

        TextView tvDel = (TextView)layout.findViewById(R.id.tvDel);
        tvDel.setOnClickListener(this);
        tvDel.setTag(todo.getId());


        return layout;
    }

    @Override
    public void onClick(View v) {
        long id = (long)v.getTag();
        Alerta alerta = new Alerta();
        alerta.setId(id);
        alerta.setAdapter(this);
        alerta.setDao(dao);
        alerta.show(activity.getFragmentManager(), "Alerta");
    }
}