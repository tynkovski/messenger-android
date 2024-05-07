package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTypography
import kotlinx.coroutines.delay

@Composable
private fun authFieldDefaultColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
    errorTextColor = MaterialTheme.colorScheme.error,
    errorSupportingTextColor = MaterialTheme.colorScheme.error,
    errorTrailingIconColor = MaterialTheme.colorScheme.error,
    errorLeadingIconColor = MaterialTheme.colorScheme.error,
)

private const val passwordChar = '●'
private val fieldStyle = MessengerTypography.labelLarge

@Composable
fun AuthField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    leading: ImageVector? = null,
    trailing: ImageVector? = null,
    supportText: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    passwordInput: Boolean = false,
    requestFocus: Boolean = false,
    colors: TextFieldColors = authFieldDefaultColors(),
    onTrailingIconClick: () -> Unit = {},
) {
    val hasText by remember(value) {
        derivedStateOf { value.isNotEmpty() }
    }

    val leadingIcon: (@Composable () -> Unit)? = leading?.let {
        {
            Icon(
                imageVector = it,
                contentDescription = null
            )
        }
    }

    val removeTextButton: (@Composable () -> Unit)? = if (hasText) {
        {
            IconButton(
                onClick = { onValueChange("") }
            ) {
                Icon(
                    imageVector = MessengerIcons.Close,
                    contentDescription = null
                )
            }
        }
    } else null

    val trailingIcon: (@Composable () -> Unit)? = trailing?.let {
        {
            IconButton(
                onClick = onTrailingIconClick
            ) {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    }

    val support: (@Composable () -> Unit)? = supportText?.let {
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = supportText,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }

    val keyboardOptions = if (passwordInput) {
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
    } else {
        KeyboardOptions.Default
    }

    val visualTransformation = if (passwordInput) {
        PasswordVisualTransformation(passwordChar)
    } else {
        VisualTransformation.None
    }

    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        singleLine = true,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(20.dp),
        textStyle = fieldStyle,
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = fieldStyle,
                text = hint,
                color = MaterialTheme.colorScheme.outline
            )
        },
        colors = colors,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon ?: removeTextButton,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        supportingText = support,
        isError = isError
    )

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            delay(200)
            focusRequester.requestFocus()
        }
    }
}