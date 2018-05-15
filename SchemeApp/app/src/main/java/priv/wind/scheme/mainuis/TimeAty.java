package priv.wind.scheme.mainuis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import priv.wind.scheme.R;

public class TimeAty extends AppCompatActivity {

    @BindView(R.id.dpk_select)
    DatePicker mDpkSelect;
    @BindView(R.id.tpk_select)
    TimePicker mTpkSelect;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_time);
        ButterKnife.bind(this);
        mTpkSelect.setIs24HourView(true);

        Intent intent = this.getIntent();
        mType = intent.getIntExtra("type", 0);
        if (mType == SchemeAty.EDIT_BEGIN_TIME || mType == SchemeAty.EDIT_END_TIME){
            String time = intent.getStringExtra("edit_time");
            String[] temp = time.split("-");
            // TODO: 2018/5/13 修改时初始化控件时间
        }
    }


    @OnClick(R.id.btn_cancel)
    public void onMBtnCancelClicked() {
        finish();
    }

    @OnClick(R.id.btn_submit)
    public void onMBtnSubmitClicked() {
        String month = String.valueOf(mDpkSelect.getMonth() + 1);
        String day = String.valueOf(mDpkSelect.getDayOfMonth());

        String hour = String.valueOf(mTpkSelect.getCurrentHour());
        String minute = String.valueOf(mTpkSelect.getCurrentMinute());

        String time = String.format("%s-%s-%s-%s", month, day, hour, minute);

        // 返回数据给主界面
        Intent intent = new Intent();
        intent.putExtra("time", time);
        setResult(mType ,intent);
        finish();
    }
}
