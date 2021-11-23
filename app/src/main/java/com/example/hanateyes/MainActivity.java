package com.example.hanateyes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    public static Context mContext;
    private RecyclerView rvList;

    Intent sttIntent;
    SpeechRecognizer mRecognizer;
    TextToSpeech tts;
    final int PERMISSION = 1;


    Button btn_transfer;
    Button btn_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;



        // 오디오, 카메라 권한설정
        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        // STT, TTS 로드
        speechInit();

        btn_transfer = findViewById(R.id.btn_transfer);
        btn_stock = findViewById(R.id.btn_stock);



        // 이체, 주식 창 띄우기
        btn_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PopupActivity.class);
                //intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
                //speechStart();

            }
        });



        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //시작 오디오
                String guideStr = "이체 주식 중 이용하실 서비스를 선택하세요.";
                Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
                funcVoiceOut(guideStr);
            }
        },1000);//바로 실행을 원하지 않으면 지워주시면 됩니다


        // 읽기, 확대하기 버튼 클릭 이벤트
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    //check id

                    case R.id.action_speech:
                        speechStart();
                        System.out.println("읽기!!!");



                    case R.id.action_zoom:
                        System.out.println("확대하기!!");

                }
                return true;
            }
        });

        //알림
        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("IDService", "device token : " + token);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //계좌 관리
        rvList = findViewById(R.id.rv_list);

        UserAdapter adapter = new UserAdapter();

        adapter.addItem(new User("kim", "100000"+"원", "12345-12345"));
        adapter.addItem(new User("kim", "100000"+"원", "12346-12346"));



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(adapter);


    }


    private void speechInit() {
        // stt 객체 생성, 초기화
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        // tts 객체 생성, 초기화
        tts = new TextToSpeech(MainActivity.this, this);
    }


    public void speechStart() {
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext); // 음성인식 객체
        mRecognizer.setRecognitionListener(listener); // 음성인식 리스너 등록
        mRecognizer.startListening(sttIntent);
    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            String guideStr = "에러가 발생하였습니다.";
            Toast.makeText(getApplicationContext(), guideStr + message, Toast.LENGTH_SHORT).show();
            funcVoiceOut(guideStr);
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String resultStr = "";

            for (int i = 0; i < matches.size(); i++) {
                //txtInMsg.setText(matches.get(i));
                resultStr += matches.get(i);
            }

            if(resultStr.length() < 1) return;
            resultStr = resultStr.replace(" ", "");

            moveActivity(resultStr);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

    public void moveActivity(String resultStr) {
        System.out.println(resultStr);
        if(resultStr.indexOf("이체") > -1) {
            String guideStr = "이체 기능으로 넘어갑니다.";
            Toast.makeText(getApplicationContext(), guideStr, Toast.LENGTH_SHORT).show();
            funcVoiceOut(guideStr);

            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
            startActivity(intent);
        }


    }

    public void funcVoiceOut(String OutMsg){
        if(OutMsg.length()<1)return;
        if(!tts.isSpeaking()) {
            tts.speak(OutMsg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(1);
        } else {
            Log.e("TTS", "초기화 실패");
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if(mRecognizer!=null){
            mRecognizer.destroy();
            mRecognizer.cancel();
            mRecognizer=null;
        }
        super.onDestroy();
    }
}