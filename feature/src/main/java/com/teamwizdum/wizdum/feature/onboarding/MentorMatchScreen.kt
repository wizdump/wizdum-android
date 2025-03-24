package com.teamwizdum.wizdum.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.teamwizdum.wizdum.data.model.response.MentorsResponse
import com.teamwizdum.wizdum.designsystem.component.appbar.BackAppBar
import com.teamwizdum.wizdum.designsystem.component.button.WizdumFilledButton
import com.teamwizdum.wizdum.designsystem.component.screen.ErrorScreen
import com.teamwizdum.wizdum.designsystem.theme.Black100
import com.teamwizdum.wizdum.designsystem.theme.Black200
import com.teamwizdum.wizdum.designsystem.theme.Black500
import com.teamwizdum.wizdum.designsystem.theme.Black600
import com.teamwizdum.wizdum.designsystem.theme.Green200
import com.teamwizdum.wizdum.designsystem.theme.WizdumTheme
import com.teamwizdum.wizdum.feature.R
import com.teamwizdum.wizdum.feature.common.base.ErrorState
import com.teamwizdum.wizdum.feature.common.component.LevelInfoCard
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun MentorMatchRoute(
    viewModel: OnboardingViewModel = hiltViewModel(),
    interestId: Int,
    levelId: Int,
    categoryId: Int,
    onNavigateBack: () -> Unit,
    onNavigateToMentorDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var isDelayedLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isDelayedLoading = false
    }

    LaunchedEffect(Unit) {
        viewModel.getMentors(interestId, levelId, categoryId)
    }

    when {
        isDelayedLoading -> {
            MatchingInProgressScreen()
        }

        uiState.isLoading -> {
            MatchingInProgressScreen()
        }

        uiState.mentors.isNotEmpty() -> {
            MentorMatchScreen(
                uiState = uiState,
                onNavigateBack = onNavigateBack,
                onNavigateToDetail = onNavigateToMentorDetail
            )
        }

        uiState.handleException is ErrorState.DisplayError -> {
            ErrorScreen(
                retry = ((uiState.handleException as ErrorState.DisplayError).retry)
            )
        }
    }
}

@Composable
private fun MentorMatchScreen(
    uiState: OnboardingUiState,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackAppBar(onNavigateBack = onNavigateBack)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.mentor_title),
                    style = WizdumTheme.typography.h2
                )
                Image(
                    painter = painterResource(id = R.drawable.img_party_popper),
                    contentDescription = "멘토 매칭 성공",
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.height(38.dp))

            /**
             * 기존 여러 명의 멘토 추천에서 단일 멘토 추천으로 변경됨
             */
            LazyColumn {
                item {
                    uiState.mentors.forEach { mentor ->
                        RenewalMentorCard(mentorInfo = mentor)
                    }
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }

            // MentorCardPager(mentors, onNavigateToDetail)
        }
        WizdumFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp, start = 32.dp, end = 32.dp)
                .align(Alignment.BottomCenter),
            title = "강의 미리보기"
        ) {
            uiState.mentors.forEach { mentors ->
                onNavigateToDetail(mentors.classId)
            }
        }
    }
}

@Composable
fun RenewalMentorCard(mentorInfo: MentorsResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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

            val HexagonShape = GenericShape { size, _ ->
                val width = size.width
                val height = size.height
                val centerX = width / 2

                moveTo(centerX, 0f) // 상단 꼭짓점

                lineTo(width, height / 4) // 오른쪽 위 변
                lineTo(width, height * 3 / 4) // 오른쪽 아래 변
                lineTo(centerX, height) // 하단 꼭짓점
                lineTo(0f, height * 3 / 4) // 왼쪽 아래 변
                lineTo(0f, height / 4) // 왼쪽 위 변
                close() // 경로 닫기
            }

            Box(
                modifier = Modifier
                    .clip(HexagonShape)
                    .size(197.dp)
                    .background(color = Green200)
                    .padding(4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mentorInfo.logoImageFilePath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "멘토 프로필 이미지",
                    modifier = Modifier
                        .size(197.dp)
                        .clip(HexagonShape)
                        .background(color = Black100)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mentorInfo.classTitle,
                style = WizdumTheme.typography.h3_semib,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            LevelInfoCard(level = mentorInfo.itemLevel)
            HorizontalDivider(
                thickness = 1.dp,
                color = Black200,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Column {
                Text(
                    text = "이 강의를 들으면 무엇이 달라질까요?",
                    style = WizdumTheme.typography.body2_semib,
                    modifier = Modifier.fillMaxWidth()
                )
                // TODO: 서버 데이터로 변경해야 한다

                val temp = listOf(
                    "직관이 아닌 논리로 의사결정을 내리는 법을 배울 수 있어요.",
                    "조직을 운영하는 논리적 사고 프레임워크를 구축할 수 있어요.",
                    "상대의 행동을 예측하고, 최적의 협상 전략을 설계할 수 있어요."
                )
                Spacer(modifier = Modifier.height(8.dp))
                temp.forEach {
                    Row(verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(2.dp)
                                .background(color = Black500, shape = CircleShape)
                        )
                        Text(
                            text = it,
                            style = WizdumTheme.typography.body2,
                            color = Black600
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MentorCardPager(mentorList: List<MentorsResponse>, onNavigateToDetail: (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { mentorList.size }, initialPage = 1)

    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
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
fun MentorCard(
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
                    modifier = Modifier
                        .size(197.dp)
                        .background(color = Black600)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mentorInfo.classTitle,
                    style = WizdumTheme.typography.h3_semib,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                LevelInfoCard(level = mentorInfo.itemLevel)
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
fun RenewalMentorCardPreview() {
    WizdumTheme {
        RenewalMentorCard(
            mentorInfo = MentorsResponse(
                mentoName = "스파르타",
                classTitle = "스파르타 코딩클럽\n시작해보자",
                itemLevel = "HIGH"
            ),
        )
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
        MentorMatchScreen(
            uiState = OnboardingUiState(mentors = mentorList),
            onNavigateBack = {},
            onNavigateToDetail = {}
        )
    }
}