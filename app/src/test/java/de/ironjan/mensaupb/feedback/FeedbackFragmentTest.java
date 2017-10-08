package de.ironjan.mensaupb.feedback;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.BuildConfig;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FeedbackFragmentTest extends TestCase {

    String bodyTemplateGerman = "Hallo Jan,\n\nich möchte dir folgendes Feedback zu MensaUPB geben:\n\n";
    String bodyTemplateEnglish = "Hi Jan,\n\nI want to send you the following feedback about MensaUPB:\n\n";

    @Config(qualifiers = "de")
    @Test
    public void test_sendingFeedbackShouldNotCrashDe() {
        initFragmentAndSendFeedback();
    }


    @Config(qualifiers = "de")
    @Test
    public void test_sendingFeedbackShouldNotCrashEn() {
        initFragmentAndSendFeedback();
    }

    private void initFragmentAndSendFeedback() {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class)
                .create()
                .start()
                .resume()
                .get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FeedbackFragment fragment = new FeedbackFragment_();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();

        Assert.assertNotNull(fragment);
        fragment.sendFeedback();

        // FIXME Assert intent was called
    }
}