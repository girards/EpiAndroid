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
public class MessageAdapter extends ArrayAdapter<Message> {

    MessageViewHolder _viewHolder;
    
    public MessageAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView == null){
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_message, parent, false);
       }
        _viewHolder = (MessageViewHolder) convertView.getTag();
        if (_viewHolder == null) {
            _viewHolder = new MessageViewHolder();
            _viewHolder.user =(TextView) convertView.findViewById(R.id.user);
            _viewHolder.title =(TextView) convertView.findViewById(R.id.title);
            _viewHolder.avatar =(ImageView) convertView.findViewById(R.id.avatar);
        }

        Message message = getItem(position);

        _viewHolder.user.setText(message.getUser().getTitle());
        _viewHolder.title.setText(Html.fromHtml(message.getTitle()));
       if (message.getUser().getPictureUrl() == null) {
            Log.d("Picture", "NULL");
            _viewHolder.avatar.setImageDrawable(ApplicationExt.getContext().getResources().getDrawable(R.mipmap.ic_launcher));
        }
        else
       {
           RequestManager.getInstance().getPhotoFromUrl(message.getUser().getPictureUrl(), new APIListener<Bitmap>() {
               @Override
               public void getResult(Bitmap object) {
                   _viewHolder.avatar.setImageBitmap(object);
               }
           });
       }

        return convertView;
    }

    private class MessageViewHolder{
        public TextView user;
        public TextView title;
        public ImageView avatar;
    }
}
