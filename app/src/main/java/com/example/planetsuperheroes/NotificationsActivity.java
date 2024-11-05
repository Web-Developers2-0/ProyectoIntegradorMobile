package com.example.planetsuperheroes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetsuperheroes.Notification;
import com.example.planetsuperheroes.models.User;
import com.example.planetsuperheroes.network.ApiService;
import com.example.planetsuperheroes.network.RetrofitClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {

    private LinearLayout notificationsContainer;
    private ArrayList<Notification> notifications;
    private SharedPreferences sharedPreferences;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notifications = new ArrayList<>();
        notificationsContainer = findViewById(R.id.notifications_container);

        sharedPreferences = getSharedPreferences("NotificationsPrefs", MODE_PRIVATE);

        getUserInfo();
    }

    private void getUserInfo() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Call<User> call = apiService.getUserInfo();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    currentUserEmail = user.getEmail();

                    // Cargar las notificaciones para el usuario actual
                    loadNotifications();
                    updateNotificationsView();
                } else {
                    // Manejo de errores
                    showNoNotificationsMessage();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showNoNotificationsMessage();
            }
        });
    }

    private void loadNotifications() {
        Set<String> closedNotifications = sharedPreferences.getStringSet(currentUserEmail + "_closed", new HashSet<>());

        // Añadir notificaciones según las reglas especificadas
        if (!closedNotifications.contains("welcome")) {
            addNotification("Bienvenido a Planet Superheroes", "Estamos emocionados de tenerte con nosotros.", "welcome");
        }
        if (!closedNotifications.contains("cyber_monday")) {
            addNotification("¡Cyber Monday del 4 al 10 de Noviembre!", "Aprovecha increíbles descuentos en cómics.", "cyber_monday");
        }
    }

    private void addNotification(String title, String detail, String id) {
        String timestamp = getCurrentTimestamp();
        notifications.add(new Notification(title, detail, id, timestamp));
    }

    private void updateNotificationsView() {
        notificationsContainer.removeAllViews();

        if (notifications.isEmpty()) {
            showNoNotificationsMessage();
        } else {
            for (Notification notification : notifications) {
                addNotificationToView(notification);
            }
        }
    }

    private void addNotificationToView(Notification notification) {
        View notificationView = getLayoutInflater().inflate(R.layout.notification_item, notificationsContainer, false);

        TextView notificationTitle = notificationView.findViewById(R.id.notification_title);
        TextView notificationDetail = notificationView.findViewById(R.id.notification_detail);
        TextView notificationTimestamp = notificationView.findViewById(R.id.notification_timestamp);
        ImageButton closeButton = notificationView.findViewById(R.id.close_button);

        notificationTitle.setText(notification.getTitle());
        notificationDetail.setText(notification.getDetail());
        notificationTimestamp.setText(notification.getTimestamp());

        closeButton.setOnClickListener(v -> {
            notifications.remove(notification);
            saveClosedNotification(notification.getId());
            updateNotificationsView();
        });

        notificationsContainer.addView(notificationView);
    }

    private void saveClosedNotification(String notificationId) {
        Set<String> closedNotifications = sharedPreferences.getStringSet(currentUserEmail + "_closed", new HashSet<>());
        closedNotifications.add(notificationId);
        sharedPreferences.edit().putStringSet(currentUserEmail + "_closed", closedNotifications).apply();
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void showNoNotificationsMessage() {
        TextView noNotificationsText = new TextView(this);
        noNotificationsText.setText("Aún no tienes notificaciones.");
        notificationsContainer.addView(noNotificationsText);
    }
}
