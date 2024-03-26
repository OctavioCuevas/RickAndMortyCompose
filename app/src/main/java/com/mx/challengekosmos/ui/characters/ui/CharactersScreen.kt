package com.mx.challengekosmos.ui.characters.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mx.challengekosmos.R
import com.mx.challengekosmos.ui.characters.data.models.RMCharacter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val characters by viewModel.characters.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    viewModel.loadCharacters(page = 1)

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
    }, content = { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            CharactersList(characters = characters)
        }
    })
}

@Composable
fun CharactersList(characters: List<RMCharacter>) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = stringResource(
                id = R.string.app_name
            ), modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn {
            items(characters) { character ->
                CharacterItem(
                    character = character
                )
            }
        }
    }
}

@Composable
fun CharacterItem(character: RMCharacter) {
    var visibleDetails by remember { mutableStateOf(false) }
    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.paddingMedium))
            .background(
                colorResource(
                    id = R.color.rm_blue
                )
            )
    ) {
        CharacterName(character.name)
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.paddingSmall)))
        CharacterImage(character.image)
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.paddingSmall)))
        DetailsButton(
            onClickButton = {
                visibleDetails = !visibleDetails
            }, visibleDetails = visibleDetails, Modifier.align(Alignment.End)
        )
        if (visibleDetails) {
            Details(character)
        }
    }
}

@Composable
fun CharacterName(name: String = stringResource(id = R.string.character_name)) {
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.paddingSmall)),
        fontSize = dimensionResource(id = R.dimen.titleSize).value.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.rm_green),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CharacterImage(imageURL: String) {
    val bitmapState: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

    val picassoTarget = rememberPicassoTarget(bitmapState)

    Picasso.get().load(imageURL).into(picassoTarget)

    bitmapState.value?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = "contentDescription",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun rememberPicassoTarget(bitmapState: MutableState<ImageBitmap?>): PicassoTarget {
    return remember {
        PicassoTarget(bitmapState)
    }
}

private class PicassoTarget(private val bitmapState: MutableState<ImageBitmap?>) : Target {
    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        bitmapState.value = bitmap?.asImageBitmap()
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        // Handle failure
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        // Placeholder preparation, if needed
    }
}

@Composable
fun DetailsButton(
    onClickButton: () -> Unit, visibleDetails: Boolean, modifier: Modifier = Modifier
) {
    TextButton(onClick = onClickButton, modifier) {
        Text(
            text = if (visibleDetails) stringResource(id = R.string.hide_details) else stringResource(
                id = R.string.show_details
            )
        )
    }
}

@Composable
fun Details(character: RMCharacter) {
    Column(Modifier.padding(dimensionResource(id = R.dimen.paddingMedium))) {
        DetailStatus(status = character.status)
        DetailSpecies(species = character.species)
        DetailType(type = character.type)
        DetailGender(gender = character.gender)
        DetailOrigin(origin = character.origin.name)
        DetailLocation(location = character.location.name)
    }
}

@Composable
fun DetailStatus(status: String) {
    Text(text = stringResource(id = R.string.details_title_status, status))
}

@Composable
fun DetailSpecies(species: String) {
    Text(text = stringResource(id = R.string.details_title_species, species))
}

@Composable
fun DetailType(type: String) {
    Text(text = stringResource(id = R.string.details_title_type, type))
}

@Composable
fun DetailGender(gender: String) {
    Text(text = stringResource(id = R.string.details_title_gender, gender))
}

@Composable
fun DetailOrigin(origin: String) {
    Text(text = stringResource(id = R.string.details_title_origin, origin))
}

@Composable
fun DetailLocation(location: String) {
    Text(text = stringResource(id = R.string.details_title_location, location))
}