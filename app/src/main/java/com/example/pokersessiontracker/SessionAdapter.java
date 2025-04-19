// Steven Gaona, CIS165DA 20747, STE2342585 //

package com.example.pokersessiontracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class SessionAdapter extends ArrayAdapter<SessionItem> {
    private final PokerSessionDatabase db;
    private final ArrayList<SessionItem> sessionList;

    public SessionAdapter(Context context, ArrayList<SessionItem> sessions, PokerSessionDatabase dbHelper) {
        super(context, 0, sessions);
        this.sessionList = sessions;
        this.db = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SessionItem session = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.session_list_item, parent, false);
        }

        TextView sessionText = convertView.findViewById(R.id.sessionDetailsText);
        Button deleteBtn = convertView.findViewById(R.id.deleteButton);

        sessionText.setText(session.details);

        deleteBtn.setOnClickListener(v -> {
            db.deleteSessionById(session.id);
            sessionList.remove(position);
            notifyDataSetChanged();
        });
        return convertView;
    }
}
