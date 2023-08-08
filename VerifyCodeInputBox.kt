package com.cesiumai.rv2.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerifyCodeInputBoxItem(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    color: Color = Color(0xFF121A26),
    fontWeight: FontWeight = FontWeight.Bold,
    text: String = "1"
) {
    Box(
        modifier = modifier
            .background(color = Color(0xFFEBF2FF), shape = RoundedCornerShape(7.dp))
            .border(width = 0.5.dp, color = Color(0xFFDEEAFF), shape = RoundedCornerShape(7.dp))
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun VerifyCodeInputBox(
    modifier: Modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        .height(52.dp),
    size: Int = 6,
    boxSpacerSize: Dp = 14.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    provideItem: @Composable RowScope.(text: String) -> Unit = {
        VerifyCodeInputBoxItem(
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight(),
            text = it
        )
    },
    onTextChange: (String) -> Unit = {}
) {
    var code by remember {
        mutableStateOf(
            TextFieldValue(text = "", selection = TextRange(Int.MAX_VALUE))
        )
    }

    BasicTextField(
        value = code,
        onValueChange = {
            if (it.text.length < code.text.length) {
                //删除了一个，直接从最后面删，然后光标移到最后面
                code = TextFieldValue(
                    code.text.substring(0, code.text.lastIndex),
                    selection = TextRange(Int.MAX_VALUE)
                )
                onTextChange(code.text)
            } else if (it.text.length <= size) {
                code = TextFieldValue(text = it.text, selection = TextRange(Int.MAX_VALUE))
                onTextChange(code.text)
            }
        },
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Box(modifier = Modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(boxSpacerSize)
                ) {
                    for (i in 0 until size) {
                        provideItem(
                            this,
                            if (code.text.length > i) code.text.substring(i, i + 1) else ""
                        )
                    }
                }
            }
        }
    )
}