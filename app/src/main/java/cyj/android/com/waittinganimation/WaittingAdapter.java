package cyj.android.com.waittinganimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chengyijun on 16/12/26.
 */

public class WaittingAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public WaittingAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.item_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(mList.get(position));
        return view;
    }

    class ViewHolder {
        TextView textView;
    }
}
