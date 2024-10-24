package com.example.planetsuperheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FaqExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listQuestions;
    private HashMap<String, List<String>> listAnswers;

    public FaqExpandableListAdapter(Context context, List<String> listQuestions,
                                    HashMap<String, List<String>> listAnswers) {
        this.context = context;
        this.listQuestions = listQuestions;
        this.listAnswers = listAnswers;
    }

    @Override
    public int getGroupCount() {
        return listQuestions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listAnswers.get(listQuestions.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listQuestions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listAnswers.get(listQuestions.get(groupPosition)).get(childPosition);
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
        String question = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null); // Cambiado a tu diseño personalizado
        }
        TextView questionText = (TextView) convertView.findViewById(R.id.item_title);
        questionText.setText(question);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String answer = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null); // Cambiado a tu diseño personalizado
        }
        TextView answerText = (TextView) convertView.findViewById(R.id.item_title);
        answerText.setText(answer);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}