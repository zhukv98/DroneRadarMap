package edu.uc.zhukv.droneradarmap.Weather_Layer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.google.android.gms.maps.model.Tile
import com.google.android.gms.maps.model.TileProvider
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class TransparentTileOWM(private val tileType: String):TileProvider {
    private val opacityPaint = Paint()

    /**
     * Sets the desired opacity of map [Tile]s, as a percentage where 0% is invisible and 100% is completely opaque.
     * @param opacity The desired opacity of map [Tile]s (as a percentage between 0 and 100, inclusive)
     */
    fun setOpacity(opacity: Int) {
        val alpha = Math.round(opacity * 2.55).toInt() // 2.55 = 255 * 0.01
        opacityPaint.alpha = alpha
    }

    override fun getTile(x: Int, y: Int, zoom: Int): Tile? {
        val tileUrl = getTileUrl(x, y, zoom)
        var tile: Tile? = null
        var stream: ByteArrayOutputStream? = null
        try {
            var image = BitmapFactory.decodeStream(tileUrl.openConnection().getInputStream())
            image = adjustOpacity(image)
            stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            tile = Tile(256, 256, byteArray)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                }
            }
        }
        return tile
    }

    /**
     * Helper method that returns the [URL] of the tile image for the given x/y/zoom location.
     *
     *
     * This method assumes the URL string provided in the constructor contains three placeholders for the x-
     * and y-positions as well as the zoom level of the desired tile; `{x}`, `{y}`, and
     * `{zoom}`. An example for an OpenWeatherMap URL would be:
     * http://tile.openweathermap.org/map/precipitation/{zoom}/{x}/{y}.png
     *
     * @param x The x-position of the tile
     * @param y The y-position of the tile
     * @param zoom The zoom level of the tile
     *
     * @return The [URL] of the desired tile image
     */
    private fun getTileUrl(x: Int, y: Int, zoom: Int): URL {
        val tileUrl = String.format(OWM_TILE_URL, tileType, zoom, x, y)
        return try {
            URL(tileUrl)
        } catch (e: MalformedURLException) {
            throw AssertionError(e)
        }
    }

    /**
     * Helper method that adjusts the given [Bitmap]'s opacity to the opacity previously set via
     * [.setOpacity]. Stolen from Elysium's comment at StackOverflow.
     *
     * @param bitmap The [Bitmap] whose opacity to adjust
     * @return A new [Bitmap] with an adjusted opacity
     *
     * @see htp://stackoverflow.com/questions/14322236/making-tileoverlays-transparent.comment19934097_14329560
     */
    private fun adjustOpacity(bitmap: Bitmap): Bitmap {
        val adjustedBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(adjustedBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, opacityPaint)
        return adjustedBitmap
    }

    companion object {
        private const val OWM_TILE_URL = "http://tile.openweathermap.org/map/%s/%d/%d/%d.png"
    }

    /**
     * This constructor assumes the `url` parameter contains three placeholders for the x- and y-positions of
     * the tile as well as the zoom level of the tile. The placeholders are assumed to be `{x}`,
     * `{y}`, and `{zoom}`. An example
     * for an OpenWeatherMap URL would be: http://tile.openweathermap.org/map/precipitation/{zoom}/{x}/{y}.png
     *
     */
    init {
        setOpacity(50)
    }
}