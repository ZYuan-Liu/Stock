package com.example.stockrecommendations.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.stockrecommendations.R;
import com.example.stockrecommendations.activity.EasterEggActivity;
import com.example.stockrecommendations.base.BaseFragment;
import com.example.stockrecommendations.entity.Event;
import com.example.stockrecommendations.util.EventBusUtil;
import com.example.stockrecommendations.util.NoDoubleClickListener;
import com.example.stockrecommendations.util.NoteDialogEx;

public class MyFragment extends BaseFragment {
    private View view;
    private TextView tv_finsh;
    private SVProgressHUD svProgressHUD;
    private RelativeLayout rl_person, rl_service, rl_mycollection, rl_commonproblem;
    private int cdCode1 = -1, cdCode2 = -1, cdCode3 = -1, cdCode4 = -1, cdCode5 = -1;
    private TextView textExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        setTop(view);
        tv_finsh = view.findViewById(R.id.tv_finsh);
        rl_person = view.findViewById(R.id.rl_personal);
        rl_service = view.findViewById(R.id.rl_onlineService);
        rl_mycollection = view.findViewById(R.id.rl_mycollection);
        rl_commonproblem = view.findViewById(R.id.rl_commonproblem);
        textExit = view.findViewById(R.id.tv_finsh);
        initEastEgg();
        tv_finsh.setOnClickListener(
                new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                     showDialog("提示","是否退出本应用？");
                    }
                }
        );

    }


    public void initEastEgg() {    //彩蛋触发方法，可以删掉-。-
        tv_finsh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (cdCode1 == 5 && cdCode2 == 0 && cdCode3 == 5 && cdCode4 == 1 && cdCode5 == 8) {
                    startActivity(EasterEggActivity.class);
                }
                return true;
            }
        });

        rl_service.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (cdCode1 == -1) {
                    cdCode1 = 5;
                } else if (cdCode1 == 5 && cdCode2 == 0) {
                    cdCode3 = 5;
                } else {
                    reSetCode();
                }
                return true;
            }
        });

        rl_person.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (cdCode1 == 5) {
                    cdCode2 = 0;
                } else {
                    reSetCode();
                }

                return true;
            }
        });

        rl_mycollection.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (cdCode1 == 5 && cdCode2 == 0 && cdCode3 == 5) {
                    cdCode4 = 1;
                } else {
                    reSetCode();
                }
                return true;
            }
        });

        rl_commonproblem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (cdCode1 == 5 && cdCode2 == 0 && cdCode3 == 5 && cdCode4 == 1) {
                    cdCode5 = 8;
                } else {
                    reSetCode();
                }
                return true;
            }
        });

    }

    private void initData() {

    }

    public void reSetCode() {
        cdCode1 = -1;
        cdCode2 = -1;
        cdCode3 = -1;
        cdCode4 = -1;
        cdCode5 = -1;
    }

    public void showDialog(String title, String message) {
        final NoteDialogEx alert = new NoteDialogEx(getActivity());
        alert.setTitle(title);
        alert.setMessageGravity(Gravity.START);
        alert.setMessage(message);
        alert.setCanceledOnTouchOutside(false);  //点击提示框以外的地方不隐藏提示框
        alert.dialog_buttons.setBackgroundResource(R.color.title_blue);
        alert.dialog_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                Event<String> event = new Event<>(2, s);
                EventBusUtil.sendEvent(event);
                alert.dismiss();
                getActivity().finish();
                //showShortToast("点击了确认");
            }
        });
        alert.dialog_buttons1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                //showShortToast("点击了取消");
            }
        });
        alert.show();
    }

}
