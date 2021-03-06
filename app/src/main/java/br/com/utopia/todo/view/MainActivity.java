package br.com.utopia.todo.view;

/**
 * Created by Kleber on 17/09/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import br.com.utopia.todo.R;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private TabHost tabHost;

    private TodoAdapter adapterConcluido;
    private TodoAdapter adapterAFazer;
    public static final int EDITAR_ITEM = 0;
    public static final int INCLUIR_ITEM= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("afazer");
        spec.setContent(R.id.afazer_layout);
        spec.setIndicator("A Fazer");
        tabHost.addTab(spec);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("concluido");
        spec2.setContent(R.id.concluido_layout);
        spec2.setIndicator("Concluído");
        tabHost.addTab(spec2);

        tabHost.setOnTabChangedListener(this);

        ListView listaAfazer = (ListView) findViewById(R.id.listaAFazer);
        adapterAFazer = new TodoAdapter(this, false);
        listaAfazer.setOnItemClickListener(this);
        listaAfazer.setAdapter(adapterAFazer);

        ListView listaConcluido = (ListView) findViewById(R.id.listaConcluido);
        adapterConcluido = new TodoAdapter(this, true);
        listaConcluido.setOnItemClickListener(this);
        listaConcluido.setAdapter(adapterConcluido);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("afazer")) {
            tabHost.setCurrentTab(0);
        } else {
            tabHost.setCurrentTab(1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), DetalheTodo.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, EDITAR_ITEM);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), DetalheTodo.class);
        startActivityForResult(intent, INCLUIR_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            adapterAFazer.notifyDataSetChanged();

            if (resultCode == EDITAR_ITEM) {
                adapterConcluido.notifyDataSetChanged();
            }

        }
    }


}
