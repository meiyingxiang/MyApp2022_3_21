package swati4star.createpdf.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import swati4star.createpdf.R;
import swati4star.createpdf.core.dialog.PrivacyPolicyActivity;
import swati4star.createpdf.core.dialog.TermsActivity;
import swati4star.createpdf.core.utils.Utils;
import swati4star.createpdf.util.FeedbackUtils;

public class AboutUsFragment extends Fragment {

    private Activity mActivity;
    private FeedbackUtils mFeedbackUtils;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, rootView);
        try {
            PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            TextView versionText = rootView.findViewById(R.id.version_value);
            String version = versionText.getText().toString() + " " + packageInfo.versionName;
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mFeedbackUtils = new FeedbackUtils(mActivity);
        return rootView;
    }

    @OnClick(R.id.layout_email)
    public void sendmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"muj4351@163.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, mActivity.getResources().getString(R.string.feedback_subject));
        intent.putExtra(Intent.EXTRA_TEXT, mActivity.getResources().getString(R.string.feedback_text));
        mFeedbackUtils.openMailIntent(intent);
    }

    @OnClick(R.id.layout_website)
    void openWeb() {
        mFeedbackUtils.openWebPage("http://swati4star.github.io/Images-to-PDF/");
    }

    @OnClick(R.id.layout_slack)
    void joinSlack() {
        mFeedbackUtils.openWebPage("https://join.slack.com/t/imagestopdf/shared_invite/" +
                "enQtNDA2ODk1NDE3Mzk3LTUwNjllYzY5YWZkZDliY2FmNDhkNmM1NjIwZTc1Y" +
                "jU4NTgxNWI0ZDczMWQxMTEyZjA0M2Y5N2RlN2NiMWRjZGI");
    }

    @OnClick(R.id.layout_github)
    void githubRepo() {
        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF");
    }

    @OnClick(R.id.layout_contri)
    void contributorsList() {
        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF/graphs/contributors");
    }

    @OnClick(R.id.layout_playstore)
    void openPlaystore() {
//        mFeedbackUtils.openWebPage("https://play.google.com/store/apps/details?id=swati4star.createpdf");
        Utils.startMarket(requireActivity());
    }

    @OnClick(R.id.layout_privacy)
    void privacyPolicy() {
//        mFeedbackUtils.openWebPage("https://sites.google.com/view/privacy-policy-image-to-pdf/home");
        startActivity(new Intent(requireActivity(), PrivacyPolicyActivity.class));
    }

    @OnClick(R.id.layout_license)
    void license() {
//        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF/blob/master/LICENSE.md");
        startActivity(new Intent(requireActivity(), TermsActivity.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
}
