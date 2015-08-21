package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.star_image_button);
        Office office = getItem(position);
        if (office.favorite) {
            imageButton.setImageResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        } else {
            imageButton.setImageResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
        imageButton.setTag(office);
        textView.setText(office.toString());
        view.setTag(office);
        return view;
    }

    public void onEventMainThread(CityOfficesFetchedEvent cityOfficesFetchedEvent) {
        addAll(cityOfficesFetchedEvent.offices());
    }
}
