package com.example.weatherapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.BitmapDescriptorFactory



@SuppressLint("MissingPermission")
@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    hasLocationPermission: Boolean,
    onRequestLocationPermission: () -> Unit
) {

//    val initialPosition = LatLng(-8.0476, -34.8770)
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(initialPosition, 10f)
//    }

    val camPosState = rememberCameraPositionState()

    val recife = remember {
        MarkerState(LatLng(-8.05, -34.9))
    }
    val caruaru = remember {
        MarkerState(LatLng(-8.27, -35.98))
    }
    val joaopessoa = remember {
        MarkerState(LatLng(-7.12, -34.84))
    }

    if (!hasLocationPermission) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Permissão de localização necessária para usar o mapa.")

            Button(
                onClick = onRequestLocationPermission
            ) {
                Text("Permitir localização")
            }
        }
    } else {

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            onMapClick = {
                viewModel.add("Cidade@${it.latitude}:${it.longitude}", location = it)
            },
            cameraPositionState = camPosState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
        ) {

            Marker(
                state = recife,
                title = "Recife",
                snippet = "Marcador em Recife",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_BLUE
                )
            )

            Marker(
                state = caruaru,
                title = "Caruaru",
                snippet = "Marcador em Caruaru",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN
                )
            )

            Marker(
                state = joaopessoa,
                title = "João Pessoa",
                snippet = "Marcador em João Pessoa",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_ORANGE
                )
            )

            viewModel.cities.forEach {
                if (it.location != null) {
                    Marker(
                        state = MarkerState(position = it.location),
                        title = it.name, snippet = "${it.location}"
                    )
                }
            }
        }
    }
}