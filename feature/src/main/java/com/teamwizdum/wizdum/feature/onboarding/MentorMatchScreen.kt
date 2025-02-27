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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import kotlin.math.absoluteValue

@Composable
fun MentorMatchScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    clickNext: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.postMentors(1, false)
    }

    val mentors = viewModel.mentors.collectAsState().value

    MentorContent(mentors, clickNext)
}

@Composable
private fun MentorContent(
    mentors: List<MentorsResponse>,
    clickNext: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackAppBar()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(text = "시간을 초월한\n당신만의 멘토를 찾았어요!", style = WizdumTheme.typography.h2)
            }
            Spacer(modifier = Modifier.height(38.dp))
            CustomCardSlot()
        }
    }
}

@Composable
private fun MentorCard(
    page: Int,
    pagerState: PagerState,
    mentorInfo: MentorsResponse,
    clickNext: () -> Unit = {},
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
            start = 0.2f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
        )

    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .height(418.dp)
                .background(color = Color.Gray)
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
                    Text(text = " 멘토님", style = WizdumTheme.typography.body1)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(197.dp)
                        .height(197.dp)
                        .background(color = Color.Green)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mentorInfo.mentoTitle,
                    style = WizdumTheme.typography.h3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "레벨", style = WizdumTheme.typography.body2)
                    Text(text = "⭐⭐⭐", style = WizdumTheme.typography.body2)
                    Text(text = "적당히 강하게", style = WizdumTheme.typography.body2)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.Green)
                    .align(Alignment.BottomCenter)
                    .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .clickable {
                        clickNext()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "강의 미리보기", style = WizdumTheme.typography.body1)
            }
        }
    }
}

@Composable
fun CustomCardSlot() {
    val dummyList = listOf(
        MentorsResponse(
            mentoName = "스파르타",
            mentoTitle = "스파르타 코딩클럽",
            itemLevel = "HIGH"
        ),
        MentorsResponse(
            mentoName = "스파르타",
            mentoTitle = "스파르타 코딩클럽",
            itemLevel = "HIGH"
        ),
        MentorsResponse(
            mentoName = "스파르타",
            mentoTitle = "스파르타 코딩클럽",
            itemLevel = "HIGH"
        ),
        MentorsResponse(
            mentoName = "스파르타",
            mentoTitle = "스파르타 코딩클럽",
            itemLevel = "HIGH"
        )

    )
    val pagerState = rememberPagerState(pageCount = { dummyList.size }, initialPage = 1)

    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp),
    ) { page ->
        MentorCard(page = page, pagerState = pagerState, mentorInfo = dummyList[page])
    }
}

//@Preview
//@Composable
//fun MentorCardPreview() {
//    WizdumTheme {
//        MentorCard(
//            mentorInfo = MentorsResponse(
//                1,
//                "스파르타",
//                "스파르타 전사들과 함께하는 나약함은 용납되지 않는다!",
//                "강인한 정신력과 철저한 자기 훈련을 통해 목표를 달성하는 스파르타식 도전"
//            )
//        )
//    }

//}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun MentorMatchScreenPreview() {
//    WizdumTheme {
//        MentorContent(
//            mentors = MentorDetailResponse(
//                mentoName = "스파르타",
//                mentoTitle = "스파르타 코딩클럽",
//                itemLevel = "HIGH"
//            )
//        ) {}
//    }
}