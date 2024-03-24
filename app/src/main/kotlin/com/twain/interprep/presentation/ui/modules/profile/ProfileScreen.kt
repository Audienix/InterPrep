package com.twain.interprep.presentation.ui.modules.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.BuildConfig
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.ui.ProfileSettingsData.ClickAction
import com.twain.interprep.data.ui.ProfileSettingsData.ProfileSettingsItemData
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.data.ui.TextInputType
import com.twain.interprep.helper.LocalizationViewModel
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.IPAvatar
import com.twain.interprep.presentation.ui.components.generic.IPChromeTab
import com.twain.interprep.presentation.ui.components.generic.IPCircleTextIcon
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPMultipleChoiceAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPTextInputDialog
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.getNameInitials

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setProfileSettings()
        viewModel.getAppThemeOptions(context)
        viewModel.getNotificationOptions(context)
    }

    Scaffold(
        topBar = {
            ProfileTopBar(getNameInitials(input = viewModel.preferenceItem.userName)) {
                navController.popBackStack()
            }
        },
        containerColor = MaterialColorPalette.surface
    ) { paddingValues ->

        viewModel.action?.let {
            HandleAction(action = it, navController = navController)
        }

        ProfileColumn(
            modifier = Modifier.padding(paddingValues),
            items = viewModel.getProfileSettingsItemDataList(context),
            onItemClick = viewModel::setAction
        )
    }
}

@Composable
fun ProfileTopBar(
    name: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dimension_188dp))
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dimension_140dp))
                .background(MaterialColorPalette.secondaryContainer)
        )
        IPIcon(
            imageVector = Icons.Filled.ArrowBack,
            tint = MaterialColorPalette.onSurfaceVariant,
            onIconClick = onBackClick
        )
        IPCircleTextIcon(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            size = dimensionResource(id = R.dimen.dimension_icon_size_96),
            text = name,
            containerColor = MaterialColorPalette.secondary,
            textStyle = MaterialTheme.typography.displaySmall,
            textColor = MaterialColorPalette.onSecondary
        )
    }
}

@Composable
fun ProfileColumn(
    modifier: Modifier,
    items: List<ProfileSettingsItemData>,
    onItemClick: (ClickAction, String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        items(items = items) {
            ProfileColumnItem(data = it, onClick = onItemClick)
        }
        item {
            AppVersion()
        }
    }
}


@Composable
fun ProfileColumnItem(
    data: ProfileSettingsItemData,
    onClick: (ClickAction, String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.dimension_8dp))
                .clickable { onClick(data.clickAction, data.label) },
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
        ) {
            IPAvatar(
                size = dimensionResource(id = R.dimen.dimension_icon_size_40),
                containerColor = MaterialColorPalette.secondaryContainer,
                image = data.imageVector,
                imageRes = data.imageRes
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = data.title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialColorPalette.onSurface
                )
                Text(
                    text = data.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialColorPalette.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "",
                tint = MaterialColorPalette.onSurfaceVariant
            )
        }
        HorizontalDivider()
    }
}

@Composable
fun AppVersion() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "${stringResource(id = R.string.label_setting_app_version)}${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}

@Composable
fun HandleAction(
    action: ClickAction,
    navController: NavHostController
) {
    when (action) {
        ClickAction.NONE -> {}
        ClickAction.NAME -> HandleNameClick()
        ClickAction.PREFERRED_LANGUAGE -> HandleLanguageClick()
        ClickAction.APP_THEME -> HandleThemeCLick()
        ClickAction.NOTIFICATION_REMINDER -> HandleNotificationCLick()
        ClickAction.RATING_FEEDBACK -> HandleAppReview()
        ClickAction.PRIVACY_POLICY -> HandlePrivacyPolicyClick(navController = navController)
    }
}

@Composable
fun HandleNameClick(viewModel: ProfileViewModel = hiltViewModel()) {
    IPTextInputDialog(
        titleRes = R.string.label_setting_name,
        cancelButtonRes = R.string.button_cancel,
        confirmButtonRes = R.string.button_confirm,
        inputText = viewModel.currentPopupValue,
        onTextUpdate = { viewModel.currentPopupValue = it },
        textInputAttributes = TextInputAttributes(
            labelTextId = R.string.label_setting_name
        ),
        onCancelClick = { viewModel.setAction(ClickAction.NONE, "") },
        onConfirmClick = { viewModel.setName(viewModel.currentPopupValue) }
    )
}

@Composable
fun HandleLanguageClick(
    viewModel: ProfileViewModel = hiltViewModel(),
    localizationViewModel: LocalizationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    IPTextInputDialog(
        titleRes = R.string.label_setting_language,
        cancelButtonRes = R.string.button_cancel,
        confirmButtonRes = R.string.button_confirm,
        inputText = viewModel.currentPopupValue,
        onTextUpdate = { viewModel.currentPopupValue = it },
        textInputAttributes = TextInputAttributes(
            labelTextId = R.string.label_setting_language,
            inputType = TextInputType.DROPDOWN,
            imeAction = ImeAction.Done,
            singleLine = true
        ),
        onCancelClick = { viewModel.setAction(ClickAction.NONE, "") },
        onConfirmClick = {
            val langCode = localizationViewModel.getLanguageCode(viewModel.currentPopupValue)
            viewModel.setLanguage(viewModel.currentPopupValue, langCode)
            localizationViewModel.setLocale(context, langCode, false)
        }
    )
}

@Composable
fun HandleThemeCLick(viewModel: ProfileViewModel = hiltViewModel()) {
    IPMultipleChoiceAlertDialog(
        titleRes = R.string.label_setting_theme,
        cancelButtonRes = R.string.button_cancel,
        confirmButtonRes = R.string.button_confirm,
        onCancelClick = { viewModel.setAction(ClickAction.NONE) },
        onConfirmClick = { viewModel.setAppTheme() },
        options = viewModel.appThemeOptions,
        onChoiceSelected = viewModel::onAppThemeSelected,
        selectedIndex = viewModel.getSelectedAppThemeIndex()
    )
}
@Composable
fun HandleNotificationCLick(viewModel: ProfileViewModel = hiltViewModel()) {
    IPMultipleChoiceAlertDialog(
        titleRes = R.string.label_setting_notification,
        cancelButtonRes = R.string.button_cancel,
        confirmButtonRes = R.string.button_confirm,
        onCancelClick = { viewModel.setAction(ClickAction.NONE) },
        onConfirmClick = { viewModel.setNotification() },
        options = viewModel.notificationOptions,
        onChoiceSelected = viewModel::onNotificationSelected,
        selectedIndex = viewModel.getSelectedNotificationIndex()
    )
}

@Composable
fun HandleAppReview(viewModel: ProfileViewModel = hiltViewModel()) {
    viewModel.setAction(ClickAction.NONE)

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val appPackageName = context.packageName
        val intent = try {
            // open the Google Play app directly
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
        } catch (e: ActivityNotFoundException) {
            // fallback to the web browser if Google Play is not installed
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
        }
        context.startActivity(intent)
    }
}

@Composable
fun HandlePrivacyPolicyClick(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    viewModel.setAction(ClickAction.NONE)
    IPChromeTab(context = LocalContext.current, url = StringConstants.PRIVACY_POLICY_WEBSITE) {
        navController.navigate(AppScreens.MainScreens.PrivacyPolicy.route)
    }
}
