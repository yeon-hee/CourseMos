import Login from "./views/user/Login.vue";
import FindPassword from "./views/user/findPassword.vue";
import Join from "./views/user/Join.vue";
import FeedMain from "./views/feed/IndexFeed.vue";
import Components from "./views/Components.vue";
import ChangePassword from "./views/user/ChangePassword.vue";
// import SignUpComplete from "./views/user/SignUpComplete.vue";
import NotFound from "./views/error/NotFound.vue";
import Error from "./views/error/Error";
import Profile from "./views/user/Profile.vue";
import UserProfile from "./views/user/UserProfile.vue";
import Alerts from "./views/user/Alerts.vue";
import Requests from "./views/user/Requests.vue";
import AlertTab from "./components/alert/AlertTab.vue";
import Followers from "./views/user/Followers.vue";
import Followings from "./views/user/Followings.vue";
import Search from "./views/Search.vue";
import AccountSetting from "./views/user/AccountSetting.vue";
import TradeDetail from "./views/trade/TradeDetail.vue";
import FeedDetail from "./views/feed/FeedDetail.vue";
import FeedComment from "./components/feed/FeedComment.vue";
import FeedItemDetail from "./components/feed/FeedItemDetail.vue";
import Crawling from "./components/feed/Crawling.vue";
import MapPage from "./views/Map.vue";
import WriteFeedPage from "./views/feed/WriteFeed.vue";
import FeedMake from "./views/feed/FeedMake.vue";
import Main from "./views/Main";
import WorldCup from "./views/feed/WorldCup.vue";
import FoodCup from "./views/feed/FoodCup.vue";
import PlayCup from "./views/feed/PlayCup.vue";
import ActCup from "./views/feed/ActCup.vue";
import UserFollowers from "./views/user/UserFollowers";
import UserFollowings from "./views/user/UserFollowings";

export default [{
        path: "/",
        name: "Login",
        component: Login,
    },
    {
        path: "/user/join",
        name: "Join",
        component: Join,
    },
    {
        path: "/user/find/password",
        name: "FindPassword",
        component: FindPassword,
    },
    {
        path: "/error",
        name: "Error",
        component: Error,
    },
    {
        path: "*",
        name: "NotFound",
        component: NotFound,
    },
    {
        path: "/",
        name: "Main",
        component: Main,
        children: [{
                path: "/feed/main",
                name: "FeedMain",
                component: FeedMain,
            },
            {
                path: "/feed/main/:worldcup",
                name: "FromWordCupToFeedMain",
                component: FeedMain,
            },
            {
                path: "/components",
                name: "Components",
                component: Components,
            },
            {
                path: "/users/profile/password",
                name: "ChangePassword",
                component: ChangePassword,
            },
            // {
            //     path: "/user/signup/complete",
            //     name: "SignUpComplete",
            //     component: SignUpComplete,
            // },
            {
                path: "/users/profile",
                name: "Profile",
                component: Profile,
            },
            {
                path: "/users/profile/info/:userId",
                name: "UserProfile",
                component: UserProfile,
            },
            {
                path: "/alerts",
                name: "Alerts",
                component: Alerts,
            },
            {
                path: "/requests",
                name: "Requests",
                component: Requests,
            },
            {
                path: "/alertTab",
                name: "AlertTab",
                component: AlertTab
            },
            {
                path: "/friends/followers",
                name: "Followers",
                component: Followers,
            },
            {
                path: "/friends/followings",
                name: "Followings",
                component: Followings,
            },
            {
                path: "/search",
                name: "Search",
                component: Search,
            },
            {
                path: "/users/profile/setting",
                name: "AccountSetting",
                component: AccountSetting,
            },
            {
                path: "/feeds/:feedNo",
                name: "FeedDetail",
                component: FeedDetail,
            },
            {
                path: "/map",
                name: "Map",
                component: MapPage,
            },
            {
                path: "/feed/write",
                name: "WriteFeed",
                component: WriteFeedPage,
            },
            {
                path: "/feedmake",
                name: "FeedMake",
                component: FeedMake,
            },
            {
                path: "/feeds/comments/:feedNo",
                name: "FeedComment",
                component: FeedComment,
            },
            {
                path: "/feeds/back/:feedNo",
                name: "FeedItemDetail",
                component: FeedItemDetail,
            },
            {
                path: "/crawling",
                name: "Crawling",
                component: Crawling,
            },
            {
                path: "/trade/:id",
                name: "TradeDetailForMakeFeed",
                component: TradeDetail,
            },
            {
                path: "/trade/:id/:feedNo",
                name: "TradeDetailForFeedDetail",
                component: TradeDetail,
            },
            {
                path: "/worldcup",
                name: "WorldCup",
                component: WorldCup,
            },
            {
                path: "/worldcup/food",
                name: "FoodCup",
                component: FoodCup,
            },
            {
                path: "/worldcup/play",
                name: "PlayCup",
                component: PlayCup,
            },
            {
                path: "/worldcup/act",
                name: "ActCup",
                component: ActCup,
            },
            {
                path: "/friends/followers/:email",
                name: "UserFollowers",
                component: UserFollowers,
            },
            {
                path: "/friends/followings/:email",
                name: "UserFollowings",
                component: UserFollowings,
            },
        ]
    },
];