package pstc.employeemanagement;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chandrakant on 28-07-2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Employee> employeeList;
    private LayoutInflater inflater;

    public CustomListAdapter(Activity activity, List<Employee> employees) {
        this.activity = activity;
        this.employeeList = employees;
    }


    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listview_item, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.employee_name_text);
        TextView dobText = (TextView) convertView.findViewById(R.id.employee_dob_text);
        Employee e = employeeList.get(position);
        nameText.setText(e.name);
        dobText.setText("Date of joining: "+e.doj);

        return convertView;
    }
}
