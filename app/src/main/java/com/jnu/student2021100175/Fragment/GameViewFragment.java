package com.jnu.student2021100175.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jnu.student2021100175.GameActivity;
import com.jnu.student.R;
import com.jnu.student2021100175.view.GameView;

public class GameViewFragment extends Fragment {
    private CountDownTimer countDownTimer; // 计时器对象
    private TextView timerTextView; // 用于显示剩余时间的 TextView
    public GameViewFragment() {
        // Required empty public constructor
    }


    public static GameViewFragment newInstance(String param1, String param2) {
        GameViewFragment fragment = new GameViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beginlayout, container, false);
        // 找到对应的 TextView
        Button startTimerButton = view.findViewById(R.id.startGameButton);
        TextView textView = view.findViewById(R.id.gameTextView);
        int score = new GameView(this.getContext()).getscore();
        //textView.setText(score + "");
        textView.setText("开始学习");
        // 设置按钮点击事件
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(); // 点击按钮后启动计时器
            }
        });

        return view;
    }

    // 启动计时器方法
    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            // 启动另一个 Activity
            Intent intent = new Intent(getActivity(), GameActivity.class);
            boolean judge = false;
            public void onTick(long millisUntilFinished) {
                if(!judge) {
                    startActivity(intent);
                    judge = true;
                }
            }
            public void onFinish() {
            }
        }.start();
    }

    private void updateTimer(int remainingTime) {

    }

}