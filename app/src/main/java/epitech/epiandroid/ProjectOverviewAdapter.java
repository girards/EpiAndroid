package epitech.epiandroid;

import android.content.Context;
import android.graphics.Bitmap;
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
 * Created by girard_s on 14/01/2016 for Epitech.
 */
public class ProjectOverviewAdapter extends ArrayAdapter<ProjectOverview> {

    ProjectOverviewViewHolder _viewHolder;

    public ProjectOverviewAdapter(Context context, List<ProjectOverview> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_project_overview, parent, false);
        }
        _viewHolder = (ProjectOverviewViewHolder) convertView.getTag();
        if (_viewHolder == null) {
            _viewHolder = new ProjectOverviewViewHolder();
            _viewHolder.project =(TextView) convertView.findViewById(R.id.project);
            _viewHolder.module =(TextView) convertView.findViewById(R.id.module);
            _viewHolder.moduleLogo =(ImageView) convertView.findViewById(R.id.moduleLogo);
            _viewHolder.registered =(ImageView) convertView.findViewById(R.id.registered);
        }

        ProjectOverview project = getItem(position);


        _viewHolder.project.setText(project.getProjectName());
        _viewHolder.module.setText(Html.fromHtml(project.getModuleTitle()));
        if (project.isRegistered() == true)
            _viewHolder.registered.setImageDrawable(ApplicationExt.getContext().getResources().getDrawable(R.mipmap.tick7));
        else
            _viewHolder.registered.setImageDrawable(ApplicationExt.getContext().getResources().getDrawable(R.mipmap.do10));
        return convertView;
    }

    private class ProjectOverviewViewHolder{
        public TextView module;
        public TextView project;
        public ImageView moduleLogo;
        public ImageView registered;
    }
}
