package ru.itis.morefy.core.presentation.fragments.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.viewmodels.TrackViewModel
import ru.itis.morefy.databinding.FragmentTrackBinding
import javax.inject.Inject


const val TRACK_ID_KEY: String = "TRACK_ID_KEY"
class TrackFragment : Fragment(R.layout.fragment_track) {
    private lateinit var binding: FragmentTrackBinding

    @Inject
    lateinit var viewModel: TrackViewModel

    private lateinit var imageDownloader: RequestManager

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        editToolbar()
        initObservers()
        arguments?.getString(TRACK_ID_KEY)?.let {
            startDownloadingData(it)
        }
    }

    private fun editToolbar() {
        (activity as AppCompatActivity?)?.apply {
            supportActionBar?.hide()
        }
    }

    override fun onDestroy() {
        (activity as AppCompatActivity?)?.apply {
            supportActionBar?.show()
        }
        super.onDestroy()
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
            // todo: map key number to key string -> enum/utils class
            tvKey.text = getString(R.string.track_key, features.key.toString())
        }
        //todo: create chart
    }

    private fun createIntentToOfficialApp(uri: String) {
        val launcher = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(launcher)
    }

    private fun getDurationFormatted(durationMs: Int): String {
        //todo
        return durationMs.toString()
    }
}
