package com.gigamole.composefadingedges.content

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfig
import com.gigamole.composefadingedges.fadingEdges
import com.gigamole.composefadingedges.fill.FadingEdgesFillType

/**
 * The fading edges content type for [fadingEdges].
 *
 * @see FadingEdgesScrollConfig
 * @see FadingEdgesFillType
 * @author GIGAMOLE
 */
sealed interface FadingEdgesContentType {

    /** The fading edges for a static content. The fading edges are always fully shown. */
    object Static : FadingEdgesContentType

    /**
     * The fading edges for a [ScrollState] scrollable content.
     *
     * @property scrollState The scrollable container state.
     * @property scrollConfig The fading edges scroll configuration.
     */
    data class Scroll(
        val scrollState: ScrollState,
        val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig
    ) : FadingEdgesContentType

    /**
     * The fading edges for a [LazyListState] scrollable content.
     *
     * @property lazyListState The lazy list state.
     * @property scrollConfig The fading edges scroll configuration.
     */
    data class LazyList(
        val lazyListState: LazyListState,
        val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig
    ) : FadingEdgesContentType
}
