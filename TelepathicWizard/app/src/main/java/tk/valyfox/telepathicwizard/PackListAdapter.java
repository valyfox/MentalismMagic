package tk.valyfox.telepathicwizard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tk.valyfox.telepathicwizard.pack.Pack;

public class PackListAdapter extends RecyclerView.Adapter<PackListAdapter.PackViewHolder>  {

    private List<Pack> mElements;
    Context context;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);

    }

    final private ListItemClickListener mOnClickListener;

    public PackListAdapter(List<Pack> elements, ListItemClickListener listener, Context c) {
        mElements = elements;
        mOnClickListener = listener;
        context = c;
    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewItem) {
        Context context = viewGroup.getContext();
        int layoutIdForWordItem = R.layout.pack_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForWordItem, viewGroup, false);
        PackViewHolder viewHolder = new PackViewHolder(view);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PackViewHolder packViewHolder, int position) {
        Pack elem = mElements.get(position);
            packViewHolder.bind(elem.name, elem.locked);

    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class PackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView itemTextView;

        public PackViewHolder(View v) {
            super(v);
            itemTextView = itemView.findViewById(R.id.tv_item_pack);
            itemView.setOnClickListener(this);
        }

        void bind(String text, boolean locked) {
            String unlockText = context.getString(R.string.unlock);
            if(locked) {
                itemTextView.setText(unlockText + " " +text);
                itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            } else {
                itemTextView.setText(text);
                itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 42);
            }



        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
