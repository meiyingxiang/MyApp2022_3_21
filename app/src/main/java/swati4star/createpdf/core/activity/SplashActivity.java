package swati4star.createpdf.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import swati4star.createpdf.R;
import swati4star.createpdf.core.dialog.PrivacyDialog;
import swati4star.createpdf.core.dialog.PrivacyPolicyActivity;
import swati4star.createpdf.core.dialog.TermsActivity;
import swati4star.createpdf.core.utils.SharedPreferenceUtils;
import swati4star.createpdf.core.utils.Utils;


public class SplashActivity extends Activity {


    private final Handler mHandler = new Handler(message -> {
        if (message.what == 1) {
            String url = (String) message.obj;
            if (!TextUtils.isEmpty(url)) {
                if (!Utils.openBrowser(SplashActivity.this, url)) {
                    showNavigate();
                }
            } else {
                showNavigate();
            }
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showNavigate();
    }


    /**
     *
     */
    private void showNavigate() {
        jumpMainUi();
    }

    private void jumpMainUi() {
        boolean jump = (boolean) SharedPreferenceUtils.getData(SplashActivity.this,
                "jump", false, SharedPreferenceUtils.JUMP);
        if (jump) {
            Intent intent = new Intent(SplashActivity.this, swati4star.createpdf.activity.SplashActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        } else {
            showPrivacy();
        }
    }


    /**
     * 显示用户协议和隐私政策
     */
    private void showPrivacy() {
        final PrivacyDialog dialog = new PrivacyDialog(SplashActivity.this);
        TextView tv_privacy_tips = dialog.findViewById(R.id.tv_privacy_tips);
        TextView btn_exit = dialog.findViewById(R.id.btn_exit);
        TextView btn_enter = dialog.findViewById(R.id.btn_enter);
        dialog.show();

        String string = getResources().getString(R.string.privacy_tips);
        String key1 = getResources().getString(R.string.privacy_tips_key1);
        String key2 = getResources().getString(R.string.privacy_tips_key2);
        int index1 = string.indexOf(key1);
        int index2 = string.indexOf(key2);

        //需要显示的字串
        SpannableString spannedString = new SpannableString(string);
        //设置点击字体颜色
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.colorBlue));
        spannedString.setSpan(colorSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.colorBlue));
        spannedString.setSpan(colorSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击字体大小
        AbsoluteSizeSpan sizeSpan1 = new AbsoluteSizeSpan(18, true);
        spannedString.setSpan(sizeSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(18, true);
        spannedString.setSpan(sizeSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, TermsActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan1, index1, index1 + key1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //点击事件去掉下划线
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan2, index2, index2 + key2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //设置点击后的颜色为透明，否则会一直出现高亮
        tv_privacy_tips.setHighlightColor(Color.TRANSPARENT);
        //开始响应点击事件
        tv_privacy_tips.setMovementMethod(LinkMovementMethod.getInstance());

        tv_privacy_tips.setText(spannedString);

        //设置弹框宽度占屏幕的80%
        WindowManager m = getWindowManager();
        Display defaultDisplay = m.getDefaultDisplay();
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (defaultDisplay.getWidth() * 0.80);
        dialog.getWindow().setAttributes(params);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferenceUtils.saveData(SplashActivity.this, SharedPreferenceUtils.JUMP, "jump", false);
                SplashActivity.this.finish();
                System.exit(0);
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferenceUtils.saveData(SplashActivity.this, SharedPreferenceUtils.JUMP, "jump", true);
                Intent intent = new Intent(SplashActivity.this, swati4star.createpdf.activity.SplashActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
