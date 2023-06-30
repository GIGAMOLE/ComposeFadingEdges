package com.gigamole.composefadingedges.fill

import androidx.compose.ui.graphics.Color
import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.fadingEdges

/**
 * The fading edges fill gradient type for [fadingEdges].
 *
 * @see FadingEdgesContentType
 * @author GIGAMOLE
 */
sealed interface FadingEdgesFillType {

    val fillStops: Triple<Float, Float, Float>
    val secondStopAlpha: Float

    /**
     * The fill gradient using the clipping mechanism on a drawn content to create fading edges effect.
     *
     * The output clipping fill gradient is the following:
     * - [fillStops] first stop <-> Clip mask color with 1.0F.
     * - [fillStops] second stop <-> Clip mask color with [secondStopAlpha].
     * - [fillStops] third stop <-> Clip mask color with 0.0F.
     *
     * And reversed for an opposite fading edge.
     *
     * @property fillStops The fading edges fill gradient stops. Range from 0.0F to 1.0F.
     * @property secondStopAlpha The fading edges second stop alpha. Range from 0.0F to 1.0F.
     * @see FadingEdgesFillType
     */
    data class FadeClip(
        override val fillStops: Triple<Float, Float, Float> = FadingEdgesFillTypeDefaults.FillStops,
        override val secondStopAlpha: Float = FadingEdgesFillTypeDefaults.SecondStopAlpha,
    ) : FadingEdgesFillType

    /**
     * The fill gradient using drawing mechanism to draw fading edges [color] gradient over the content.
     *
     * The output drawing fill gradient is the following:
     * - [fillStops] first stop <-> [color] with 1.0F.
     * - [fillStops] second stop <-> [color] with [secondStopAlpha].
     * - [fillStops] third stop <-> [color] with 0.0F.
     *
     * And reversed for an opposite fading edge.
     *
     * @property fillStops The fading edges fill gradient stops. Range from 0.0F to 1.0F.
     * @property secondStopAlpha The fading edges second stop alpha. Range from 0.0F to 1.0F.
     * @property color The fading edges fill gradient color.
     * @see FadingEdgesFillType
     */
    data class FadeColor(
        override val fillStops: Triple<Float, Float, Float> = FadingEdgesFillTypeDefaults.FillStops,
        override val secondStopAlpha: Float = FadingEdgesFillTypeDefaults.SecondStopAlpha,
        val color: Color = FadingEdgesFillTypeDefaults.FadeColor.FadeColor
    ) : FadingEdgesFillType
}
