import axios from "axios";
import url from "./Url.js";

const uploadFeedCourses = (data, callback, errorCallback) => {
    axios.post(url.url + '/courses', data, { headers: { "Authorization": data.token } })
        .then(res => callback(res))
        .catch(res => errorCallback(res));
}

const CourseApi = {
    uploadFeedCourses: (data, callback, errorCallback) => uploadFeedCourses(data, callback, errorCallback)
}

export default CourseApi