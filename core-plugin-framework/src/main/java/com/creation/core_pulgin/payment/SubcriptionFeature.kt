package com.creation.core_pulgin.payment

object CreationUnicornAppsInfo {
    var FILE_MANAGER = "com.filefolder.newfilemanager"
    var VIDEO_DOWNLOADER = "com.creation.video.downloader"
}

enum class FeatureInfo() {
    THEME,
    NO_ADS,
    TRASH,
    LYRICS,
    CROP,
    FILTER_DUPLICATE,
    UPCOMING,
    NONE;

    companion object {
        var appFeatures = ArrayList<FeatureInfo>()
        fun getAppSpecificFeatures(applicationId: String): List<FeatureInfo> {
            appFeatures.clear()
            when (applicationId) {
                CreationUnicornAppsInfo.FILE_MANAGER -> {
                    appFeatures.add(NO_ADS)
                    appFeatures.add(UPCOMING)
                }
                CreationUnicornAppsInfo.VIDEO_DOWNLOADER -> {
                    appFeatures.add(NO_ADS)
                    appFeatures.add(UPCOMING)
                }
            }
            return appFeatures
        }
    }
}

