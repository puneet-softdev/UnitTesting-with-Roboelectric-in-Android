package com.codingwithpuneet.learnunittestingusingroboelectric

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowIntent
import org.robolectric.shadows.ShadowLog

// "@RunWith(RobolectricTestRunner::class)" to indicate that you want to use Robolectric to run your tests.
@RunWith(RobolectricTestRunner::class)
// @Config(manifest = "src/main/AndroidManifest.xml")
// @Config(sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@Config(sdk = [28], manifest = Config.NONE)
class MainActivityTest {

    private var activity: Activity? = null

    @Before
    fun setUp() {
        // we use Robolectric's buildActivity method to create an instance of MainActivity
        activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    }

    @After
    fun tearDown() {
    }

    // Example 1: Testing a button click

    /* In this example, find the button in the layout using findViewById, perform a click on it,
    and then check that the next activity that is launched is NextActivity
     */
    @Test
    fun testLaunchActivityOnClick() {
        val button = activity?.findViewById<Button>(R.id.btnTest)
        button?.performClick()
        val nextActivity = Shadows.shadowOf(activity).peekNextStartedActivity()
        assertThat(nextActivity.component?.className, equalTo(SecondActivity::class.java.name))
    }

    @Test
    fun testTextViewInMainActivity() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, MainActivity::class.java)
        val activity = Robolectric.buildActivity(MainActivity::class.java, intent).create().get()
        val textView = activity.findViewById<TextView>(R.id.textView)
        textView.text = "Hello, world!"
        assertEquals("Hello, world!", textView.text)
    }

    @Test
    fun validateTextViewContent() {
        val textView: TextView = activity!!.findViewById<View>(R.id.textView) as TextView

        assertNotNull("TextView is null", textView)
        assertTrue(
            "TextView's text does not match.",
            "Puneet Grover" == textView.getText().toString()
        )
    }

    @Test
    fun validateButtonClick() {
        val button: Button = activity!!.findViewById<View>(R.id.btnTest) as Button
        button.performClick()
        val shadowActivity: ShadowActivity = shadowOf(activity)
        val startedIntent = shadowActivity.nextStartedActivity
        val shadowIntent: ShadowIntent = shadowOf(startedIntent)
        assertThat(shadowIntent.intentClass.name, equalTo(SecondActivity::class.java.name))
    }

    @Test
    fun validateText() {
        val textView: TextView = activity?.findViewById(R.id.textView) as TextView
        assertTrue("Puneet Grover" == textView.text.toString())
    }
}