package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.onboarding.component.LevelInfo
import kotlin.math.absoluteValue

@Composable
fun MentorMatchRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    categoryId: Int,
    onNavigateToMentorDetail: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getMentors(categoryId, false)
    }

    val mentors = viewModel.mentors.collectAsState().value

    MentorMatchScreen(mentors = mentors, onNavigateToDetail = onNavigateToMentorDetail)
}

@Composable
private fun MentorMatchScreen(
    mentors: List<MentorsResponse>,
    onNavigateToDetail: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackAppBar()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.mentor_title),
                    style = WizdumTheme.typography.h2
                )
            }
            Spacer(modifier = Modifier.height(38.dp))
            MentorCardPager(mentors, onNavigateToDetail)
        }
    }
}


@Composable
fun MentorCardPager(mentorList: List<MentorsResponse>, onNavigateToDetail: (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { mentorList.size }, initialPage = 1)

    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 50.dp),
    ) { page ->
        MentorCard(
            page = page,
            pagerState = pagerState,
            mentorInfo = mentorList[page],
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
private fun MentorCard(
    page: Int,
    pagerState: PagerState,
    mentorInfo: MentorsResponse,
    onNavigateToDetail: (Int) -> Unit = {},
) {
    Box(modifier = Modifier.graphicsLayer {
        val pageOffset =
            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

        lerp(
            start = 0.7f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        alpha = lerp(
            start = 0.8f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
        )

    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .height(418.dp)
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = mentorInfo.mentoName, style = WizdumTheme.typography.h3_semib)
                    Text(text = " 멘토님", style = WizdumTheme.typography.body1, color = Black600)
                }
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mentorInfo.logoImageFilePath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "멘토 프로필 이미지",
                    modifier = Modifier.size(197.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mentorInfo.classTitle,
                    style = WizdumTheme.typography.h3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                LevelInfo(level = mentorInfo.itemLevel)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = WizdumTheme.colorScheme.primary)
                    .align(Alignment.BottomCenter)
                    .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .clickable {
                        onNavigateToDetail(mentorInfo.classId)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "강의 미리보기", style = WizdumTheme.typography.body1, color = Color.White)
            }
        }
    }
}


@Preview
@Composable
fun MentorCardPreview() {
    val pagerState = rememberPagerState(pageCount = { 0 }, initialPage = 0)
    WizdumTheme {
        MentorCard(
            page = 0,
            pagerState = pagerState,
            mentorInfo = MentorsResponse(
                mentoName = "스파르타",
                classTitle = "스파르타 코딩클럽\n시작해보자",
                itemLevel = "HIGH"
            ),
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorMatchScreenPreview() {
    val mentorList = listOf(
        MentorsResponse(
            mentoName = "스파르타",
            classTitle = "스파르타 코딩클럽",
            itemLevel = "HIGH"
        )
    )
    WizdumTheme {
        MentorMatchScreen(mentors = mentorList) {}
    }
}