package ru.itis.morefy.core.presentation.fragments.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.FeaturesUtils
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.presentation.chart.ChartDrawer
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.viewmodels.TrackViewModel
import ru.itis.morefy.databinding.FragmentTrackBinding
import javax.inject.Inject


const val TRACK_ID_KEY: String = "TRACK_ID_KEY"
class TrackFragment : Fragment(R.layout.fragment_track) {
    private lateinit var binding: FragmentTrackBinding

    @Inject
    lateinit var viewModel: TrackViewModel

    @Inject
    lateinit var chartDrawer: ChartDrawer
    private lateinit var imageDownloader: RequestManager

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createNewActionBar()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun createNewActionBar() {
//        val inflater = LayoutInflater.from(context)
//        val toolbar = inflater.inflate(R.layout.toolbar_when_collapsing, null)
//            .findViewById<Toolbar>(R.id.toolbar_when_collapsing)
//        (activity as MainActivity?)?.apply {
//            supportActionBar?.hide()
//            setSupportActionBar(toolbar)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        initObservers()
        arguments?.getString(TRACK_ID_KEY)?.let {
            startDownloadingData(it)
        }
    }

    override fun onDestroy() {
        setMainActionBar()
        super.onDestroy()
    }

    private fun setMainActionBar() {
//        (activity as AppCompatActivity?)?.apply {
//            val prevToolbar = findViewById<Toolbar>(R.id.main_toolbar)
//            setSupportActionBar(findViewById(R.id.main_toolbar))
//        }
    }

    private fun initObservers() {
        viewModel.track.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateView(it)
                },
                onFailure = {
                    Log.e("ERROR TRACK FRAGMENT", "UNABLE TO GET TRACK DATA FROM VIEW")
                }
            )
        }

        viewModel.trackFeatures.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateChart(it)
                },
                onFailure = {
                    Log.e("ERROR TRACK FRAGMENT", "UNABLE TO GET TRACK DATA FROM VIEW")
                }
            )
        }
    }

    private fun startDownloadingData(id: String) {
        viewModel.getTrackInfo(id)
        viewModel.getTrackFeaturesInfo(id)
    }


    private fun updateView(track: Track) {
        imageDownloader = Glide.with(requireContext())
        with(binding) {
            imageDownloader.load(track.album.imageUrl)
                .into(ivTrackCover)
            tvTrack.text = track.name
            tvDuration.text = getDurationFormatted(track.durationMs)
            if (track.isExplicit)
                ivExplicit.visibility = View.VISIBLE

            // todo: change to rv instead of showing one artist
            imageDownloader.load(track.artists.first().imageUrl)
                .into(itemArtist.ivArtistCover)
            itemArtist.tvArtistName.text = track.artists.first().name

            imageDownloader.load(track.album.imageUrl)
                .into(itemAlbum.ivAlbumCover)
            itemAlbum.ivAlbumName.text = track.album.name
            // todo: add album type?
            itemAlbum.tvTracksCount.text = track.album.tracksCount.toString()

            btnListenInSpotifyApp.setOnClickListener {
                createIntentToOfficialApp(track.uri)
            }
        }
    }

    private fun updateChart(features: TrackFeatures) {
        with(binding) {
            tvTempo.text = getString(R.string.track_tempo, features.tempo)
            tvKey.text = getString(R.string.track_key, features.key.toString())
            chartDrawer.drawRadarChart(requireContext(), itemChart.radarChart, getDataFromFeatures(features))
        }
    }

    private fun getDataFromFeatures(features: TrackFeatures): Map<String, Float> {
        return FeaturesUtils.toMap(requireContext(), features)
    }

    private fun createIntentToOfficialApp(uri: String) {
        val launcher = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(launcher)
    }

    private fun getDurationFormatted(durationMs: Int): String {
        val min = durationMs / 60000
        val seconds = (durationMs % 60000) / 1000
        return if (seconds < 10) {
            "$min:0$seconds"
        } else {
            "$min:$seconds"
        }
    }
}
