import axios from "axios";
import url from "./Url.js";

const requestList = (data, callback, errorCallback) => {
    axios.get(url.url + '/requests/' + data.email)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestReject = (data, callback, errorCallback) => {
    axios.delete(url.url + '/requests/' + data.req.follower + "/" + data.req.email)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestAllow = (data, callback, errorCallback) => {
    axios.get(url.url + '/requests/allow/' + data.req.follower + "/" + data.req.email, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestAllowAll = (data, callback, errorCallback) => {
    axios.get(url.url + '/requests/allowAll/', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestRejectAll = (data, callback, errorCallback) => {
    axios.delete(url.url + '/requests/rejectAll/', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const followRequest = (data, callback, errorCallback) => {
    axios.post(url.url + '/requests', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}


const RequestApi = {
    requestList: (data, callback, errorCallback) => requestList(data, callback, errorCallback),
    requestReject: (data, callback, errorCallback) => requestReject(data, callback, errorCallback),
    requestAllow: (data, callback, errorCallback) => requestAllow(data, callback, errorCallback),
    requestAllowAll: (data, callback, errorCallback) => requestAllowAll(data, callback, errorCallback),
    requestRejectAll: (data, callback, errorCallback) => requestRejectAll(data, callback, errorCallback),
    followRequest: (data, callback, errorCallback) => followRequest(data, callback, errorCallback)
}

export default RequestApi