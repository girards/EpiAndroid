package epitech.epiandroid;

/**
 * Created by LouisAudibert on 26/01/2016.
 */

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

import org.w3c.dom.Text;

import java.util.List;

public class GradeAdapter extends ArrayAdapter<Grade> {

    GradeViewHolder _viewHolder;

    public GradeAdapter(Context context, List<Grade> grades) {
        super(context, 0, grades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grade, parent, false);
        }
        _viewHolder = (GradeViewHolder) convertView.getTag();
        if (_viewHolder == null) {
            _viewHolder = new GradeViewHolder();
            _viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
        }

        Grade grade = getItem(position);

        _viewHolder.comment.setText(grade.getComment());
        return convertView;
    }

    private class GradeViewHolder{
    /*
        public TextView _scolaryear;
        public TextView _titleModule;
        public TextView _title;
        public TextView _correcteur;
        public TextView _date;
        public TextView _note;
    */
        public TextView comment;
    }
}
