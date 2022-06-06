package ru.itis.morefy.core.presentation.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import ru.itis.morefy.R
import javax.inject.Inject


class ChartDrawer @Inject constructor(

) {
    private var tfLight: Typeface? = null

    fun drawRadarChart(
        context: Context,
        chart: RadarChart,
        title: String,
        data: Map<String, Float>,
        color: Int = Color.LTGRAY
    ) {
        val keys = data.keys.toList()
        val values = ArrayList<Float>()
        for (key in keys)
            data[key]?.let {
                values.add(it)
            }

        tfLight = Typeface.createFromAsset(context.assets, "OpenSans-Light.ttf")

        basicInitialization(chart, color)
        addMarkerToChart(context, chart)
        setData(title, chart, values)
        addAnimations(chart)
        initX(chart.xAxis, keys, values)
        initY(chart.yAxis, keys.size)
        drawLegend(chart.legend)
        chart.invalidate();
    }

    private fun basicInitialization(chart: RadarChart, color: Int) {
        chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.description.isEnabled = false
        chart.webLineWidth = 1f
        chart.webColor = color
        chart.webLineWidthInner = 1f
        chart.webColorInner = color
        chart.webAlpha = 100
    }

    private fun initY(yAxis: YAxis?, labelCount: Int) {
        yAxis?.apply {
            typeface = tfLight
            setLabelCount(labelCount, true)
            textSize = 8f
            axisMinimum = 0f
            axisMaximum = 80f
            setDrawLabels(true)
        }
    }

    private fun initX(xAxis: XAxis?, keys: List<String>, values: ArrayList<Float>) {
        xAxis?.apply {
            typeface = tfLight
            textSize = 4f
            yOffset = 0f
            xOffset = 0f
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${keys[(value.toInt() % keys.size)]} - ${values[(value.toInt() % keys.size)]}"
                }
            }
            textColor = Color.WHITE
        }
    }

    private fun drawLegend(l: Legend?) {
        l?.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
            typeface = tfLight
            textSize = 10f
            xEntrySpace = 7f
            yEntrySpace = 5f
            textColor = Color.WHITE
        }
    }

    private fun addMarkerToChart(context: Context, chart: RadarChart) {
        val mv: MarkerView = RadarMarkerView(context, R.layout.radar_marker_view, Color.TRANSPARENT)
        mv.chartView = chart
        chart.marker = mv
    }

    private fun addAnimations(chart: RadarChart) {
        chart.animateXY(1400, 1400, Easing.EaseInOutQuad)
        chart.isRotationEnabled = true
    }

    private fun setData(title: String, chart: RadarChart, values: List<Float>) {
        val entries = ArrayList<RadarEntry>()

        for (value in values) {
            entries.add(RadarEntry(value))
        }

        val set1 = RadarDataSet(entries, title)
        set1.color = Color.rgb(103, 110, 129)           // todo: change color depending on data
        set1.fillColor = Color.rgb(103, 110, 129)
        set1.setDrawFilled(true)
        set1.fillAlpha = 180
        set1.lineWidth = 2f
        set1.isDrawHighlightCircleEnabled = true
        set1.setDrawHighlightIndicators(false)

        val sets = ArrayList<IRadarDataSet>()
        sets.add(set1)

        val radarData = RadarData(sets)
        radarData.setValueTypeface(tfLight);
        radarData.setValueTextSize(8f);
        radarData.setDrawValues(true);
        radarData.setValueTextColor(Color.WHITE);

        chart.data = radarData;
    }

    fun invalidateOldChart(context: Context, chart: RadarChart) {
        chart.invalidate()
    }

    fun setNewData(
        context: Context,
        chart: RadarChart,
        title: String,
        data: Map<String, Float>
    ) {
        chart.notifyDataSetChanged()
        chart.clearValues()
        val keys = data.keys.toList()
        val values = ArrayList<Float>()
        for (key in keys)
            data[key]?.let {
                values.add(it)
            }

        setData(title, chart, values)
        initX(chart.xAxis, keys, values)
        initY(chart.yAxis, keys.size)
        chart.invalidate()
    }
}
