package es.ulpgc.IST.infosierrapp.maestrodetalle;

import es.ulpgc.IST.infosierrapp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Presenta los items obtenidos en la busqueda
 * @author jesus
 *
 */
//TODO no crear un ListModel
public class ListPresenter extends Activity{
	private static final int REQUEST_CODE = 0;
	private ListView list;
	private ListModel model;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView(R.layout.vista_v_maestro);
		
		list = (ListView) findViewById (R.id.listView);
		model = new ListModel();
		
		ListAdapter adapter = new ListAdapter (this, model.getData());
		list.setAdapter(adapter);
		
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int pos, long id){
                startActivity((ItemModel) a.getAdapter().getItem(pos));
            }
        });
	}
	
    private void startActivity(ItemModel itemModel) {
        Intent intent = new Intent(ListPresenter.this, ItemPresenter.class);
        intent.putExtra(Intent.ACTION_EDIT, itemModel);
        startActivityForResult(intent, REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent intent) {
        if (resCode == RESULT_OK && reqCode == REQUEST_CODE) {

            if (intent.hasExtra(Intent.ACTION_DEFAULT)) {

            }
            if (intent.hasExtra(Intent.ACTION_EDIT)) {
                ItemModel itemModel = (ItemModel)intent.getSerializableExtra(
                        Intent.ACTION_EDIT);
                model.setData(Integer.parseInt(itemModel.getPos()), itemModel);
            }
            if (intent.hasExtra(Intent.ACTION_DELETE)) {
                ItemModel itemModel = (ItemModel)intent.getSerializableExtra(
                        Intent.ACTION_DELETE);
                model.delData(Integer.parseInt(itemModel.getPos()));

                ListAdapter adapter = new ListAdapter(this, model.getData());
                list.setAdapter(adapter);
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.master_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new:
                startActivity(new ItemModel());
                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
