package com.gigamole.composefadingedges.content.scrollconfig

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import com.gigamole.composefadingedges.FadingEdgesDefaults
import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfigDefaults.AnimationSpec
import com.gigamole.composefadingedges.fadingEdges
import com.gigamole.composefadingedges.lerpLazyListPartialContentByDifference

/**
 * The default values for [FadingEdgesScrollConfig].
 *
 * @author GIGAMOLE
 */
object FadingEdgesScrollConfigDefaults {

    /**
     * The default fading edges scroll animation specification.
     *
     * The ComposeFadingEdges advises using an animation (custom or default [AnimationSpec]) to provide an interactive fading edges size transitions (due to the dynamic
     * items size or partial content). Especially useful when [Dynamic.IsLerpByDifferenceForPartialContent] is enabled.
     */
    val AnimationSpec: AnimationSpec<Float>
        get() = spring()

    /** The default values for [FadingEdgesScrollConfig.Dynamic] type. */
    object Dynamic {

        /**
         * Determines whether the fading edges should interpolate their scroll offset length for partial content.
         *
         * This functionality is useful when the [FadingEdgesContentType.Dynamic] combined content size (items size and offset) is slightly larger than the viewport size
         * but smaller than the fading edges length. By enabling this feature, the fading edges can smoothly interpolate their scroll offset length by considering the
         * partial content difference, rather than fading edges length.
         *
         * @see FadingEdgesDefaults
         * @see fadingEdges
         * @see lerpLazyListPartialContentByDifference
         */
        const val IsLerpByDifferenceForPartialContent: Boolean = true

        /** The default fading edges scroll factor. */
        const val ScrollFactor: Float = 1.25F
    }
}