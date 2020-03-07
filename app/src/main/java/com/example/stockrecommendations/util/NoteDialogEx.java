package com.example.stockrecommendations.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stockrecommendations.R;

import java.util.HashMap;

public class NoteDialogEx {
    private Context mContext = null;
    private LayoutInflater inflater = null;
    private Dialog dialog = null;
    private View dialogView = null;
    private TextView dialog_title = null;
    private TextView dialog_message = null;
    private LinearLayout dialog_content = null;
    public TextView dialog_buttons = null;
    public TextView dialog_buttons1 = null;
    public CheckBox cb_note = null;

    private String mTitle = null;
    private String mMessage = null;
    private View customContentView = null;
    private LayoutParams customContentLayout = null;
    private LayoutParams buttonParams = null;
    private int screenWidth = 0;

    private ListView listView = null;
    private AdapterView.OnItemClickListener listViewListener = null;
    public static final int BUTTON_CONFIRM = -1;
    public static final int BUTTON_CANCEL = -2;
    public static final int BUTTON_OTHER = -3;

    private int mMessageGravity = Gravity.CENTER;

    HashMap<Object, OnClickListener> buttonMapListener = null;

    private int selectItem = -1; // listView 选择位置

    public NoteDialogEx(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        create();
    }

    private void create() {
        buttonMapListener = new HashMap<Object, OnClickListener>();
        mTitle = mContext.getResources().getString(R.string.app_name);

        DisplayMetrics display = mContext.getResources().getDisplayMetrics();
        screenWidth = (int) (display.widthPixels * 0.9);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        dialogView = inflater.inflate(R.layout.note_dialog, null);


        dialog_title = (TextView) dialogView.findViewById(R.id.tv_dialog_title);
        dialog_title.setVisibility(View.GONE);


        dialog_message = (TextView) dialogView.findViewById(R.id.tv_dialog_message);
        dialog_message.setVisibility(View.GONE);
        dialog_message.setMovementMethod(ScrollingMovementMethod.getInstance());

        dialog_content = (LinearLayout) dialogView.findViewById(R.id.li_dialog_content);
        dialog_buttons = (TextView) dialogView.findViewById(R.id.li_dialog_buttons);
        dialog_buttons1 = dialogView.findViewById(R.id.li_dialog_buttons1);

        dialog = builder.create();
        buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        buttonParams.weight = 1.0f;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
            Object key = v.getTag();
            if (key != null && buttonMapListener.containsKey(key)) {
                OnClickListener onClick = buttonMapListener.get(key);
                if (onClick != null)
                    onClick.onClick(NoteDialogEx.this, Integer.parseInt(key.toString()));
            }
        }
    };

    public int getSelectItem() {
        return selectItem;
    }

    //点击提示框以外的地方不隐藏提示框
    public void setCanceledOnTouchOutside(boolean isCancel) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(isCancel);
    }

    /**
     * 禁止返回按钮取消对话框
     *
     * @param isCancel
     */
    public void setCancelable(boolean isCancel) {
        if (dialog != null)
            dialog.setCancelable(isCancel);
    }

    public void setMessageGravity(int gravity) {
        this.mMessageGravity = gravity;
    }

    public void setTitleIco(int rid) {
        if (dialog_title != null && rid > 0) {
            dialog_title.setCompoundDrawablesWithIntrinsicBounds(rid, 0, 0, 0);
        }
    }

    private void initContent() {
        if (dialog_title != null && mTitle != null) {
            dialog_title.setText(mTitle);
            dialog_title.setVisibility(View.VISIBLE);
        }

        if (cb_note != null) {
            cb_note.setVisibility(View.VISIBLE);
        }

        if (dialog_message != null && mMessage != null) {
            dialog_message.setText(mMessage);
            dialog_message.setGravity(mMessageGravity);
            dialog_message.setVisibility(View.VISIBLE);
        }

        if (customContentView != null && dialog_content != null) {
            // dialog_content.removeAllViews();
            if (customContentLayout != null)
                dialog_content.addView(customContentView, customContentLayout);
            else {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.weight = 1.0f;
                dialog_content.addView(customContentView, params);
            }
        }


    }

    public NoteDialogEx setCancelButton(String butStr, OnClickListener clickListener) {
        addButton(BUTTON_CANCEL, butStr, clickListener);
        return this;
    }

    public NoteDialogEx setCancelButton(int rid, OnClickListener clickListener) {
        String butStr = mContext.getResources().getString(rid);
        addButton(BUTTON_CANCEL, butStr, clickListener);
        return this;
    }

    public NoteDialogEx setConfirmButton(String butStr, OnClickListener clickListener) {
        addButton(BUTTON_CONFIRM, butStr, clickListener);
        return this;
    }

    public NoteDialogEx setConfirmButton(int rid, OnClickListener clickListener) {
        String butStr = mContext.getResources().getString(rid);
        addButton(BUTTON_CONFIRM, butStr, clickListener);
        return this;
    }

    public void addButton(int which, String butStr, OnClickListener clickListener) {
        if (dialog_buttons == null)
            return;
        dialog_buttons.setVisibility(View.VISIBLE);
//        if (dialog_buttons.getChildCount() > 0) {
//            ImageView view = new ImageView(mContext);
//            view.setBackgroundColor(Color.rgb(0xf2, 0xf2, 0xf2));
//            float w = mContext.getResources().getDimension(R.dimen.WH1dp);
//            LayoutParams params = new LayoutParams((int) w, LayoutParams.MATCH_PARENT);
//            dialog_buttons.addView(view, params);
//        }

        Button button = (Button) inflater.inflate(R.layout.item_button, null);
        button.setText(butStr);
        button.setTag(which);
        if (clickListener != null)
            buttonMapListener.put(which, clickListener);

        dialog_buttons.setOnClickListener(onClickListener);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        this.customContentView = view;
        this.customContentLayout = layoutParams;

        if (dialog != null && dialog.isShowing())
            initContent();
    }

    public void setContentView(View view) {
        this.customContentView = view;

        if (dialog != null && dialog.isShowing())
            initContent();
    }

    public NoteDialogEx setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public NoteDialogEx setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public void show(String message) {
        this.mMessage = message;
        show();
    }

    public void show(String title, String message) {
        this.mTitle = title;
        this.mMessage = message;
        show();
    }

    public void show() {
        initContent();
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.setContentView(dialogView, new ViewGroup.LayoutParams(screenWidth, LayoutParams.WRAP_CONTENT));
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    AdapterView.OnItemClickListener itemClickListener1 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem = position;
            if (listViewListener != null) {
                listViewListener.onItemClick(parent, view, position, id);
            }

        }
    };
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            dismiss();
            if (listViewListener != null) {
                listViewListener.onItemClick(parent, view, position, id);
            }

        }
    };

    public interface OnClickListener {
        public void onClick(NoteDialogEx dialog, int button);
    }

    public void setIcon(Drawable drawable) {

    }
}
