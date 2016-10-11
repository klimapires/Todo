package br.com.utopia.todo.view;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import br.com.utopia.todo.R;
import br.com.utopia.todo.model.Item;
import br.com.utopia.todo.model.ItemDao;

/**
 * Created by Kleber on 11/10/2016.
 */

public class ItemAdapter extends BaseAdapter implements View.OnClickListener {
    private ItemDao dao;
    private SparseArray<Long> mapa;
    private Activity parent;

    public ItemAdapter(Activity activity, ItemDao dao) {
        this.parent = activity;
        this.dao = dao;

        criaMapa();
    }

    @Override
    public void notifyDataSetChanged() {
        criaMapa();
        super.notifyDataSetChanged();
    }

    private void criaMapa() {
        mapa = new SparseArray();
        int id = 0;

        for (Item obj : dao.listar()) {
            mapa.put(id++, obj.getId());
        }
    }

    @Override
    public int getCount() {
        return mapa.size();
    }

    @Override
    public Object getItem(int id) {
        return dao.localizar(id);
    }

    @Override
    public long getItemId(int lin) {
        return mapa.get(lin);
    }

    @Override
    public View getView(int lin, View viewObj, ViewGroup parent) {
        ConstraintLayout layout;

        if (viewObj == null) {
            Context ctx = parent.getContext();
            layout = new ConstraintLayout(ctx);
            LayoutInflater service = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            service.inflate(R.layout.adapter_item, layout, true);
        } else {
            layout = (ConstraintLayout) viewObj;
        }

        Item obj = dao.localizar(mapa.get(lin));
        CheckBox checkBox = (CheckBox) layout.findViewById(R.id.ckDel);
        checkBox.setChecked(obj.isFeito());
        checkBox.setOnClickListener(this);
        checkBox.setTag(obj.getId());

        TextView tvItem = (TextView) layout.findViewById(R.id.tvTitulo);
        tvItem.setText(obj.getDescricao().toString());

        TextView tvDel = (TextView) layout.findViewById(R.id.tvDel);
        tvDel.setOnClickListener(this);
        tvDel.setTag(obj.getId());

        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CheckBox) {
            // marca como feito
            long id = (long) view.getTag();
            Item obj = dao.localizar(id);
            obj.setFeito(!obj.isFeito());

            dao.salvar(obj);
        } else if (view instanceof TextView) {
            // remove o item
            Alerta alerta = new Alerta();
            alerta.setId((long) view.getTag());
            alerta.setAdapter(this);
            alerta.setDao(dao);
            alerta.show(parent.getFragmentManager(), "Alerta");
        }
    }
}
