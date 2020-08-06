import axios from "axios";
import url from "./Url.js";

const loadFeeds = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds?page=' + data.page, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}
const loadUserFeeds = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds/user/' + data.userId, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const loadFeedDetail = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds/' + data.feedNo, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}
const loadFeedPhotos = (feedNo, callback, errorCallback) => {
    axios.get(url.url + '/feeds/' + feedNo + '/photos')
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}
const loadFeedComments = (data, callback, errorCallback) => {
    axios.get(url.url + '/comments/' + data.feedNo)
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const registerComment = (data, callback, errorCallback) => {
    axios.post(url.url + '/comments/', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const clickLike = (data, callback, errorCallback) => {
    axios.put(url.url + '/feeds/' + data.feedNo + '/like', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const deleteComment = (data, callback, errorCallback) => {
    axios.delete(url.url + '/comments/' + data.feedNo + '/' + data.commentNo, data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const getCourse = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds/course/' + data.feedNo, data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const writeFeed = (data, callback, errorCallback) => {
    axios.post(url.url + '/feeds', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const uploadFeedImage = (data, callback, errorCallback) => {
    axios.post(url.url + '/photos', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const searchFeeds = (data, callback, errorCallback) => {
    axios.get(url.url + '/feeds/search/' + data.search + "?page=" + data.page, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const FeedApi = {
    loadFeeds: (data, callback, errorCallback) => loadFeeds(data, callback, errorCallback),
    loadUserFeeds: (data, callback, errorCallback) => loadUserFeeds(data, callback, errorCallback),
    loadFeedDetail: (feedNo, callback, errorCallback) => loadFeedDetail(feedNo, callback, errorCallback),
    loadFeedPhotos: (feedNo, callback, errorCallback) => loadFeedPhotos(feedNo, callback, errorCallback),
    loadFeedComments: (feedNo, callback, errorCallback) => loadFeedComments(feedNo, callback, errorCallback),
    registerComment: (data, callback, errorCallback) => registerComment(data, callback, errorCallback),
    clickLike: (data, callback, errorCallback) => clickLike(data, callback, errorCallback),
    deleteComment: (data, callback, errorCallback) => deleteComment(data, callback, errorCallback),
    getCourse: (data, callback, errorCallback) => getCourse(data, callback, errorCallback),
    writeFeed: (data, callback, errorCallback) => writeFeed(data, callback, errorCallback),
    uploadFeedImage: (data, callback, errorCallback) => uploadFeedImage(data, callback, errorCallback),
    searchFeeds: (data, callback, errorCallback) => searchFeeds(data, callback, errorCallback)
}

export default FeedApi