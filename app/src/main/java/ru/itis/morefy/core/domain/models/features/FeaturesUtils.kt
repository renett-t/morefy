package ru.itis.morefy.core.domain.models.features

import android.content.Context
import android.graphics.Color
import android.os.Build
import ru.itis.morefy.R
import java.math.RoundingMode
import java.text.DecimalFormat

class FeaturesUtils {

    companion object {
        fun toMap(context: Context, features: TrackFeatures): Map<String, Float> {
            val map = HashMap<String, Float>()

            map[context.getString(R.string.acousticness)] = features.acousticness
            map[context.getString(R.string.danceability)] = features.danceability
            map[context.getString(R.string.energy)] = features.energy
            map[context.getString(R.string.instrumentalness)] = features.instrumentalness
            map[context.getString(R.string.liveness)] = features.liveness
            map[context.getString(R.string.speechiness)] = features.speechiness
            map[context.getString(R.string.valence)] = features.valence

            return normalizeData(map)
        }

        fun toMap(context: Context, features: AverageTracksFeatures): Map<String, Float> {
            val map = HashMap<String, Float>()

            map[context.getString(R.string.acousticness)] = features.acousticness
            map[context.getString(R.string.danceability)] = features.danceability
            map[context.getString(R.string.energy)] = features.energy
            map[context.getString(R.string.instrumentalness)] = features.instrumentalness
            map[context.getString(R.string.liveness)] = features.liveness
            map[context.getString(R.string.speechiness)] = features.speechiness
            map[context.getString(R.string.valence)] = features.valence

            return normalizeData(map)
        }

        private fun normalizeData(map: MutableMap<String, Float>): Map<String, Float> {
            val newData = HashMap<String, Float>()

            map.mapValuesTo(newData) { entry ->
               (entry.value * 100f).toBigDecimal().setScale(2, RoundingMode.UP).toFloat()
            }
            return newData
        }

        fun determineColor(key: MusicalKey): Color {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                when (key) {
                    MusicalKey.C ->
                        Color.valueOf(55f, 226f, 213f)
                    MusicalKey.CD -> Color.valueOf(89f, 6f, 150f)
                    MusicalKey.F -> Color.valueOf(199f, 10f, 128f)
                    else -> Color.valueOf(251f, 203f, 10f)
                }
            } else {
                Color()
            }
        }
    }
}
