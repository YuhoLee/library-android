package com.project.ibooku.presentation.ui.feature.home

import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.project.ibooku.data.remote.UrlLink.CENTRAL_HOMEPAGE_URL
import com.project.ibooku.domain.model.external.PopularBooksModel
import com.project.ibooku.presentation.R
import com.project.ibooku.presentation.ui.BottomNavigationBar
import com.project.ibooku.presentation.ui.NavItem
import com.project.ibooku.presentation.ui.StatusBarColorsTheme
import com.project.ibooku.presentation.ui.base.BaseLoadingIndicator
import com.project.ibooku.presentation.ui.theme.Gray10
import com.project.ibooku.presentation.ui.theme.Gray50
import com.project.ibooku.presentation.ui.theme.Gray60
import com.project.ibooku.presentation.ui.theme.Gray70
import com.project.ibooku.presentation.ui.theme.Gray80
import com.project.ibooku.presentation.ui.theme.IbookuTheme
import com.project.ibooku.presentation.ui.theme.PlaceHolderColor
import com.project.ibooku.presentation.ui.theme.SkyBlue10
import com.project.ibooku.presentation.ui.theme.White
import com.project.ibooku.presentation.ui.theme.notosanskr


/**
 * 홈 화면
 * @param
 */
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current
    val backPressStr = stringResource(id = R.string.back_press_toast_msg)

    BackHandler {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - backPressedTime < 2000) {
            // 2초 이내에 두 번 뒤로 가기 누름 -> 앱 종료
            (context as? android.app.Activity)?.finish()
        } else {
            // 처음 뒤로 가기 버튼을 누른 경우
            backPressedTime = currentTime
            Toast.makeText(context, backPressStr, Toast.LENGTH_SHORT).show()
        }
    }

    StatusBarColorsTheme(statusBarColor = SkyBlue10)

    IbookuTheme {
        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().background(SkyBlue10)) {
                    HomeHeaderTop(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(15.dp)
                    )
                }
            }
        ) {
            val homeState = viewModel.homeState.collectAsStateWithLifecycle()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    HomeHeader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        navController.navigate(NavItem.BookSearch.route)
                    }

                    HomeBody(
                        popularBookList = homeState.value.popularBooks,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onReadReviewClick = {
                            navController.navigate(NavItem.BookReviewReadMap.route)
                        },
                        onWriteReviewClick = {
                            navController.navigate(NavItem.BookReviewOnboarding.route)
                        },
                        onPopularBookClick = { model ->
                            val isbn = model.isbn
                            if(isbn.isNotEmpty()){
                                val route = NavItem.BookDetail.route.replace("{isbn}", isbn)
                                navController.navigate(route)
                            }
                        }
                    )
                }

            }

        }
    }

}


/**
 * 홈 화면
 * @param
 */
@Composable
@Preview(showBackground = true, heightDp = 2000)
fun HomeScreenPreview() {
    val dummyPopularBookList = listOf(
        PopularBooksModel(
            bookName = "이 책 진짜 귀함",
            bookDetailUrl = "",
            isbn = "3",
            bookImgUrl = "",
            authors = "나나",
            ranking = "1",
            additionSymbol = "",
            className = "ㅎㅎ",
            loanCount = "5",
            publisher = "",
            publicationYear = "2024.08.18"
        ),
        PopularBooksModel(
            bookName = "이 책 진짜 귀함2",
            bookDetailUrl = "",
            isbn = "3",
            bookImgUrl = "",
            authors = "나나",
            ranking = "2",
            additionSymbol = "",
            className = "ㅎㅎ",
            loanCount = "5",
            publisher = "",
            publicationYear = "2024.08.18"
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            HomeHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

            }

            HomeBody(
                popularBookList = dummyPopularBookList,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onReadReviewClick = {},
                onWriteReviewClick = {},
                onPopularBookClick = {}
            )
        }

    }

}

@Composable
private fun HomeHeader(modifier: Modifier = Modifier, onSearchBarClick: () -> Unit) {

    Column(
        modifier = modifier
            .background(SkyBlue10)
            .padding(15.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        HomeHeaderSearch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            onSearchBarClick()
        }
    }
}

@Composable
private fun HomeHeaderTop(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.wrapContentSize()) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = White,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.home_header_text),
                color = White,
                fontSize = 12.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontFamily = notosanskr
            )
        }

        Row(modifier = Modifier.wrapContentSize()) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Default.Notifications,
                    tint = White,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Default.Person,
                    tint = White,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun HomeHeaderSearch(modifier: Modifier = Modifier, onSearchBarClick: () -> Unit) {
    Surface(modifier = modifier, shape = RoundedCornerShape(100),
        onClick = {
            onSearchBarClick()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(White)
                .padding(start = 26.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.home_header_search_place_holder),
                color = PlaceHolderColor,
                fontSize = 14.sp,
                fontFamily = notosanskr,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Search,
                    tint = SkyBlue10,
                    contentDescription = null
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
private fun HomeBody(
    popularBookList: List<PopularBooksModel>,
    modifier: Modifier = Modifier,
    onWriteReviewClick: () -> Unit,
    onReadReviewClick: () -> Unit,
    onPopularBookClick: (PopularBooksModel) -> Unit
) {
    val context = LocalContext.current
    Surface(modifier = modifier.background(Color.Transparent), shape = RoundedCornerShape(16.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Gray10)
        ) {
            HomeBodyReview(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onWriteReviewClick = onWriteReviewClick,
                onReadReviewClick = onReadReviewClick
            )
            Spacer(modifier = Modifier.height(26.dp))

            if(popularBookList.isNotEmpty()){
                HomeBodyPopularBook(
                    popularBookList = popularBookList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onPopularBookClick = onPopularBookClick
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HomeBodyHelp(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                object : HomeHelpClickListener {
                    override fun onUserGuideClick() {

                    }

                    override fun onHomePageClick() {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(CENTRAL_HOMEPAGE_URL))
                        context.startActivity(intent)
                    }

                    override fun onCsClick() {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            // 이메일만 처리할 수 있는 인텐트를 설정합니다.
                            data = Uri.parse("mailto:")
                            // 받는 사람 이메일 주소
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("rigizer@gmail.com"))

                        }
                        context.startActivity(emailIntent)
                    }

                }
            )
        }
    }
}

@Composable
private fun HomeBodyReview(
    modifier: Modifier = Modifier,
    onWriteReviewClick: () -> Unit,
    onReadReviewClick: () -> Unit
) {
    Column(modifier = modifier.padding(start = 18.dp, end = 18.dp, top = 28.dp, bottom = 16.dp)) {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(id = R.string.home_body_review),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        HomeBodyReviewWrite(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onWriteReviewClick = onWriteReviewClick
        )

        Spacer(modifier = Modifier.height(14.dp))

        HomeBodyReviewRead(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onReadReviewClick = onReadReviewClick
        )
    }
}

@Composable
private fun HomeBodyReviewWrite(modifier: Modifier = Modifier, onWriteReviewClick: () -> Unit) {
    Surface(
        modifier = modifier.background(White),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        onClick = onWriteReviewClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(White)
                .padding(start = 22.dp, end = 6.dp, top = 16.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()

            ) {
                Text(
                    text = stringResource(id = R.string.home_body_review_write_desc),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Gray50
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.home_body_review_write_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray70
                )
            }

            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Gray60,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun HomeBodyReviewRead(modifier: Modifier = Modifier, onReadReviewClick: () -> Unit) {
    Surface(
        modifier = modifier.background(White),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        onClick = onReadReviewClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(White)
                .padding(start = 14.dp, end = 6.dp, top = 16.dp, bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    val randInt = (0..2).random()
                    val descState by remember {
                        mutableIntStateOf(randInt)
                    }
                    val descStrId = when (descState) {
                        0 -> R.string.home_body_review_read_desc1
                        1 -> R.string.home_body_review_read_desc2
                        else -> R.string.home_body_review_read_desc3
                    }

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = descStrId),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray50
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.home_body_review_read_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray70
                    )
                }

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = Gray60,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(), painter = painterResource(
                    id = R.drawable.img_home_map_example
                ), contentDescription = null
            )
        }
    }
}

@Composable
private fun HomeBodyPopularBook(
    popularBookList: List<PopularBooksModel>,
    modifier: Modifier = Modifier,
    onPopularBookClick: (PopularBooksModel) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.home_body_popular_book),
                    color = Gray80,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Gray10),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    modifier = Modifier.background(Gray10),
                    text = stringResource(id = R.string.home_body_popular_book_see_details),
                    color = SkyBlue10,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 6.dp, start = 12.dp, end = 12.dp)
        ) {
            items(items = popularBookList) { item ->
                HomeBodyPopularBookItem(
                    item = item,
                    modifier = Modifier
                        .width(240.dp)
                        .wrapContentHeight(),
                    onPopularBookClick = onPopularBookClick
                )
            }
        }

    }
}

@Composable
fun HomeBodyPopularBookItem(
    item: PopularBooksModel,
    modifier: Modifier = Modifier,
    onPopularBookClick: (PopularBooksModel) -> Unit
) {
    Surface(
        modifier = modifier
            .padding(6.dp)
            .border(width = 0.5.dp, color = Gray10, RoundedCornerShape(10.dp)),
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onPopularBookClick(item)
        }
    ) {
        Row(
            modifier = Modifier
                .background(White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.ranking,
                color = Gray70,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(12.dp))

            AsyncImage(
                modifier = Modifier.size(50.dp, 74.dp),
                model = item.bookImgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = item.bookName,
                    color = Gray70,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
                Text(
                    text = item.authors,
                    color = Gray50,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun HomeBodyHelp(modifier: Modifier = Modifier, helpClickListener: HomeHelpClickListener) {
    Column(modifier = modifier.padding(14.dp)) {
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
            text = stringResource(id = R.string.home_body_help),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Gray80
        )

        Spacer(modifier = Modifier.height(10.dp))

        HomeBodyHelpUserGuide(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) { helpClickListener.onUserGuideClick() }

        Spacer(modifier = Modifier.height(14.dp))

        HomeBodyHelpHomepageAndCs(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            { helpClickListener.onHomePageClick() },
            { helpClickListener.onCsClick() }
        )

    }
}

@Composable
private fun HomeBodyHelpUserGuide(modifier: Modifier = Modifier, onUserGuideClick: () -> Unit) {
    Surface(
        modifier = modifier
            .background(White),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        onClick = onUserGuideClick
    ) {
        Row(modifier = Modifier.background(White)) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(White)
                    .padding(20.dp)
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(
                        id = R.drawable.img_home_help_profile
                    ),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.home_body_help_guide_title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray70
                    )

                    Text(
                        text = stringResource(id = R.string.home_body_help_guide_desc),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray50
                    )
                }

            }

            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Close,
                        tint = Gray50,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeBodyHelpHomepageAndCs(
    modifier: Modifier = Modifier,
    onHomePageClick: () -> Unit,
    onCsClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .background(White),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            onClick = onHomePageClick
        ) {
            Row(
                modifier = Modifier
                    .background(White)
                    .padding(horizontal = 15.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(
                        id = R.drawable.img_home_homepage
                    ),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.home_body_help_homepage_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Gray70
                    )

                    Text(
                        text = stringResource(id = R.string.home_body_help_homepage_desc),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray50
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Surface(
            modifier = Modifier
                .weight(1f)
                .background(White),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            onClick = onCsClick
        ) {
            Row(
                modifier = Modifier
                    .background(White)
                    .padding(horizontal = 15.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(
                        id = R.drawable.img_home_cs
                    ),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.home_body_help_cs_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Gray70
                    )

                    Text(
                        text = stringResource(id = R.string.home_body_help_cs_desc),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray50
                    )
                }
            }
        }
    }
}


interface HomeHelpClickListener {
    fun onUserGuideClick()
    fun onHomePageClick()
    fun onCsClick()
}