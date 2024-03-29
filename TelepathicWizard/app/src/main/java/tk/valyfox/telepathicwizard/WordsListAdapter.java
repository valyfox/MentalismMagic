package tk.valyfox.telepathicwizard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tk.valyfox.telepathicwizard.word.Element;

public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.WordViewHolder> {
    private List<Element> mElements;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);

    }

    final private ListItemClickListener mOnClickListener;

    public WordsListAdapter(List<Element> elements, ListItemClickListener listener) {
        mElements = elements;
        mOnClickListener = listener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewItem) {
        Context context = viewGroup.getContext();
        int layoutIdForWordItem = R.layout.word_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForWordItem, viewGroup, shouldAttachToParentImmediately);
        WordViewHolder viewHolder = new WordViewHolder(view);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int position) {
        wordViewHolder.bind(mElements.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView itemTextView;

        public WordViewHolder(View v) {
            super(v);
            itemTextView = (TextView) itemView.findViewById(R.id.tv_item_word);
            itemView.setOnClickListener(this);
        }

        void bind(String text) {
            itemTextView.setText(text);
        }

       @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
           mOnClickListener.onListItemClick(clickedPosition);
       }
    }
}
