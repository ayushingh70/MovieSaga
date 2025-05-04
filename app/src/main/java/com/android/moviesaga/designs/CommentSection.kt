package com.example.moviesaga.designs

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CommentSection(type: String, id: String) {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().reference
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var commentText by remember { mutableStateOf(TextFieldValue("")) }
    var comments by remember { mutableStateOf(listOf<Comment>()) }
    var replyingToId by remember { mutableStateOf<String?>(null) }

    // Load comments
    LaunchedEffect(id) {
        val commentRef = database.child(type).child(id).child("comments")
        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newComments = mutableListOf<Comment>()
                for (commentSnapshot in snapshot.children) {
                    val comment = commentSnapshot.getValue(Comment::class.java)
                    if (comment != null) {
                        newComments.add(comment.copy(id = commentSnapshot.key ?: ""))
                    }
                }
                comments = newComments.sortedBy { it.timestamp }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load comments", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Comments", color = Color.White, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(12.dp))

        comments.filter { it.replyTo == null }.forEach { parentComment ->
            CommentItem(comment = parentComment, onReply = {
                replyingToId = parentComment.id

                val firstName = parentComment.username.split(" ").first()
                val newText = "@$firstName "
                commentText = TextFieldValue(
                    text = newText,
                    selection = TextRange(newText.length)
                )

                coroutineScope.launch {
                    bringIntoViewRequester.bringIntoView()
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            })

            val replies = comments.filter { it.replyTo == parentComment.id }
            if (replies.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 6.dp)
                        .background(Color(0xFF2E2E2E), shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    replies.forEach { reply ->
                        CommentItem(comment = reply, onReply = {
                            replyingToId = parentComment.id

                            val firstName = reply.username.split(" ").first()
                            val newText = "@$firstName "
                            commentText = TextFieldValue(
                                text = newText,
                                selection = TextRange(newText.length)
                            )

                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                                focusRequester.requestFocus()
                                keyboardController?.show()
                            }
                        }, isReply = true)
                    }
                }
            }

            Divider(
                color = Color.Gray.copy(alpha = 0.3f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = commentText,
                onValueChange = { commentText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                placeholder = { Text("Write a comment...", color = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        sendComment(auth, database, context, type, id, commentText.text, replyingToId) {
                            commentText = TextFieldValue("")
                            replyingToId = null
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF1A1A1A),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            )
            Button(
                onClick = {
                    focusManager.clearFocus()
                    sendComment(auth, database, context, type, id, commentText.text, replyingToId) {
                        commentText = TextFieldValue("")
                        replyingToId = null
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6C63FF)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.height(55.dp)
            ) {
                Text("Send")
            }
        }
    }
}

private fun sendComment(
    auth: FirebaseAuth,
    database: DatabaseReference,
    context: Context,
    type: String,
    id: String,
    commentText: String,
    replyToId: String?,
    onComplete: () -> Unit
) {
    val user = auth.currentUser
    if (user != null && commentText.isNotBlank()) {
        val uid = user.uid
        val commentRef = database.child(type).child(id).child("comments").push()

        database.child("users").child(uid).child("username").get()
            .addOnSuccessListener { snapshot ->
                val username = snapshot.getValue(String::class.java) ?: user.displayName ?: "Unknown User"
                val timestamp = System.currentTimeMillis()
                val comment = Comment(uid, username, commentText.trim(), timestamp, replyTo = replyToId)

                commentRef.setValue(comment)
                onComplete()
            }
            .addOnFailureListener {
                val fallbackName = user.displayName ?: "Unknown User"
                val timestamp = System.currentTimeMillis()
                val comment = Comment(uid, fallbackName, commentText.trim(), timestamp, replyTo = replyToId)
                commentRef.setValue(comment)
                onComplete()
            }
    } else {
        Toast.makeText(context, "You must be logged in to comment", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CommentItem(comment: Comment, onReply: () -> Unit, isReply: Boolean = false) {
    val formattedTime = remember(comment.timestamp) {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        sdf.format(Date(comment.timestamp))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "${comment.username} • $formattedTime",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.weight(1f)
            )
            Text(
                "Reply",
                color = Color(0xFF6C63FF),
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable { onReply() }
                    .padding(end = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = if (isReply) 16.dp else 0.dp, top = 4.dp)
        ) {
            CommentText(comment.comment)
        }
    }
}

@Composable
fun CommentText(text: String) {
    val annotatedText = buildAnnotatedString {
        if (text.startsWith("@")) {
            val firstSpace = text.indexOf(' ')
            if (firstSpace != -1) {
                val usernamePart = text.substring(0, firstSpace)
                val commentPart = text.substring(firstSpace + 1)

                withStyle(SpanStyle(color = Color(0xFF6C63FF), fontSize = 13.sp)) {
                    append(usernamePart)
                }
                append(" ")
                withStyle(SpanStyle(color = Color.White, fontSize = 16.sp)) {
                    append(commentPart)
                }
            } else {
                withStyle(SpanStyle(color = Color(0xFF6C63FF), fontSize = 13.sp)) {
                    append(text)
                }
            }
        } else {
            withStyle(SpanStyle(color = Color.White, fontSize = 16.sp)) {
                append(text)
            }
        }
    }

    Text(annotatedText)
}

// Data class
data class Comment(
    val userId: String = "",
    val username: String = "",
    val comment: String = "",
    val timestamp: Long = 0L,
    val replyTo: String? = null,
    val id: String = ""
)