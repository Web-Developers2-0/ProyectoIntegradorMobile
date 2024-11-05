package com.example.planetsuperheroes;

import android.os.Bundle;
import android.widget.ExpandableListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TermsActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    TermsExpandableListAdapter listAdapter;
    List<String> sectionTitles;
    HashMap<String, List<String>> sectionContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_terms);
        EdgeToEdge.enable(this);
        expandableListView = findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new TermsExpandableListAdapter(this, sectionTitles, sectionContent);
        expandableListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        sectionTitles = new ArrayList<>();
        sectionContent = new HashMap<>();

        // Agregar secciones principales
        sectionTitles.add("Términos y Condiciones de Uso de Planet Super Heroes");
        sectionTitles.add("1. Acceso y Uso de la App");
        sectionTitles.add("2. Datos Personales del Usuario");
        sectionTitles.add("3. Menores de Edad");
        sectionTitles.add("4. Exclusión de Garantías y Responsabilidad");
        sectionTitles.add("5. Derechos de Propiedad Intelectual");
        sectionTitles.add("6. Jurisdicción y Ley Aplicable");
        sectionTitles.add("7. Contacto");

        // Agregar contenido para cada sección
        List<String> introduction = new ArrayList<>();
        introduction.add("Este documento establece las condiciones para el uso de los servicios (en adelante, los “Servicios”) ofrecidos en la App Planet Super Heroes (en adelante, la “App”).\n\n" +
                "El uso de la App implica la aceptación plena y sin reservas de estos términos.");

        List<String> accessAndUse = new ArrayList<>();
        accessAndUse.add("1.1. Acceso a la App: La utilización de Planet Super Heroes no requiere registro previo, pero el acceso a servicios específicos sí lo exige.");
        accessAndUse.add("1.2. Uso de la App: El Usuario debe utilizar la App conforme a las leyes aplicables y de manera ética, sin dañar o perjudicar el funcionamiento de la App o afectar a otros usuarios.");
        accessAndUse.add("1.3. Contenido de la App: Los textos, gráficos, imágenes, logotipos, software y otros materiales están protegidos por derechos de autor y son propiedad de Planet Super Heroes.");
        accessAndUse.add("1.4. Uso Permitido: El Usuario no debe emplear la App para fines ilícitos, incluyendo el uso de scripts automáticos, accesos no autorizados a cuentas o la distribución de contenido no permitido.");

        List<String> personalData = new ArrayList<>();
        personalData.add("La App podrá requerir ciertos datos personales del Usuario para completar su registro y procesar sus compras. Estos datos serán tratados conforme a la Política de Privacidad.");

        List<String> minors = new ArrayList<>();
        minors.add("El uso de Planet Super Heroes está prohibido para menores de edad sin supervisión. Los padres o tutores son responsables de cualquier acción realizada en la App por menores a su cargo.");

        List<String> warrantiesAndLiabilities = new ArrayList<>();
        warrantiesAndLiabilities.add("4.1. Disponibilidad: Planet Super Heroes no garantiza que la App funcione sin interrupciones o errores. La App se ofrece \"tal cual\", sin garantías adicionales.");
        warrantiesAndLiabilities.add("4.2. Limitación de Responsabilidad: Planet Super Heroes no será responsable por daños que puedan derivarse del uso o imposibilidad de uso de la App.");
        warrantiesAndLiabilities.add("4.3. Contenidos Externos: La App puede contener enlaces a sitios de terceros, sobre los cuales Planet Super Heroes no se responsabiliza.");

        List<String> intellectualProperty = new ArrayList<>();
        intellectualProperty.add("Todos los derechos de propiedad intelectual de la App, su contenido y servicios pertenecen a Planet Super Heroes o a sus proveedores.");

        List<String> jurisdictionAndLaw = new ArrayList<>();
        jurisdictionAndLaw.add("Estos términos están regidos por las leyes de la República Argentina y cualquier controversia se someterá a la jurisdicción de los tribunales de la República Argentina.");

        List<String> contact = new ArrayList<>();
        contact.add("Para consultas sobre estos términos, puedes comunicarte con nosotros en soporte@planetsuperheroes.com.");

        // Mapear títulos a contenido
        sectionContent.put(sectionTitles.get(0), introduction);
        sectionContent.put(sectionTitles.get(1), accessAndUse);
        sectionContent.put(sectionTitles.get(2), personalData);
        sectionContent.put(sectionTitles.get(3), minors);
        sectionContent.put(sectionTitles.get(4), warrantiesAndLiabilities);
        sectionContent.put(sectionTitles.get(5), intellectualProperty);
        sectionContent.put(sectionTitles.get(6), jurisdictionAndLaw);
        sectionContent.put(sectionTitles.get(7), contact);
    }
}