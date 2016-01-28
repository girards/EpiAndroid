package epitech.epiandroid;

/**
 * Created by LouisAudibert on 26/01/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grade, parent, false);
        }
        _viewHolder = (GradeViewHolder) convertView.getTag();
        if (_viewHolder == null) {
            _viewHolder = new GradeViewHolder();
            _viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            _viewHolder.note = (TextView) convertView.findViewById(R.id.note);
            _viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
        }

        Grade grade = getItem(position);
        String codeModule = grade.getCodemodule();

        _viewHolder.note.setText(grade.getNote());
        if (String.format("#%X", codeModule.hashCode()).length() != 9)
            ;
        else
            _viewHolder.note.setBackgroundColor(Color.parseColor(String.format("#%X", codeModule.hashCode())));
        _viewHolder.title.setText(grade.getTitle());
        _viewHolder.comment.setText(grade.getComment());
        return convertView;
    }

    private class GradeViewHolder{
        public TextView scolaryear;
        public TextView titleModule;
        public TextView title;
        public TextView correcteur;
        public TextView date;
        public TextView note;
        public TextView comment;
    }
}
