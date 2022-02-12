<template>
  <el-container>
    <el-main>
      <div id="session-header">
        <!-- <h1 id="session-title">{{ state.mySessionId + '번 방 스터디룸 내부화면' }}</h1> -->
      </div>
      <el-row
        :gutter="5"
      >
        <el-col
          :xs="24" :sm="24" v-bind:md="state.headCountForGrid"
        >
          <div
            class="grid-content video-sub-container"
            :style="state.headCountForVideoWidth"
          >
            <user-video
              :head-count="state.headCountForVideoWidth"
              :stream-manager="state.publisher"
              :session="state.session"
              @click="updateMainVideoStreamManager(state.publisher)"
            />
            <div
              class="info-container"
              :style="state.headCountForInfoContainer"
            >
              <div class="nickname-area">{{state.myUserName}}</div>
              <div class="studytime-area">{{state.newStudyTime}}</div>
            </div>
          </div>
        </el-col>
        <el-col
          :xs="24" :sm="24" v-bind:md="state.headCountForGrid"
          v-for="sub in state.subscribers"
          :key="sub.stream.connection.connectionId"
        >
          <div
            class="grid-content video-sub-container"
          >
            <user-video
              :head-count="state.headCountForVideoWidth"
              :session="state.session"
              :stream-manager="sub"
              @click="updateMainVideoStreamManager(sub)"
            />
            <div
              class="info-container"
              :style="state.headCountForInfoContainer"
            >
              <div class="nickname-area">{{(sub.stream.connection.data).slice(15).slice(0, -2)}}</div>
              <div
                class="studytime-area"
                :id="sub.stream.connection.connectionId"
              ></div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-main>

  </el-container>
  <div class="studyroom-footer">
    <div class="time-area">
      <span>학습 시간 {{state.newStudyTime}}</span>
    </div>
    <div class="button-area">

      <el-button
        round
        class="gray-btn"
        @click="audioBtnClick()"
        v-bind:class="{'red-btn' : state.isAudioMuted}"
      >
        <font-awesome-icon v-if="!state.isAudioMuted" icon="microphone"/>
        <font-awesome-icon v-if="state.isAudioMuted" icon="microphone-slash"/>
        <span class="btn-desc">마이크</span>
      </el-button>
      <el-button
        round
        class="gray-btn"
        @click="videoBtnClick()"
        v-bind:class="{'red-btn' : state.isVideoMuted}"
      >
        <font-awesome-icon v-if="!state.isVideoMuted" icon="video"/>
        <font-awesome-icon v-if="state.isVideoMuted" icon="video-slash"/>
        <span class="btn-desc">비디오</span>
      </el-button>
      <el-button
        round
        class="gray-btn"
        @click="restBtnClick()"
        v-bind:class="{'red-btn' : state.isResting}">
        <font-awesome-icon v-if="!state.isResting" icon="moon" />
        <font-awesome-icon v-if="state.isResting" icon="pen"/>
        <span v-if="!state.isResting" class="btn-desc">휴식</span>
        <span v-if="state.isResting" class="btn-desc">복귀</span>
      </el-button>
      <el-button
        @click="state.closeDialogVisible = true"
        round
        class="red-btn">
        <font-awesome-icon icon="phone-slash"/>
      </el-button>
      <el-button
        round
        class="green-btn is-study-area"
        v-bind:class="{'red-btn' : !state.isStudying}">
        <font-awesome-icon v-if="state.isStudying" icon="pen"/>
        <font-awesome-icon v-if="!state.isStudying" icon="bed"/>
        <span v-if="state.isStudying" class="btn-desc">학습중</span>
        <span v-if="!state.isStudying" class="btn-desc">학습정지</span>
      </el-button>
    </div>
  </div>

  <el-dialog
    v-model="state.closeDialogVisible"
    width="300px"
    destroy-on-close
    center
  >

    <div style="text-align:center;">
      <strong>정말 퇴장하시겠습니까?</strong>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="leaveSession">예</el-button>
        <el-button
          @click="state.closeDialogVisible = false"
          >아니오</el-button
        >
      </span>
    </template>
  </el-dialog>


</template>

<style scoped>
  .el-container {
    /* background: #202124; */
    background:black;
    height: 92.5vh;
    align-items: center;
  }
  .studyroom-footer {
    background: rgb(5, 5, 5);
    height: 7.5vh
  }
  .studyroom-footer .button-area {
    text-align: center;
    line-height: 7.5vh;
  }
  @media (max-width: 850px) {
    .studyroom-footer .time-area {
      display: none;
    }
    .is-study-area {
      display: none;
    }
  }
  .studyroom-footer .time-area {
    position: absolute;
    line-height: 7.5vh;
    left: 10px;
    float:left;
    color:white;
  }

  .studyroom-footer .is-study-area {
    position: absolute;
    right:40px;
    bottom:15px;
  }
  .studyroom-footer .button-area button{
    height:40px;
    font-weight: 500;
    font-size: 17px;
    padding: 10px 25px 10px 25px;
  }

  .studyroom-footer .button-area button .btn-desc{
    margin-left : 10px;
  }

  .studyroom-footer .button-area .gray-btn{
    background: #5f6368;
    color:white;
    border: none;
  }
  .studyroom-footer .button-area .green-btn{
    background: #3AC258;
    color:white;
    border: none;
  }
  .studyroom-footer .button-area .gray-btn:hover {
    background: #7e8085;
  }
  .studyroom-footer .button-area .red-btn{
    background: #d81411;
    color:white;
    border: none;
  }
  .studyroom-footer .button-area .red-btn:hover {
    background: #ce5250;
    color:white;
    border: none;
  }
  .el-container .el-main {
    /* background:#202124; */
    background: rgb(20, 20, 20);
    width: 100vw;
    height: 92.5vh;
    margin: 0px 8vw 0px 8vw;
    display: flex;
    align-items: center;
    justify-content: center;
    --el-main-padding: 0px;
  }
  .el-container .el-main .el-row {
    justify-content: center;
  }
  .el-container .el-main .el-row:last-child {
    margin-bottom: 0;
  }
  .el-container .el-main .el-row .el-col {
    /* width:71vw; */
    /* width:42vw; */
    /* width:28vw; */
    height:auto;
    margin-bottom: 10px;
  }
  .el-container .el-main .el-row .el-col .video-sub-container {
    display: inline-block;
    /* width:70vw; */
    /* width:41vw; */
    /* width:27vw; */
    height:auto;
  }
  .el-container .el-main .el-row .el-col .grid-content video {
    height: auto;
    border-radius: 10px
  }

  .el-container .el-main .el-row .el-col .grid-content .info-container {
    /* height: 37.3vw; */
    /* margin-top: -37.3vw; */
    /* height: 22.3vw;
    margin-top: -22.3vw; */
    /* height: 14.8vw;
    margin-top: -14.8vw; */
    z-index: 100;
    position: relative;
    color:white;
    border-radius: 20px 20px 20px 20px;
  }

  .el-container .el-main .el-row .el-col .grid-content .info-container div {
    position: absolute;
    height: 2vw;
    line-height: 2vw;
    background : rgba(0, 0, 0, 0.651);
    border-radius: 10px;
    padding: 0 0.5vw 0 0.5vw;
  }
  .el-container .el-main .el-row .el-col .grid-content .info-container .studytime-area {
    right:0.5vw;
    bottom: 0.5vw;
  }
  .el-container .el-main .el-row .el-col .grid-content .info-container .nickname-area {
    left: 0.5vw;
    top: 0.5vw;
  }

</style>


<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from './components/UserVideo.vue';
import { reactive, onMounted, onUnmounted, onBeforeMount, computed } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'


// 티쳐블머신

import * as tmPose from '@teachablemachine/pose';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = 'https://' + 'i6b105.p.ssafy.io' + ':6443';
const OPENVIDU_SERVER_SECRET = 'MY_SECRET';

export default {
	name: 'StudyRoomInside',

	components: {
		UserVideo,
	},

  setup () {
    const route = useRoute()
    const store = useStore()
    const router = useRouter()
    const state = reactive({

      headCountForGrid: computed(() => {
        const headCount = state.subscribers.length
        if(headCount == 0){
          return 24
        } else if(headCount > 3) {
          return 8
        } else {
          return 12
        }
      }),
      headCountForVideoWidth: computed(() => {
        if (state.subscribers.length == 0){
          return 70
        } else if(state.subscribers.length > 3){
          return 27
        } else {
          return 41
        }
      }),

      headCountForInfoContainer: computed(() => {
        return {
          'height': state.headCountForVideoWidth * 0.540 + 'vw',
          'margin-top': state.headCountForVideoWidth * -0.540 + 'vw'
        }

      }),
      closeDialogVisible : false,
      // openvidu
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: '',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
      isAudioMuted: false,
      isVideoMuted: false,

      // 세션 종료시 clearInterval 용도
      isLeaveSession : undefined,

      // timer
      myStudyTime: 0,
      newStudyTime: computed(() => {
        const date = new Date(null);
        date.setSeconds(state.myStudyTime / 1000);
        const utc = date.toUTCString();
        return utc.substr(utc.indexOf(':') - 2, 8);
      }),
      timer: undefined,
      isTimerWorking: false,
      isResting: false,
      isStudying: false,


      // teachable machine
      isMotionTracking : false,
      model : undefined,
      webcam : undefined,
      ctx : undefined,
      maxPredictions : undefined,
      URL : 'https://teachablemachine.withgoogle.com/models/tCGfolhxR/',

    })

    // life cycles
    onMounted(() => {
      init()
    })

    onUnmounted(() => {
      leaveSession()
    })


    onBeforeMount(() => {
      state.mySessionId = route.params.studyroomId
      store.commit('root/setMenuActiveMenuName', 'home')
      if (route.params.isVideo=='true') {
        state.isVideoMuted = true
      } else{
        state.isVideoMuted= false
      }

      if (route.params.isAudio=='true') {
        state.isAudioMuted = true
      } else {
        state.isAudioMuted = false
      }
      joinSession()
    })

    // my methods
    const timerStart = function() {
      state.timer = setInterval(() => {
        state.myStudyTime += 1000;
      }, 1000);
    }

    const timerStop = function() {
      clearInterval(state.timer);
    }

    const audioBtnClick = function() {
      if(state.isAudioMuted){
        state.publisher.publishAudio(true)
        state.isAudioMuted = false
      } else {
        state.publisher.publishAudio(false)
        state.isAudioMuted = true

      }
    }

    const videoBtnClick = function () {
      if(state.isVideoMuted) {
        state.publisher.publishVideo(true)
        state.isVideoMuted = false
      } else {
        state.publisher.publishVideo(false)
        state.isVideoMuted = true
        timerStop()
      }
    }

    const restBtnClick = function() {
      if(state.isResting) {
        state.isResting = false
      } else {
        state.isResting = true
      }
    }

    // openvidu methods
    const sendMessage = function () {
      if (state.session) {
        state.session.signal({
          data: state.newStudyTime,
          to: [],
          type: 'my-chat'
        })
        .then(() => {
            console.log('Message successfully sent');
        })
        .catch(error => {
            console.error(error);
        })
      } else {
        window.clearInterval(state.isLeaveSession)
      }
    }

    const sendMessageLoop = function () {
      state.isLeaveSession = setInterval(sendMessage, 1000)
    }

    const listenMessage = function() {

			state.session.on('signal:my-chat', (event) => {
        if (event.from.connectionId !== state.publisher.stream.connection.connectionId){
          const sendedData = document.getElementById(event.from.connectionId)
          sendedData.innerHTML = event.data
        }
			});
		}


    const joinSession = function () {
			// --- Get an OpenVidu object ---

			state.OV = new OpenVidu();

			// --- Init a session ---
			state.session = state.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			state.session.on('streamCreated', ({ stream }) => {
				const subscriber = state.session.subscribe(stream);
				state.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			state.session.on('streamDestroyed', ({ stream }) => {
				const index = state.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					state.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			state.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// message send 위치
      listenMessage()

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			getToken(state.mySessionId).then(token => {
				state.session.connect(token, { clientData: state.myUserName })
					.then(() => {
						// --- Get your own camera stream with the desired properties ---

						let publisher = state.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: !state.isAudioMuted,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: !state.isVideoMuted,  	// Whether you want to start publishing with your video enabled or not
							resolution: '800x425',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						state.mainStreamManager = publisher;
						state.publisher = publisher;

						// --- Publish your stream ---

						state.session.publish(state.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			// message sending 위치
      sendMessageLoop()

			window.addEventListener('beforeunload', leaveSession)
		}

    const leaveSession = function() {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (state.session) state.session.disconnect();
      if (state.webcam) state.webcam.stop();

			state.session = undefined;
			state.mainStreamManager = undefined;
			state.publisher = undefined;
			state.subscribers = [];
			state.OV = undefined;
      state.isMotionTracking = false;
      state.model = undefined;
      state.webcam = undefined;
      state.ctx = undefined;
      state.maxPredictions = undefined;

			window.removeEventListener('beforeunload', leaveSession);
      router.push({
        name: 'home',
      })
		}

		const updateMainVideoStreamManager = function(stream) {
			if (state.mainStreamManager === stream) return;
			state.mainStreamManager = stream;
		}

    const getToken = function(mySessionId) {
			return createSession(mySessionId).then(sessionId => createToken(sessionId));
		}

    const createSession = function(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		}

    const createToken = function(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		}


    // teachable machine
    async function init() {
      const modelURL = state.URL + 'model.json';
      const metadataURL = state.URL + 'metadata.json';

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // Note: the pose library adds a tmPose object to your window (window.tmPose)
      state.model = Object.freeze(await tmPose.load(modelURL, metadataURL))
      state.maxPredictions = state.model.getTotalClasses();

      // Convenience function to setup a webcam
      const size = 500;
      const flip = true; // whether to flip the webcam
      state.webcam = new tmPose.Webcam(size, size, flip); // width, height, flip
      await state.webcam.setup(); // request access to the webcam
      await state.webcam.play();
      window.requestAnimationFrame(loop);

      // append/get elements to the DOM
      const canvas = state.webcam.canvas;
      canvas.width = size; canvas.height = size;
      state.ctx = canvas.getContext('2d');

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
      if ( prediction[0].probability.toFixed(2) > 0.5) {
        state.isMotionTracking = true
      } else {
        state.isMotionTracking = false
      }

      if (state.isMotionTracking && !state.isVideoMuted && !state.isResting) {
        state.isStudying = true
        if(!state.isTimerWorking){
          timerStart()
          state.isTimerWorking = true
        }
      } else {
        state.isStudying = false
        state.isTimerWorking = false
        timerStop()
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

    return {state, updateMainVideoStreamManager, leaveSession, joinSession, timerStart, timerStop, audioBtnClick, videoBtnClick, restBtnClick}
  }

}
</script>

