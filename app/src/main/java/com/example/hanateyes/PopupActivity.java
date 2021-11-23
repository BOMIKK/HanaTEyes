package com.example.hanateyes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


public class PopupActivity extends Activity implements TextToSpeech.OnInitListener {

    Button btn1;
    Button btn2;
    EditText txtAccount;
    EditText txtPrice;
    public static Context mContext;

    Intent sttIntent;
    SpeechRecognizer mRecognizer;
    TextToSpeech tts;
    final int PERMISSION = 1;

    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transfer_popup);
        mContext = this;

        txtAccount= (EditText)findViewById(R.id.account);
        txtPrice= (EditText)findViewById(R.id.price);
        //계좌번호
        btn1 = (Button)findViewById(R.id.btn1);

        //금액
        btn2 = (Button)findViewById(R.id.btn2);

        // 오디오, 카메라 권한설정
        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        // STT, TTS 로드
        speechInit();

        // Button Click Event 설정
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                speechStart();
                String guideStr = "이체할 금액을 말해주세요";
                funcVoiceOut(guideStr);
            }
        });



    }

    //닫기 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
        //커밋

    }

    // 이체 버튼 클릭
    public void mTransferOn(View v){

        //데이터 전달하기!!!
        price=findViewById(R.id.price);
        System.out.println(price.getText());

        //User user=new User("lee",price.getText().toString(),"7777-7777");
        //Toast.makeText(getApplicationContext(), "이체를 완료하였습니다.", Toast.LENGTH_SHORT).show();
        //myRef.child("users").child("1").setValue(user);

        String guideStr = "이체를 위해 지문인식을 시작합니다.";
        funcVoiceOut(guideStr);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String guideStr2 = "이체가 완료되었습니다.";
                funcVoiceOut(guideStr2);
            }
        },3000);//바로 실행을 원하지 않으면 지워주시면 됩니다



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    //음성 인식


    private void speechInit() {
        // stt 객체 생성, 초기화
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        // tts 객체 생성, 초기화
        tts = new TextToSpeech(PopupActivity.this, this);
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
                txtPrice.setText(matches.get(i));
                resultStr += matches.get(i);
            }

            if(resultStr.length() < 1) return;
            resultStr = resultStr.replace(" ", "");


        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };


    public void funcVoiceOut(String OutMsg){
        if(OutMsg.length()<1)return;
        if(!tts.isSpeaking()) {
            tts.speak(OutMsg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


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
