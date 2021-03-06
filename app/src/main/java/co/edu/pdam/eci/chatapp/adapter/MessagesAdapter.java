package co.edu.pdam.eci.chatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.edu.pdam.eci.chatapp.Model.Message;
import co.edu.pdam.eci.chatapp.R;

/**
 * @author Santiago Carrillo
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>
{


    private final Context context;

    private List<Message> messages = new ArrayList<>();

    public MessagesAdapter( Context context )
    {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.message_row, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position )
    {
        Message message = messages.get( position );
        viewHolder.sender.setText( message.getUser() );
        viewHolder.message.setText( message.getMessage() );
        if ( message.getImageUrl() != null )
        {
            viewHolder.sender.setVisibility( View.GONE );
            viewHolder.message.setVisibility( View.GONE );
            viewHolder.imageView.setVisibility( View.VISIBLE );
            Picasso.with( context ).load( message.getImageUrl() ).into( viewHolder.imageView );
        }
        else
        {
            viewHolder.sender.setVisibility( View.VISIBLE );
            viewHolder.message.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public int getItemCount()
    {
        return messages.size();
    }

    public void addMessage( Message message )
    {
        messages.add( 0, message );
        notifyDataSetChanged();
    }

    public void removeMessage( Message message )
    {
        messages.remove( message );
        notifyDataSetChanged();
    }

    static class ViewHolder
        extends RecyclerView.ViewHolder
    {
        @Bind( R.id.sender )
        TextView sender;

        @Bind( R.id.message )
        TextView message;

        @Bind( R.id.image )
        ImageView imageView;

        ViewHolder( View view )
        {
            super( view );
            ButterKnife.bind( this, view );
        }
    }
}

