package widget

import MainViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import common.IntUiThemes
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

/**
 * @Author : lcc
 * @CreateData : 2024/1/29
 * @Description:
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DecoratedWindowScope.TitleBarWidget(mainViewModel: MainViewModel) {
    val theme by mainViewModel.theme.collectAsState()
    val isExpanded by mainViewModel.isExpanded.collectAsState()
    val chooseFile by mainViewModel.chooseFile.collectAsState()
    TitleBar(Modifier.newFullscreenControls(), gradientStartColor = Color(0xFFF5D4C1)) {
        Row(
            Modifier.align(Alignment.Start)
                .padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Tooltip({
                Text(if (isExpanded) "折叠" else "展开")
            }) {
                IconButton({
                    mainViewModel.changeExpanded(!isExpanded)
                }) {
                    Icon(
                        if (isExpanded) painterResource("ic_menu_fold.svg") else painterResource("ic_menu_unfold.svg"),
                        if (isExpanded) "折叠" else "展开"
                    )
                }
            }
            if (chooseFile.file.isNotEmpty()) {
                Dropdown(Modifier.height(30.dp), menuContent = {
                    chooseFile.file.forEachIndexed { index, file ->
                        selectableItem(
                            selected = index == chooseFile.currentIndex,
                            onClick = {
                                mainViewModel.selectFile(index)
                            },
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
//                        val painterProvider =
//                            rememberResourcePainterProvider(it.icon, StandaloneSampleIcons::class.java)
//                        val painter by painterProvider.getPainter(Size(20))
//                        Icon(painter, "icon")
                                Text(file.name)
                            }
                        }
                    }
                }) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
//                        val painterProvider =
//                            rememberResourcePainterProvider(
//                                MainViewModel.currentView.icon,
//                                StandaloneSampleIcons::class.java,
//                            )
//                        val painter by painterProvider.getPainter(Size(20))
//                        Icon(painter, "icon")
                        Text(chooseFile.file.getOrNull(chooseFile.currentIndex)?.name ?: "")

                    }
                }
            }
        }

        Text(title)

        Row(Modifier.align(Alignment.End)) {
            Tooltip({
                when (theme) {
                    IntUiThemes.Light -> Text("Switch to light theme with light header")
                    IntUiThemes.LightWithLightHeader -> Text("Switch to dark theme")
                    IntUiThemes.Dark -> Text("Switch to light theme")
                }
            }) {
                IconButton({
                    mainViewModel.themeMode(
                        when (theme) {
                            IntUiThemes.Light -> IntUiThemes.LightWithLightHeader
                            IntUiThemes.LightWithLightHeader -> IntUiThemes.Dark
                            IntUiThemes.Dark -> IntUiThemes.Light
                        }
                    )
                }, Modifier.size(40.dp).padding(5.dp)) {
                    when (theme) {
                        IntUiThemes.Light -> Icon(
                            painter = painterResource("icons/lightTheme@20x20.svg"),
                            "Themes"
                        )

                        IntUiThemes.LightWithLightHeader -> Icon(
                            painter = painterResource("icons/lightWithLightHeaderTheme@20x20.svg"),
                            "Themes"
                        )

                        IntUiThemes.Dark -> Icon(
                            painter = painterResource("icons/darkTheme@20x20.svg"),
                            "Themes"
                        )
                    }
                }
            }
        }
    }
}