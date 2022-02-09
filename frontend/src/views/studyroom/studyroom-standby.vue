<template>
  <div class="bg-clr">
    <div class="main-container">
      <h2 class="tw" style="margin-bottom:0px;">스터디룸 입장준비</h2>
      <p class="tw">{{ $route.params.studyroomId + '번 방 상세 보기 페이지' }}</p>
      <div>
        <canvas style="width:68vh; height:auto;" id="canvas" v-bind:class="{isStudying : state.isStudying}" class="isNotStudying"/>
        <div class="white-box" style="margin-top:-5px;">
          <div style="display:flex; justify-content:space-between; align-items:center;">
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
            <el-button @click="state.dialogVisible = true" class="btn-stl" type="text">모션인식 도움말</el-button>
            <el-dialog
              v-model="state.dialogVisible"
              width="50%"
            >
              <!-- <swiper
                :slides-per-view="3"
                :space-between="50"
                @swiper="onSwiper"
                @slideChange="onSlideChange"
              >
                <swiper-slide>Slide 1</swiper-slide>
                <swiper-slide>Slide 2</swiper-slide>
                <swiper-slide>Slide 3</swiper-slide>
                ...
              </swiper> -->
              <el-carousel indicator-position="outside">
                <el-carousel-item v-for="tutorial in state.tutorials" :key="tutorial.id">
                  <img style="width:500px; height:300px;" :src="tutorial.imageUrl" alt="tutorial">
                </el-carousel-item>
              </el-carousel>

              <!-- <template #footer>
                <span class="dialog-footer">
                  <el-button @click="state.dialogVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="state.dialogVisible = false"
                    >Confirm</el-button
                  >
                </span>
              </template> -->
            </el-dialog>
            <el-button
              @click="enterRoom(state.studyroomId)"
              plain type="success"
              style="height:80px; width:100px; border-bottom-right-radius:20px;"
            >입장</el-button>
          </div>
        </div>
      </div>
      <div v-show="state.hidden" id="label-container"></div>
    </div>
  </div>
</template>
<style scoped>
  .bg-clr {
    background-color: black;
    width: 100vw;
    height: 100vh;
  }
  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .tw {
    color: white;
  }
  .btn-stl {
    border: 0px;
    padding: 0px;
    color: black;
  }
  .btn-stl:hover {
    color: rgba(58, 194, 88, 1);
  }
  .white-box {
    border-bottom-right-radius:20px;
    border-bottom-left-radius:20px;
    background-color: white;
    height: 80px;
    width: 68vh + 6;
  }
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
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'

//swiper
// import { Swiper, SwiperSlide } from 'swiper/vue';
// import 'swiper/css'; // swiper styles

// 티쳐블머신
import * as tmPose from '@teachablemachine/pose';

export default {
  name: 'studyroom-detail',
  // components: {
  //   Swiper,
  //   SwiperSlide,
  // },

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
      hidden: false,
      muteVar: false,
      videoVar: false,
      dialogVisible: false,
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
          studyroomId: id
        }
      })
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
      const size = 700;
      const flip = true; // whether to flip the webcam
      state.webcam = new tmPose.Webcam(size, size, flip); // width, height, flip
      await state.webcam.setup(); // request access to the webcam
      await state.webcam.play();
      window.requestAnimationFrame(loop);

      // append/get elements to the DOM
      const canvas = document.getElementById('canvas');
      canvas.width = size; canvas.height = size;
      state.ctx = canvas.getContext('2d');
      state.labelContainer = document.getElementById('label-container');
      for (let i = 0; i < state.maxPredictions; i++) { // and class labels
          state.labelContainer.appendChild(document.createElement('div'));
      }
    }

    async function stopVideo() {
      if(state.webcam) state.webcam.stop();
      state.isStudying = false;
      state.model = undefined;
      state.webcam = undefined;
      state.ctx = undefined;
      state.labelContainer = undefined;
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
      for (let i = 0; i < state.maxPredictions; i++) {
        const classPrediction =
          prediction[i].className + ': ' + prediction[i].probability.toFixed(2);
          state.labelContainer.childNodes[i].innerHTML = classPrediction;
      }


      // finally draw the poses
      drawPose(pose);
    }

    function drawPose(pose) {
      if (state.webcam.canvas) {
        state.ctx.drawImage(state.webcam.canvas, 0, 0);
        // draw the keypoints and skeleton
        if (pose) {
          const minPartConfidence = 0.5;
          tmPose.drawKeypoints(pose.keypoints, minPartConfidence, state.ctx);
          tmPose.drawSkeleton(pose.keypoints, minPartConfidence, state.ctx);

        }
      }
    }

    const mute = function () {
      state.muteVar = !state.muteVar
    }

    const video = function () {
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
      // onSwiper,
      // onSlideChange
    }
  },


}
</script>
