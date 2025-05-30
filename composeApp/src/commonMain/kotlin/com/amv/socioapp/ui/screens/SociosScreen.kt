package com.amv.socioapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.size
import coil3.compose.AsyncImage
import com.amv.socioapp.model.Categoria
import com.amv.socioapp.model.Socio
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SociosScreen(
    socios: List<Socio> = listOf(
        Socio(
            dni = "10102030A", nSocio = 16, nombre = "Ricardo", apellidos = "Moya Soler",
            telefono = "600101010", correo = "ricardo.moya@example.com", fechaNacimiento = "1975-04-12",
            fechaAntiguedad = "2005-01-10", categoria = com.amv.socioapp.model.Categoria.ADULTO, esAbonado = true,
            direccion = "Calle Luna 23, Madrid", urlImagen = "https://picsum.photos/seed/ricardo/200"
        ),
        Socio(
            dni = "20203040B", nSocio = 17, nombre = "Isabel", apellidos = "Garrido Núñez",
            telefono = "611202020", correo = "isabel.garrido@example.com", fechaNacimiento = "1990-08-22",
            fechaAntiguedad = "2018-06-15", categoria = com.amv.socioapp.model.Categoria.ADULTO, esAbonado = false,
            direccion = "Avenida del Puerto 8, Valencia", urlImagen = "https://picsum.photos/seed/isabel/200"
        ),
        Socio(
            dni = "30304050C", nSocio = 18, nombre = "Alberto", apellidos = "Campos Durán",
            telefono = null, correo = "alberto.campos@example.com", fechaNacimiento = "2013-05-30",
            fechaAntiguedad = "2022-03-01", categoria = com.amv.socioapp.model.Categoria.INFANTIL, esAbonado = true,
            direccion = "Plaza Nueva 1, Sevilla", urlImagen = "https://picsum.photos/seed/alberto/200"
        ),
        Socio(
            dni = "40405060D", nSocio = 19, nombre = "Beatriz", apellidos = "Vidal Cruz",
            telefono = "633404040", correo = null, fechaNacimiento = "1962-11-05",
            fechaAntiguedad = "2000-11-20", categoria = com.amv.socioapp.model.Categoria.SENIOR, esAbonado = true,
            direccion = "Calle del Carmen 15, Barcelona", urlImagen = "https://picsum.photos/seed/beatriz/200"
        ),
        Socio(
            dni = "50506070E", nSocio = 20, nombre = "Óscar", apellidos = "Prieto Ramos",
            telefono = "644505050", correo = "oscar.prieto@example.com", fechaNacimiento = "1983-02-18",
            fechaAntiguedad = "2010-09-05", categoria = com.amv.socioapp.model.Categoria.ADULTO, esAbonado = true,
            direccion = null, urlImagen = "https://picsum.photos/seed/oscar/200"
        ),
        Socio(
            dni = "60607080F", nSocio = 21, nombre = "Mónica", apellidos = "Serrano Bravo",
            telefono = "655606060", correo = "monica.serrano@example.com", fechaNacimiento = "2016-09-10",
            fechaAntiguedad = "2023-01-15", categoria = com.amv.socioapp.model.Categoria.INFANTIL, esAbonado = false,
            direccion = "Paseo de la Castellana 100, Madrid", urlImagen = "https://picsum.photos/seed/monica/200"
        ),
        Socio(
            dni = "70708090G", nSocio = 22, nombre = "Fernando", apellidos = "Reyes Soto",
            telefono = null, correo = null, fechaNacimiento = "1953-07-25",
            fechaAntiguedad = "1995-05-01", categoria = com.amv.socioapp.model.Categoria.SENIOR, esAbonado = true,
            direccion = "Calle Ancha 7, Granada", urlImagen = "https://picsum.photos/seed/fernando/200"
        ),
        Socio(
            dni = "80809000H", nSocio = 23, nombre = "Natalia", apellidos = "Flores Vega",
            telefono = "677808080", correo = "natalia.flores@example.com", fechaNacimiento = "1998-12-01",
            fechaAntiguedad = "2020-02-20", categoria = com.amv.socioapp.model.Categoria.ADULTO, esAbonado = true,
            direccion = "Urbanización Las Lomas 22, Marbella", urlImagen = null
        ),
        Socio(
            dni = "90900010I", nSocio = 24, nombre = "Hugo", apellidos = "Pascual Ibáñez",
            telefono = "688909090", correo = "hugo.pascual@example.com", fechaNacimiento = "2014-03-03",
            fechaAntiguedad = "2022-09-10", categoria = com.amv.socioapp.model.Categoria.INFANTIL, esAbonado = true,
            direccion = "Calle del Pez 5, Madrid", urlImagen = "https://picsum.photos/seed/hugo/200"
        ),
        Socio(
            dni = "00011120J", nSocio = 25, nombre = "Silvia", apellidos = "Aguilar Roca",
            telefono = "699000000", correo = "silvia.aguilar@example.com", fechaNacimiento = "1960-01-20",
            fechaAntiguedad = "1998-07-01", categoria = com.amv.socioapp.model.Categoria.SENIOR, esAbonado = false,
            direccion = "Avenida Diagonal 300, Barcelona", urlImagen = "https://picsum.photos/seed/silvia/200"
        ),
        Socio(
            dni = "11122230K", nSocio = 26, nombre = "Daniel", apellidos = "Lorenzo Ortiz",
            telefono = "711111111", correo = "daniel.lorenzo@example.com", fechaNacimiento = "1979-06-14",
            fechaAntiguedad = "2008-04-11", categoria = com.amv.socioapp.model.Categoria.ADULTO, esAbonado = true,
            direccion = "Calle Mayor 55, Zaragoza", urlImagen = "https://picsum.photos/seed/daniel/200"
        ),
    )
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Socio>()
    val selectedItem = scaffoldNavigator.currentDestination?.contentKey

    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        scaffoldState = scaffoldNavigator.scaffoldState,
        listPane = {
            AnimatedPane {
                ListPaneContent(
                    items = socios,
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                selectedItem?.let { item ->
                    DetailPaneContent(item)
                }
            }
        }
    )
    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }
}

@Composable
fun ListPaneContent(
    items: List<Socio>,
    onItemClick: (Socio) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) { 
        items(items) { item ->
            ListItem(
                headlineContent = { Text(item.obtenerNombreCompleto()) },
                supportingContent = {
                    Row (modifier = Modifier.fillMaxWidth()) {
                        Text(text = item.categoria.name, modifier = Modifier.weight(1f))
                        Text(text = item.fechaAntiguedad, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                    }
                },
                leadingContent = {
                    SocioAvatar(
                        imageUrl = item.urlImagen,
                        contentDescription = item.obtenerNombreCompleto()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
fun DetailPaneContent(socio: Socio) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

    }
}

@Composable
fun SocioAvatar(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp
) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(iconSize)
                .clip(CircleShape)
        )
    } else {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = contentDescription,
            modifier = modifier.size(iconSize)
        )
    }
}