import android.os.Build;
import android.widget.Button;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import zekisanmobile.petsitter.BuildConfig;
import zekisanmobile.petsitter.MainActivity;
import zekisanmobile.petsitter.R;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class Test1 {

    private MainActivity activity;

    @Before
    public void setup(){
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent(){
        Button loginDono = (Button) activity.findViewById(R.id.loginOwner);
        assertNotNull("Button could not be found", loginDono);
        assertTrue("Button contains correct text",
                "LOGIN DONO PET".equalsIgnoreCase(loginDono.getText().toString()));
    }

    /*@Test
    public void donoHomeActivityStartedOnClick(){
        activity.findViewById(R.id.loginDono).performClick();

        Intent expectedIntent = new Intent(activity, OwnerHomeActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }*/
}
