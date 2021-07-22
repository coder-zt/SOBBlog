package com.coder.zt.sobblog.model.moyu

data class WarpData(
    val CarouselApiUrl: String,
    val ClientSettings: ClientSettings,
    val CurrentIndex: Int,
    val CustomFields: Any,
    val Features: Features,
    val Footer: List<Footer>,
    val FormCode: String,
    val HasCarousel: Boolean,
    val HasVerticalScroll: Boolean,
    val ImageCropSize: String,
    val InPrivate: Boolean,
    val IsBingWpAppEnabled: Boolean,
    val IsCN: Boolean,
    val IsChromeExtensionUser: Boolean,
    val IsChromeNewTab: Boolean,
    val IsEnSearch: Boolean,
    val IsMobile: Boolean,
    val IsSuperApp: Boolean,
    val LocStrings: LocStrings,
    val Market: String,
    val MediaContents: List<MediaContent>,
    val MultiLangKeyboardEnabled: Boolean,
    val RewardsMobileHeaderEnabled: Boolean,
    val RtlLang: Boolean,
    val Scopes: List<Scope>,
    val Scripts: List<Script>,
    val SearchBoxPlaceHolder: Any,
    val Styles: List<Style>,
    val SupportedLanguages: List<Any>,
    val SymbolOfSolidarity: Any,
    val UserType: String
)

data class ClientSettings(
    val Ap: Boolean,
    val Dft: Any,
    val Flt: Int,
    val Imp: Int,
    val Iotd: Int,
    val Lad: String,
    val Mute: Boolean,
    val Mvs: Int,
    val Pn: Pn,
    val Qz: Qz,
    val Sc: Sc
)

data class Features(
    val IdentityHeaderVNext: String,
    val MicEnabled: String
)

data class Footer(
    val BaseUrl: String,
    val ExposeIfPossible: Boolean,
    val Id: String,
    val Overflow: Boolean,
    val Text: String
)

data class LocStrings(
    val InPrivate: String,
    val LOC_BING_WP_APP_LINK: String,
    val LOC_BING_WP_APP_LINK_TEXT: String,
    val LOC_HOMEPAGE_ANSWER_FINANCE_DECREASE_ARIALABEL: String,
    val LOC_HOMEPAGE_ANSWER_FINANCE_INCREASE_ARIALABEL: String,
    val LOC_HOMEPAGE_ANSWER_FINANCE_TITLE: String,
    val LOC_HOMEPAGE_ARCHIVE_NEXT: String,
    val LOC_HOMEPAGE_ARCHIVE_PREV: String,
    val LOC_HOMEPAGE_CAROUSEL_MORE: String,
    val LOC_HOMEPAGE_CAROUSEL_PREV: String,
    val LOC_HOMEPAGE_CAROUSEL_SWITCHER_INTERESTS: String,
    val LOC_HOMEPAGE_COPYRIGHT_FORMAT: String,
    val LOC_HOMEPAGE_FOLLOW_US: String,
    val LOC_HOMEPAGE_GALLERY_CARD_TITLE: String,
    val LOC_HOMEPAGE_HOLIDAY_BTN_DONATE: String,
    val LOC_HOMEPAGE_INFO_TEXT: String,
    val LOC_HOMEPAGE_LANGUAGESWITCH_TITLETEXT: String,
    val LOC_HOMEPAGE_ON_THIS_DAY_TITLE: String,
    val LOC_HOMEPAGE_OPALUPSELL_INSTALLCARD_TIP: String,
    val LOC_HOMEPAGE_QS_TITLE: String,
    val LOC_HOMEPAGE_QS_TOGGLECAROUSEL: String,
    val LOC_HOMEPAGE_QS_TOGGLEIOTD: String,
    val LOC_HOMEPAGE_QS_TOGGLEMVS: String,
    val LOC_HOMEPAGE_QS_TOGGLENAV: String,
    val LOC_HOMEPAGE_SETDEFAULTHPLINK: String,
    val LOC_HOMEPAGE_SETDEFAULTHPLINK_DESC_TEXT: String,
    val LOC_HOMEPAGE_SETDEFAULTHPLINK_DESC_TITLE: String,
    val LOC_HOMEPAGE_SHARE_TOOLTIP: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_DONATE_NOW: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_FACT: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_GALLERY_IMAGE_ARIALABEL: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_GET_INVOLVED: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_IOTD: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_IOTD_LEARN_MORE: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_OPAL_UPSELL_TITLE: String,
    val LOC_HOMEPAGE_VERTICAL_SCROLL_TOB: String,
    val LOC_HOMEPAGE_WALLPAPERDOWNLOAD_TOOLTIP: String,
    val LOC_HOMEPAGE_WALLPAPERNOTAVAILABLE: String,
    val LOC_HOMEPAGE_WEATHER_TITLE: String,
    val LOC_KEYBOARD_ICON_TOOLTIP: String,
    val LOC_MODULES_AD_NEWS_TITLE: String,
    val LOC_MODULES_BREAKING_NEWS_TITLE: String,
    val LOC_MODULES_PROACTIVE_EDIT_TOOLTIP: String,
    val LOC_MSB_OPTIN_ACCEPT: String,
    val LOC_MSB_OPTIN_MESSAGE: String,
    val LOC_MSB_OPTIN_REJECT: String,
    val LOC_MSB_OPTIN_TITLE: String,
    val LOC_MSB_OPTIN_TOGGLE_TITLE: String,
    val LOC_MUSCARD_DOWNLOAD_LINK: String,
    val LOC_SEARCH_SWITCHTAB_DESKTOP_CN: String,
    val LOC_SEARCH_SWITCHTAB_DESKTOP_EN: String,
    val LOC_SEARCH_SWITCHTAB_MOBILE_CN: String,
    val LOC_SEARCH_SWITCHTAB_MOBILE_CN_TIP: String,
    val LOC_SEARCH_SWITCHTAB_MOBILE_EN: String,
    val LOC_SEARCH_THE_WEB: String,
    val LOC_SEARCH_USING_AN_IMAGE: String,
    val LOC_ZINC_SCOPES_TITLE: String
)

data class MediaContent(
    val AudioContent: Any,
    val FullDateString: String,
    val ImageContent: ImageContent,
    val Ssd: String,
    val VideoContent: Any
)

data class Scope(
    val BaseUrl: String,
    val ExposeIfPossible: Boolean,
    val Id: String,
    val IsDivider: Boolean,
    val Overflow: Boolean,
    val SubScopes: List<SubScope>,
    val Text: String
)

data class Script(
    val Path: String,
    val Postloaded: Boolean
)

data class Style(
    val Path: String,
    val Postloaded: Boolean
)

data class Pn(
    val Cn: Int,
    val Prod: String,
    val Qs: Int,
    val St: Int
)

data class Qz(
    val Cn: Int,
    val Prod: String,
    val Qs: Int,
    val St: Int
)

data class Sc(
    val Cn: Int,
    val Prod: String,
    val Qs: Int,
    val St: Int
)

data class ImageContent(
    val BackstageUrl: String,
    val Copyright: String,
    val Description: Any,
    val Headline: Any,
    val Image: Image,
    val MapLink: MapLink,
    val QuickFact: QuickFact,
    val SocialGood: Any,
    val Title: String,
    val TriviaId: String,
    val TriviaUrl: String
)

data class Image(
    val Downloadable: Boolean,
    val Url: String,
    val Wallpaper: String
)

data class MapLink(
    val Link: String,
    val Url: String
)

data class QuickFact(
    val LinkText: String,
    val LinkUrl: String,
    val MainText: String
)

data class SubScope(
    val BaseUrl: String,
    val ExposeIfPossible: Boolean,
    val Id: String,
    val IsDivider: Boolean,
    val Overflow: Boolean,
    val SubScopes: List<Any>,
    val Text: String
)