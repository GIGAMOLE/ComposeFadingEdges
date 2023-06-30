package com.gigamole.composefadingedges

import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.fill.FadingEdgesFillType

/**
 * The fading edges orientation for [fadingEdges].
 *
 * @see FadingEdgesContentType
 * @see FadingEdgesFillType
 * @see FadingEdgesGravity
 * @author GIGAMOLE
 */
enum class FadingEdgesOrientation {

    /** The fading edges for a vertical content orientation. */
    Vertical,

    /** The fading edges for a horizontal content orientation. */
    Horizontal
}