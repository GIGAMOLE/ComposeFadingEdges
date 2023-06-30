@file:Suppress("unused")

package com.gigamole.composefadingedges

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfig
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfigDefaults
import com.gigamole.composefadingedges.fill.FadingEdgesFillType
import java.lang.Float.min
import kotlin.math.abs

/**
 * The utility [Modifier] to add fading edges to the text marquee (custom or default [basicMarquee]).
 *
 * The ComposeFadingEdges advises to use the default [basicMarquee], because custom implementations can differentiate drastically. In case the custom marquee is not
 * working well with [marqueeHorizontalFadingEdges], please, report an issue.
 *
 * When [isMarqueeAutoLayout] is enabled, the origin width is extended based on the provided [length] and [gravity], while maintaining the same offset. This ensures that
 * when fading edges [length] paddings are applied to the marquee text, the text remains positioned at its origin location. It is important to leave sufficient free space
 * around the marquee to ensure the effect is drawn correctly. On the other hand, when [isMarqueeAutoLayout] is disabled, the fading edges length paddings are applied to
 * the marquee text, and the origin position is offset by the [length]. In this case, you need to manually handle this padding offset.
 *
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @param isMarqueeAutoLayout Determines whether the [horizontalFadingEdges] and text marquee should be automatically aligned during the layout process to accommodate
 *     additional text paddings required for proper fading edges drawing.
 * @param marqueeProvider The custom or default [basicMarquee] provider.
 * @return The [Modifier] with extended width in case of [isMarqueeAutoLayout] is enabled, [horizontalFadingEdges], provided marquee with [marqueeProvider], and
 *     additional horizontal paddings.
 * @author GIGAMOLE
 */
fun Modifier.marqueeHorizontalFadingEdges(
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
    isMarqueeAutoLayout: Boolean = FadingEdgesDefaults.IsMarqueeAutoLayout,
    marqueeProvider: Modifier.() -> Modifier
): Modifier =
    then(
        if (isMarqueeAutoLayout) {
            Modifier.layout { measurable, constraints ->
                val lengthPx = length.roundToPx()
                val widthLength = when (gravity) {
                    FadingEdgesGravity.All -> {
                        lengthPx * 2
                    }
                    FadingEdgesGravity.Start,
                    FadingEdgesGravity.End -> {
                        lengthPx
                    }
                }

                // Extend the content width for fading edges and an additional text padding later.
                val placeable = measurable.measure(
                    constraints = constraints.copy(
                        maxWidth = constraints.maxWidth + widthLength
                    )
                )

                layout(
                    width = placeable.width,
                    height = placeable.height
                ) {
                    placeable.placeWithLayer(
                        x = 0,
                        y = 0
                    )
                }
            }
        } else {
            Modifier
        }
    )
        .horizontalFadingEdges(
            gravity = gravity,
            length = length,
            fillType = fillType
        )
        .then(marqueeProvider())
        .then(
            // Applying an additional padding to text, so fading edges dont cover it, at its origin position.
            when (gravity) {
                FadingEdgesGravity.All -> {
                    Modifier.padding(horizontal = length)
                }
                FadingEdgesGravity.Start -> {
                    Modifier.padding(start = length)
                }
                FadingEdgesGravity.End -> {
                    Modifier.padding(end = length)
                }
            }
        )

/**
 * The [Modifier] to add horizontal [fadingEdges] with a [FadingEdgesContentType.Static] content type. The fading edges are always fully shown.
 *
 * In case, the content is scrollable ([horizontalScroll] or [verticalScroll]) this [Modifier] should be added first.
 *
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with always fully shown horizontal [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.horizontalFadingEdges(
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = horizontalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = FadingEdgesContentType.Static,
    fillType = fillType
)

/**
 * The [Modifier] to add horizontal [fadingEdges] to a content with [horizontalScroll].
 *
 * This [Modifier] should be added before the [horizontalScroll].
 *
 * @param contentType The [FadingEdgesContentType.Scroll] content type for a [horizontalScroll] container.
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with horizontal [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.horizontalFadingEdges(
    contentType: FadingEdgesContentType.Scroll,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = horizontalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = contentType as FadingEdgesContentType,
    fillType = fillType
)

/**
 * The [Modifier] to add horizontal [fadingEdges] to a [LazyRow].
 *
 * @param contentType The [FadingEdgesContentType.LazyList] content type.
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with horizontal [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.horizontalFadingEdges(
    contentType: FadingEdgesContentType.LazyList,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = horizontalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = contentType as FadingEdgesContentType,
    fillType = fillType
)

/**
 * The [Modifier] to add horizontal [fadingEdges].
 *
 * In case, the content is scrollable ([horizontalScroll] or [verticalScroll]) this [Modifier] should be added first.
 *
 * @param contentType The [FadingEdgesContentType].
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with horizontal [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.horizontalFadingEdges(
    contentType: FadingEdgesContentType,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = fadingEdges(
    orientation = FadingEdgesOrientation.Horizontal,
    gravity = gravity,
    length = length,
    contentType = contentType,
    fillType = fillType
)

/**
 * The [Modifier] to add vertical [fadingEdges] with a [FadingEdgesContentType.Static] content type. The fading edges are always fully shown.
 *
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with always fully shown vertical [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.verticalFadingEdges(
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = verticalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = FadingEdgesContentType.Static,
    fillType = fillType
)

/**
 * The [Modifier] to add vertical [fadingEdges] to a content with [verticalScroll].
 *
 * This [Modifier] should be added before the [verticalScroll].
 *
 * @param contentType The [FadingEdgesContentType.Scroll] content type for a [verticalScroll] container.
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with vertical [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.verticalFadingEdges(
    contentType: FadingEdgesContentType.Scroll,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = verticalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = contentType as FadingEdgesContentType,
    fillType = fillType
)

/**
 * The [Modifier] to add vertical [fadingEdges] to a [LazyColumn].
 *
 * @param contentType The [FadingEdgesContentType.LazyList] content type.
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with vertical [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.verticalFadingEdges(
    contentType: FadingEdgesContentType.LazyList,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = verticalFadingEdges(
    gravity = gravity,
    length = length,
    contentType = contentType as FadingEdgesContentType,
    fillType = fillType
)

/**
 * The [Modifier] to add vertical [fadingEdges].
 *
 * In case, the content is scrollable ([horizontalScroll] or [verticalScroll]) this [Modifier] should be added first.
 *
 * @param contentType The [FadingEdgesContentType].
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with vertical [fadingEdges].
 * @author GIGAMOLE
 */
fun Modifier.verticalFadingEdges(
    contentType: FadingEdgesContentType,
    gravity: FadingEdgesGravity = FadingEdgesDefaults.Gravity,
    length: Dp = FadingEdgesDefaults.Length,
    fillType: FadingEdgesFillType = FadingEdgesDefaults.FillType,
): Modifier = fadingEdges(
    orientation = FadingEdgesOrientation.Vertical,
    gravity = gravity,
    length = length,
    contentType = contentType,
    fillType = fillType
)

/**
 * The core fading edges [Modifier].
 *
 * @param orientation The [FadingEdgesOrientation].
 * @param gravity The [FadingEdgesGravity].
 * @param length The fading edges length.
 * @param contentType The [FadingEdgesContentType].
 * @param fillType The [FadingEdgesFillType].
 * @return The [Modifier] with [fadingEdges].
 * @author GIGAMOLE
 */
internal fun Modifier.fadingEdges(
    orientation: FadingEdgesOrientation,
    gravity: FadingEdgesGravity,
    length: Dp,
    contentType: FadingEdgesContentType,
    fillType: FadingEdgesFillType
): Modifier = composed {
    // No need to add fading edges when they are empty.
    if (length <= 0.dp) {
        return@composed Modifier
    }

    val isFadeClip: Boolean
    val solidColor: Color
    val startLength: Float
    val endLength: Float

    when (fillType) {
        is FadingEdgesFillType.FadeClip -> {
            isFadeClip = true
            solidColor = Color.Black
        }
        is FadingEdgesFillType.FadeColor -> {
            isFadeClip = false
            solidColor = fillType.color.copy(alpha = 1.0F)
        }
    }

    val midColor = solidColor.copy(alpha = fillType.secondStopAlpha)
    val transparentColor = solidColor.copy(alpha = 0.0F)
    val startColors = arrayOf(
        fillType.fillStops.first to solidColor,
        fillType.fillStops.second to midColor,
        fillType.fillStops.third to transparentColor
    )
    val endColors = arrayOf(
        1.0F - fillType.fillStops.third to transparentColor,
        1.0F - fillType.fillStops.second to midColor,
        1.0F - fillType.fillStops.first to solidColor
    )
    val lengthPx = with(LocalDensity.current) { length.toPx() }

    when (contentType) {
        is FadingEdgesContentType.Scroll -> {
            with(contentType) {
                var scrollStartLength by remember { mutableStateOf(0.0F) }
                var scrollEndLength by remember { mutableStateOf(0.0F) }

                val rawStartScroll by derivedStateOf {
                    scrollState.value.toFloat()
                }
                val rawEndScroll by derivedStateOf {
                    (scrollState.maxValue - scrollState.value).toFloat()
                }

                when (scrollConfig) {
                    is FadingEdgesScrollConfig.Dynamic -> {
                        val maxScroll = scrollState.maxValue.toFloat()
                        val dynamicStartScroll: Float
                        val dynamicEndScroll: Float

                        if (scrollConfig.isLerpByDifferenceForPartialContent && maxScroll > 0 && maxScroll <= lengthPx) {
                            dynamicStartScroll = lengthPx * (rawStartScroll / maxScroll).coerceIn(0.0F, 1.0F)
                            dynamicEndScroll = lengthPx * (rawEndScroll / maxScroll).coerceIn(0.0F, 1.0F)
                        } else {
                            dynamicStartScroll = rawStartScroll
                            dynamicEndScroll = rawEndScroll
                        }

                        scrollStartLength = lengthPx * ((dynamicStartScroll / lengthPx) * scrollConfig.scrollFactor).coerceIn(0.0F, 1.0F)
                        scrollEndLength = lengthPx * ((dynamicEndScroll / lengthPx) * scrollConfig.scrollFactor).coerceIn(0.0F, 1.0F)
                    }
                    is FadingEdgesScrollConfig.Full -> {
                        scrollStartLength = if (scrollState.canScrollBackward) {
                            lengthPx
                        } else {
                            0.0F
                        }
                        scrollEndLength = if (scrollState.canScrollForward) {
                            lengthPx
                        } else {
                            0.0F
                        }
                    }
                    is FadingEdgesScrollConfig.Static -> {
                        if (scrollState.canScrollForward || scrollState.canScrollBackward) {
                            scrollStartLength = lengthPx
                            scrollEndLength = lengthPx
                        } else {
                            scrollStartLength = 0.0F
                            scrollEndLength = 0.0F
                        }
                    }
                }

                val animationSpec = scrollConfig.animationSpec
                if (animationSpec == null) {
                    startLength = scrollStartLength
                    endLength = scrollEndLength
                } else {
                    startLength = animateFloatAsState(
                        targetValue = scrollStartLength,
                        animationSpec = animationSpec
                    ).value
                    endLength = animateFloatAsState(
                        targetValue = scrollEndLength,
                        animationSpec = animationSpec
                    ).value
                }
            }
        }
        is FadingEdgesContentType.LazyList -> {
            with(contentType) {
                var scrollStartLength by remember { mutableStateOf(0.0F) }
                var scrollEndLength by remember { mutableStateOf(0.0F) }

                val layoutInfo = lazyListState.layoutInfo
                val lazyListScrollStartLength by derivedStateOf {
                    val fraction = if (layoutInfo.visibleItemsInfo.isEmpty()) {
                        0.0F
                    } else {
                        when (scrollConfig) {
                            is FadingEdgesScrollConfig.Dynamic -> {
                                val firstItem = layoutInfo.visibleItemsInfo.first()

                                if (firstItem.index > 0) {
                                    1.0F
                                } else {
                                    val firstItemSize = firstItem.size
                                    val minLength = if (firstItemSize == 0) {
                                        lengthPx
                                    } else {
                                        min(lengthPx, firstItemSize.toFloat())
                                    }

                                    var lerpLength = minLength

                                    if (scrollConfig.isLerpByDifferenceForPartialContent &&
                                        firstItem.index == 0 &&
                                        lazyListState.canScrollBackward &&
                                        layoutInfo.totalItemsCount == layoutInfo.visibleItemsInfo.size
                                    ) {
                                        lerpLength = lerpLazyListPartialContentByDifference(
                                            layoutInfo = layoutInfo,
                                            lerpLength = lerpLength,
                                            minLength = minLength
                                        )
                                    }

                                    abs(firstItem.offset).toFloat() / lerpLength * scrollConfig.scrollFactor
                                }
                            }
                            is FadingEdgesScrollConfig.Full -> {
                                if (lazyListState.canScrollBackward) {
                                    1.0F
                                } else {
                                    0.0F
                                }
                            }
                            is FadingEdgesScrollConfig.Static -> {
                                if (lazyListState.canScrollForward || lazyListState.canScrollBackward) {
                                    1.0F
                                } else {
                                    0.0F
                                }
                            }
                        }
                    }.coerceIn(0.0F, 1.0F)

                    fraction * lengthPx
                }
                val lazyListScrollEndLength by derivedStateOf {
                    val fraction = if (layoutInfo.visibleItemsInfo.isEmpty()) {
                        0.0F
                    } else {
                        when (scrollConfig) {
                            is FadingEdgesScrollConfig.Dynamic -> {
                                val lastItem = layoutInfo.visibleItemsInfo.last()

                                if (lastItem.index < layoutInfo.totalItemsCount - 1) {
                                    1.0F
                                } else {
                                    val lastItemSize = lastItem.size
                                    val minLength = if (lastItemSize == 0) {
                                        lengthPx
                                    } else {
                                        min(lengthPx, lastItemSize.toFloat())
                                    }

                                    var lerpLength = minLength

                                    if (scrollConfig.isLerpByDifferenceForPartialContent &&
                                        lastItem.index == layoutInfo.totalItemsCount - 1 &&
                                        lazyListState.canScrollForward &&
                                        layoutInfo.totalItemsCount == layoutInfo.visibleItemsInfo.size
                                    ) {
                                        lerpLength = lerpLazyListPartialContentByDifference(
                                            layoutInfo = layoutInfo,
                                            lerpLength = lerpLength,
                                            minLength = minLength
                                        )
                                    }

                                    val lastItemOffset = lastItem.size + lastItem.offset + layoutInfo.beforeContentPadding - layoutInfo.viewportEndOffset

                                    lastItemOffset / lerpLength * scrollConfig.scrollFactor
                                }
                            }
                            is FadingEdgesScrollConfig.Full -> {
                                if (lazyListState.canScrollForward) {
                                    1.0F
                                } else {
                                    0.0F
                                }
                            }
                            is FadingEdgesScrollConfig.Static -> {
                                if (lazyListState.canScrollForward || lazyListState.canScrollBackward) {
                                    1.0F
                                } else {
                                    0.0F
                                }
                            }
                        }
                    }.coerceIn(0.0F, 1.0F)

                    fraction * lengthPx
                }

                scrollStartLength = lazyListScrollStartLength
                scrollEndLength = lazyListScrollEndLength

                val animationSpec = scrollConfig.animationSpec
                if (animationSpec == null) {
                    startLength = scrollStartLength
                    endLength = scrollEndLength
                } else {
                    startLength = animateFloatAsState(
                        targetValue = scrollStartLength,
                        animationSpec = animationSpec
                    ).value
                    endLength = animateFloatAsState(
                        targetValue = scrollEndLength,
                        animationSpec = animationSpec
                    ).value
                }
            }
        }
        is FadingEdgesContentType.Static -> {
            startLength = lengthPx
            endLength = lengthPx
        }
    }

    this
        .then(
            if (isFadeClip) {
                Modifier.graphicsLayer {
                    // Required to apply clip mask to a drawn content.
                    compositingStrategy = CompositingStrategy.Offscreen
                }
            } else {
                Modifier
            }
        )
        .drawWithContent {
            drawContent()

            if (gravity.isStart()) {
                drawFadingEdge(
                    orientation = orientation,
                    isStartGravity = true,
                    colorStops = startColors,
                    isClip = isFadeClip,
                    length = startLength
                )
            }

            if (gravity.isEnd()) {
                drawFadingEdge(
                    orientation = orientation,
                    isStartGravity = false,
                    colorStops = endColors,
                    isClip = isFadeClip,
                    length = endLength
                )
            }
        }
}

/**
 * Interpolates the fading edges length difference in partial content state.
 *
 * @param layoutInfo The [LazyListLayoutInfo].
 * @param lerpLength The default partial content fading edges length difference.
 * @param minLength The minimum fading edges length.
 * @return The interpolated fading edges length difference.
 * @see FadingEdgesScrollConfig.Dynamic
 * @see FadingEdgesScrollConfigDefaults.Dynamic.IsLerpByDifferenceForPartialContent
 * @author GIGAMOLE
 */
internal fun lerpLazyListPartialContentByDifference(
    layoutInfo: LazyListLayoutInfo,
    lerpLength: Float,
    minLength: Float
): Float = with(layoutInfo) {
    val visibleSize = visibleItemsInfo.sumOf { it.size } +
            beforeContentPadding +
            afterContentPadding +
            mainAxisItemSpacing * (visibleItemsInfo.size - 1)

    val side = when (orientation) {
        Orientation.Vertical -> {
            viewportSize.height
        }
        Orientation.Horizontal -> {
            viewportSize.width
        }
    }

    if (visibleSize > side && visibleSize <= side + minLength) {
        val finalLength = (visibleSize - side).toFloat()

        if (finalLength > 0) {
            return@with finalLength
        }
    }

    lerpLength
}

/**
 * Draws a single fading edge fill gradient for [fadingEdges].
 *
 * @param orientation The [FadingEdgesOrientation].
 * @param isStartGravity Determines whether the fading edge is aligned with the start gravity.
 * @param colorStops An array of color stops specifying the fading edge fill gradient.
 * @param isClip Determines whether the fading edge fill gradient using a clipping mask.
 * @param length The fading edge length.
 * @author GIGAMOLE
 */
private fun ContentDrawScope.drawFadingEdge(
    orientation: FadingEdgesOrientation,
    isStartGravity: Boolean,
    colorStops: Array<Pair<Float, Color>>,
    isClip: Boolean,
    length: Float
) {
    val blendMode = if (isClip) {
        BlendMode.DstOut
    } else {
        DrawScope.DefaultBlendMode
    }

    when (orientation) {
        FadingEdgesOrientation.Vertical -> {
            val topLeft = if (isStartGravity) {
                Offset.Zero
            } else {
                Offset(
                    x = 0.0F,
                    y = size.height - length
                )
            }

            drawRect(
                brush = Brush.verticalGradient(
                    colorStops = colorStops,
                    startY = if (isStartGravity) {
                        0.0F
                    } else {
                        size.height - length
                    },
                    endY = if (isStartGravity) {
                        length
                    } else {
                        size.height
                    },
                ),
                topLeft = topLeft,
                size = if (isStartGravity) {
                    Size(
                        width = size.width,
                        height = length
                    )
                } else {
                    Size(
                        width = size.width - topLeft.x,
                        height = size.height - topLeft.y
                    )
                },
                blendMode = blendMode
            )
        }
        FadingEdgesOrientation.Horizontal -> {
            val topLeft = if (isStartGravity) {
                Offset.Zero
            } else {
                Offset(
                    x = size.width - length,
                    y = 0.0F
                )
            }

            drawRect(
                brush = Brush.horizontalGradient(
                    colorStops = colorStops,
                    startX = if (isStartGravity) {
                        0.0F
                    } else {
                        size.width - length
                    },
                    endX = if (isStartGravity) {
                        length
                    } else {
                        size.width
                    },
                ),
                topLeft = topLeft,
                size = if (isStartGravity) {
                    Size(
                        width = length,
                        height = size.height
                    )
                } else {
                    Size(
                        width = size.width - topLeft.x,
                        height = size.height - topLeft.y
                    )
                },
                blendMode = blendMode
            )
        }
    }
}
