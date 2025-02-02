package br.com.envolvedesenvolve.notacaopolonesareversa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String digitando = "";
    private TextView txtResult;
    private EditText edtValue;
    private Button btnClean, btnBackspace, btnConvert;
    private Pilha p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValue = findViewById(R.id.edtValue);
        btnClean = findViewById(R.id.btnClean);
        btnBackspace = findViewById(R.id.btnBackspace);
        txtResult = findViewById(R.id.txtResult);
        btnConvert = findViewById(R.id.btnConvert);

        configurarBotoes();
        configurarBotoesFavoritos();
        configurarBotaoBackspace();
        configurarBotaoClean();
        configurarBotaoConvert();
    }

    private void configurarBotoes() {
        Button[] botoes = new Button[] {
                findViewById(R.id.buttonLeft),
                findViewById(R.id.buttonRight),
                findViewById(R.id.buttonSom),
                findViewById(R.id.buttonSub),
                findViewById(R.id.buttonMul),
                findViewById(R.id.buttonDiv),
                findViewById(R.id.buttonA),
                findViewById(R.id.buttonB),
                findViewById(R.id.buttonC),
                findViewById(R.id.buttonD),
                findViewById(R.id.buttonE)
        };

        String[] textos = new String[] {"(", ")", "+", "-", "*", "/", "A", "B", "C", "D", "E"};

        for (int i = 0; i < botoes.length; i++) {
            final String texto = textos[i];
            botoes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    digitando += texto;
                    edtValue.setText(digitando);
                }
            });
        }
    }

    private void configurarBotoesFavoritos() {
        Button[] botoes = new Button[] {
                findViewById(R.id.btnFav01),
                findViewById(R.id.btnFav02),
                findViewById(R.id.btnFav03),
                findViewById(R.id.btnFav04)
        };

        String[] textos = new String[] {"A+B*C", "A*(B+C)", "(A+B)/(C-D)", "(A+B)/(C-D)*E"};

        for (int i = 0; i < botoes.length; i++) {
            final String texto = textos[i];
            botoes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtValue.setText(texto);
                    txtResult.setText(converteNPR(texto));
                }
            });
        }
    }

    private void configurarBotaoBackspace() {
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edtValue.getText().toString();
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                    edtValue.setText(str);
                }
            }
        });
    }

    private void configurarBotaoClean() {
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                digitando = "";
                txtResult.setText("Resultado");
                edtValue.setText("");
            }
        });
    }

    private void configurarBotaoConvert() {
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edtValue.getText().toString();
                txtResult.setText(converteNPR(value));
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
