package com.sergiupuhalschi.presentation.ui.common.views.images

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.sergiupuhalschi.presentation.ui.common.theme.Skeleton
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appDimens
import com.sergiupuhalschi.presentation.ui.common.views.indicators.shimmer

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String,
    shape: Shape = RoundedCornerShape(MaterialTheme.appDimens.LargeImageCornerRadius),
    addOverlay: Boolean = false,
    @DrawableRes placeholderRes: Int? = null,
    placeholderColor: Color = Skeleton,
    cacheKey: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    NetworkImage(
        modifier = modifier,
        url = url,
        shape = shape,
        addOverlay = addOverlay,
        loadingPlaceholder = {
            NetworkImagePlaceholderLayout(
                shape = shape,
                placeholderRes = placeholderRes,
                placeholderColor = placeholderColor,
                useSkeleton = true
            )
        },
        errorPlaceholder = {
            NetworkImagePlaceholderLayout(
                shape = shape,
                placeholderColor = placeholderColor,
                useSkeleton = false
            )
        },
        cacheKey = cacheKey,
        contentScale = contentScale
    )
}

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String,
    shape: Shape = RoundedCornerShape(MaterialTheme.appDimens.LargeImageCornerRadius),
    addOverlay: Boolean = false,
    loadingPlaceholder: @Composable () -> Unit,
    errorPlaceholder: @Composable () -> Unit = loadingPlaceholder,
    cacheKey: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .memoryCacheKey(cacheKey)
            .diskCacheKey(cacheKey)
            .build(),
        contentDescription = null,
        loading = { loadingPlaceholder() },
        error = { errorPlaceholder() },
        contentScale = contentScale,
        modifier = modifier
            .clip(shape = shape)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    if (addOverlay) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                0f to Color(0x00000000),
                                0.9f to Color(0x80000000)
                            ),
                            blendMode = BlendMode.Multiply
                        )
                    }
                }
            }
    )
}

@Composable
fun NetworkImagePlaceholderLayout(
    shape: Shape,
    @DrawableRes placeholderRes: Int? = null,
    placeholderColor: Color = Skeleton,
    useSkeleton: Boolean = true
) {
    if (placeholderRes != null) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = placeholderRes),
            contentDescription = null
        )
    } else if (useSkeleton) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmer(
                    color = placeholderColor,
                    shape = shape
                )
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = placeholderColor,
                    shape = shape
                )
        )
    }
}