package com.swalisoft.payer.core.composable.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.swalisoft.payer.app.AppColorSchema


@Composable
fun CustomOutlinedField(
  value: String,
  label: String,
  placeholder: String,
  keyboardOptions: KeyboardOptions = KeyboardOptions(
    keyboardType = KeyboardType.Text,
    imeAction = ImeAction.Next,
  ),
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onChange: (String) -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      color = Color.White,
    )

    OutlinedTextField(
      value = value,
      onValueChange = onChange,
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(10.dp),
      singleLine = true,
      colors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = AppColorSchema.BorderColor,
      ),
      placeholder = {
        Text(
          text = placeholder,
          color = AppColorSchema.PlaceholderColor,
        )
      },
      visualTransformation = visualTransformation,
      keyboardOptions = keyboardOptions,
    )
  }
}
