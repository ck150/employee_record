package pstc.employeemanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Employee> employeesList;
    private CustomListAdapter customListAdapter;
    private ListView listView;
    private int pageNo;
    private static final int pageLen = 10;
    private TextView textPageNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Employees Record");
        listView = (ListView) findViewById(R.id.employees_list_id);
        textPageNo = (TextView) findViewById(R.id.page_no_text);
        pageNo = 0;
    }

    @Override
    public void onResume(){
        super.onResume();
        textPageNo.setText("Page " + (pageNo + 1));
        readRecords(pageNo * pageLen, pageLen);
        customListAdapter = new CustomListAdapter(this, employeesList);
        listView.setAdapter(customListAdapter);
        customListAdapter.notifyDataSetChanged();
        ListOnClick();
    }

    public void onPrevPressed(View v){
        if(pageNo==0){
            Toast.makeText(this,"No previous page",Toast.LENGTH_SHORT).show();
            return;
        }
        if(readRecords((pageNo-1)*pageLen,pageLen)){
            customListAdapter = new CustomListAdapter(this, employeesList);
            listView.setAdapter(customListAdapter);
            customListAdapter.notifyDataSetChanged();
            pageNo--;
            textPageNo.setText("Page " + (pageNo + 1));
        }else{
            Toast.makeText(this,"No more records found",Toast.LENGTH_SHORT).show();
        }
    }

    public void onNextPressed(View v){

        if(readRecords((pageNo+1)*pageLen,pageLen)){
            Log.v("tag1",Integer.toString(employeesList.size()));
            customListAdapter = new CustomListAdapter(this, employeesList);
            listView.setAdapter(customListAdapter);
            customListAdapter.notifyDataSetChanged();
            pageNo++;
            textPageNo.setText("Page " + (pageNo + 1));
        }else{
            employeesList = new TableControllerDB(this).read((pageNo)*pageLen,pageLen);
            customListAdapter = new CustomListAdapter(this, employeesList);
            listView.setAdapter(customListAdapter);
            customListAdapter.notifyDataSetChanged();
            Toast.makeText(this,"No more records found",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean readRecords(int start,int length){
        List<Employee> demoList = new TableControllerDB(this).read(start,length);
        if(demoList==null){
            return false;
        }else if(demoList.size()==0){
            return false;
        }else{
            if(employeesList!=null){
                employeesList.clear();
            }
            employeesList = new ArrayList<>(demoList);
            customListAdapter = new CustomListAdapter(this, employeesList);
            listView.setAdapter(customListAdapter);
            customListAdapter.notifyDataSetChanged();

            return true;
        }
    }

    public void ListOnClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _id = Integer.toString(employeesList.get(position).id);
                Intent intent = new Intent(MainActivity.this,EmployeeDetailActivity.class);
                intent.putExtra(Constants.GET_INTENT_ID,_id);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(this,AddEmployee.class);
            i.setAction(Constants.ACTION_ADD_EMP);
            startActivityForResult(i, Constants.REQUEST_CODE_ADD);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onViewCountPressed(View v){
        int c = new TableControllerDB(this).count();
        Toast.makeText(this,"No. of employee entries: "+ c,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constants.REQUEST_CODE_ADD)
            try {
                Log.v("tag1", Integer.toString(requestCode));
                String msgName = data.getStringExtra(Constants.NAME_RESULT);
                String msgDoj = data.getStringExtra(Constants.DOJ_RESULT);
                int msgStar = data.getIntExtra(Constants.STAR_RESULT, 0);
                int msgPay = data.getIntExtra(Constants.PAY_RESULT, 0);

                //Employee e = new Employee(msgName,msgDoj,msgPay);

                //customListAdapter.notifyDataSetChanged();
            } catch (NullPointerException n) {
                Log.e("tag1", "nothing returned");
            }
    }
}
