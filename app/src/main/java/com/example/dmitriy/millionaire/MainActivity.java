package com.example.dmitriy.millionaire;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitriy.millionaire.controller.Controller;
import com.example.dmitriy.millionaire.models.GameQuestions;
import com.example.dmitriy.millionaire.rest.Retrofit2Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SAVE_GAME = "SAVE_GAME";
    private static final String INDEX = "INDEX";

    private TextView prize;
    private TextView tvQuestion;
    private Button startGame;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private Button fifty_fifty;
    private Button friend;
    private Button audience;

    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;

    private Controller controller;
    private GameQuestions question;

    private int index ;
    private ArrayList<GameQuestions> gameQuestions;
    private List<GameQuestions> Questionses;
    private Integer savedIndex;

    private Random r;
    private boolean friendHelp = false;
    private boolean fiftyHelp = false;
    private HashSet<Button> hiddenButtons;
    private Button[] allButtons;
    private HashMap<Integer,Button> visibleButtons;
    private Set<Integer> keys;
    private Integer [] arrayButtonsKeys;
    private Button greenButton;
    private int[] money = {0,100,200,400,800,1600,3500,8000,16000,32000,64000,125000,250000,500000,1000000};

    private SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prize = (TextView) findViewById(R.id.prize);
        startGame = (Button) findViewById(R.id.start_game);
        tvQuestion = (TextView) findViewById(R.id.questions);
        answerA = (Button) findViewById(R.id.answer_a);
        answerB = (Button) findViewById(R.id.answer_b);
        answerC = (Button) findViewById(R.id.answer_c);
        answerD = (Button) findViewById(R.id.answer_d);
        fifty_fifty = (Button) findViewById(R.id.fity_fifty);
        friend = (Button) findViewById(R.id.friend);
        audience = (Button) findViewById(R.id.audience);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);

        startGame.setOnClickListener(this);
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        fifty_fifty.setOnClickListener(this);
        friend.setOnClickListener(this);
        audience.setOnClickListener(this);

        r = new Random();
        hiddenButtons = new HashSet<>();

        gameQuestions = new ArrayList<>();

        visibleButtons = new HashMap<>();
        allButtons = new Button[]{answerA, answerB, answerC, answerD};
        setEnableButtons(false);
        utils();
        Log.e("HashMap SIZE onCreate", "" +visibleButtons.size());
        keys = visibleButtons.keySet();
        arrayButtonsKeys = keys.toArray(new Integer[keys.size()]);



        sPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sPref.getString(SAVE_GAME,"");
        savedIndex = sPref.getInt(INDEX,0);
        Type type = new TypeToken<List<GameQuestions>>(){}.getType();
        Questionses = gson.fromJson(json,type);
        if(Questionses!=null && Questionses.size()!=0){
        Log.d("SHAREDPREF NEW: ",Questionses.get(0).getBody());
        Log.d("SHAREDPREF NEWINDEX: ",String.valueOf(savedIndex));}
    }





    protected void saveGame() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameQuestions);
        editor.putString(SAVE_GAME,json);
        editor.commit();
        Log.d("SaveGame",gameQuestions.get(0).getBody());
    }

    @Override
    protected void onStop() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(INDEX,index);
        editor.commit();
        Log.d("SHAREDPREF INDEX",String.valueOf(index));
        super.onStop();
    }

    public void setEnableButtons(boolean isEnable){
        for(Button button:allButtons){
            button.setEnabled(isEnable);
        }
        fifty_fifty.setEnabled(isEnable);
        friend.setEnabled(isEnable);
        audience.setEnabled(isEnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game:
                if(Questionses==null||Questionses.size()==0)
                createRequest();
                else resumeGame(Questionses,savedIndex);

                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                linear3.setVisibility(View.VISIBLE);
                startGame.setVisibility(View.GONE);
                prize.setVisibility(View.VISIBLE);

                break;
            case R.id.answer_a:
                if (question.getAnswers().get(0).is_correct()) {
                    question = controller.getQuestion((savedIndex!=0)?++savedIndex:++index);
                    if (question != null)
                        setTextInView();
                    else createDialogWinner();
                } else createDialogGameOver();
                break;
            case R.id.answer_b:
                if (question.getAnswers().get(1).is_correct()) {
                    question = controller.getQuestion((savedIndex!=0)?++savedIndex:++index);
                    if (question != null)
                        setTextInView();
                    else createDialogWinner();
                } else createDialogGameOver();
                break;
            case R.id.answer_c:
                if (question.getAnswers().get(2).is_correct()) {
                    question = controller.getQuestion((savedIndex!=0)?++savedIndex:++index);
                    if (question != null)
                        setTextInView();
                    else createDialogWinner();
                } else createDialogGameOver();
                break;
            case R.id.answer_d:
                if (question.getAnswers().get(3).is_correct()) {
                    question = controller.getQuestion((savedIndex!=0)?++savedIndex:++index);
                    if (question != null)
                        setTextInView();
                    else createDialogWinner();
                } else createDialogGameOver();
                break;
            case R.id.fity_fifty:
                fiftyFiftyHelp();
                fifty_fifty.setVisibility(View.GONE);
                break;
            case R.id.friend:
                friendHelp();
                break;
            case R.id.audience:
                audienceHelp();
                audience.setVisibility(View.GONE);
                break;
        }
    }



    //region UI BUILDER
    public void setTextInView() {
        Log.d("VIEW","view = "+tvQuestion.getText().toString());
        Log.d("QUESTION","question = "+question.getBody());
        Log.d("QUESTION","question = "+question.getAnswers().get(0).getBody());
        tvQuestion.setText(question.getBody());

        answerA.setText("A: " + question.getAnswers().get(0).getBody());
        answerB.setText("B: " + question.getAnswers().get(1).getBody());
        answerC.setText("C: " + question.getAnswers().get(2).getBody());
        answerD.setText("D: " + question.getAnswers().get(3).getBody());
        if(savedIndex!=0){
            index=savedIndex;
            prize.setText(getResources().getString(R.string.prize) + " "+ String.valueOf(money[index]));
        }
        else prize.setText(getResources().getString(R.string.prize) + " "+ String.valueOf(money[index]));


        if (friendHelp) {
            greenButton.setTextColor(getResources().getColor(R.color.colorBlack));
            //friendHelp = false;

        }

        if(hiddenButtons.size()>0){
            for (Button hidenButton : hiddenButtons) {
                hidenButton.setVisibility(View.VISIBLE);
                //hidenButton.setEnabled(true);
                fiftyHelp = false;
            }
            utils();
        }
        hiddenButtons.clear();

    }


    public void newGame(){
        index = 0;
        prize.setVisibility(View.GONE);
        tvQuestion.setText(getResources().getText(R.string.welcome));
        linear1.setVisibility(View.INVISIBLE);
        linear2.setVisibility(View.INVISIBLE);
        linear3.setVisibility(View.INVISIBLE);
        startGame.setVisibility(View.VISIBLE);

    }
    //endregion

    //region Request
    private void createRequest() {
        Call<ArrayList<GameQuestions>> call = Retrofit2Config.getService().getGameQuestions();
        call.enqueue(new Callback<ArrayList<GameQuestions>>() {
            @Override
            public void onResponse(Call<ArrayList<GameQuestions>> call, Response<ArrayList<GameQuestions>> response) {
                index = 0;
                if(gameQuestions.size()!=0)gameQuestions.clear();
                gameQuestions = response.body();
                saveGame();
                Log.d("RETROFIT", "onResponse() called with: " + "call = [" + call + "], response = [" + response.body().toString() + "]");
                customizeGame(gameQuestions,index);
            }

            @Override
            public void onFailure(Call<ArrayList<GameQuestions>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Check internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }
    //endregion


    public void resumeGame(List<GameQuestions> gameQuestionses,int index){
     customizeGame((ArrayList<GameQuestions>)gameQuestionses,index);
    }

    public void customizeGame(ArrayList<GameQuestions>gameQuestions,int index){
        controller = new Controller(gameQuestions);
        question = controller.getQuestion(index);
        if(question != null){
            setEnableButtons(true);
            setTextInView();}
    }


    //region Help

    public void friendHelp(){
        friendHelp = true;
        Log.e("HashMap SIZE friend", "" + visibleButtons.size());


        keys = visibleButtons.keySet();
        arrayButtonsKeys = keys.toArray(new Integer[keys.size()]);

        int position;
        int index;

        if(fiftyHelp){
            position = r.nextInt(arrayButtonsKeys.length);
            index = arrayButtonsKeys[position];
            greenButton = visibleButtons.get(index);
        }else {
            position = r.nextInt(question.getAnswers().size());
            greenButton = visibleButtons.get(position);
        }

        greenButton.setTextColor(getResources().getColor(R.color.colorGreen));
        friend.setVisibility(View.GONE);
    }

    public void fiftyFiftyHelp() {
        fiftyHelp = true;
        do {
            int index = r.nextInt(question.getAnswers().size());
            if (!question.getAnswers().get(index).is_correct() && !hiddenButtons.contains(allButtons[index])){
                hiddenButtons.add(allButtons[index]);
                visibleButtons.remove(index);
                Log.e("HashMap SIZE fifty ", "" +visibleButtons.size());}
        } while (hiddenButtons.size() < question.getAnswers().size() / 2);

        Log.d("VISIBLE BUTTON SIZE", " "+ visibleButtons.size());
        Log.d("HIDDEN BUTTON SIZE", " "+ hiddenButtons.size());

        for (Button button : hiddenButtons) {
            button.setVisibility(View.INVISIBLE);
        }
        fifty_fifty.setVisibility(View.GONE);
    }

    public void audienceHelp(){
        String[] buttonNames = new String[keys.size()];
        Log.d("KEYS SYZE"," "+ keys.size() );
        Log.d("Visible button SYZE"," "+ visibleButtons.size() );
        Log.d("Button  keys SYZE"," "+ arrayButtonsKeys.length );

        keys = visibleButtons.keySet();
        arrayButtonsKeys = keys.toArray(new Integer[keys.size()]);
        Log.d("Visible button SYZE 2"," "+ visibleButtons.size() );
        Log.d("Button  keys SYZE 2"," "+ arrayButtonsKeys.length );


        for(int i = 0; i<visibleButtons.size();i++){
            buttonNames[i] = visibleButtons.get(arrayButtonsKeys[i]).getText().toString();}
        Log.d("BUTTON NAMES SIZE", String.valueOf(buttonNames.length));
        createAudiencehelp(buttonNames);
    }

    //endregion

    //region Dialogs
    private void createDialogGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Over" + " Ваш " + prize.getText().toString());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                newGame();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createDialogWinner() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You win 1000000 $");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               if (savedIndex!=0){
                   savedIndex=0;
               }else index =0;
                newGame();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createAudiencehelp(String[] namesButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        StringBuilder build = new StringBuilder();
        int []chance = getChance(namesButton.length);
        for(int i = 0;i< namesButton.length;i++){
            build.append(namesButton[i] +"    "+ String.valueOf(chance[i])+"%"+"\n");
        }
        builder.setTitle("Results");
        builder.setMessage(build.toString());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //endregion

    //region Utils
    public void utils(){
        for(int i = 0;i<allButtons.length;i++){
            visibleButtons.put(i,allButtons[i]);
        }
    }

    private int[] getChance(int countButtons){
        int percent = 100;
        int temp;
        int equals = percent;

        int [] mass = new int[countButtons];
        for(int i = 0; i < countButtons - 1;i++){
            temp = r.nextInt(equals) + 1;
            equals = equals - temp;
            mass[i] = temp;
        }
        mass[countButtons-1] = equals;
        return mass;
    }
    //endregion
}
