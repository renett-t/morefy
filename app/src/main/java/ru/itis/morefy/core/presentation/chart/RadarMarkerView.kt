package ru.itis.morefy.core.presentation.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import ru.itis.morefy.R

@SuppressLint("ViewConstructor")
class RadarMarkerView(context: Context, layout: Int, backgroundColor: Int)
    : MarkerView(context, layout) {
    private val tvContent: TextView = findViewById(R.id.tvContent)

    init {
        tvContent.typeface = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")
        setBackgroundColor(backgroundColor)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null)
            tvContent.text = String.format("%.2f %%", e.y)

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(
            (-(width / 2)).toFloat(),
            (-height - 10).toFloat()
        )
    }
}
