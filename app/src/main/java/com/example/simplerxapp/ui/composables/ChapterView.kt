package com.example.simplerxapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.models.ChapterModel

@Composable
fun ChapterView(
    modifier: Modifier,
    chapter: ChapterModel
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
                    text = chapter.title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Text(
                modifier = Modifier,
                text = chapter.description ?: "no description",
                style = MaterialTheme.typography.bodySmall
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = chapter.type,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = chapter.status,
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
        ChapterView(
            modifier = Modifier.fillMaxWidth(),
            ChapterModel(
                id = "",
                title = "testTitle",
                status = "Status",
                type = "LESSON",
                description = "This is a chapter description",
                template = false,
                subjectId = "112233"
            )
        )
    }
}