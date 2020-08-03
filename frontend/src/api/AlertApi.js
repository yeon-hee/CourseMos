import axios from "axios";
import url from "./Url.js";

const requestAlert = (data, callback, errorCallback) => {
    axios.get(url.url+'/alerts', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestClose = (data, callback, errorCallback) => {
    axios.delete(url.url+'/alerts/' + data.alert.alertNo)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestCloseAll = (data, callback, errorCallback) => {
    axios.delete(url.url+'/alerts/CloseAll', { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const requestLike = (data, callback, errorCallback) => {
    axios.post(url.url+'/alerts/' + data.feed.userId + '/like' , { headers: { "Authorization": data.token }})
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const AlertApi = {
    requestAlert: (data, callback, errorCallback) => requestAlert(data, callback, errorCallback),
    requestClose: (data, callback, errorCallback) => requestClose(data, callback, errorCallback),
    requestCloseAll: (data, callback, errorCallback) => requestCloseAll(data, callback, errorCallback),
    requestLike: (data, callback, errorCallback) => requestLike(data, callback, errorCallback)
}

export default AlertApi