package com.gigamole.composefadingedges.content

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
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
    data object Static : FadingEdgesContentType

    /** The fading edges for a dynamic content. The fading edges are dynamic by the scroll amount. */
    sealed interface Dynamic : FadingEdgesContentType {

        /** The fading edges scroll configuration. */
        val scrollConfig: FadingEdgesScrollConfig

        /** Indicates whether the scroll is possible. */
        val isScrollPossible: Boolean

        /**
         * The fading edges for a [ScrollState] scrollable content.
         *
         * @property scrollConfig The fading edges scroll configuration.
         * @property state The scrollable container state.
         */
        data class Scroll(
            override val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig,
            val state: ScrollState
        ) : Dynamic {

            /** Indicates whether the scroll is possible. */
            override val isScrollPossible: Boolean
                get() = state.canScrollForward || state.canScrollBackward
        }

        /** The fading edges for a dynamic lazy content. */
        sealed interface Lazy : Dynamic {

            /**
             * The fading edges for a [LazyListState] scrollable content.
             *
             * @property scrollConfig The fading edges scroll configuration.
             * @property state The lazy list state.
             */
            data class List(
                override val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig,
                val state: LazyListState
            ) : Lazy {

                /** Indicates whether the scroll is possible. */
                override val isScrollPossible: Boolean
                    get() = state.canScrollForward || state.canScrollBackward
            }

            /**
             * The fading edges for a [LazyGridState] scrollable content.
             *
             * @property scrollConfig The fading edges scroll configuration.
             * @property state The lazy list state.
             * @property spanCount The grid span count.
             */
            data class Grid(
                override val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig,
                val state: LazyGridState,
                val spanCount: Int
            ) : Lazy {

                /** Indicates whether the scroll is possible. */
                override val isScrollPossible: Boolean
                    get() = state.canScrollForward || state.canScrollBackward
            }

            /**
             * The fading edges for a [LazyStaggeredGridState] scrollable content.
             *
             * The ComposeFadingEdges library highly recommends to use a [scrollConfig] with a provided [FadingEdgesScrollConfig.animationSpec], because staggered layout
             * sometimes can be unpredicted and some items can be placed in the way that the interpolation between these items is too short or almost zero, which causes
             * some fading edges jumps. Also, [LazyStaggeredGridState.layoutInfo] item spacing, before and after content paddings, causes some extra calculations, so if
             * it is possible, add these padding to the items/cards instead, to improve performance.
             *
             * @property scrollConfig The fading edges scroll configuration.
             * @property state The lazy list state.
             * @property spanCount The staggered grid span count.
             */
            data class StaggeredGrid(
                override val scrollConfig: FadingEdgesScrollConfig = FadingEdgesContentTypeDefaults.ScrollConfig,
                val state: LazyStaggeredGridState,
                val spanCount: Int
            ) : Lazy {

                /** Indicates whether the scroll is possible. */
                override val isScrollPossible: Boolean
                    get() = state.canScrollForward || state.canScrollBackward
            }
        }
    }
}
