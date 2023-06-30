package com.gigamole.composefadingedges.content.scrollconfig

import androidx.compose.animation.core.AnimationSpec
import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfig.Dynamic
import com.gigamole.composefadingedges.fadingEdges
import com.gigamole.composefadingedges.lerpLazyListPartialContentByDifference

/**
 * The fading edges scroll config for [FadingEdgesContentType.Scroll] and [FadingEdgesContentType.LazyList] types.
 *
 * The ComposeFadingEdges advises using an animation (custom or default [FadingEdgesScrollConfigDefaults.AnimationSpec]) to provide an interactive fading edges size
 * transitions (due to the dynamic items size or partial content). Especially useful for [Dynamic].
 *
 * @see FadingEdgesContentType
 * @see fadingEdges
 * @author GIGAMOLE
 */
sealed interface FadingEdgesScrollConfig {

    val animationSpec: AnimationSpec<Float>?

    /**
     * The dynamic fading edges scroll configuration interpolates their lengths on the scroll offset. The fading edges fade in when the content is away from the edges and
     * fade out as it gets closer. The fading edges are fully hidden when the content size is not scrollable.
     *
     * The [isLerpByDifferenceForPartialContent] functionality is useful when the [FadingEdgesContentType.Scroll] or [FadingEdgesContentType.LazyList] combined content
     * size (items size and offset) is slightly larger than the viewport size but smaller than the fading edges length. By enabling this feature, the fading edges can
     * smoothly interpolate their scroll offset length by considering the partial content difference, rather than fading edges length.
     *
     * @property animationSpec The fading edges scroll animation specification.
     * @property isLerpByDifferenceForPartialContent Determines whether the fading edges should interpolate their scroll offset length for partial content.
     * @property scrollFactor The fading edges scroll factor.
     * @see FadingEdgesScrollConfigDefaults.Dynamic.IsLerpByDifferenceForPartialContent
     * @see fadingEdges
     * @see lerpLazyListPartialContentByDifference
     */
    data class Dynamic(
        override val animationSpec: AnimationSpec<Float>? = FadingEdgesScrollConfigDefaults.AnimationSpec,
        val isLerpByDifferenceForPartialContent: Boolean = FadingEdgesScrollConfigDefaults.Dynamic.IsLerpByDifferenceForPartialContent,
        val scrollFactor: Float = FadingEdgesScrollConfigDefaults.Dynamic.ScrollFactor,
    ) : FadingEdgesScrollConfig

    /**
     * The full show and hide length fading edges scroll configuration. The fading edges are fully shown when the content is out from the edges and fully hidden at the
     * edges. The fading edges are fully hidden when the content size is not scrollable.
     *
     * @property animationSpec The fading edges scroll animation specification.
     */
    data class Full(
        override val animationSpec: AnimationSpec<Float>? = FadingEdgesScrollConfigDefaults.AnimationSpec
    ) : FadingEdgesScrollConfig

    /**
     * The static length fading edges scroll configuration. The fading edges are always fully shown, unless the content size is not scrollable.
     *
     * @property animationSpec The fading edges scroll animation specification.
     */
    data class Static(
        override val animationSpec: AnimationSpec<Float>? = FadingEdgesScrollConfigDefaults.AnimationSpec
    ) : FadingEdgesScrollConfig
}
