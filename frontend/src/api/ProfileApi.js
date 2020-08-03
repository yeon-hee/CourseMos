import axios from "axios";
import url from "./Url.js";

const requestCount = (data, callback, errorCallback) => {
    axios.get(url.url + '/counts/profile', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestUserCount = (data, callback, errorCallback) => {
    axios.get(url.url + '/counts/profile/' + data.email, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestProfile = (data, callback, errorCallback) => {
    axios.get(url.url + '/users/profile', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestUserProfile = (data, callback, errorCallback) => {
    axios.get(url.url + '/users/profile/' + data.userId, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestFeed = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds/' + data.userId)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const ProfileApi = {
    requestCount: (data, callback, errorCallback) => requestCount(data, callback, errorCallback),
    requestUserCount: (data, callback, errorCallback) => requestUserCount(data, callback, errorCallback),
    requestProfile: (data, callback, errorCallback) => requestProfile(data, callback, errorCallback),
    requestUserProfile: (data, callback, errorCallback) => requestUserProfile(data, callback, errorCallback),
    requestFeed: (data, callback, errorCallback) => requestFeed(data, callback, errorCallback)
}

export default ProfileApi