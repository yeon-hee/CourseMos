/*
 User API 예시
 */
import axios from "axios";
import url from "./Url.js";

const requestLogin = (data, callback, errorCallback) => {
    axios.post(url.url+'/users/login', data)
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}
const requestPassword = (data, callback, errorCallback) => {
    axios.put(url.url+'/users/profile/password', data, {
            headers: { "Authorization": data.token }
        })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}
const checkPassword = (data, callback, errorCallback) => {
    axios.get(url.url+'/users/profile/checkpassword?password=' + data.password, {
            headers: { "Authorization": data.token }
        })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}
const requestSignup = (data, callback, errorCallback) => {
    axios.post(url.url+"/users/signup", data)
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const findPassword = (data, callback, errorCallback) => {
    axios.get(url.url+"/users/find/password/" + data.userId + "/" + data.email, { headers: { "Authorization": data.token } })
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

// const checkDuplicate = (data, callback, errorCallback) => {
//     axios.post("http://127.0.0.1:8080/account/checkDuplicate", data)
//     .then( (res) => callback(res))
//     .catch( (res) => errorCallback(res));
// }

const requestSettings = (data, callback, errorCallback) => {
    axios.put(url.url+"/users/profile", data, { headers: { "Authorization": data.token } })
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const requestUserInfo = (data, callback, errorCallback) => {
    axios.get(url.url+'/users/profile', { headers: { "Authorization": data.token } })
        .then((res) => callback(res))
        .catch((res) => errorCallback(res));
}

const findUserBykeyword = (keyword, callback, errorCallback) => {
    axios.get(url.url+'/users/' + keyword )
        .then(res => callback(res))
        .catch(err => errorCallback(err));
}

const UserApi = {
    requestLogin: (data, callback, errorCallback) => requestLogin(data, callback, errorCallback),
    requestSignup: (data, callback, errorCallback) => requestSignup(data, callback, errorCallback),
    // checkDuplicate:(data,callback,errorCallback)=>checkDuplicate(data,callback,errorCallback),
    requestPassword: (data, callback, errorCallback) => requestPassword(data, callback, errorCallback),
    requestSettings: (data, callback, errorCallback) => requestSettings(data, callback, errorCallback),
    checkPassword: (data, callback, errorCallback) => checkPassword(data, callback, errorCallback),
    requestUserInfo: (data, callback, errorCallback) => requestUserInfo(data, callback, errorCallback),
    findUserBykeyword: (data, callback, errorCallback) => findUserBykeyword(data, callback, errorCallback),
    findPassword: (data, callback, errorCallback) => findPassword(data, callback, errorCallback)
}

export default UserApi