<template>
  <div class="bg-clr">
    <div class="main-container">

      <div class="header-area">
        <h2 style="">스터디룸 입장준비</h2>
        <p> {{ $route.params.studyroomId + '번 방 입장 준비 페이지' }} </p>
      </div>

      <div class="body-area">
        <div class="no-video-area" v-if="state.videoVar" style="" >
          <img :src="state.noVideoImg" alt="" style="">
        </div>
        <canvas v-if="!state.videoVar" style="" id="canvas" v-bind:class="{isStudying : state.isStudying}" class="isNotStudying"/>
      </div>

      <div class="footer-button-area" style="">
        <el-button @click="mute()" class="btn-stl" type="text" style="margin-left:50px;">
          <font-awesome-icon v-if="!state.muteVar" icon="microphone"/>
          <font-awesome-icon v-if="state.muteVar" icon="microphone-slash"/>
          <p style="margin-left:5px;">마이크</p>
        </el-button>
        <el-button @click="video()" class="btn-stl" type="text">
          <font-awesome-icon v-if="!state.videoVar" icon="video"/>
          <font-awesome-icon v-if="state.videoVar" icon="video-slash"/>
          <p style="margin-left:5px;">비디오</p>
        </el-button>
        <el-button
          class="btn-stl"
          type="text"
          @click="state.dialogVisible = true"
        >모션인식 도움말
        </el-button>
        <el-button
          @click="enterRoom(state.studyroomId)"
          plain type="success"
          style="height:80px; width:100px; border-bottom-right-radius:20px;"
        >입장</el-button>
      </div>
    </div>
    <el-dialog
      v-model="state.dialogVisible"
      width: 50%
    >
      <el-carousel
        :interval="500000"
        arrow="always"
        style="height:32vw"
      >
        <el-carousel-item v-for="tutorial in state.tutorials" :key="tutorial.id">
          <img style="width:35vw; height:auto; " :src="tutorial.imageUrl" alt="tutorial">
        </el-carousel-item>
      </el-carousel>
    </el-dialog>
  </div>
</template>
<style scoped>
  .isNotStudying {
    border-top-left-radius : 20px;
    border-top-right-radius: 20px;
    border : 5px solid red;
  }
  .isStudying {
    border-top-left-radius : 20px;
    border-top-right-radius: 20px;
    border : 5px solid green;
  }
  .bg-clr {
    background-color: black;
    width: 100vw;
    height: 100vh;
  }
  .bg-clr .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .bg-clr .main-container .header-area {
    color: white;
    text-align: center;
  }
  .bg-clr .main-container .footer-button-area {
    border-bottom-right-radius:20px;
    border-bottom-left-radius:20px;
    background-color: white;
    height: 80px;
    width: calc( 70vw + 10px );
    margin-top:-5px;
    display:flex;
    justify-content:space-between;
    align-items:center;
  }
  .bg-clr .main-container .header-area h2 {
    margin-bottom:0px;
  }
  .bg-clr .main-container .body-area canvas {
    width:70vw;
    height:34vw;
  }
  .bg-clr .main-container .body-area .no-video-area {
    width:calc( 70vw + 10px );
    height:34vw;
    background:gray;
    text-align:center;
    line-height:34vw;
  }
  .bg-clr .main-container .body-area .no-video-area img {
    vertical-align:middle;
    width:50vw;
    height:auto;
  }

  .bg-clr .main-container .footer-button-area .btn-stl {
    border: 0px;
    padding: 0px;
    color: black;
  }
  .bg-clr .main-container .footer-button-area .btn-stl:hover {
    color: rgba(58, 194, 88, 1);
  }

  .el-carousel__item {
    text-align: center;
  }
  .el-carousel__item {
    height: auto;
  }
  .el-dialog__body {
    background: black;
  }
  .el-dialog {
    --el-dialog-bg-color: black !important;
  }
  .el-carousel__container{
    height:33vw;
  }

</style>

<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'

// 티쳐블머신
import * as tmPose from '@teachablemachine/pose';

export default {
  name: 'studyroom-detail',
  components: {

  },

  setup () {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      studyroomId: '',
      isStudying : false,
      model : undefined,
      webcam : undefined,
      ctx : undefined,
      labelContainer : undefined,
      maxPredictions : undefined,
      URL : 'https://teachablemachine.withgoogle.com/models/tCGfolhxR/',
      muteVar: false,
      videoVar: false,
      dialogVisible: false,
      showModal: false,
      noVideoImg: require('../../assets/images/novideo.png'),
      tutorials: [
        {
          id: 1,
          imageUrl: require('../../assets/images/tutorial/tuto1.png')
        },
        {
          id: 2,
          imageUrl: require('../../assets/images/tutorial/tuto2.png')
        },
        {
          id: 3,
          imageUrl: require('../../assets/images/tutorial/tuto3.png')
        },
        {
          id: 4,
          imageUrl: require('../../assets/images/tutorial/tuto4.png')
        },
        {
          id: 5,
          imageUrl: require('../../assets/images/tutorial/tuto5.png')
        },
        {
          id: 6,
          imageUrl: require('../../assets/images/tutorial/tuto6.png')
        },
        {
          id: 7,
          imageUrl: require('../../assets/images/tutorial/tuto7.png')
        },
      ]
    })
    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.studyroomId = route.params.studyroomId
      store.commit('root/setMenuActiveMenuName', 'home')
      init()
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.studyroomId = ''
      stopVideo()
    })

    const enterRoom = function (id) {
      router.push({
        name: 'studyroom-inside',
        params: {
          studyroomId: id,
          isAudio: state.muteVar,
          isVideo: state.videoVar,
        }
      })
    }

    const onOpen = function () {
      state.dialogOpen = true
    }

    async function init() {
      const modelURL = state.URL + 'model.json';
      const metadataURL = state.URL + 'metadata.json';

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // Note: the pose library adds a tmPose object to your window (window.tmPose)
      state.model = Object.freeze(await tmPose.load(modelURL, metadataURL))
      console.log(state.model)
      state.maxPredictions = state.model.getTotalClasses();

      // Convenience function to setup a webcam
      const width = 1000;
      const height = 1000;
      const flip = false; // whether to flip the webcam
      state.webcam = new tmPose.Webcam(width, height, flip); // width, height, flip
      await state.webcam.setup(); // request access to the webcam
      await state.webcam.play();
      window.requestAnimationFrame(loop);

      // append/get elements to the DOM
      const canvas = document.getElementById('canvas');
      canvas.width = 1000; canvas.height = 1000;
      state.ctx = canvas.getContext('2d');
    }

    async function stopVideo() {
      if(state.webcam) state.webcam.stop();
      state.isStudying = false;
      state.model = undefined;
      state.webcam = undefined;
      state.ctx = undefined;
      state.maxPredictions = undefined;
    }

    async function loop() {
      state.webcam.update(); // update the webcam frame
      await predict();
      window.requestAnimationFrame(loop);
    }

    async function predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { pose, posenetOutput } = await state.model.estimatePose(state.webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await state.model.predict(posenetOutput);
      if ( prediction[0].probability.toFixed(2) > 0.5 ) {
        state.isStudying = true
      } else {
        state.isStudying = false
      }

      // finally draw the poses
      drawPose(pose);
    }

    function drawPose(pose) {
      if (state.webcam.canvas) {
        state.ctx.drawImage(state.webcam.canvas, 0, 0);
        // draw the keypoints and skeleton
        if (pose) {
          // const minPartConfidence = 0.5;
          // tmPose.drawKeypoints(pose.keypoints, minPartConfidence, state.ctx);
          // tmPose.drawSkeleton(pose.keypoints, minPartConfidence, state.ctx);

        }
      }
    }

    const mute = function () {
      state.muteVar = !state.muteVar
    }

    const video = function () {
      if (state.videoVar) {
        init()
      } else {
        stopVideo()
      }
      state.videoVar = !state.videoVar

    }

    // const onSwiper = (swiper) => {
    //     console.log(swiper);
    // }

    // const onSlideChange = () => {
    //   console.log('slide change');
    // }

    return {
      state,
      enterRoom,
      init,
      mute,
      video,
      onOpen
    }
  },


}
</script>
