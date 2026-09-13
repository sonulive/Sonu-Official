package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.util.HapticFeedbackManager
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class HapticFeedbackManagerTest {

    @Test
    fun `haptic feedback manager executes selection haptic without crash`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val manager = HapticFeedbackManager(context)
        assertNotNull(manager)

        // Verifying selection haptic triggers gracefully without exception
        manager.performSelectionHaptic()
        manager.performResonancePulse()
        manager.performLightTick()
    }
}
