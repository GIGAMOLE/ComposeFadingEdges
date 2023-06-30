package com.gigamole.composefadingedges

import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.fill.FadingEdgesFillType

/**
 * The fading edges gravity for [fadingEdges].
 *
 * @see FadingEdgesContentType
 * @see FadingEdgesFillType
 * @see FadingEdgesGravity
 * @author GIGAMOLE
 */
enum class FadingEdgesGravity {

    /** The fading edges at the [Start] and [End] gravity. */
    All,

    /** The fading edge at the [Start] gravity. */
    Start,

    /** The fading edge at the [End] gravity. */
    End;

    /**
     * Util function to check whether the fading edge at the [Start] gravity.
     *
     * @return The indication whether the fading edge at the [Start] or not.
     */
    fun isStart(): Boolean = this == All || this == Start

    /**
     * Util function to check whether the fading edge at the [End] gravity.
     *
     * @return The indication whether the fading edge at the [End] or not.
     */
    fun isEnd(): Boolean = this == All || this == End
}