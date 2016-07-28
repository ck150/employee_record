package pstc.employeemanagement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class AddEmployee extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText name_edit;
    private EditText pay_edit;
    private DatePicker datePicker;
    private String INTENT_ACTION;
    private Employee e_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar_id);
        name_edit = (EditText) findViewById(R.id.name_edit);
        pay_edit = (EditText) findViewById(R.id.pay_edit);
        datePicker = (DatePicker) findViewById(R.id.dob_edit);
        INTENT_ACTION = getIntent().getAction();
        if(INTENT_ACTION.equals(Constants.ACTION_ADD_EMP)){
            getSupportActionBar().setTitle("Add Employee");
            ratingBar.setVisibility(View.GONE);

        }else if(INTENT_ACTION.equals(Constants.ACTION_EDIT_EMP)){
            getSupportActionBar().setTitle("Edit Employee");
            datePicker.setVisibility(View.GONE);
            String _id = Integer.toString(getIntent().getIntExtra(Constants.GET_INTENT_ID, 0));
            e_old = new TableControllerDB(this).readOne(_id);
            Log.v("tag1","tag1 "+_id);
            ratingBar.setRating(e_old.getPerformance().performance_rating);
            name_edit.setText(e_old.name);
            pay_edit.setText(Integer.toString(e_old.getPerformance().pay_package));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            if(INTENT_ACTION.equals(Constants.ACTION_ADD_EMP)){
                String dob_e = Constants.MONTHS[datePicker.getMonth()] + " " + datePicker.getDayOfMonth()+ ", " +datePicker.getYear();
                boolean success = false;
                try {
                    Employee e = new Employee(name_edit.getText().toString(), dob_e, Integer.parseInt(pay_edit.getText().toString()));
                    success = new TableControllerDB(this).create(e);
                    if(success){
                        Toast.makeText(this, "Employee record added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.putExtra(Constants.NAME_RESULT,e.name);
                        i.putExtra(Constants.DOJ_RESULT,e.doj);
                        i.putExtra(Constants.STAR_RESULT,e.getPerformance().performance_rating);
                        i.putExtra(Constants.PAY_RESULT,e.getPerformance().pay_package);
                        setResult(Constants.REQUEST_CODE_ADD, i);
                        finish();

                    }else{
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                }catch (NumberFormatException n){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    return false;
                }
            return true;
            }else if(INTENT_ACTION.equals(Constants.ACTION_EDIT_EMP)){
                try{
                    Employee e = new Employee(name_edit.getText().toString(), e_old.doj, Integer.parseInt(pay_edit.getText().toString()));
                    e.id = e_old.id;
                    e.setPerformance((int)ratingBar.getRating(),Integer.parseInt(pay_edit.getText().toString()));
                    boolean success = new TableControllerDB(this).update(e);

                    if(success){
                        Toast.makeText(this, "Employee record updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this,MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }


                }catch(NumberFormatException n){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
