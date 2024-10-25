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
    private List<String> listQuestions;  // Lista de preguntas (grupos)
    private HashMap<String, List<String>> listAnswers;  // Mapa de preguntas a respuestas (grupos a hijos)

    public FaqExpandableListAdapter(Context context, List<String> listQuestions,
                                    HashMap<String, List<String>> listAnswers) {
        this.context = context;
        this.listQuestions = listQuestions;
        this.listAnswers = listAnswers;
    }

    @Override
    public int getGroupCount() {
        return listQuestions.size();  // Devuelve el número de preguntas
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listAnswers.get(listQuestions.get(groupPosition)).size();  // Devuelve el número de respuestas para una pregunta
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listQuestions.get(groupPosition);  // Devuelve la pregunta en la posición especificada
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listAnswers.get(listQuestions.get(groupPosition)).get(childPosition);  // Devuelve la respuesta en la posición especificada
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;  // Devuelve el ID de la pregunta
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;  // Devuelve el ID de la respuesta
    }

    @Override
    public boolean hasStableIds() {
        return false;  // Indica si los IDs son estables (en este caso, no)
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String question = (String) getGroup(groupPosition);  // Obtiene la pregunta
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_list_group, null);  // Infla el layout para las preguntas (grupos)
        }
        TextView questionText = (TextView) convertView.findViewById(R.id.question_text);  // Asigna la pregunta al TextView
        questionText.setText(question);
        return convertView;  // Retorna la vista de la pregunta
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String answer = (String) getChild(groupPosition, childPosition);  // Obtiene la respuesta
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_list_item, null);  // Infla el layout para las respuestas (hijos)
        }
        TextView answerText = (TextView) convertView.findViewById(R.id.answer_text);  // Asigna la respuesta al TextView
        answerText.setText(answer);
        return convertView;  // Retorna la vista de la respuesta
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;  // Indica si las respuestas son seleccionables (en este caso, sí)
    }
}