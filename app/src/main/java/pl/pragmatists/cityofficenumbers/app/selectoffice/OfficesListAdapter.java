package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectoffice.messages.CityOfficesFetchedEvent;

import java.util.ArrayList;
import java.util.Collection;

public class OfficesListAdapter extends ArrayAdapter<Office> {

    private final LayoutInflater layoutInflater;

    private Collection<? extends Office> offices;

    public OfficesListAdapter(Context context) {
        super(context, -1);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.office_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.office_item_name);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.star_image_button);
        Office office = getItem(position);
        // TODO: Set the correct star image
        imageButton.setTag(office);
        textView.setText(office.toString());
        view.setTag(office);
        return view;
    }

    public void onEventMainThread(CityOfficesFetchedEvent cityOfficesFetchedEvent) {
        offices = cityOfficesFetchedEvent.offices();
        clear();
        addAll(offices);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    result.values = offices;
                    result.count = offices.size();
                }
                else {
                    Collection<Office> filtered = new ArrayList<>();
                    for (Office office : offices) {
                        if (office.favorite) {
                            filtered.add(office);
                        }
                    }
                    result.values = filtered;
                    result.count = filtered.size();
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((Collection<? extends Office>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
