package epitech.epiandroid;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by girard_s on 25/01/2016 for Epitech.
 */
public class ModuleOverviewAdapter extends ArrayAdapter<ModuleOverview> {

    ModuleOverviewHolder _viewHolder;

    public ModuleOverviewAdapter(Context context, List<ModuleOverview> modules) {
        super(context, 0, modules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_module, parent, false);
        }
        _viewHolder = (ModuleOverviewHolder) convertView.getTag();
        if (_viewHolder == null) {
            _viewHolder = new ModuleOverviewHolder();
            _viewHolder.module =(TextView) convertView.findViewById(R.id.module);
            _viewHolder.semester =(TextView) convertView.findViewById(R.id.semester);
            _viewHolder.credit =(TextView) convertView.findViewById(R.id.moduleCredit);
            _viewHolder.registered =(ImageView) convertView.findViewById(R.id.registered);
        }

        ModuleOverview module = getItem(position);


        _viewHolder.module.setText(module.getTitle());
        if (String.format("#%X", module.getCodeModule().hashCode()).length() != 9)
            ;
        else
            _viewHolder.credit.setBackgroundColor(Color.parseColor(String.format("#%X", module.getCodeModule().hashCode())));
        _viewHolder.credit.setText("" + module.getCredits());
        _viewHolder.semester.setText(("Semester " + module.getSemester()));
        return convertView;
    }

    private class ModuleOverviewHolder{
        public TextView semester;
        public TextView module;
        public TextView credit;
        public ImageView registered;
    }
}
