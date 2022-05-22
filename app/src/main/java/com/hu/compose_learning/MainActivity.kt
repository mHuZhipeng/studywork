package com.hu.compose_learning

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hu.compose_learning.data.Message
import com.hu.compose_learning.data.SampleData
import com.hu.compose_learning.ui.theme.Compose_learningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_learningTheme{
                Conversations(SampleData.conversationSample)
            }
        }
    }
}

@Composable
fun MessageCard(message: Message) {
    Row(modifier = Modifier.padding(8.dp)){
        Image(painter = painterResource(id = R.drawable.bg_a_women),
            contentDescription = stringResource(id = R.string.description_of_women_picture),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape))
        
        Spacer(modifier = Modifier.width(8.dp))
        // 跟踪message的展开状态
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(text = message.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.width(4.dp))
            Surface(shape = MaterialTheme.shapes.medium,
                color = surfaceColor,
                elevation = 1.dp){
                Text(text = message.content,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier.animateContentSize().padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview(name = "Light Mode",
        showBackground = true,
        widthDp = 320)
@Preview(
    name = "Dark Mode",
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320
)
@Composable
fun MessageCardPreview() {
    Compose_learningTheme {
        MessageCard(Message("Android","jetpack compose"))
    }
}

@Composable
fun Conversations(messages:List<Message>){
    LazyColumn{
        items(messages){message ->
            MessageCard(message = message)
        }
    }
}

@Preview(name = "Light Mode",
        showBackground = true)
@Preview(name = "Dark Mode",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun ConversationPreview(){
    Compose_learningTheme {
        Conversations(SampleData.conversationSample)
    }
}