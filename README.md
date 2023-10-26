[![](/media/header.png)](https://intive.com/)

![](https://jitpack.io/v/GIGAMOLE/ComposeFadingEdges.svg?style=flat-square) | [Setup Guide](#setup)
| [Report new issue](https://github.com/GIGAMOLE/ComposeFadingEdges/issues/new)

# ComposeFadingEdges

The `ComposeFadingEdges` is a powerful Android Compose library that seamlessly incorporates customisable fading edges with horizontal or vertical orientations, static or scrollable content, clip or color draw.

![](/media/demo.gif)

Features:

- **Multiple Modifiers:** Add fading edges with custom orientation, gravity, length, content and fill type.
- **Scrolls States:** Ready for `ScrollState`, `LazyListState`, `LazyGridState`, `LazyStaggeredGridState`.
- **Scrolling Settings:** Configure fading edges scroll behavior with dynamic, full, or static settings.
- **Text Marquee:** Easily add fading edges to text marquee with automatic layout alignment.
- **Adjustable Fade:** Choose between fade clip or fade color fill types for fading edges.
- **Sample App:** Explore and experiment with [sample app](#sample-app).

## Sample App

| Sample 1 | Sample 2 | Sample 3 |
|-|-|-|
| <img src="/media/sample-1.gif" width="248"/> | <img src="/media/sample-2.gif" width="248"/> | <img src="/media/sample-3.gif" width="248"/> |

Download or clone this repository to discover the sample app.

## Setup

Add to the root `build.gradle.kts`:

``` groovy
allprojects {
    repositories {
        ...
        maven("https://jitpack.io")
    }
}
```

Add to the package `build.gradle.kts`:

``` groovy
dependencies {
    implementation("com.github.GIGAMOLE:ComposeFadingEdges:{latest-version}")
}
```

Latest version: ![](https://jitpack.io/v/GIGAMOLE/ComposeFadingEdges.svg?style=flat-square).

Also, it's possible to download the latest artifact from the [releases page](https://github.com/GIGAMOLE/ComposeFadingEdges/releases).

## Guide

The `ComposeFadingEdges` comes with multiple `Modifiers`: [`Modifier.horizontalFadingEdges(...)`](#fading-edges-modifiers)
, [`Modifier.verticalFadingEdges(...)`](#fading-edges-modifiers), etc.

For more technical and detailed documentation, read the library `KDoc`.

### Fading Edges Modifiers

The fading edges `Modifiers` are available for different content orientation and type.

|Param|Description|
|-|-|
|`orientation`|The fading edges orientation: `Horizontal` or `Vertical`.|
|`gravity`|The fading edges gravity: `All`, `Start`, or `End`.|
|`length`|The fading edges length.|
|`contentType`|The [`FadingEdgesContentType`](#fadingedgescontentType).|
|`fillType`|The [`FadingEdgesFillType`](#fadingedgesfilltype).|

In case, `contentType` is not provided, the `FadingEdgesContentType` equals to `Static`.

The `ComposeFadingEdges` also provides fading edges for [text marquee](#modifiermarqueehorizontalfadingedges).

### FadingEdgesContentType

The `FadingEdgesContentType` can be `Static` or `Dynamic`.

The `Dynamic` content sub-type can be the following:

- `Scroll`: The dynamic fading edges for a `ScrollState` content.
- `Lazy.List`: The dynamic fading edges for a `LazyListState` content.
- `Lazy.Grid`: The dynamic fading edges for a `LazyGridState` content.
- `Lazy.StaggeredGrid`: The dynamic fading edges for a `LazyStaggeredGridState` content.

The `Dynamic` content type requires [`FadingEdgesScrollConfig`](#fadingedgesscrollconfig).

#### FadingEdgesScrollConfig

The `FadingEdgesScrollConfig` can be `Dynamic`, `Full` or `Static`.

All of them can configure fading edges scroll based offset `animationSpec`.

The `Dynamic` configuration comes with additional params:

|Param|Description|
|-|-|
|`isLerpByDifferenceForPartialContent`|Determines whether the fading edges should interpolate their scroll offset length for partial content.|
|`scrollFactor`|The fading edges scroll factor.|

### FadingEdgesFillType

The `FadingEdgesFillType` can be `FadeClip` or `FadeColor`.

The fading edges fill gradient is automatically faded from max alpha to min alpha.

All of them can configure fading edges fill gradient `fillStops` and `secondStopAlpha`.

The `FadeClip` type clips the content with the fading edges fill gradient.

The `FadeColor` type requires a `color` param and draws the fading edges fill gradient over the content.

### Modifier.marqueeHorizontalFadingEdges

The utility `Modifier` to add fading edges to the text marquee (custom or default `basicMarquee`).

This `Modifier` comes with common [fading edges params](#fading-edges-modifiers) and a few additional params:

|Param|Description|
|-|-|
|`isMarqueeAutoLayout`|Determines whether the `horizontalFadingEdges(...)` and text marquee should be automatically aligned during the layout process to accommodate additional text paddings required for proper fading edges drawing.|
|`isMarqueeAutoPadding`|Determines if padding values according to the `gravity` and `length` should be applied.|
|`marqueeProvider`|The custom or default `basicMarquee` provider.|

## License

MIT License. See the [LICENSE](https://github.com/GIGAMOLE/ComposeFadingEdges/blob/master/LICENSE) file for more details.

## Credits

Special thanks to the [GoDaddy](https://github.com/godaddy) for the amazing [color picker library](https://github.com/godaddy/compose-color-picker).

Created at [intive](https://intive.com).  
**We spark digital excitement.**

[![](/media/credits.png)](https://intive.com/)

## Author:

[Basil Miller](https://www.linkedin.com/in/gigamole/)  
[gigamole53@gmail.com](mailto:gigamole53@gmail.com)

[![](/media/footer.png)](https://intive.com/careers)
