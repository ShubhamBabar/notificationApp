package com.example.notificationapp;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText messageEditText;
    private EditText iconURLEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        messageEditText = findViewById(R.id.messageEditText);
        iconURLEditText = findViewById(R.id.iconURLEditText);
    }

    public void showNotification(View view) {
        String title = titleEditText.getText().toString();
        String message = messageEditText.getText().toString();
        String iconURL = iconURLEditText.getText().toString();

        // Create a notification channel for Android 8.0 and above
        String channelId = "CustomNotification";
        createNotificationChannel(channelId);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (!iconURL.isEmpty()) {
            Bitmap iconBitmap = getBitmapFromURL(iconURL);
            if (iconBitmap != null) {
                builder.setLargeIcon(iconBitmap);
            }
        }

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = (int) System.currentTimeMillis(); // Use a unique ID
        notificationManager.notify(notificationId, notification);
    }

    private void createNotificationChannel(String channelId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Custom Notification Channel";
            String description = "Channel for custom notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Bitmap getBitmapFromURL(String iconURL) {
        try {
            return BitmapFactory.decodeStream(new java.net.URL(iconURL).openStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}