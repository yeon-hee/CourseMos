<template>
    <div class="wrap components-page">
        <div class="wrapB">
            <h1>피드 작성하기</h1>

            <h2>form</h2>
            <div class="wrap">
                <!-- input type=text -->


                <textarea cols="50" rows="10" v-model="contents" style="border-style: solid;"></textarea>

                
            </div>

            <div class="wrap">
                <input @change="selectImage()" type="file" ref="feedImage" name="imgFile" class="upload-input" accept="image/*">
                <ul class="profile-pictures">
                    <li class="profile-pictures" v-for="thumbnail in imageThumbnails" :key="thumbnail">
                        <img
                            :src="thumbnail"
                            class="profile-picture-picture"
                            width="40px" height="40px"
                        />
                    </li>
                </ul>
            </div>
            <div class="wrap">
                <input type="button" value="피드 추가" @click="checkImage()"/>
            </div>

        </div>
    </div>
</template>

<script>
import "../../assets/css/components.scss";
import "../../components/css/user/profile.css";
import FeedApi from "../../api/FeedApi";
import * as firebase from "firebase/app";

export default {
    components: {
    },
    data: () => {
        return {
            images : [],
            imageThumbnails : [],
            uploadedImageUrl : [],
            token : localStorage.getItem('token'),
            userId : localStorage.getItem('userId'),
            contents : ""
        };
    },
    methods : {
        selectImage() {
            const profileImage = this.$refs.feedImage.files[0];
            (File)
            console.dir(profileImage)
            this.images.push(profileImage)

            var binaryData = [];
            binaryData.push(profileImage)

            const profileThumbnail = window.URL.createObjectURL(new Blob(binaryData, {type: "application/zip"}))
            this.imageThumbnails.push(profileThumbnail)
        },
        checkImage() {
            if(this.images.length > 0 )
                this.uploadImages()
            else
                this.uploadFeed()
        },
        uploadImages() {
            for(var i = 0 ; i < this.images.length; i++) {
                var fullPath = new Date().getTime() + this.images[i].name
                var storageRef = firebase.storage().ref('images/' + fullPath);

                storageRef.put(this.images[i]).then( (snapshot) => {
                    var imageUrl = firebase.storage().ref('images/' + fullPath).getDownloadURL();
                    imageUrl.then((url) => {
                        console.log(url)
                        this.uploadedImageUrl.push(url)

                        if(this.uploadedImageUrl.length == this.images.length) {
                            alert("이미지 업로드 완료")
                            this.uploadFeed()
                        }
                    })
                })
            }
        },
        uploadFeed() {
            let data = {
                token : this.token,
                userId : this.userId,
                contents : this.contents,
                thumbnail : null
            }

            if(this.images.length > 0 ) {
                data.thumbnail = this.uploadedImageUrl[0]
            }
            FeedApi.writeFeed(
                data,
                response => {
                    console.dir(response);
                    let photoData = {
                        photoUrl : null,
                        feedNo : response.data.feedNo
                    }
                    if(this.images.length > 0 ) {
                        for(const url of this.uploadedImageUrl) {
                            photoData.photoUrl = url
                            FeedApi.uploadFeedImage(
                                photoData,
                                response => {
                                    console.log(url)
                                },
                                error => {
                                    alert(error);
                                }
                            )
                        }
                        this.$router.push("/feed/main");
                    }
                    else {
                        this.$router.push("/feed/main");
                    }
                },
                error => {
                    alert(error);
                }
            )
        }
    }
};
</script>

<style scoped>
</style>
