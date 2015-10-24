package de.ironjan.mensaupb.feedback;

import junit.framework.TestCase;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.*;
import org.robolectric.annotation.Config;
import junit.framework.Assert;
import java.util.IllegalFormatConversionException;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.feedback.*;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.app_info.*;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FeedbackFragmentTest extends TestCase {

  String bodyTemplateGerman="Hallo Jan,\n\nich möchte dir folgendes Feedback zu MensaUPB geben:\n\n";
  String bodyTemplateEnglish = "Hi Jan,\n\nI want to send you the following feedback about MensaUPB:\n\n";

  @Config(qualifiers="de")
  @Test
  public void test_sendingFeedbackShouldNotCrashDe() {
   initFragmentAndSendFeedback();
 }


 @Config(qualifiers="de")
 @Test
 public void test_sendingFeedbackShouldNotCrashEn() {
  initFragmentAndSendFeedback();
}

private void initFragmentAndSendFeedback(){
  FeedbackFragment fragment = new FeedbackFragment_();
  SupportFragmentTestUtil.startFragment( fragment );
  Assert.assertNotNull(fragment);
  fragment.sendFeedback();
}
}