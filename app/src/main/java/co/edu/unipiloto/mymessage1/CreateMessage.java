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

public class CreateMessage extends AppCompatActivity {

    private EditText etMessage1;
    private Button btEnviar1;
    private RecyclerView recyclerView1;
    private MessageAdapter messageAdapter;
    private List<String> messages;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        etMessage1 = findViewById(R.id.etMessage1);
        btEnviar1 = findViewById(R.id.btEnviar);
        recyclerView1 = findViewById(R.id.recyclerView1);

        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(messageAdapter);

        sharedPreferences = getSharedPreferences("MyMessages", MODE_PRIVATE);

        // Cargar mensajes previos desde SharedPreferences
        Set<String> savedMessages = sharedPreferences.getStringSet("messages", new HashSet<String>());
        messages.addAll(savedMessages);
        messageAdapter.notifyDataSetChanged();

        btEnviar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensaje1 = etMessage1.getText().toString();
                if (!textoMensaje1.isEmpty()) {
                    // Agrega el nuevo mensaje al principio de la lista
                    messages.add(0, "Yo: " + textoMensaje1);
                    etMessage1.setText("");

                    // Guardar mensajes en SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("messages", new HashSet<>(messages));
                    editor.apply();

                    // Actualiza el RecyclerView
                    messageAdapter.notifyDataSetChanged();
                    // Desplázate al primer mensaje (en la parte superior) para mostrar el nuevo mensaje
                    recyclerView1.smoothScrollToPosition(0);

                    // Envía la lista de mensajes a la actividad ReceiveMessage
                    Intent intent1 = new Intent(CreateMessage.this, ReceiveMessage.class);
                    intent1.putStringArrayListExtra("messages", new ArrayList<>(messages));
                    startActivity(intent1);
                }
            }
        });
    }
}
