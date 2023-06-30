@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.gigamole.composefadingedges.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigamole.composefadingedges.FadingEdgesDefaults
import com.gigamole.composefadingedges.FadingEdgesGravity
import com.gigamole.composefadingedges.FadingEdgesOrientation
import com.gigamole.composefadingedges.content.FadingEdgesContentType
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfig
import com.gigamole.composefadingedges.content.scrollconfig.FadingEdgesScrollConfigDefaults
import com.gigamole.composefadingedges.fill.FadingEdgesFillType
import com.gigamole.composefadingedges.fill.FadingEdgesFillTypeDefaults
import com.gigamole.composefadingedges.horizontalFadingEdges
import com.gigamole.composefadingedges.marqueeHorizontalFadingEdges
import com.gigamole.composefadingedges.verticalFadingEdges
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@Composable
private fun MainScreen() {
    MainTheme {
        MainScreenContent()
//        MainScreenDemoContent()
    }
}

@Suppress("unused")
@Composable
fun MainScreenDemoContent() {
    val initialDelay = 1000L
    val scrollDuration = 900L
    val fadingEdgesDuration = 450L
    val fadingEdgeInitialScrollLength = 50
    val fadingEdgeScrollLength = 90
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState(initial = fadingEdgeInitialScrollLength)
    val scrollToAnimationSpec = tween<Float>(durationMillis = scrollDuration.toInt())
    val fadingEdgesAnimationSpec = tween<Float>(durationMillis = fadingEdgesDuration.toInt())
    var areStaticFadingEdges by remember { mutableStateOf(false) }
    val fadingEdgesScrollConfig by remember(areStaticFadingEdges) {
        derivedStateOf {
            if (areStaticFadingEdges) {
                FadingEdgesScrollConfig.Static(animationSpec = fadingEdgesAnimationSpec)
            } else {
                FadingEdgesScrollConfig.Dynamic(animationSpec = fadingEdgesAnimationSpec)
            }
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            areStaticFadingEdges = false
            delay(initialDelay)
            kotlinx.coroutines.coroutineScope {
                scrollState.animateScrollTo(
                    value = fadingEdgeScrollLength,
                    animationSpec = scrollToAnimationSpec
                )
            }
            delay(scrollDuration)
            kotlinx.coroutines.coroutineScope {
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = scrollToAnimationSpec
                )
            }
            delay(scrollDuration)
            areStaticFadingEdges = true
            kotlinx.coroutines.coroutineScope {
                scrollState.animateScrollTo(
                    value = fadingEdgeScrollLength,
                    animationSpec = scrollToAnimationSpec
                )
            }
            delay(scrollDuration)
            kotlinx.coroutines.coroutineScope {
                scrollState.animateScrollTo(
                    value = fadingEdgeInitialScrollLength,
                    animationSpec = scrollToAnimationSpec
                )
            }
            delay(scrollDuration)
            areStaticFadingEdges = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Box(
                modifier = Modifier
                    .height(height = 170.dp)
                    .verticalFadingEdges(
                        contentType = FadingEdgesContentType.Scroll(
                            scrollState = scrollState,
                            scrollConfig = fadingEdgesScrollConfig
                        )
                    )
                    .verticalScroll(state = scrollState)
            ) {
                Text(
                    modifier = Modifier
                        .width(width = 250.dp)
                        .background(color = Color(0xFF9FDAFF))
                        .padding(
                            horizontal = 32.dp,
                            vertical = 32.dp
                        ),
                    text = "Compose\nFading\nEdges",
                    color = Color(0xFF007AC9),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamilySpaceGrotesk
                )
            }

            Box {
                Text(
                    modifier = Modifier
                        .width(width = 250.dp)
                        .marqueeHorizontalFadingEdges(isMarqueeAutoLayout = false) {
                            background(color = Color(0xFFFEECF4))
                                .basicMarquee(
                                    delayMillis = initialDelay.toInt(),
                                    spacing = MarqueeSpacing.fractionOfContainer(fraction = 0.05F),
                                    velocity = 60.dp
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                        },
                    text = "Enrich Android Compose UI with fading edges",
                    color = Color(0xFFE21776),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamilyOpenSans
                )
            }
        }
    }
}

val SampleBgColors = listOf(
    Color(0xFF00A6FB),
    Color(0xFF0582CA),
    Color(0xFF006494),
    Color(0xFF003554),
    Color(0xFF051923),
)

@Composable
fun MainScreenContent() {
    val rawStaticScrollState = rememberScrollState()
    val rawScrollState = rememberScrollState()
    val rawLazyListState = rememberLazyListState()
    val configScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var sampleContentType by remember { mutableStateOf(SampleContentType.Static) }
    var itemsCount by remember { mutableStateOf(10) }
    var itemAdditionalSize by remember { mutableStateOf(0.dp) }
    var itemsSpacing by remember { mutableStateOf(0.dp) }
    var itemsRandomHeight by remember { mutableStateOf(false) }

    var fadingEdgesOrientation by remember { mutableStateOf(FadingEdgesOrientation.Vertical) }
    var fadingEdgesGravity by remember { mutableStateOf(FadingEdgesDefaults.Gravity) }
    var fadingEdgesLength by remember { mutableStateOf(FadingEdgesDefaults.Length) }
    var fadingEdgesSampleScrollConfig by remember { mutableStateOf(SampleScrollConfig.Static) }
    var fadingEdgesSampleAnimationType by remember { mutableStateOf(SampleAnimationType.Spring) }
    var fadingEdgesDynamicScrollFactor by remember { mutableStateOf(FadingEdgesScrollConfigDefaults.Dynamic.ScrollFactor) }
    var fadingEdgesDynamicIsLerpByDifferenceForPartialContent by remember { mutableStateOf(FadingEdgesScrollConfigDefaults.Dynamic.IsLerpByDifferenceForPartialContent) }
    var fadingEdgesSampleFillType by remember { mutableStateOf(SampleFillType.Clip) }
    var fadingEdgesFillStopFirst by remember { mutableStateOf(FadingEdgesFillTypeDefaults.FillStops.first) }
    var fadingEdgesFillStopSecond by remember { mutableStateOf(FadingEdgesFillTypeDefaults.FillStops.second) }
    var fadingEdgesFillStopThird by remember { mutableStateOf(FadingEdgesFillTypeDefaults.FillStops.third) }
    var fadingEdgesFillSecondStopAlpha by remember { mutableStateOf(FadingEdgesFillTypeDefaults.SecondStopAlpha) }
    var fadingEdgesColor by remember { mutableStateOf(FadingEdgesFillTypeDefaults.FadeColor.FadeColor) }
    var isColorPickerVisible by remember { mutableStateOf(false) }
    var randomSeed by remember { mutableStateOf(0) }

    val fadingEdgesScrollAnimationSpec: AnimationSpec<Float>? by remember(fadingEdgesSampleAnimationType) {
        derivedStateOf {
            when (fadingEdgesSampleAnimationType) {
                SampleAnimationType.None -> {
                    null
                }
                SampleAnimationType.Spring -> {
                    spring()
                }
                SampleAnimationType.Tween_700 -> {
                    tween(durationMillis = 700)
                }
            }
        }
    }
    val fadingEdgesScrollConfig by remember(
        fadingEdgesSampleScrollConfig,
        fadingEdgesScrollAnimationSpec,
        fadingEdgesDynamicScrollFactor,
        fadingEdgesDynamicIsLerpByDifferenceForPartialContent
    ) {
        derivedStateOf {
            when (fadingEdgesSampleScrollConfig) {
                SampleScrollConfig.Static -> {
                    FadingEdgesScrollConfig.Static(animationSpec = fadingEdgesScrollAnimationSpec)
                }
                SampleScrollConfig.Dynamic -> {
                    FadingEdgesScrollConfig.Dynamic(
                        animationSpec = fadingEdgesScrollAnimationSpec,
                        isLerpByDifferenceForPartialContent = fadingEdgesDynamicIsLerpByDifferenceForPartialContent,
                        scrollFactor = fadingEdgesDynamicScrollFactor
                    )
                }
                SampleScrollConfig.Full -> {
                    FadingEdgesScrollConfig.Full(animationSpec = fadingEdgesScrollAnimationSpec)
                }
            }
        }
    }

    val fadingEdgesContentType by remember(
        sampleContentType,
        fadingEdgesScrollConfig
    ) {
        derivedStateOf {
            when (sampleContentType) {
                SampleContentType.Static -> {
                    FadingEdgesContentType.Static
                }
                SampleContentType.Scroll -> {
                    FadingEdgesContentType.Scroll(
                        scrollState = rawScrollState,
                        scrollConfig = fadingEdgesScrollConfig
                    )
                }
                SampleContentType.LazyList -> {
                    FadingEdgesContentType.LazyList(
                        lazyListState = rawLazyListState,
                        scrollConfig = fadingEdgesScrollConfig
                    )
                }
            }
        }
    }

    val fadingEdgesFillThresholds by remember(
        fadingEdgesFillStopFirst,
        fadingEdgesFillStopSecond,
        fadingEdgesFillStopThird
    ) {
        derivedStateOf {
            Triple(
                first = fadingEdgesFillStopFirst,
                second = fadingEdgesFillStopSecond,
                third = fadingEdgesFillStopThird,
            )
        }
    }
    val fadingEdgesFillType by remember(
        fadingEdgesSampleFillType,
        fadingEdgesFillThresholds,
        fadingEdgesFillSecondStopAlpha,
        fadingEdgesColor
    ) {
        derivedStateOf {
            when (fadingEdgesSampleFillType) {
                SampleFillType.Clip -> {
                    FadingEdgesFillType.FadeClip(
                        fillStops = fadingEdgesFillThresholds,
                        secondStopAlpha = fadingEdgesFillSecondStopAlpha
                    )
                }
                SampleFillType.Color -> {
                    FadingEdgesFillType.FadeColor(
                        fillStops = fadingEdgesFillThresholds,
                        secondStopAlpha = fadingEdgesFillSecondStopAlpha,
                        color = fadingEdgesColor
                    )
                }
            }
        }
    }

    fun resetScrolls() {
        coroutineScope.launch {
            rawStaticScrollState.scrollTo(0)
        }
        coroutineScope.launch {
            rawScrollState.scrollTo(0)
        }
        coroutineScope.launch {
            rawLazyListState.scrollToItem(0)
        }
        coroutineScope.launch {
            configScrollState.scrollTo(0)
        }
    }

    fun reset() {
        sampleContentType = SampleContentType.Static
        itemsCount = 10
        itemAdditionalSize = 0.dp
        itemsSpacing = 0.dp
        itemsRandomHeight = false
        fadingEdgesOrientation = FadingEdgesOrientation.Vertical
        fadingEdgesGravity = FadingEdgesDefaults.Gravity
        fadingEdgesLength = FadingEdgesDefaults.Length
        fadingEdgesSampleScrollConfig = SampleScrollConfig.Static
        fadingEdgesSampleAnimationType = SampleAnimationType.Spring
        fadingEdgesDynamicScrollFactor = FadingEdgesScrollConfigDefaults.Dynamic.ScrollFactor
        fadingEdgesDynamicIsLerpByDifferenceForPartialContent = FadingEdgesScrollConfigDefaults.Dynamic.IsLerpByDifferenceForPartialContent
        fadingEdgesSampleFillType = SampleFillType.Clip
        fadingEdgesFillStopFirst = FadingEdgesFillTypeDefaults.FillStops.first
        fadingEdgesFillStopSecond = FadingEdgesFillTypeDefaults.FillStops.second
        fadingEdgesFillStopThird = FadingEdgesFillTypeDefaults.FillStops.third
        fadingEdgesFillSecondStopAlpha = FadingEdgesFillTypeDefaults.SecondStopAlpha
        fadingEdgesColor = FadingEdgesFillTypeDefaults.FadeColor.FadeColor
        isColorPickerVisible = false

        coroutineScope.launch {
            resetScrolls()
        }
    }

    LaunchedEffect(sampleContentType) {
        resetScrolls()
    }

    @Composable
    fun SampleItem(
        index: Int,
        orientation: FadingEdgesOrientation
    ) {
        val rawItemSize = if (itemsRandomHeight) {
            val random = Random(index + randomSeed)

            if (random.nextFloat() >= 0.1F) {
                100.dp * random.nextFloat()
            } else {
                400.dp
            }
        } else {
            65.dp
        }
        val itemSize = rawItemSize + itemAdditionalSize

        Box(
            modifier = Modifier
                .background(color = SampleBgColors[index % SampleBgColors.size])
                .then(
                    when (orientation) {
                        FadingEdgesOrientation.Vertical -> {
                            Modifier
                                .fillMaxWidth()
                                .requiredSizeIn(minHeight = 10.dp)
                                .height(height = itemSize)
                                .padding(horizontal = 20.dp)
                        }
                        FadingEdgesOrientation.Horizontal -> {
                            Modifier
                                .fillMaxHeight()
                                .requiredSizeIn(minWidth = 10.dp)
                                .width(width = itemSize)
                                .padding(vertical = 20.dp)
                        }
                    }
                ),
            contentAlignment = when (orientation) {
                FadingEdgesOrientation.Vertical -> {
                    Alignment.CenterStart
                }
                FadingEdgesOrientation.Horizontal -> {
                    Alignment.Center
                }
            }

        ) {
            val text = (index + 1).toString()

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.95F)
                .padding(all = 20.dp)
                .clipToBounds(),
            targetState = sampleContentType to fadingEdgesOrientation
        ) { (sampleScrollTypeState, fadingEdgesOrientationState) ->
            when (sampleScrollTypeState) {
                SampleContentType.Scroll -> {
                    when (fadingEdgesOrientationState) {
                        FadingEdgesOrientation.Vertical -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalFadingEdges(
                                        contentType = fadingEdgesContentType,
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    )
                                    .verticalScroll(state = rawScrollState)
                                    .padding(vertical = itemsSpacing),
                                verticalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                repeat(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                        FadingEdgesOrientation.Horizontal -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .horizontalFadingEdges(
                                        contentType = fadingEdgesContentType,
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    )
                                    .horizontalScroll(state = rawScrollState)
                                    .padding(horizontal = itemsSpacing),
                                horizontalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                repeat(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                    }
                }
                SampleContentType.LazyList -> {
                    when (fadingEdgesOrientationState) {
                        FadingEdgesOrientation.Vertical -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalFadingEdges(
                                        contentType = fadingEdgesContentType,
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    ),
                                contentPadding = PaddingValues(vertical = itemsSpacing),
                                state = rawLazyListState,
                                verticalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                items(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                        FadingEdgesOrientation.Horizontal -> {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .horizontalFadingEdges(
                                        contentType = fadingEdgesContentType,
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    ),
                                contentPadding = PaddingValues(horizontal = itemsSpacing),
                                state = rawLazyListState,
                                horizontalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                items(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                    }
                }
                SampleContentType.Static -> {
                    when (fadingEdgesOrientationState) {
                        FadingEdgesOrientation.Vertical -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalFadingEdges(
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    )
                                    .verticalScroll(state = rawStaticScrollState)
                                    .padding(vertical = itemsSpacing),
                                verticalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                repeat(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                        FadingEdgesOrientation.Horizontal -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .horizontalFadingEdges(
                                        gravity = fadingEdgesGravity,
                                        length = fadingEdgesLength,
                                        fillType = fadingEdgesFillType
                                    )
                                    .horizontalScroll(state = rawStaticScrollState)
                                    .padding(horizontal = itemsSpacing),
                                horizontalArrangement = Arrangement.spacedBy(space = itemsSpacing)
                            ) {
                                repeat(itemsCount) {
                                    SampleItem(
                                        index = it,
                                        orientation = fadingEdgesOrientationState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Divider()
        TabRow(
            selectedTabIndex = sampleContentType.ordinal,
            modifier = Modifier.fillMaxWidth(),
            tabs = {
                SampleContentType.values().forEach { enumValue ->
                    Tab(
                        selected = enumValue == sampleContentType,
                        text = {
                            Text(
                                modifier = Modifier.basicMarquee(),
                                text = enumValue.name.uppercase(),
                                maxLines = 1
                            )
                        },
                        onClick = {
                            sampleContentType = enumValue
                        },
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = configScrollState)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(space = 4.dp)
        ) {
            Text(
                text = "ITEMS COUNT:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = itemsCount.toFloat(),
                onValueChange = {
                    itemsCount = it.toInt()
                },
                valueRange = 0.0F..20.0F,
                steps = 20
            )

            Text(
                text = "ITEM ADDITIONAL SIZE:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = itemAdditionalSize.value,
                onValueChange = {
                    itemAdditionalSize = it.dp
                },
                valueRange = -40.0F..40.0F,
                steps = 50
            )

            Text(
                text = "ITEMS SPACING:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = itemsSpacing.value,
                onValueChange = {
                    itemsSpacing = it.dp
                },
                valueRange = 0F..25.0F,
                steps = 25
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(weight = 1.0F)
                        .basicMarquee(),
                    text = "ITEMS RANDOM HEIGHT:",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1
                )
                Switch(
                    checked = itemsRandomHeight,
                    onCheckedChange = {
                        itemsRandomHeight = it
                        if (itemsRandomHeight) {
                            randomSeed = Random.nextInt()
                        }
                    }
                )
            }

            Text(
                text = "ORIENTATION:",
                style = MaterialTheme.typography.labelLarge
            )
            TabRow(
                selectedTabIndex = fadingEdgesOrientation.ordinal,
                modifier = Modifier.fillMaxWidth(),
                tabs = {
                    FadingEdgesOrientation.values().forEach { enumValue ->
                        Tab(
                            selected = enumValue == fadingEdgesOrientation,
                            text = {
                                Text(
                                    modifier = Modifier.basicMarquee(),
                                    text = enumValue.name.uppercase(),
                                    maxLines = 1
                                )
                            },
                            onClick = {
                                fadingEdgesOrientation = enumValue
                            },
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "GRAVITY:",
                style = MaterialTheme.typography.labelLarge
            )
            TabRow(
                selectedTabIndex = fadingEdgesGravity.ordinal,
                modifier = Modifier.fillMaxWidth(),
                tabs = {
                    FadingEdgesGravity.values().forEach { enumValue ->
                        Tab(
                            selected = enumValue == fadingEdgesGravity,
                            text = {
                                Text(
                                    modifier = Modifier.basicMarquee(),
                                    text = enumValue.name.uppercase(),
                                    maxLines = 1
                                )
                            },
                            onClick = {
                                fadingEdgesGravity = enumValue
                            },
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "LENGTH:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fadingEdgesLength.value,
                onValueChange = {
                    fadingEdgesLength = it.dp
                },
                valueRange = 0F..65.0F,
                steps = 40
            )

            if (sampleContentType != SampleContentType.Static) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "SCROLL CONFIG:",
                    style = MaterialTheme.typography.labelLarge
                )
                TabRow(
                    selectedTabIndex = fadingEdgesSampleScrollConfig.ordinal,
                    modifier = Modifier.fillMaxWidth(),
                    tabs = {
                        SampleScrollConfig.values().forEach { enumValue ->
                            Tab(
                                selected = enumValue == fadingEdgesSampleScrollConfig,
                                text = {
                                    Text(
                                        modifier = Modifier.basicMarquee(),
                                        text = enumValue.name.uppercase(),
                                        maxLines = 1
                                    )
                                },
                                onClick = {
                                    fadingEdgesSampleScrollConfig = enumValue
                                },
                            )
                        }
                    }
                )

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "ANIMATION TYPE:",
                    style = MaterialTheme.typography.labelLarge
                )
                TabRow(
                    selectedTabIndex = fadingEdgesSampleAnimationType.ordinal,
                    modifier = Modifier.fillMaxWidth(),
                    tabs = {
                        SampleAnimationType.values().forEach { enumValue ->
                            Tab(
                                selected = enumValue == fadingEdgesSampleAnimationType,
                                text = {
                                    Text(
                                        modifier = Modifier.basicMarquee(),
                                        text = enumValue.name.uppercase(),
                                        maxLines = 1
                                    )
                                },
                                onClick = {
                                    fadingEdgesSampleAnimationType = enumValue
                                },
                            )
                        }
                    }
                )

                if (fadingEdgesSampleScrollConfig == SampleScrollConfig.Dynamic) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(weight = 1.0F)
                                .marqueeHorizontalFadingEdges(length = 8.dp) {
                                    basicMarquee()
                                },
                            text = "IS LERP BY DIFFERENCE FOR PARTIAL CONTENT:",
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1
                        )

                        Switch(
                            modifier = Modifier.padding(start = 8.dp),
                            checked = fadingEdgesDynamicIsLerpByDifferenceForPartialContent,
                            onCheckedChange = {
                                fadingEdgesDynamicIsLerpByDifferenceForPartialContent = it
                            }
                        )
                    }

                    Text(
                        text = "SCROLL FACTOR:",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Slider(
                        modifier = Modifier.fillMaxWidth(),
                        value = fadingEdgesDynamicScrollFactor,
                        onValueChange = {
                            fadingEdgesDynamicScrollFactor = it
                        },
                        valueRange = 0.2F..3.0F,
                        steps = 20
                    )
                } else {
                    Spacer(modifier = Modifier.height(height = 16.dp))
                }
            }

            Text(
                text = "FILL TYPE:",
                style = MaterialTheme.typography.labelLarge
            )
            TabRow(
                selectedTabIndex = fadingEdgesSampleFillType.ordinal,
                modifier = Modifier.fillMaxWidth(),
                tabs = {
                    SampleFillType.values().forEach { enumValue ->
                        Tab(
                            selected = enumValue == fadingEdgesSampleFillType,
                            text = {
                                Text(
                                    modifier = Modifier.basicMarquee(),
                                    text = enumValue.name.uppercase(),
                                    maxLines = 1
                                )
                            },
                            onClick = {
                                fadingEdgesSampleFillType = enumValue
                            },
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "FILL STOP FIRST:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fadingEdgesFillStopFirst,
                onValueChange = {
                    fadingEdgesFillStopFirst = it
                },
                valueRange = 0.0F..fadingEdgesFillStopSecond,
                steps = (fadingEdgesFillStopSecond * 9.0F).toInt()
            )

            Text(
                text = "FILL STOP SECOND:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fadingEdgesFillStopSecond,
                onValueChange = {
                    fadingEdgesFillStopSecond = it
                },
                valueRange = fadingEdgesFillStopFirst..fadingEdgesFillStopThird,
                steps = ((fadingEdgesFillStopThird - fadingEdgesFillStopFirst) * 9.0F).toInt()
            )

            Text(
                text = "FILL STOP THIRD:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fadingEdgesFillStopThird,
                onValueChange = {
                    fadingEdgesFillStopThird = it
                },
                valueRange = fadingEdgesFillStopSecond..1.0F,
                steps = ((1.0F - fadingEdgesFillStopSecond) * 9.0F).toInt()
            )

            Text(
                text = "FILL SECOND STOP ALPHA:",
                style = MaterialTheme.typography.labelLarge
            )
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fadingEdgesFillSecondStopAlpha,
                onValueChange = {
                    fadingEdgesFillSecondStopAlpha = it
                },
                valueRange = 0.2F..0.8F,
                steps = 7
            )

            if (fadingEdgesSampleFillType == SampleFillType.Color) {
                Text(
                    text = "COLOR:",
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(space = 20.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(weight = 1.0F),
                        onClick = {
                            isColorPickerVisible = true
                        }
                    ) {
                        Text(text = "PICK COLOR")
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(ratio = 1.0F)
                            .background(
                                color = fadingEdgesColor,
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(size = 4.dp)
                            )
                            .clickable {
                                isColorPickerVisible = true
                            }
                    )
                }
                Spacer(modifier = Modifier.height(height = 4.dp))
            }


            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    reset()
                }
            ) {
                Text(text = "RESET")
            }
        }
    }

    if (isColorPickerVisible) {
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = {
                isColorPickerVisible = false
            }
        ) {
            ClassicColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 300.dp)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                color = HsvColor.from(fadingEdgesColor)
            ) {
                fadingEdgesColor = it.toColor()
            }
        }
    }
}

enum class SampleContentType {
    Static,
    Scroll,
    LazyList
}

enum class SampleScrollConfig {
    Static,
    Dynamic,
    Full
}

enum class SampleAnimationType {
    None,
    Spring,
    Tween_700
}

enum class SampleFillType {
    Clip,
    Color
}