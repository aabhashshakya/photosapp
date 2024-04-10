package com.avas.photosapp.screens
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.avas.photosapp.helper.MEDIA_IMAGES_READ_PERMISSION
import com.avas.photosapp.helper.checkPermissionGranted
import com.avas.photosapp.helper.isTriangular
import com.avas.photosapp.helper.requestPermissionIfNeeded
import com.avas.photosapp.widgets.PhotoWithIndex

@Composable
fun MainScreen(

) {
    val context = LocalContext.current
    val photoUris = remember() { mutableStateListOf<Uri>() }

    var maxImagesCount by remember {
        mutableStateOf("50")
    }

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 2),
        onResult = { uris ->
            photoUris.clear()
            photoUris.addAll(uris)
        }

    )
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )

                }
            })

    val columnScrollState = rememberScrollState()
    val pattern = remember { Regex("^\\d+\$") }

    // UI
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(columnScrollState, true)
    ) {
        Text("Enter max number of images to display in a triangular sequence:")

        Spacer(Modifier.height(5.dp))

        TextField(value = maxImagesCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    maxImagesCount = it
                }
            })
        Spacer(Modifier.height(5.dp))

        Text("Images picked: ${photoUris.size}/2")

        Spacer(Modifier.height(5.dp))

        Button(onClick = {
            if (checkPermissionGranted(context, MEDIA_IMAGES_READ_PERMISSION)) {
                multiplePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                requestPermissionIfNeeded(context, MEDIA_IMAGES_READ_PERMISSION, permissionLauncher)
            }
        }) {
            Text("Pick Images")
        }

        // triangular sequence
        if (photoUris.size == 2 && maxImagesCount.isNotEmpty()) {
            val nonTriangularNumberIndexes = mutableListOf<Int>()

            for (i in 1..maxImagesCount.toInt()) {
                if (isTriangular(i)) {
                    Row(Modifier.horizontalScroll(rememberScrollState(), true)) {
                        nonTriangularNumberIndexes.forEach { index ->
                            PhotoWithIndex(image = photoUris[1], index = index)
                        }
                        PhotoWithIndex(image = photoUris[0], index = i)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    nonTriangularNumberIndexes.clear()
                } else {
                    nonTriangularNumberIndexes.add(i)
                }
            }
        }
    }


}



