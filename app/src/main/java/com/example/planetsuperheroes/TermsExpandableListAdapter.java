package com.example.planetsuperheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TermsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> sectionTitles;  // Lista de secciones de términos
    private HashMap<String, List<String>> sectionContent;  // Mapa de secciones a contenido

    public TermsExpandableListAdapter(Context context, List<String> sectionTitles,
                                      HashMap<String, List<String>> sectionContent) {
        this.context = context;
        this.sectionTitles = sectionTitles;
        this.sectionContent = sectionContent;
    }

    @Override
    public int getGroupCount() {
        return sectionTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sectionContent.get(sectionTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sectionTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sectionContent.get(sectionTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.terms_list_group, null);
        }
        TextView titleText = (TextView) convertView.findViewById(R.id.terms_section_title);
        titleText.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String content = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.terms_list_item, null);
        }
        TextView contentText = (TextView) convertView.findViewById(R.id.terms_content_text);
        contentText.setText(content);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}