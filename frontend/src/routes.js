import Login from './views/user/Login.vue'
import FindPassword from './views/user/findPassword.vue'
import Join from './views/user/Join.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import ChangePassword from './views/user/ChangePassword.vue'
import SignUpComplete from './views/user/SignUpComplete.vue'
import NotFound from './views/error/NotFound.vue'
import Error from './views/error/Error'
import Profile from './views/user/Profile.vue'
import UserProfile from './views/user/UserProfile.vue'
import Alerts from './views/user/Alerts.vue'
import Requests from './views/user/Requests.vue'
import Followers from './views/user/Followers.vue'
import Followings from './views/user/Followings.vue'
import Search from './views/Search.vue'
import AccountSetting from './views/user/AccountSetting.vue';
import FeedDetail from './views/feed/FeedDetail.vue';

export default [{
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/user/join',
        name: 'Join',
        component: Join
    },
    {
        path: '/user/find/password',
        name: 'FindPassword',
        component: FindPassword
    },
    {
        path: '/feed/main',
        name: 'FeedMain',
        component: FeedMain
    },
    {
        path: '/components',
        name: 'Components',
        component: Components
    },
    {
        path: '/users/profile/password',
        name: 'ChangePassword',
        component: ChangePassword
    },
    {
        path: '/user/signup/complete',
        name: 'SignUpComplete',
        component: SignUpComplete
    },
    {
        path: '/error',
        name: 'Error',
        component: Error
    },
    {
        path: '*',
        name: 'NotFound',
        component: NotFound
    },
    {
        path: '/users/profile',
        name: 'Profile',
        component: Profile
    },
    {
        path: '/users/profile/info/:userId',
        name: 'UserProfile',
        component: UserProfile
    },
    {
        path: '/alerts',
        name: 'Alerts',
        component: Alerts
    },
    {
        path: '/requests',
        name: 'Requests',
        component: Requests
    },
    {
        path: '/friends/followers',
        name: 'Followers',
        component: Followers
    },
    {
        path: '/friends/followings',
        name: 'Followings',
        component: Followings
    },
    {
        path: '/search',
        name: 'Search',
        component: Search
    },
    {
        path: '/users/profile/setting',
        name: 'AccountSetting',
        component: AccountSetting
    },
    {
        path: '/feeds/:feedNo',
        name: 'FeedDetail',
        component: FeedDetail
    }
]