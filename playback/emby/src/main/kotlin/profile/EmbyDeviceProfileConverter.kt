package org.moonfin.playback.emby.profile

import org.emby.client.model.DlnaCodecProfile
import org.emby.client.model.DlnaContainerProfile
import org.emby.client.model.DlnaDeviceProfile
import org.emby.client.model.DlnaDirectPlayProfile
import org.emby.client.model.DlnaProfileCondition
import org.emby.client.model.DlnaSubtitleProfile
import org.emby.client.model.DlnaTranscodingProfile
import org.jellyfin.sdk.model.api.DeviceProfile as JellyfinDeviceProfile

fun JellyfinDeviceProfile.toEmbyDeviceProfile(): DlnaDeviceProfile = DlnaDeviceProfile(
	name = name,
	maxStreamingBitrate = maxStreamingBitrate?.toLong(),
	directPlayProfiles = directPlayProfiles.map { it.toEmby() },
	transcodingProfiles = transcodingProfiles.map { it.toEmby() },
	containerProfiles = containerProfiles.map { it.toEmby() },
	codecProfiles = codecProfiles.map { it.toEmby() },
	subtitleProfiles = subtitleProfiles.map { it.toEmby() },
)

private fun org.jellyfin.sdk.model.api.DirectPlayProfile.toEmby() = DlnaDirectPlayProfile(
	container = container,
	audioCodec = audioCodec,
	videoCodec = videoCodec,
	type = when (type) {
		org.jellyfin.sdk.model.api.DlnaProfileType.AUDIO -> DlnaDirectPlayProfile.Type.Audio
		org.jellyfin.sdk.model.api.DlnaProfileType.VIDEO -> DlnaDirectPlayProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.PHOTO -> DlnaDirectPlayProfile.Type.Photo
		org.jellyfin.sdk.model.api.DlnaProfileType.SUBTITLE -> DlnaDirectPlayProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.LYRIC -> DlnaDirectPlayProfile.Type.Audio
		null -> null
	},
)

private fun org.jellyfin.sdk.model.api.TranscodingProfile.toEmby() = DlnaTranscodingProfile(
	container = container,
	type = when (type) {
		org.jellyfin.sdk.model.api.DlnaProfileType.AUDIO -> DlnaTranscodingProfile.Type.Audio
		org.jellyfin.sdk.model.api.DlnaProfileType.VIDEO -> DlnaTranscodingProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.PHOTO -> DlnaTranscodingProfile.Type.Photo
		org.jellyfin.sdk.model.api.DlnaProfileType.SUBTITLE -> DlnaTranscodingProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.LYRIC -> DlnaTranscodingProfile.Type.Audio
		null -> null
	},
	videoCodec = videoCodec,
	audioCodec = audioCodec,
	protocol = protocol.serialName,
	context = when (context) {
		org.jellyfin.sdk.model.api.EncodingContext.STREAMING -> DlnaTranscodingProfile.Context.Streaming
		org.jellyfin.sdk.model.api.EncodingContext.STATIC -> DlnaTranscodingProfile.Context.Static
		null -> null
	},
	copyTimestamps = copyTimestamps,
	segmentLength = segmentLength.takeIf { it > 0 },
	minSegments = minSegments.takeIf { it > 0 },
	breakOnNonKeyFrames = breakOnNonKeyFrames,
	maxAudioChannels = maxAudioChannels?.toString(),
	manifestSubtitles = if (enableSubtitlesInManifest) "HLS" else null,
)

private fun org.jellyfin.sdk.model.api.ContainerProfile.toEmby() = DlnaContainerProfile(
	type = when (type) {
		org.jellyfin.sdk.model.api.DlnaProfileType.AUDIO -> DlnaContainerProfile.Type.Audio
		org.jellyfin.sdk.model.api.DlnaProfileType.VIDEO -> DlnaContainerProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.PHOTO -> DlnaContainerProfile.Type.Photo
		org.jellyfin.sdk.model.api.DlnaProfileType.SUBTITLE -> DlnaContainerProfile.Type.Video
		org.jellyfin.sdk.model.api.DlnaProfileType.LYRIC -> DlnaContainerProfile.Type.Audio
		null -> null
	},
	conditions = conditions.map { it.toEmby() },
	container = container,
)

private fun org.jellyfin.sdk.model.api.CodecProfile.toEmby() = DlnaCodecProfile(
	type = when (type) {
		org.jellyfin.sdk.model.api.CodecType.VIDEO -> DlnaCodecProfile.Type.Video
		org.jellyfin.sdk.model.api.CodecType.VIDEO_AUDIO -> DlnaCodecProfile.Type.VideoAudio
		org.jellyfin.sdk.model.api.CodecType.AUDIO -> DlnaCodecProfile.Type.Audio
		null -> null
	},
	conditions = conditions.map { it.toEmby() },
	applyConditions = applyConditions.map { it.toEmby() },
	codec = codec,
	container = container,
)

private fun org.jellyfin.sdk.model.api.SubtitleProfile.toEmby() = DlnaSubtitleProfile(
	format = format,
	method = when (method) {
		org.jellyfin.sdk.model.api.SubtitleDeliveryMethod.ENCODE -> DlnaSubtitleProfile.Method.Encode
		org.jellyfin.sdk.model.api.SubtitleDeliveryMethod.EMBED -> DlnaSubtitleProfile.Method.Embed
		org.jellyfin.sdk.model.api.SubtitleDeliveryMethod.EXTERNAL -> DlnaSubtitleProfile.Method.External
		org.jellyfin.sdk.model.api.SubtitleDeliveryMethod.HLS -> DlnaSubtitleProfile.Method.Hls
		org.jellyfin.sdk.model.api.SubtitleDeliveryMethod.DROP -> DlnaSubtitleProfile.Method.Encode
		null -> null
	},
	language = language,
	container = container,
)

private fun org.jellyfin.sdk.model.api.ProfileCondition.toEmby() = DlnaProfileCondition(
	condition = when (condition) {
		org.jellyfin.sdk.model.api.ProfileConditionType.EQUALS -> DlnaProfileCondition.Condition.Equals
		org.jellyfin.sdk.model.api.ProfileConditionType.NOT_EQUALS -> DlnaProfileCondition.Condition.NotEquals
		org.jellyfin.sdk.model.api.ProfileConditionType.LESS_THAN_EQUAL -> DlnaProfileCondition.Condition.LessThanEqual
		org.jellyfin.sdk.model.api.ProfileConditionType.GREATER_THAN_EQUAL -> DlnaProfileCondition.Condition.GreaterThanEqual
		org.jellyfin.sdk.model.api.ProfileConditionType.EQUALS_ANY -> DlnaProfileCondition.Condition.EqualsAny
		null -> null
	},
	property = when (property) {
		org.jellyfin.sdk.model.api.ProfileConditionValue.AUDIO_CHANNELS -> DlnaProfileCondition.Property.AudioChannels
		org.jellyfin.sdk.model.api.ProfileConditionValue.AUDIO_BITRATE -> DlnaProfileCondition.Property.AudioBitrate
		org.jellyfin.sdk.model.api.ProfileConditionValue.AUDIO_PROFILE -> DlnaProfileCondition.Property.AudioProfile
		org.jellyfin.sdk.model.api.ProfileConditionValue.WIDTH -> DlnaProfileCondition.Property.Width
		org.jellyfin.sdk.model.api.ProfileConditionValue.HEIGHT -> DlnaProfileCondition.Property.Height
		org.jellyfin.sdk.model.api.ProfileConditionValue.HAS_64_BIT_OFFSETS -> DlnaProfileCondition.Property.Has64BitOffsets
		org.jellyfin.sdk.model.api.ProfileConditionValue.PACKET_LENGTH -> DlnaProfileCondition.Property.PacketLength
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_BIT_DEPTH -> DlnaProfileCondition.Property.VideoBitDepth
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_BITRATE -> DlnaProfileCondition.Property.VideoBitrate
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_FRAMERATE -> DlnaProfileCondition.Property.VideoFramerate
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_LEVEL -> DlnaProfileCondition.Property.VideoLevel
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_PROFILE -> DlnaProfileCondition.Property.VideoProfile
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_TIMESTAMP -> DlnaProfileCondition.Property.VideoTimestamp
		org.jellyfin.sdk.model.api.ProfileConditionValue.IS_ANAMORPHIC -> DlnaProfileCondition.Property.IsAnamorphic
		org.jellyfin.sdk.model.api.ProfileConditionValue.REF_FRAMES -> DlnaProfileCondition.Property.RefFrames
		org.jellyfin.sdk.model.api.ProfileConditionValue.NUM_AUDIO_STREAMS -> DlnaProfileCondition.Property.NumAudioStreams
		org.jellyfin.sdk.model.api.ProfileConditionValue.NUM_VIDEO_STREAMS -> DlnaProfileCondition.Property.NumVideoStreams
		org.jellyfin.sdk.model.api.ProfileConditionValue.IS_SECONDARY_AUDIO -> DlnaProfileCondition.Property.IsSecondaryAudio
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_CODEC_TAG -> DlnaProfileCondition.Property.VideoCodecTag
		org.jellyfin.sdk.model.api.ProfileConditionValue.IS_AVC -> DlnaProfileCondition.Property.IsAvc
		org.jellyfin.sdk.model.api.ProfileConditionValue.IS_INTERLACED -> DlnaProfileCondition.Property.IsInterlaced
		org.jellyfin.sdk.model.api.ProfileConditionValue.AUDIO_SAMPLE_RATE -> DlnaProfileCondition.Property.AudioSampleRate
		org.jellyfin.sdk.model.api.ProfileConditionValue.AUDIO_BIT_DEPTH -> DlnaProfileCondition.Property.AudioBitDepth
		// No direct Emby equivalent — map to closest match
		org.jellyfin.sdk.model.api.ProfileConditionValue.VIDEO_RANGE_TYPE -> DlnaProfileCondition.Property.VideoBitrate
		org.jellyfin.sdk.model.api.ProfileConditionValue.NUM_STREAMS -> DlnaProfileCondition.Property.NumVideoStreams
		null -> null
	},
	value = value,
	isRequired = isRequired,
)
