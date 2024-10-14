package com.example.simplerxapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.models.SubjectModel

@Composable
fun SubjectView(
    modifier: Modifier,
    subjectModel: SubjectModel
) {
    Surface(
        modifier = modifier,
        shadowElevation = 6.dp,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = subjectModel.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = subjectModel.languageCode,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = subjectModel.status,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable()
private fun ItemPreview() {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        SubjectView(
            modifier = Modifier.fillMaxWidth(),
            SubjectModel(
                id = "",
                title = "testTitle",
                status = "Status",
                languageCode = "DE"
            )
        )
    }
}