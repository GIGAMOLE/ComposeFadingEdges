package com.gigamole.composefadingedges.fill

import androidx.compose.ui.graphics.Color

/**
 * The default values for [FadingEdgesFillType].
 *
 * @author GIGAMOLE
 */
object FadingEdgesFillTypeDefaults {

    /**
     * The default fading edges fill gradient stops. Range from 0.0F to 1.0F.
     *
     * @see SecondStopAlpha
     */
    val FillStops: Triple<Float, Float, Float> = Triple(
        first = 0.0F,
        second = 0.5F,
        third = 1.0F
    )

    /**
     * The default fading edges second stop alpha. Range from 0.0F to 1.0F.
     *
     * @see SecondStopAlpha
     */
    val SecondStopAlpha: Float = 0.5F

    /** The default values for [FadingEdgesFillType.FadeColor]. */
    object FadeColor {

        /** The default fading edges fill gradient color. */
        val FadeColor: Color = Color.White
    }
}