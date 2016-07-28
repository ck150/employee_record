package pstc.employeemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class EmployeeDetailActivity extends AppCompatActivity {

    private TextView textName;
    private TextView textDoj;
    private TextView textPay;
    private RatingBar ratingBar;
    private Employee e;
    private String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        textName = (TextView) findViewById(R.id.emp_name_detail);
        textDoj = (TextView) findViewById(R.id.emp_doj_detail);
        textPay = (TextView) findViewById(R.id.emp_pay_detail);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar_detail);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        setDetails();
    }

    private void setDetails(){
        Intent i = getIntent();
        _id = i.getStringExtra(Constants.GET_INTENT_ID);
        e = new TableControllerDB(this).readOne(_id);
        textName.setText("Name: " + e.name);
        textDoj.setText("Date of joining: "+e.doj);
        textPay.setText("Pay: " + Integer.toString(e.getPerformance().pay_package));
        ratingBar.setRating(e.getPerformance().performance_rating);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_detail, menu);
        return true;
    }

    public void showAlertDelete(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                switch(arg1){
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean deleteSuccessful = new TableControllerDB(EmployeeDetailActivity.this).delete(e.id);
                        if (deleteSuccessful){
                            Toast.makeText(EmployeeDetailActivity.this, "Record successfully deleted.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(EmployeeDetailActivity.this, "Unable to delete record.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this record?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            NavUtils.navigateUpFromSameTask(this);
            finish();
            return true;
        }else if (id == R.id.action_edit) {
            Intent i = new Intent(this,AddEmployee.class);
            i.setAction(Constants.ACTION_EDIT_EMP);
            i.putExtra(Constants.GET_INTENT_ID, e.id);
            startActivity(i);
        }else if (id == R.id.action_delete) {
            showAlertDelete();
        }

        return super.onOptionsItemSelected(item);
    }
}
