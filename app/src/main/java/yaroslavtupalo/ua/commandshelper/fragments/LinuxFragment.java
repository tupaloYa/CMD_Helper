package yaroslavtupalo.ua.commandshelper.fragments;

/**
 * Created by Yaroslav on 02/29/2016.
 */
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import yaroslavtupalo.ua.commandshelper.R;

public class LinuxFragment extends Fragment {

    private ArrayAdapter<?> adapterSpinner;

    public LinuxFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_linux, container, false);

        final Spinner mySpinner = (Spinner)rootView.findViewById(R.id.linuxSpinner);
        // Настраиваем адаптер
        adapterSpinner = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.linuxList,
                android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Вызываем адаптер
        mySpinner.setAdapter(adapterSpinner);

        return rootView;
    }
}
