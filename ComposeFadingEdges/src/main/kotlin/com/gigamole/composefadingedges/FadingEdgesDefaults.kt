package com.gigamole.composefadingedges

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gigamole.composefadingedges.fill.FadingEdgesFillType

/**
 * The default values for [fadingEdges].
 *
 * @see FadingEdgesGravity
 * @see FadingEdgesFillType
 * @author GIGAMOLE
 */
object FadingEdgesDefaults {

    /** The default fading edges [FadingEdgesGravity]. */
    val Gravity: FadingEdgesGravity = FadingEdgesGravity.All

    /** The default fading edges length. */
    val Length: Dp = 16.dp

    /** The default fading edges [FadingEdgesFillType]. */
    val FillType: FadingEdgesFillType = FadingEdgesFillType.FadeClip()

    /**
     * Determines whether the [horizontalFadingEdges] and horizontal text marquee should be automatically aligned in the layout process (due to the additional text
     * paddings for a correct fading edges drawing).
     *
     * @see marqueeHorizontalFadingEdges
     */
    const val IsMarqueeAutoLayout: Boolean = true

    /**
     * Determines whether the [horizontalFadingEdges] and horizontal text marquee should apply paddings paddings according to the gravity and length.
     *
     * @see marqueeHorizontalFadingEdges
     */
    const val IsMarqueeAutoPadding: Boolean = true

}