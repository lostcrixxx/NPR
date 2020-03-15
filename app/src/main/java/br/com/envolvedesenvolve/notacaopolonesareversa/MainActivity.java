package br.com.envolvedesenvolve.notacaopolonesareversa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Pilha p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtValue = findViewById(R.id.edtValue);
        Button btnFav01 = findViewById(R.id.btnFav01);
        Button btnConvert = findViewById(R.id.btnConvert);
        final TextView txtResult = findViewById(R.id.txtResult);

        btnFav01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String notacao = "A+B*C";
                String notacao = "(A+B)/(C-D)*E";
                // Resultado AB+CD-/E*

                txtResult.setText(converteNPR(notacao));
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edtValue.getText().toString(); // infixa

//                Log.e("teste", converteNPR(notacao));
                txtResult.setText(converteNPR(value)); // pósfixa
//                Log.e("teste", p.print());
            }
        });
    }

    public static int prio(char op) {
        if (op == '(') return 1;
        else if (op == '+' || op == '-') return 2;
        else return 3;
    }

    public String converteNPR(String infixa) {
        String npr = "";
        // passo 1
        infixa = infixa.toUpperCase();
//        Pilha p = new Pilha(infixa.length());
        p = new Pilha();

        // passo 2 - varrer expressão
        for (int i = 0; i < infixa.length(); i++) {
            char atual = infixa.charAt(i);

            // encontra operando
            if (atual >= 'A' && atual <= 'Z')
                npr += atual;
            else if (atual == '+' || atual == '-' ||
                    atual == '*' || atual == '/') {
                while (!p.isEmpty() &&
                        prio((char) p.top()) >= prio(atual))
                    npr += p.pop();

                p.push(atual);
            } else if (atual == '(')
                p.push(atual);
            else if (atual == ')') {
                while ((char) p.top() != '(')
                    npr += p.pop();

                p.pop();
            }
        } // fim do for

        // passo 3 - limpar a pilha
        while (!p.isEmpty())
            npr += p.pop();
        return npr;
    }
}
