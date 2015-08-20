package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.offices.Office;
import pl.pragmatists.cityofficenumbers.offices.messages.CityOfficesFetchedEvent;

public class OfficesListAdapter extends ArrayAdapter<Office> {

    private final LayoutInflater layoutInflater;

    public OfficesListAdapter(Context context) {
        super(context, -1);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.office_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.office_item_name);
        textView.setText(getItem(position).toString());
        view.setTag(getItem(position));
        return view;
    }

    public void onEventMainThread(CityOfficesFetchedEvent cityOfficesFetchedEvent) {
        addAll(cityOfficesFetchedEvent.offices());
    }
}
