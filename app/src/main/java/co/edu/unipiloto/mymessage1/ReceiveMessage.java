package co.edu.unipiloto.mymessage1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReceiveMessage extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private EditText etMensaje2;
    private Button btnEnviar2;
    private MessageAdapter messageAdapter;
    private List<String> messages;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        recyclerView2 = findViewById(R.id.recyclerView2);
        etMensaje2 = findViewById(R.id.etMensaje2);
        btnEnviar2 = findViewById(R.id.btnEnviar2);

        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(messageAdapter);

        sharedPreferences = getSharedPreferences("MyMessages", MODE_PRIVATE);

        // Cargar mensajes previos desde SharedPreferences
        Set<String> savedMessages = sharedPreferences.getStringSet("messages", new HashSet<String>());
        messages.addAll(savedMessages);
        messageAdapter.notifyDataSetChanged();

        btnEnviar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String respuesta = etMensaje2.getText().toString();
                if (!respuesta.isEmpty()) {
                    // Agrega el nuevo mensaje al final de la lista
                    messages.add("Amigo: " + respuesta);
                    etMensaje2.setText("");

                    // Guardar mensajes en SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("messages", new HashSet<>(messages));
                    editor.apply();

                    // Actualiza el RecyclerView
                    messageAdapter.notifyDataSetChanged();
                    // Desplázate al último mensaje (en la parte inferior) para mostrar el nuevo mensaje
                    recyclerView2.smoothScrollToPosition(messages.size() - 1);

                    // Envía la lista de mensajes actualizada de vuelta a la actividad CreateMessage
                    Intent intent2 = new Intent(ReceiveMessage.this, CreateMessage.class);
                    intent2.putStringArrayListExtra("messages", new ArrayList<>(messages));
                    startActivity(intent2);
                }
            }
        });
    }
}
