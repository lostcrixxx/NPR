package br.com.envolvedesenvolve.notacaopolonesareversa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String digitando = "";
    private EditText edtValue;
    private Button btnClean, btnBackspace;
    private Pilha p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValue = findViewById(R.id.edtValue);
        btnClean = findViewById(R.id.btnClean);
        btnBackspace = findViewById(R.id.btnBackspace);

        Button btnFav01 = findViewById(R.id.btnFav01);
        Button btnFav02 = findViewById(R.id.btnFav02);
        Button btnFav03 = findViewById(R.id.btnFav03);
        Button btnFav04 = findViewById(R.id.btnFav04);

        Button buttonLeft = findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(this);
        Button buttonRight = findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(this);

        Button buttonSom = findViewById(R.id.buttonSom);
        buttonSom.setOnClickListener(this);
        Button buttonSub = findViewById(R.id.buttonSub);
        buttonSub.setOnClickListener(this);
        Button buttonMul = findViewById(R.id.buttonMul);
        buttonMul.setOnClickListener(this);
        Button buttonDiv = findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(this);

        Button buttonA = findViewById(R.id.buttonA);
        buttonA.setOnClickListener(this);
        Button buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(this);
        Button buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(this);
        Button buttonD = findViewById(R.id.buttonD);
        buttonD.setOnClickListener(this);
        Button buttonE = findViewById(R.id.buttonE);
        buttonE.setOnClickListener(this);

        Button btnConvert = findViewById(R.id.btnConvert);
        final TextView txtResult = findViewById(R.id.txtResult);

        btnFav01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notacao = "A+B*C";
                edtValue.setText(notacao);
                txtResult.setText(converteNPR(notacao));
            }
        });

        btnFav02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notacao = "A*(B+C)";
                edtValue.setText(notacao);
                txtResult.setText(converteNPR(notacao));
            }
        });

        btnFav03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notacao = "(A+B)/(C-D)";
                edtValue.setText(notacao);
                txtResult.setText(converteNPR(notacao));
            }
        });

        btnFav04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String notacao = "A+B*C";
                String notacao = "(A+B)/(C-D)*E";
                // Resultado AB+CD-/E*

                edtValue.setText(notacao);
                txtResult.setText(converteNPR(notacao));
            }
        });

        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edtValue.getText().toString();
                if (str.length() >1 ) {
                    str = str.substring(0, str.length() - 1);
                    edtValue.setText(str);
                }
                else if (str.length() <=1 ) {
                    edtValue.setText("");
                }
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                digitando = "";
                txtResult.setText("Resultado");
                edtValue.setText("");
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonLeft) {
            digitando += "(";
        } else if (id == R.id.buttonRight) {
            digitando += ")";
        } else if (id == R.id.buttonSom) {
            digitando += "+";
        } else if (id == R.id.buttonSub) {
            digitando += "-";
        } else if (id == R.id.buttonMul) {
            digitando += "*";
        } else if (id == R.id.buttonDiv) {
            digitando += "/";
        } else if (id == R.id.buttonA) {
            digitando += "A";
        } else if (id == R.id.buttonB) {
            digitando += "B";
        } else if (id == R.id.buttonC) {
            digitando += "C";
        } else if (id == R.id.buttonD) {
            digitando += "D";
        } else if (id == R.id.buttonE) {
            digitando += "E";
        }
        edtValue.setText(digitando);
    }
}
