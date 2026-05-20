package com.example.dictionary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionary.R
import com.example.dictionary.domain.model.Dictionary
import com.example.dictionary.domain.model.Word


@Composable
fun DictionaryContent(
    dictionaries : List<Dictionary>,
    onRemoveWord : (Int, Int) -> Unit,
    onDeleteDictionary : (Int) -> Unit,
    onOpenAddWordDialog : (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dictionaries) { dictionary ->
            DictionaryCard(
                dictionary = dictionary,
                onAddWord = { onOpenAddWordDialog(dictionary.id) },
                onRemoveWord = { wordId -> onRemoveWord(dictionary.id, wordId) },
                onDeleteDictionary = { onDeleteDictionary(dictionary.id) })
        }
    }
}

@Composable
private fun DictionaryCard(
    dictionary : Dictionary,
    onAddWord : () -> Unit,
    onRemoveWord : (Int) -> Unit,
    onDeleteDictionary : () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val isSystem = dictionary.ownerId == null || dictionary.ownerId == 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dictionary.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                if (isSystem) {
                    SystemDictionaryLabel()
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = onAddWord) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_word)
                            )
                        }
                        IconButton(onClick = onDeleteDictionary) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_dictionary),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) stringResource(R.string.show_less) else stringResource(
                            R.string.show_more
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.words_count, dictionary.words.size),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))

                if (dictionary.words.isNotEmpty()) {
                    dictionary.words.forEach { word ->
                        WordItem(
                            word = word,
                            isSystem = isSystem,
                            onRemove = { if (!isSystem) onRemoveWord(word.id) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Text(
                        text = stringResource(R.string.no_words),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WordItem(
    word : Word, isSystem : Boolean, onRemove : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = word.engLang,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = word.rusLang,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        if (!isSystem) {
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.RemoveCircleOutline,
                    contentDescription = stringResource(R.string.remove_word),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        } else {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            ) {
                Text(
                    text = stringResource(R.string.system),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun SystemDictionaryLabel() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 2.dp
    ) {
        Text(
            text = stringResource(R.string.system),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}