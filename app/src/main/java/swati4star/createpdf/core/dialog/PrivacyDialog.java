package swati4star.createpdf.core.dialog;

import android.app.Dialog;
import android.content.Context;

import swati4star.createpdf.R;


public class PrivacyDialog extends Dialog {

    public PrivacyDialog(Context context) {
        super(context, R.style.PrivacyThemeDialog);

        setContentView(R.layout.dialog_privacy);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
