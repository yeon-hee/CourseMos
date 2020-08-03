import axios from "axios";
import url from "./Url.js";

const getFollower = (data, callback, errorCallback) => {
    axios.get(url.url+'/friends/followers/' + data.email)
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const deleteFollower = (data, callback, errorCallback) => {
    axios.delete(url.url+'/friends/followers/' + data.follower + '/' + data.email)
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const getFollowing = (data, callback, errorCallback) => {
    axios.get(url.url+'/friends/followings/' + data.follower)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const deleteFollowing = (data, callback, errorCallback) => {
    axios.delete(url.url+'/friends/followings/' + data.follower + '/' + data.userId)
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const updateFollower = (data, callback, errorCallback) => {
    axios.get(url.url+'/counts/followerDown/', { headers: { "Authorization": data.token } })
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const updateFollowing = (data, callback, errorCallback) => {
    axios.get(url.url+'/counts/followingDown/', { headers: { "Authorization": data.token } })
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const FollowApi = {
    getFollower: (data, callback, errorCallback) => getFollower(data, callback, errorCallback),
    deleteFollower: (data, callback, errorCallback) => deleteFollower(data, callback, errorCallback),
    getFollowing: (data, callback, errorCallback) => getFollowing(data, callback, errorCallback),
    deleteFollowing: (data, callback, errorCallback) => deleteFollowing(data, callback, errorCallback),
    updateFollower: (data, callback, errorCallback) => updateFollower(data, callback, errorCallback),
    updateFollowing: (data, callback, errorCallback) => updateFollowing(data, callback, errorCallback)
}

export default FollowApi