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
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.morefy.R
import ru.itis.morefy.core.di.assisted.ArtistsAdapterFactory
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.FeaturesUtils
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.presentation.chart.ChartDrawer
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.findNavigationController
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.core.presentation.fragments.details.rv.track.ArtistsAdapter
import ru.itis.morefy.core.presentation.viewmodels.details.TrackViewModel
import ru.itis.morefy.databinding.FragmentTrackBinding
import java.util.stream.Collectors
import javax.inject.Inject

const val TRACK_ID_KEY: String = "TRACK_ID_KEY"
class TrackFragment : Fragment(R.layout.fragment_track) {
    private lateinit var binding: FragmentTrackBinding

    @Inject
    lateinit var viewModel: TrackViewModel
    @Inject
    lateinit var chartDrawer: ChartDrawer
    @Inject
    lateinit var adapterFactory: ArtistsAdapterFactory

    private lateinit var artistsAdapter: ArtistsAdapter
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

        initRecycler(binding.rvArtists)
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

    private fun initRecycler(rvArtists: RecyclerView) {
        artistsAdapter = adapterFactory.provideArtistsAdapter(
            Glide.with(requireContext())
        ) {
            navigateToArtistScreen(it)
        }

        rvArtists.apply {
            adapter = artistsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
        }
    }

    private fun initObservers() {
        viewModel.track.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateView(it)
                    reDownloadArtists(it.artists)
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

        viewModel.artists.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    artistsAdapter.submitList(it)
                },
                onFailure = {
                    Log.e("ERROR TRACK FRAGMENT", "UNABLE TO GET ARTIST DATA FROM VIEW")
                }
            )
        }
    }

    private fun startDownloadingData(id: String) {
        viewModel.getTrackInfo(id)
        viewModel.getTrackFeaturesInfo(id)
    }

    private fun reDownloadArtists(artists: List<Artist>) {
        val ids = artists.stream().map { it.id }.collect(Collectors.toList())
        viewModel.getArtists(ids)
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

            artistsAdapter.submitList(track.artists)

            imageDownloader.load(track.album.imageUrl)
                .into(itemAlbum.ivAlbumCover)
            itemAlbum.tvAlbumName.text = track.album.name
            itemAlbum.tvAlbumType.text = track.album.type
            itemAlbum.tvTracksCount.text = track.album.tracksCount.toString()
            itemAlbum.tvAlbumName.setOnClickListener {
                navigateToAlbumScreen(track.album.id)
            }
            itemAlbum.tvAlbumName.setOnClickListener {
                navigateToAlbumScreen(track.album.id)
            }

            btnListenInSpotifyApp.setOnClickListener {
                createIntentToOfficialApp(track.uri)
            }
        }
    }

    private fun updateChart(features: TrackFeatures) {
        with(binding) {
            tvTempo.text = getString(R.string.track_tempo, features.tempo)
            tvKey.text = getString(R.string.track_key, features.key.toString())
            chartDrawer.drawRadarChart(
                requireContext(),
                itemChart.radarChart,
                getString(R.string.track_features_analysis),
                getDataFromFeatures(features))
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

    private fun navigateToArtistScreen(id: String) {
        val bundle = Bundle().apply {
            putString(ARTIST_ID_KEY, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true) // todo: add animations
            .build()

        requireActivity().findNavigationController(R.id.container).navigate(
            R.id.action_trackFragment_to_artistFragment,
            bundle,
            options
        )
    }

    private fun navigateToAlbumScreen(id: String) {
        showMessage("Navigation to Album $id Screen")
    }
}
