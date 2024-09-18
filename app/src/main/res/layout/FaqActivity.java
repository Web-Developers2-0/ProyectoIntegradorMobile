import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    FaqExpandableListAdapter listAdapter;
    List<String> listQuestions;
    HashMap<String, List<String>> listAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);

        expandableListView = findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new FaqExpandableListAdapter(this, listQuestions, listAnswers);
        expandableListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listQuestions = new ArrayList<>();
        listAnswers = new HashMap<>();

        // Añade las preguntas
        listQuestions.add("¿Ofrecen ediciones limitadas o coleccionables?");
        listQuestions.add("¿Cómo puedo hacer un pedido de cómics en línea?");
        listQuestions.add("¿Cuáles son los métodos de pago disponibles?");

        // Añade las respuestas
        List<String> answer1 = new ArrayList<>();
        answer1.add("Sí, contamos con ediciones limitadas y coleccionables. Puedes encontrarlos en nuestra sección de “Cómics Exclusivos”.");

        List<String> answer2 = new ArrayList<>();
        answer2.add("Puedes hacer un pedido en nuestra tienda en línea seleccionando los cómics que te interesen y completando el proceso de pago.");

        List<String> answer3 = new ArrayList<>();
        answer3.add("Aceptamos pagos con tarjeta de crédito, débito, PayPal y transferencias bancarias.");

        // Mapea las preguntas a las respuestas
        listAnswers.put(listQuestions.get(0), answer1);
        listAnswers.put(listQuestions.get(1), answer2);
        listAnswers.put(listQuestions.get(2), answer3);
    }
}
