package com.example.abc12.navigationview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by abc12 on 2017/12/19.
 */

public class AccountFragment extends Fragment {
    private String[] options = {"User Manage","Orders","Edit Info","Log out"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment,container,false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,options);
        ListView listView = (ListView)view.findViewById(R.id.account_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String result = options[i];
                switch (result){
                    case "User Manage":
                        Log.d("Account: ","open user manage");
                        break;
                    case "Orders":
                        Intent OpenOrder = new Intent(getContext(),Orders.class);
                        getActivity().startActivity(OpenOrder);
                        break;
                    case "Log out":
                        Intent intent = new Intent(getContext(),TestActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case "Edit Info":
                        Intent OpenEdit = new Intent(getContext(),User_Edit.class);
                        getActivity().startActivity(OpenEdit);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}
