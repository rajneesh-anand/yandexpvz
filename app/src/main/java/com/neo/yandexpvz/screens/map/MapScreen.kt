package com.neo.yandexpvz.screens.map


import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.neo.yandexpvz.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen() {
    YandexMapView()
//    HtmlText(html = "Greetings, I am text with <b> bold</b> formatting")
}

//@Composable
//fun HtmlText(html: String, modifier: Modifier = Modifier) {
//    AndroidView(
//        modifier = modifier,
//        factory = { context -> TextView(context) },
//        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
//    )
//}

@Composable
fun YandexMapView() {
     lateinit var mapView: MapView
     val context = LocalContext.current
     val POINT = Point(51.559398, 46.022891)
     val POSITION = com.yandex.mapkit.map.CameraPosition(POINT, 17.0f, 150.0f, 30.0f)

    val placeTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(context, "Tapped the point (${point.longitude}, ${point.latitude})", Toast.LENGTH_SHORT).show()
        true
    }
    AndroidView(
        factory = {
           MapKitFactory.initialize(it)
           View.inflate(it,R.layout.main_layout,null )
        },
        modifier = Modifier.fillMaxSize(),
        update = {
            mapView = it.findViewById(R.id.mapview)
            val map = mapView.mapWindow.map
            map.move(POSITION)
            val imageProvider = ImageProvider.fromResource(context,R.drawable.ic_place_pin)
            val markObject = map.mapObjects.addPlacemark(POINT, imageProvider)
            markObject.addTapListener(placeTapListener)
            MapKitFactory.getInstance().onStart()
            mapView.onStart()
        }
    )


}

