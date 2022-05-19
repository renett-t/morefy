package ru.itis.morefy.core.data.response.track.audio

data class TrackAudioAnalysis(
    val bars: List<Bar>,
    val beats: List<Beat>,
    val meta: Meta,
    val sections: List<Section>,
    val segments: List<Segment>,
    val tatums: List<Tatum>,
    val track: Track
)
