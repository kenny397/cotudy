<template>
  <div class="bg-clr">
    <div class="main-container">
      <h2 style="margin-top: 100px;">스터디룸 입장준비</h2>
      <p>{{ $route.params.studyroomId + '번 방 상세 보기 페이지' }}</p>
      <div>
        <canvas id="canvas" v-bind:class="{isStudying : state.isStudying}" class="isNotStudying"/>
        <div class="white-box"></div>
      </div>
      <div style="margin-bottom:10px" id="label-container"></div>
    </div>
  </div>
  <div style="position:relative; top:-222px; left:27px;">
    <el-button class="mm">마이크</el-button>
    <el-button class="mm">비디오</el-button>
    <el-button class="mm" style="width:120px;">모션인식 도움말</el-button>
    <el-button
      @click="enterRoom(state.studyroomId)"
      plain type="success" class="mm"
      style="border-bottom-right-radius:20px;"
    >입장</el-button>
  </div>
</template>
<style scoped>
  .mm {
    height:64px;
    width:80px;
    margin-right: 30px;
  }
  .aa {
    border-bottom-right-radius:20px;  padding-top:10px;
  }
  .bg-clr {
    background-color: black;
    width: 100vw;
    height: 100vh;
    color: white;
    z-index: -2;
  }
  .block {
    display: block;
  }
  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .white-box {
    position: relative;
    top: -50px;
    z-index: -1;
    border-radius : 20px;
    background-color: white;
    height: 110px;
    width: 510px;
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

// 티쳐블머신
import * as tmPose from '@teachablemachine/pose';

export default {
  name: 'studyroom-detail',

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
      URL : 'https://teachablemachine.withgoogle.com/models/tCGfolhxR/'
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
      const size = 500;
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

    return { state, enterRoom, init }
  },


}
</script>
