package com.dk.mensajero;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matias on 26/04/15.
 */
public class AdaptadorDePaises extends BaseExpandableListAdapter {


    private Context ctx;
    private HashMap<String, List<String>> paisesDetallados;
    private List<String> listaDePaises;

    public AdaptadorDePaises(Context ctx, HashMap<String, List<String>> paisesDetallados, List<String> listaDePaises){
        this.ctx = ctx;
        this.paisesDetallados = paisesDetallados;
        this.listaDePaises = listaDePaises;
    }


    @Override
    public int getGroupCount() {
        return listaDePaises.size();
    }

    @Override
    public int getChildrenCount(int arg0) {
        return paisesDetallados.get(listaDePaises.get(arg0)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listaDePaises.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        return paisesDetallados.get(listaDePaises.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String groupTitle = (String) getGroup(parent);
        if (convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout, parentView, false);
        }
        TextView parentTextView = (TextView) convertView.findViewById(R.id.parent_txt);
        parentTextView.setTypeface(null, Typeface.BOLD);
        parentTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {

        String child_title = (String) this.getChild(parent,child);
        if (convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout, parentView, false );
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
