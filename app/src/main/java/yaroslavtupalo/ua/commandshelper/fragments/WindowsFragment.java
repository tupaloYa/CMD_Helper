package yaroslavtupalo.ua.commandshelper.fragments;

/**
 * Created by Yaroslav on 02/29/2016.
 */
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yaroslavtupalo.ua.commandshelper.R;
import yaroslavtupalo.ua.commandshelper.open_helper.DatabaseHelper;


public class WindowsFragment extends Fragment {

   private DatabaseHelper sqlHelper;
   private Cursor userCursor;

   private ListView mList;
   private SimpleCursorAdapter userAdapter;

   private EditText windowsFilter;

    public WindowsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_windows, container, false);

        mList = (ListView)rootView.findViewById(R.id.listWindows);
        windowsFilter = (EditText)rootView.findViewById(R.id.windowsFilter);

        sqlHelper = new DatabaseHelper(getActivity().getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        try{
            sqlHelper.open();
            userCursor = sqlHelper.database.rawQuery("select * from " + DatabaseHelper.TABLE, null);

            String[] headers = new String[]{DatabaseHelper.COLUMN_COMMANDS_NAME, DatabaseHelper.COLUMN_COMMANDS_DESCRIPTION};
            userAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                    R.layout.view,
                    userCursor, headers,
                    new int[]{R.id.commandsName, R.id.commandsDescriptions}, 0);

            // если в текстовом поле есть текст, выполняем фильтрацию
            // данная проверка нужна при переходе от одной ориентации экрана к другой
            if(!windowsFilter.getText().toString().isEmpty())
                userAdapter.getFilter().filter(windowsFilter.getText().toString());

            // установка слушателя изменения текста
            windowsFilter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    userAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            // устанавливаем провайдер фильтрации
            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    if(constraint == null || constraint.length() == 0){
                        return sqlHelper.database.rawQuery("select * from " + DatabaseHelper.TABLE, null);
                    }else{
                        return sqlHelper.database.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                                DatabaseHelper.COLUMN_COMMANDS_NAME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });

            mList.setAdapter(userAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
        sqlHelper.database.close();
        userCursor.close();
    }
}
