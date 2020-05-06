<template>
    <div class="ezm-icon-picker flex-box flex-center ">
        <el-input
            :value="value"
            :disabled="disabled"
            :placeholder="`点击选择icon`"
            @input="(v)=> $emit('input', v)"
            readonly
            @click.native="disabled? null : showDialog = true"
        >
        </el-input>
        <div class="mlr-xs" style="font-size: 1.3em;">
            <i :class="previewIcon" title="图标预览"></i>
        </div>
        <ezm-edit-dialog
            v-model="showDialog"
            width="1000px"
        >
            <ezm-window
                :max-height="600"
                icon="icon-smile-wink1"
                title="选择图标"
            >
                <template slot="button-left">
                    <div class="">
                        <el-input v-model="searchText" style="width: 200px;" placeholder="搜索"></el-input>
                        <el-button type="danger" @click="cancelIcon" class="">取消图标</el-button>
                    </div>
                </template>
                <ezm-window-item
                >
                    <div class="flex-box flex-center flex-wrap pa-sm">
                        <div class="flex-shk-off text-center icon-container"
                             v-for="(item,i) in iconsList"
                             :key="item"
                             v-show="item.includes(searchText)"
                             v-if="i< showRow"
                             @click="clickIcon(item)"
                        >
                            <div class="">
                                <i class="iconfont " :class="item"></i>
                            </div>
                            <div class="one-line">{{ item }}</div>
                        </div>
                    </div>
                </ezm-window-item>
            </ezm-window>
        </ezm-edit-dialog>
    </div>
</template>

<script>
    import EzmEditDialog from "@/components/editor/container/ezm-edit-dialog";
    import EzmWindow from "@/components/window/ezm-window";
    import EzmWindowItem from "@/components/window/ezm-window-item";

    const icons = ["icon-window-restore1", "icon-window-minimize1", "icon-window-maximize1", "icon-window-close1", "icon-user-circle1", "icon-user1", "icon-trash-alt1", "icon-tired1", "icon-thumbs-up1", "icon-times-circle1", "icon-thumbs-down1", "icon-surprise1", "icon-sun1", "icon-stop-circle1", "icon-sticky-note1", "icon-star-half1", "icon-star1", "icon-snowflake1", "icon-square1", "icon-smile-wink1", "icon-smile-beam1", "icon-smile1", "icon-share-square1", "icon-save1", "icon-sad-tear1", "icon-sad-cry1", "icon-registered1", "icon-question-circle1", "icon-plus-square1", "icon-play-circle1", "icon-pause-circle1", "icon-paper-plane1", "icon-object-ungroup1", "icon-object-group1", "icon-money-bill-alt1", "icon-newspaper1", "icon-moon1", "icon-minus-square1", "icon-meh-rolling-eyes1", "icon-map1", "icon-meh-blank1", "icon-meh1", "icon-list-alt1", "icon-lightbulb1", "icon-life-ring1", "icon-lemon1", "icon-laugh-wink1", "icon-laugh-squint1", "icon-laugh-beam1", "icon-laugh1", "icon-kiss-wink-heart1", "icon-kiss-beam1", "icon-kiss1", "icon-keyboard1", "icon-images1", "icon-image1", "icon-id-card1", "icon-id-badge1", "icon-hourglass1", "icon-hospital1", "icon-heart1", "icon-hdd1", "icon-hand-spock1", "icon-handshake1", "icon-hand-scissors1", "icon-hand-rock1", "icon-hand-point-up1", "icon-hand-point-right1", "icon-hand-pointer1", "icon-hand-point-left1", "icon-hand-point-down1", "icon-hand-peace1", "icon-hand-paper1", "icon-hand-lizard1", "icon-grin-tongue-wink1", "icon-grin-wink1", "icon-grin-tongue-squint1", "icon-grin-tongue1", "icon-grin-tears1", "icon-grin-stars1", "icon-grin-squint-tears1", "icon-grin-squint1", "icon-grin-beam-sweat1", "icon-grin-hearts1", "icon-grin-beam1", "icon-grin-alt1", "icon-grin1", "icon-grimace1", "icon-gem1", "icon-futbol1", "icon-frown-open1", "icon-frown1", "icon-folder-open1", "icon-folder1", "icon-flag1", "icon-flushed1", "icon-file-word1", "icon-file-video1", "icon-file-powerpoint1", "icon-file-pdf1", "icon-file-image1", "icon-file-excel1", "icon-file-code1", "icon-file-audio1", "icon-file-archive1", "icon-file-alt1", "icon-file1", "icon-eye-slash1", "icon-eye1", "icon-envelope-open1", "icon-envelope1", "icon-edit1", "icon-dot-circle1", "icon-dizzy1", "icon-credit-card1", "icon-copyright1", "icon-copy1", "icon-compass1", "icon-comments1", "icon-comment-dots1", "icon-comment-alt1", "icon-comment1", "icon-closed-captioning1", "icon-clone1", "icon-clock1", "icon-clipboard1", "icon-check-square1", "icon-circle1", "icon-check-circle1", "icon-caret-square-right1", "icon-chart-bar1", "icon-caret-square-up1", "icon-caret-square-left1", "icon-caret-square-down1", "icon-calendar-times1", "icon-calendar-plus1", "icon-calendar-check1", "icon-calendar-alt1", "icon-calendar-minus1", "icon-calendar1", "icon-bookmark1", "icon-arrow-alt-circle-dow1", "icon-building1", "icon-bell-slash1", "icon-bell1", "icon-arrow-alt-circle-up1", "icon-arrow-alt-circle-rig1", "icon-arrow-alt-circle-lef1", "icon-angry1", "icon-address-card1", "icon-address-book1", "icon-wix", "icon-zhihu", "icon-youtube-square", "icon-youtube", "icon-yoast", "icon-yelp", "icon-y-combinator", "icon-yandex-international", "icon-yandex", "icon-yahoo", "icon-xing-square", "icon-xing", "icon-xbox", "icon-wpforms", "icon-wpexplorer", "icon-wpbeginner", "icon-wordpress-simple", "icon-wordpress", "icon-wolf-pack-battalion", "icon-windows", "icon-wikipedia-w", "icon-whmcs", "icon-whatsapp-square", "icon-whatsapp", "icon-weixin", "icon-weibo", "icon-weebly", "icon-vuejs", "icon-vnv", "icon-vk", "icon-vine", "icon-vimeo-v", "icon-vimeo-square", "icon-vimeo", "icon-viber", "icon-viadeo-square", "icon-viadeo", "icon-viacoin", "icon-ussunnah", "icon-vaadin", "icon-usb", "icon-untappd", "icon-uniregistry", "icon-uikit", "icon-uber", "icon-typo", "icon-twitter-square", "icon-twitter", "icon-twitch", "icon-tumblr-square", "icon-tripadvisor", "icon-tumblr", "icon-themeisle", "icon-trello", "icon-trade-federation", "icon-themeco", "icon-tencent-weibo", "icon-telegram-plane", "icon-telegram", "icon-supple", "icon-teamspeak", "icon-superpowers", "icon-stumbleupon-circle", "icon-stumbleupon", "icon-studiovinari", "icon-strava", "icon-stripe-s", "icon-stripe", "icon-sticker-mule", "icon-steam-symbol", "icon-steam-square", "icon-steam", "icon-staylinked", "icon-stack-overflow", "icon-stack-exchange", "icon-squarespace", "icon-spotify", "icon-speakap", "icon-soundcloud", "icon-snapchat-square", "icon-snapchat-ghost", "icon-snapchat", "icon-slideshare", "icon-slack-hash", "icon-slack", "icon-skype", "icon-skyatlas", "icon-sistrix", "icon-simplybuilt", "icon-shirtsinbulk", "icon-shopware", "icon-searchengin", "icon-sellcast", "icon-servicestack", "icon-sellsy", "icon-scribd", "icon-schlix", "icon-sass", "icon-safari", "icon-r-project", "icon-rockrms", "icon-rocketchat", "icon-rev", "icon-resolving", "icon-researchgate", "icon-replyd", "icon-renren", "icon-rendact", "icon-red-river", "icon-reddit-square", "icon-reddit-alien", "icon-reddit", "icon-rebel", "icon-readme", "icon-react", "icon-ravelry", "icon-r", "icon-quora", "icon-python", "icon-quinscape", "icon-qq", "icon-pushed", "icon-product-hunt", "icon-playstation", "icon-pinterest-square", "icon-pinterest-p", "icon-pinterest", "icon-pied-piper-pp", "icon-pied-piper-hat", "icon-pied-piper", "icon-pied-piper-alt", "icon-php", "icon-phoenix-squadron", "icon-phoenix-framework", "icon-phabricator", "icon-periscope", "icon-paypal", "icon-patreon", "icon-palfed", "icon-optin-monster", "icon-pagelines", "icon-page", "icon-osi", "icon-opera", "icon-old-republic", "icon-openid", "icon-opencart", "icon-odnoklassniki-square", "icon-odnoklassniki", "icon-nutritionix", "icon-ns", "icon-npm", "icon-node-js", "icon-node", "icon-nintendo-switch", "icon-nimblr", "icon-neos", "icon-napster", "icon-monero", "icon-modx", "icon-mizuni", "icon-mixcloud", "icon-mix", "icon-microsoft", "icon-megaport", "icon-meetup", "icon-medrt", "icon-medium-m", "icon-medium", "icon-medapps", "icon-maxcdn", "icon-mastodon", "icon-mandalorian", "icon-markdown", "icon-mailchimp", "icon-linux", "icon-linode", "icon-magento", "icon-lyft", "icon-linkedin-in", "icon-linkedin", "icon-line", "icon-less", "icon-leanpub", "icon-lastfm-square", "icon-lastfm", "icon-laravel", "icon-korvue", "icon-kickstarter-k", "icon-kickstarter", "icon-keycdn", "icon-keybase", "icon-kaggle", "icon-js-square", "icon-jsfiddle", "icon-js", "icon-joomla", "icon-joget", "icon-jenkins", "icon-jedi-order", "icon-java", "icon-itunes-note", "icon-itunes", "icon-ioxhost", "icon-internet-explorer", "icon-instagram", "icon-imdb", "icon-hubspot", "icon-html", "icon-houzz", "icon-hotjar", "icon-hornbill", "icon-hooli", "icon-hire-a-helper", "icon-hips", "icon-hackerrank", "icon-hacker-news-square", "icon-grunt", "icon-hacker-news", "icon-gulp", "icon-gripfire", "icon-grav", "icon-gratipay", "icon-google-wallet", "icon-google-plus-square", "icon-google-plus-g", "icon-google-plus", "icon-google-play", "icon-google-drive", "icon-google", "icon-goodreads-g", "icon-goodreads", "icon-gofore", "icon-glide-g", "icon-glide", "icon-gitter", "icon-git-square", "icon-gitlab", "icon-gitkraken", "icon-github-square", "icon-github-alt", "icon-github", "icon-git", "icon-gg-circle", "icon-gg", "icon-get-pocket", "icon-galactic-senate", "icon-galactic-republic", "icon-fulcrum", "icon-free-code-camp", "icon-freebsd", "icon-foursquare", "icon-fort-awesome-alt", "icon-forumbee", "icon-fort-awesome", "icon-fonticons-fi", "icon-fonticons", "icon-font-awesome-flag", "icon-font-awesome-alt", "icon-font-awesome", "icon-fly", "icon-flipboard", "icon-flickr", "icon-first-order-alt", "icon-first-order", "icon-firstdraft", "icon-firefox", "icon-facebook-square", "icon-facebook-messenger", "icon-facebook-f", "icon-facebook", "icon-expeditedssl", "icon-etsy", "icon-ethereum", "icon-erlang", "icon-envira", "icon-empire", "icon-ember", "icon-ello", "icon-elementor", "icon-edge", "icon-ebay", "icon-earlybirds", "icon-dyalog", "icon-drupal", "icon-dribbble-square", "icon-dropbox", "icon-dribbble", "icon-draftdigital", "icon-docker", "icon-dochub", "icon-discourse", "icon-discord", "icon-digital-ocean", "icon-digg", "icon-dashcube", "icon-deskpro", "icon-deviantart", "icon-deploydog", "icon-delicious", "icon-d-and-d", "icon-cuttlefish", "icon-css-alt", "icon-css", "icon-creative-commons-sha", "icon-creative-commons-sam1", "icon-creative-commons-sam", "icon-creative-commons-rem", "icon-creative-commons-sa", "icon-creative-commons-pd-", "icon-creative-commons-pd", "icon-creative-commons-nd", "icon-sith", "icon-creative-commons-nc-1", "icon-creative-commons-nc-", "icon-creative-commons-nc", "icon-creative-commons-by", "icon-creative-commons", "icon-cpanel", "icon-contao", "icon-connectdevelop", "icon-codepen", "icon-codiepie", "icon-cloudversify", "icon-cloudsmith", "icon-cloudscale", "icon-chrome", "icon-cc-visa", "icon-centercode", "icon-cc-stripe", "icon-cc-paypal", "icon-cc-mastercard", "icon-cc-amex", "icon-cc-jcb", "icon-cc-apple-pay", "icon-cc-discover", "icon-cc-diners-club", "icon-cc-amazon-pay", "icon-buysellads", "icon-btc", "icon-buromobelexperte", "icon-bluetooth", "icon-bluetooth-b", "icon-blogger-b", "icon-blogger", "icon-black-tie", "icon-bity", "icon-bitcoin", "icon-blackberry", "icon-bitbucket", "icon-bimobject", "icon-behance-square", "icon-behance", "icon-bandcamp", "icon-aws", "icon-aviato", "icon-avianex", "icon-autoprefixer", "icon-audible", "icon-asymmetrik", "icon-app-store-ios", "icon-app-store", "icon-apple-pay", "icon-apper", "icon-angrycreative", "icon-apple", "icon-angular", "icon-amazon-pay", "icon-angellist", "icon-android", "icon-amilia", "icon-amazon", "icon-algolia", "icon-adversal", "icon-affiliatetheme", "icon-accusoft", "icon-adn", "icon-px", "icon-accessible-icon", "icon-yen-sign", "icon-x-ray", "icon-won-sign", "icon-wrench", "icon-wine-glass-alt", "icon-wine-glass", "icon-window-restore", "icon-window-minimize", "icon-window-maximize", "icon-window-close", "icon-wifi", "icon-wheelchair", "icon-weight-hanging", "icon-warehouse", "icon-weight", "icon-wallet", "icon-walking", "icon-volume-up", "icon-volume-off", "icon-volume-down", "icon-volleyball-ball", "icon-video-slash", "icon-video", "icon-vials", "icon-vial", "icon-venus-mars", "icon-venus-double", "icon-venus", "icon-vector-square", "icon-utensil-spoon", "icon-utensils", "icon-user-times", "icon-user-tie", "icon-user-tag", "icon-user-slash", "icon-user-shield", "icon-user-secret", "icon-users-cog", "icon-users", "icon-user-plus", "icon-user-ninja", "icon-user-minus", "icon-user-md", "icon-user-lock", "icon-user-graduate", "icon-user-friends", "icon-user-edit", "icon-user-cog", "icon-user-clock", "icon-user-circle", "icon-user-check", "icon-user-astronaut", "icon-user-alt-slash", "icon-user-alt", "icon-user", "icon-upload", "icon-unlink", "icon-unlock-alt", "icon-unlock", "icon-university", "icon-universal-access", "icon-undo-alt", "icon-undo", "icon-underline", "icon-umbrella-beach", "icon-umbrella", "icon-tv", "icon-tty", "icon-tshirt", "icon-truck-pickup", "icon-truck-moving", "icon-truck-monster", "icon-truck-loading", "icon-truck", "icon-trophy", "icon-train", "icon-tree", "icon-trash-alt", "icon-trash", "icon-transgender-alt", "icon-transgender", "icon-traffic-light", "icon-trademark", "icon-tooth", "icon-toolbox", "icon-toggle-on", "icon-toggle-off", "icon-tired", "icon-tint-slash", "icon-tint", "icon-times-circle", "icon-times", "icon-ticket-alt", "icon-thumbtack", "icon-thumbs-up", "icon-thumbs-down", "icon-th-list", "icon-th-large", "icon-thermometer-three-qu", "icon-thermometer-quarter", "icon-thermometer-half", "icon-thermometer-full", "icon-thermometer-empty", "icon-thermometer", "icon-theater-masks", "icon-th", "icon-text-width", "icon-text-height", "icon-terminal", "icon-teeth-open", "icon-teeth", "icon-taxi", "icon-tasks", "icon-tape", "icon-tag", "icon-tags", "icon-tablets", "icon-tachometer-alt", "icon-table-tennis", "icon-tablet-alt", "icon-tablet", "icon-table", "icon-syringe", "icon-sync-alt", "icon-sync", "icon-swimming-pool", "icon-swimmer", "icon-swatchbook", "icon-surprise", "icon-superscript", "icon-sun", "icon-stroopwafel", "icon-suitcase-rolling", "icon-suitcase", "icon-subway", "icon-subscript", "icon-strikethrough", "icon-street-view", "icon-stream", "icon-store-alt", "icon-store", "icon-stopwatch", "icon-stop-circle", "icon-stop", "icon-sticky-note", "icon-stethoscope", "icon-step-forward", "icon-step-backward", "icon-star-of-life", "icon-star-half-alt", "icon-star", "icon-star-half", "icon-stamp", "icon-square-full", "icon-square", "icon-spray-can", "icon-splotch", "icon-spinner", "icon-space-shuttle", "icon-spa", "icon-sort-up", "icon-sort-numeric-up", "icon-sort-numeric-down", "icon-sort-down", "icon-sort-amount-up", "icon-sort-amount-down", "icon-sort-alpha-down", "icon-solar-panel", "icon-sort", "icon-snowflake", "icon-smoking-ban", "icon-smoking", "icon-smile-wink", "icon-smile-beam", "icon-smile", "icon-sliders-h", "icon-skull", "icon-sitemap", "icon-sign-out-alt", "icon-sign-language", "icon-sign-in-alt", "icon-signature", "icon-signal", "icon-shoe-prints", "icon-sign", "icon-shuttle-van", "icon-shower", "icon-shopping-cart", "icon-shopping-basket", "icon-shopping-bag", "icon-shipping-fast", "icon-ship", "icon-shekel-sign", "icon-share-square", "icon-share-alt-square", "icon-share-alt", "icon-share", "icon-shapes", "icon-server", "icon-seedling", "icon-search-plus", "icon-search-minus", "icon-search", "icon-screwdriver", "icon-school", "icon-save", "icon-sad-tear", "icon-sad-cry", "icon-rupee-sign", "icon-ruler-vertical", "icon-ruler-horizontal", "icon-ruler-combined", "icon-ruler", "icon-ruble-sign", "icon-rss-square", "icon-route", "icon-rss", "icon-rocket", "icon-robot", "icon-road", "icon-ribbon", "icon-retweet", "icon-reply-all", "icon-reply", "icon-registered", "icon-redo-alt", "icon-redo", "icon-recycle", "icon-receipt", "icon-random", "icon-quote-right", "icon-quote-left", "icon-quidditch", "icon-question-circle", "icon-question", "icon-qrcode", "icon-project-diagram", "icon-puzzle-piece", "icon-procedures", "icon-print", "icon-prescription-bottle-", "icon-prescription-bottle", "icon-prescription", "icon-power-off", "icon-pound-sign", "icon-portrait", "icon-poop", "icon-poo", "icon-podcast", "icon-plus-square", "icon-plus-circle", "icon-plus", "icon-plug", "icon-play-circle", "icon-play", "icon-plane-departure", "icon-plane-arrival", "icon-plane", "icon-pills", "icon-piggy-bank", "icon-phone-volume", "icon-phone-square", "icon-phone-slash", "icon-phone", "icon-percentage", "icon-percent", "icon-people-carry", "icon-pen-square", "icon-pen-nib", "icon-pen-fancy", "icon-pencil-ruler", "icon-pencil-alt", "icon-pen-alt", "icon-pen", "icon-paw", "icon-pause-circle", "icon-pause", "icon-paste", "icon-passport", "icon-parking", "icon-paragraph", "icon-parachute-box", "icon-paper-plane", "icon-paperclip", "icon-paint-brush", "icon-oil-can", "icon-paint-roller", "icon-pallet", "icon-palette", "icon-outdent", "icon-object-ungroup", "icon-object-group", "icon-notes-medical", "icon-not-equal", "icon-newspaper", "icon-neuter", "icon-mouse-pointer", "icon-music", "icon-monument", "icon-motorcycle", "icon-mortar-pestle", "icon-moon", "icon-money-check-alt", "icon-money-check", "icon-money-bill-wave-alt", "icon-money-bill-wave", "icon-money-bill-alt", "icon-money-bill", "icon-mobile-alt", "icon-mobile", "icon-minus-square", "icon-minus-circle", "icon-minus", "icon-microscope", "icon-microphone-slash", "icon-microphone-alt-slash", "icon-microchip", "icon-microphone-alt", "icon-microphone", "icon-mercury", "icon-memory", "icon-meh-rolling-eyes", "icon-meh-blank", "icon-sort-alpha-up", "icon-meh", "icon-medkit", "icon-mars-stroke", "icon-medal", "icon-map-signs", "icon-mars-stroke-v", "icon-mars-stroke-h", "icon-shield-alt", "icon-mars-double", "icon-mars", "icon-marker", "icon-map-pin", "icon-map-marker-alt", "icon-map-marker", "icon-map", "icon-map-marked-alt", "icon-map-marked", "icon-male", "icon-magnet", "icon-magic", "icon-luggage-cart", "icon-low-vision", "icon-long-arrow-alt-up", "icon-long-arrow-alt-right", "icon-long-arrow-alt-left", "icon-long-arrow-alt-down", "icon-lock", "icon-location-arrow", "icon-lock-open", "icon-list-ol", "icon-list-ul", "icon-list-alt", "icon-link", "icon-lira-sign", "icon-list", "icon-lightbulb", "icon-life-ring", "icon-level-up-alt", "icon-level-down-alt", "icon-less-than-equal", "icon-lemon", "icon-less-than", "icon-leaf", "icon-laugh", "icon-layer-group", "icon-laugh-beam", "icon-laugh-wink", "icon-laugh-squint", "icon-laptop-code", "icon-language", "icon-laptop", "icon-kiss-beam", "icon-kiwi-bird", "icon-kiss-wink-heart", "icon-kiss", "icon-keyboard", "icon-key", "icon-joint", "icon-italic", "icon-info-circle", "icon-info", "icon-infinity", "icon-industry", "icon-indent", "icon-inbox", "icon-images", "icon-image", "icon-id-card-alt", "icon-id-card", "icon-id-badge", "icon-i-cursor", "icon-h-square", "icon-hourglass-start", "icon-hourglass-half", "icon-hourglass-end", "icon-hourglass", "icon-hot-tub", "icon-hotel", "icon-hospital-symbol", "icon-hospital-alt", "icon-hospital", "icon-home", "icon-heartbeat", "icon-hockey-puck", "icon-history", "icon-highlighter", "icon-helicopter", "icon-heart", "icon-headset", "icon-headphones-alt", "icon-headphones", "icon-heading", "icon-hdd", "icon-hashtag", "icon-hand-spock", "icon-hands-helping", "icon-handshake", "icon-hand-scissors", "icon-hands", "icon-hand-rock", "icon-hand-point-up", "icon-hand-point-right", "icon-hand-point-left", "icon-hand-pointer", "icon-hand-point-down", "icon-hand-peace", "icon-hand-paper", "icon-hand-lizard", "icon-hand-holding-usd", "icon-hand-holding-heart", "icon-hand-holding", "icon-grip-vertical", "icon-grin-wink", "icon-grin-tongue-wink", "icon-grin-tongue-squint", "icon-grin-tongue", "icon-grin-tears", "icon-grin-stars", "icon-grin-squint-tears", "icon-grin-squint", "icon-grin-hearts", "icon-grin-beam-sweat", "icon-grin-beam", "icon-grin-alt", "icon-grin", "icon-grimace", "icon-greater-than-equal", "icon-greater-than", "icon-graduation-cap", "icon-golf-ball", "icon-globe-asia", "icon-globe-americas", "icon-globe-africa", "icon-globe", "icon-glass-martini-alt", "icon-glass-martini", "icon-glasses", "icon-gift", "icon-genderless", "icon-gem", "icon-gavel", "icon-gas-pump", "icon-gamepad", "icon-futbol", "icon-frown-open", "icon-frown", "icon-font", "icon-frog", "icon-forward", "icon-football-ball", "icon-folder-open", "icon-folder", "icon-flushed", "icon-flask", "icon-flag-checkered", "icon-flag", "icon-fish", "icon-first-aid", "icon-fire-extinguisher", "icon-fire", "icon-fingerprint", "icon-filter", "icon-film", "icon-fill-drip", "icon-fill", "icon-file-word", "icon-file-video", "icon-file-upload", "icon-file-signature", "icon-file-prescription", "icon-file-powerpoint", "icon-file-pdf", "icon-file-medical-alt", "icon-file-medical", "icon-file-invoice-dollar", "icon-file-invoice", "icon-file-import", "icon-file-image", "icon-file-export", "icon-file-excel", "icon-file-download", "icon-file-contract", "icon-file-code", "icon-file-audio", "icon-file-archive", "icon-file-alt", "icon-file", "icon-fighter-jet", "icon-female", "icon-feather-alt", "icon-feather", "icon-fax", "icon-fast-forward", "icon-fast-backward", "icon-eye-slash", "icon-eye-dropper", "icon-eye", "icon-external-link-square", "icon-external-link-alt", "icon-exclamation-circle", "icon-expand-arrows-alt", "icon-expand", "icon-exclamation-triangle", "icon-exclamation", "icon-exchange-alt", "icon-euro-sign", "icon-eraser", "icon-equals", "icon-envelope-square", "icon-envelope-open", "icon-envelope", "icon-ellipsis-v", "icon-ellipsis-h", "icon-eject", "icon-edit", "icon-dumbbell", "icon-drum-steelpan", "icon-drum", "icon-draw-polygon", "icon-drafting-compass", "icon-download", "icon-dove", "icon-dot-circle", "icon-door-open", "icon-dolly-flatbed", "icon-door-closed", "icon-donate", "icon-dolly", "icon-dollar-sign", "icon-dna", "icon-dizzy", "icon-divide", "icon-directions", "icon-digital-tachograph", "icon-dice-two", "icon-dice-three", "icon-dice-six", "icon-dice-one", "icon-dice-four", "icon-dice-five", "icon-dice", "icon-diagnoses", "icon-desktop", "icon-deaf", "icon-database", "icon-cut", "icon-cubes", "icon-cube", "icon-crown", "icon-crow", "icon-crosshairs", "icon-crop-alt", "icon-crop", "icon-credit-card", "icon-couch", "icon-copyright", "icon-copy", "icon-cookie-bite", "icon-cookie", "icon-concierge-bell", "icon-compress", "icon-compass", "icon-compact-disc", "icon-comment-slash", "icon-comments", "icon-comment-dots", "icon-cog", "icon-comment-alt", "icon-comment", "icon-cogs", "icon-coins", "icon-columns", "icon-code-branch", "icon-coffee", "icon-code", "icon-cocktail", "icon-cloud-upload-alt", "icon-cloud-download-alt", "icon-cloud", "icon-closed-captioning", "icon-clock", "icon-clone", "icon-clipboard-list", "icon-clipboard-check", "icon-clipboard", "icon-circle-notch", "icon-church", "icon-circle", "icon-child", "icon-chevron-up", "icon-chevron-right", "icon-chevron-left", "icon-chevron-down", "icon-grip-horizontal", "icon-chevron-circle-up", "icon-chevron-circle-right", "icon-chess-rook", "icon-chevron-circle-left", "icon-chevron-circle-down", "icon-chess-queen", "icon-chess-pawn", "icon-chess-knight", "icon-chess", "icon-check-square", "icon-chess-bishop", "icon-chess-board", "icon-chess-king", "icon-check-double", "icon-check-circle", "icon-chart-pie", "icon-chart-line", "icon-check", "icon-chart-bar", "icon-chart-area", "icon-charging-station", "icon-cart-plus", "icon-certificate", "icon-chalkboard-teacher", "icon-chalkboard", "icon-cart-arrow-down", "icon-car-side", "icon-caret-up", "icon-caret-square-up", "icon-caret-square-right", "icon-caret-square-left", "icon-car-crash", "icon-caret-square-down", "icon-caret-left", "icon-caret-down", "icon-caret-right", "icon-car-battery", "icon-cannabis", "icon-car-alt", "icon-camera-retro", "icon-capsules", "icon-car", "icon-camera", "icon-calendar-times", "icon-calendar-plus", "icon-calendar-check", "icon-calendar-minus", "icon-calendar-alt", "icon-bus-alt", "icon-calendar", "icon-calculator", "icon-bus", "icon-burn", "icon-bullseye", "icon-bullhorn", "icon-building", "icon-bug", "icon-brush", "icon-broom", "icon-broadcast-tower", "icon-briefcase-medical", "icon-briefcase", "icon-brain", "icon-braille", "icon-box", "icon-box-open", "icon-boxes", "icon-bowling-ball", "icon-book-reader", "icon-book-open", "icon-bookmark", "icon-book", "icon-bong", "icon-bone", "icon-bomb", "icon-bolt", "icon-bold", "icon-blind", "icon-birthday-cake", "icon-blender", "icon-bicycle", "icon-binoculars", "icon-bezier-curve", "icon-bell-slash", "icon-bell", "icon-beer", "icon-bed", "icon-battery-three-quarte", "icon-battery-quarter", "icon-battery-half", "icon-battery-full", "icon-battery-empty", "icon-bath", "icon-basketball-ball", "icon-baseball-ball", "icon-bars", "icon-barcode", "icon-band-aid", "icon-ban", "icon-balance-scale", "icon-audio-description", "icon-backward", "icon-backspace", "icon-award", "icon-atom", "icon-atlas", "icon-at", "icon-asterisk", "icon-assistive-listening-", "icon-arrow-up", "icon-arrows-alt-v", "icon-arrows-alt-h", "icon-arrows-alt", "icon-arrow-right", "icon-arrow-left", "icon-arrow-down", "icon-arrow-circle-up", "icon-arrow-circle-right", "icon-arrow-circle-left", "icon-arrow-circle-down", "icon-arrow-alt-circle-up", "icon-arrow-alt-circle-rig", "icon-arrow-alt-circle-lef", "icon-angle-right", "icon-arrow-alt-circle-dow", "icon-archway", "icon-archive", "icon-apple-alt", "icon-angry", "icon-angle-up", "icon-angle-left", "icon-angle-down", "icon-angle-double-down", "icon-angle-double-up", "icon-angle-double-left", "icon-angle-double-right", "icon-allergies", "icon-anchor", "icon-american-sign-langua", "icon-ambulance", "icon-align-right", "icon-align-left", "icon-align-justify", "icon-air-freshener", "icon-align-center", "icon-address-card", "icon-adjust", "icon-address-book"];

    export default {
        name: 'ezm-icon-picker',
        mixins: [],
        components: {EzmWindowItem, EzmWindow, EzmEditDialog},
        props: {
            placeholder: {
                default: '点击选择图标'
            },
            value: {
                default: ''
            },
            disabled: {
                default: false,
            }
        },
        data() {
            return {
                showDialog: false,
                iconsList: [],
                searchText: "",
                showRow: 100,
                showTimer: null,
            }
        },
        computed: {
            previewIcon() {
                if (this.value) {
                    return 'iconfont ' + this.value;
                }
                return 'el-icon-question gray';
            }
        },
        watch: {
            showDialog(v) {
                clearTimeout(this.showTimer);
                if (v) this.showIcon();
                else this.showRow = 100;
            }
        },
        created() {
        },
        mounted() {
            this.iconsList = icons;
        },
        beforeDestroy() {
        },
        destroyed() {
        },
        methods: {
            showIcon() {
                if (this.showRow > this.iconsList.length) return;
                this.showTimer = setTimeout(() => {
                    this.showRow += 200;
                    this.showIcon();
                }, 200)
            },
            clickIcon(item) {
                clearTimeout(this.showRow);
                this.$emit('input', item);
                this.showDialog = false;
            },
            cancelIcon() {
                clearTimeout(this.showRow);
                this.$emit('input', "");
                this.showDialog = false;
            }
        }
    };
</script>

<style scoped lang="less">
    .ezm-icon-picker {

    }

    .icon-container {
        width: 10%;
        padding: 25px 10px 10px 10px;
        margin: 10px;
        cursor: pointer;
        border-radius: 5px;

        &:hover {
            background-color: #001f3f;
            color: #a9c0d8;
        }

        .iconfont {
            font-size: 40px;
        }
    }
</style>