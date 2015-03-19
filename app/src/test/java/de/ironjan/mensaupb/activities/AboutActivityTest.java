package de.ironjan.mensaupb.activities;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import de.ironjan.mensaupb.R;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AboutActivityTest {

    @Test
    public void test_LinkifyTxtDependencies() {
        ActivityController controller = Robolectric.buildActivity(About_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);

        TextView textSourceLink = (TextView) activity.findViewById(R.id.txtDependencies);
        Assert.assertNotNull(textSourceLink);

        MovementMethod movementMethod = textSourceLink.getMovementMethod();
        boolean isLinkMovementMethod = (movementMethod instanceof LinkMovementMethod);

        String message = "TextView txtDependencies is not LinkMovementMethod.";
        Assert.assertTrue(message, isLinkMovementMethod);
    }


    @Test
    public void test_LinkifyTxtDependencyNames() {
        ActivityController controller = Robolectric.buildActivity(About_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);

        TextView textSourceLink = (TextView) activity.findViewById(R.id.txtDependencyNames);
        Assert.assertNotNull(textSourceLink);

        MovementMethod movementMethod = textSourceLink.getMovementMethod();
        boolean isLinkMovementMethod = (movementMethod instanceof LinkMovementMethod);

        String message = "TextView txtDependencyNames is not LinkMovementMethod.";
        Assert.assertTrue(message, isLinkMovementMethod);
    }

    @Test
    public void test_LinkifyTxtAbout() {
        ActivityController controller = Robolectric.buildActivity(About_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);

        TextView textSourceLink = (TextView) activity.findViewById(R.id.txtAbout);
        Assert.assertNotNull(textSourceLink);

        MovementMethod movementMethod = textSourceLink.getMovementMethod();
        boolean isLinkMovementMethod = (movementMethod instanceof LinkMovementMethod);

        String message = "TextView txtAbout is not LinkMovementMethod.";
        Assert.assertTrue(message, isLinkMovementMethod);
    }

    @Test
    public void test_LinkifyTextSourceLink() {
        ActivityController controller = Robolectric.buildActivity(About_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);

        TextView textSourceLink = (TextView) activity.findViewById(R.id.textSourceLink);
        Assert.assertNotNull(textSourceLink);

        MovementMethod movementMethod = textSourceLink.getMovementMethod();
        boolean isLinkMovementMethod = (movementMethod instanceof LinkMovementMethod);

        String message = "TextView textSourceLink is not LinkMovementMethod.";
        Assert.assertTrue(message, isLinkMovementMethod);
    }
}