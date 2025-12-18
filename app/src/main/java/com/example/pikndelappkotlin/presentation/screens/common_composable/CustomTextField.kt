package com.example.pikndelappkotlin.presentation.screens.common_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "",
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isEditable: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    onlyNumbers: Boolean = false,
    maxLines: Int = 1,
    prefix: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisibility by remember { mutableStateOf(false) }

    val containerShape = RoundedCornerShape(10.dp)
    val borderColor = if (isError) MaterialTheme.colorScheme.error else Color.Gray
    val containerColor = Color(0xBFEAE8E8)

    Box(modifier = modifier.fillMaxWidth()) {
        // Compact container with custom border and background
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = borderColor, shape = containerShape)
                .background(color = containerColor, shape = containerShape)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            if (leadingIcon != null) {
                leadingIcon()
            }

            if (prefix != null) {
                Box(modifier = Modifier.padding(end = 4.dp)) {
                    prefix()
                }
            }

            // Text field area
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty() && placeholderText.isNotEmpty()) {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = {
                        if (!onlyNumbers || it.all { char -> char.isDigit() }) {
                            onValueChange(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = singleLine,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = maxLines,
                    readOnly = !isEditable,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            onImeAction?.invoke()
                        },
                        onGo = { onImeAction?.invoke() }
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                    visualTransformation = if (isPassword && !passwordVisibility) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    }
                )
            }

            val trailing = trailingIcon ?: if (isPassword) {
                {
                    val icon =
                        if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else null

            if (trailing != null) {
                trailing()
            }
        }
    }
    Spacer(Modifier.height(3.dp))
    if (isError && !errorMessage.isNullOrBlank()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall
        )
    }

}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    placeholderText: String = "",
//    modifier: Modifier = Modifier,
//    isPassword: Boolean = false,
//    isEditable: Boolean = true,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    keyboardType: KeyboardType = KeyboardType.Text,
//    imeAction: ImeAction = ImeAction.Done,
//    onImeAction: (() -> Unit)? = null,
//    onlyNumbers: Boolean = false,
//    maxLines: Int = 1,
//    prefix: @Composable (() -> Unit)? = null,
//    singleLine: Boolean = true,
//    isError: Boolean = false,
//    errorMessage: String? = null
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    var passwordVisibility by remember { mutableStateOf(false) }
//    OutlinedTextField(
//        value = value,
//        onValueChange = {
//            if (!onlyNumbers || it.all { char -> char.isDigit() }) {
//                onValueChange(it)
//            }
//        },
//        singleLine = singleLine,
//        placeholder = {
//            Text(
//                text = placeholderText,
//                style = MaterialTheme.typography.bodyLarge
//            )
//        },
//        prefix = prefix,
//        modifier = modifier
//            .heightIn(min = 50.dp)
//            .fillMaxWidth(),
//        maxLines = maxLines,
//        readOnly = !isEditable,
//        isError = isError,
//        textStyle = TextStyle(fontSize = 16.sp),
//        visualTransformation = if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
//        trailingIcon = trailingIcon ?: if (isPassword) {
//            {
//                val icon =
//                    if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff
//                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
//                    Icon(
//                        imageVector = icon,
//                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
//                    )
//                }
//            }
//        } else null,
//        shape = RoundedCornerShape(10.dp),
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = Color.Gray,
//            unfocusedBorderColor = Color.Gray,
//            focusedTrailingIconColor = Color.Gray,
//            unfocusedTrailingIconColor =  Color.Gray,
//            focusedLeadingIconColor =  Color.Gray,
//            unfocusedLeadingIconColor =  Color.Gray,
//            focusedLabelColor = Color.DarkGray,
//            unfocusedLabelColor = Color.DarkGray,
//            cursorColor = Color.DarkGray,
//            unfocusedContainerColor = Color(0xBFEAE8E8)
//
//
//
//
//        ),
//        keyboardOptions = KeyboardOptions.Default.copy(
//            keyboardType = keyboardType,
//            imeAction = imeAction
//        ),
//        keyboardActions = KeyboardActions(
//            onDone = {
//                keyboardController?.hide()
//                onImeAction?.invoke()
//            },
//            onGo = { onImeAction?.invoke() }
//
//        ),
//        leadingIcon = leadingIcon,
//        supportingText = if (isError && !errorMessage.isNullOrBlank()) {
//            {
//                Text(
//                    text = errorMessage,
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.titleSmall
//                )
//            }
//        } else null
//
//    )
//
//}